#!/usr/bin/env python3
"""Send MC console commands via local Wings API and run redstone halt tests."""
from __future__ import annotations

import json
import re
import ssl
import sys
import time
import urllib.error
import urllib.request
from pathlib import Path

SERVER_UUID = "575ac3f3-f74c-47a1-9a6d-33f1b78dd75d"
WINGS_URL = f"https://127.0.0.1:8443/api/servers/{SERVER_UUID}/commands"
CONFIG_PATH = Path("/etc/pterodactyl/config.yml")

# Test pad in overworld (player-loaded audit site)
X, Y, Z = -600, 66, -330


def load_token() -> str:
    text = CONFIG_PATH.read_text()
    match = re.search(r'^token:\s*"?([^"\n]+)"?\s*$', text, re.M)
    if not match:
        raise RuntimeError("Could not read Wings token from config.yml")
    return match.group(1).strip()


def mc(*commands: str, delay: float = 0.35) -> None:
    token = load_token()
    ctx = ssl.create_default_context()
    ctx.check_hostname = False
    ctx.verify_mode = ssl.CERT_NONE
    payload = json.dumps({"commands": list(commands)}).encode()
    req = urllib.request.Request(WINGS_URL, data=payload, method="POST")
    req.add_header("Authorization", f"Bearer {token}")
    req.add_header("Content-Type", "application/json")
    req.add_header("Host", "panel.neomechanical.com")
    with urllib.request.urlopen(req, context=ctx, timeout=15) as resp:
        if resp.status not in (200, 204):
            raise RuntimeError(f"Wings returned {resp.status}")
    if delay:
        time.sleep(delay)


def say(msg: str) -> None:
    mc(f"say [NP-TEST] {msg}")


def build_test_pad() -> None:
    say("Building redstone test structures...")
    mc(
        f"execute in minecraft:overworld run forceload add {X//16} {Z//16}",
        f"execute in minecraft:overworld run fill {X-2} {Y-1} {Z-2} {X+8} {Y-1} {Z+8} stone",
        f"execute in minecraft:overworld run fill {X-2} {Y} {Z-2} {X+8} {Y+3} {Z+8} air",
        # Static lamp (always powered)
        f"execute in minecraft:overworld run setblock {X} {Y} {Z} redstone_block",
        f"execute in minecraft:overworld run setblock {X} {Y} {Z+1} redstone_wire",
        f"execute in minecraft:overworld run setblock {X} {Y} {Z+2} redstone_lamp",
        # Repeater clock -> lamp
        f"execute in minecraft:overworld run setblock {X+3} {Y} {Z} stone",
        f"execute in minecraft:overworld run setblock {X+3} {Y} {Z+1} redstone_wire",
        f"execute in minecraft:overworld run setblock {X+4} {Y} {Z+1} repeater[facing=east,delay=2]",
        f"execute in minecraft:overworld run setblock {X+5} {Y} {Z+1} redstone_wire",
        f"execute in minecraft:overworld run setblock {X+5} {Y} {Z} redstone_wire",
        f"execute in minecraft:overworld run setblock {X+4} {Y} {Z} repeater[facing=west,delay=2]",
        f"execute in minecraft:overworld run setblock {X+3} {Y} {Z} redstone_torch[lit=true]",
        f"execute in minecraft:overworld run setblock {X+5} {Y} {Z+2} redstone_lamp",
        # Sticky piston door segment
        f"execute in minecraft:overworld run setblock {X+7} {Y} {Z} sticky_piston[facing=north]",
        f"execute in minecraft:overworld run setblock {X+7} {Y+1} {Z} sand",
        f"execute in minecraft:overworld run setblock {X+7} {Y} {Z+1} redstone_block",
        delay=1.0,
    )


def probe_lamps() -> None:
    mc(
        f"execute in minecraft:overworld run data get block {X} {Y} {Z+2} lit",
        f"execute in minecraft:overworld run data get block {X+5} {Y} {Z+2} lit",
        f"execute in minecraft:overworld run data get block {X+7} {Y} {Z} extended",
        delay=0.5,
    )


def teleport_players() -> None:
    mc(
        "execute as @a[gamemode=!spectator] at @s run spawnpoint @s "
        f"minecraft:overworld {X+4} {Y} {Z+4}",
        f"tp @a {X+4} {Y+1} {Z+4}",
        delay=0.5,
    )


def main() -> int:
    action = sys.argv[1] if len(sys.argv) > 1 else "full"

    if action == "build":
        build_test_pad()
        teleport_players()
        probe_lamps()
        return 0

    if action == "halt-on":
        say("Engaging manual halt")
        mc("np halt", delay=1.0)
        probe_lamps()
        return 0

    if action == "halt-off":
        say("Releasing manual halt")
        mc("np halt", delay=2.0)
        probe_lamps()
        return 0

    if action == "probe":
        probe_lamps()
        return 0

    # full sequence
    build_test_pad()
    teleport_players()
    say("Baseline probe before halt")
    probe_lamps()
    time.sleep(2)
    say("HALT ON")
    mc("np halt", delay=3.0)
    probe_lamps()
    # Attempt extra redstone activity while halted (should be suppressed + cached)
    mc(
        f"execute in minecraft:overworld run setblock {X+2} {Y} {Z+4} lever[face=floor,facing=north,powered=true]",
        f"execute in minecraft:overworld run setblock {X+2} {Y} {Z+5} redstone_lamp",
        delay=2.0,
    )
    probe_lamps()
    say("HALT OFF / restore")
    mc("np halt", delay=5.0)
    probe_lamps()
    say("Test sequence complete — check lamps and piston near 1200 70 1200")
    return 0


if __name__ == "__main__":
    raise SystemExit(main())

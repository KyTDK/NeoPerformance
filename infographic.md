# NeoPerformance — one-page infographic

```text
┌─────────────────────────────────────────────────────────────────────────┐
│  NEO PERFORMANCE                                                        │
│  Lag insight · Halt & recovery · SmartClear · Chunks · SmartNotify      │
│  Insights · Reports · Email · Optional Spark / DiscordSRV               │
│  Free chat moderation (Neomechanical API)                               │
└─────────────────────────────────────────────────────────────────────────┘
```

---

## Description

NeoPerformance is a performance management utility. Tools such as cluster detection (find large groups of entities), as well as sorting chunks by entities allows server administrators to find, fix and prevent lag sources. Additionally, it is able to prevent lag machines, as well as prevent fatal server crashes.

---

## PREFACE:

This plugin isn't designed as an 'anti-lag plugin'—there's no magic solution for that. NeoPerformance empowers administrators by providing tools such as server halting and entity locating to optimize server performance. However, it's crucial to note that if your server uses suboptimal hardware, NeoPerformance's impact may be limited. Hardware limitations are beyond the plugin's control.

---

## FEATURES:

Prevent server lag by preventing many forms of lag machines and optimizing standard gameplay.
Shows recommendations for server configuration and automatically applies recommendation within game.
Prevent server crashes by halting the server at set TPS.
SmartClear - Remove large clusters of entities, this is better than the standard entity removal method that most lag prevention plugins use.
Chunks - This shows the top chunks that have the most entities.
SmartNotify - Notifies admins when there are too many entities that are causing lag.
Send emails when the server is lagging.
Highly customizable (Check YML file at the bottom of the page)
Automatically finds the source of lag and performs a server cleanup.

---

## Free chat moderation (new)

Optional **free** chat moderation hooks your server into the Neomechanical moderation API (configure `chat_moderation` in `performanceConfig.yml`). When a player sends chat, NeoPerformance can classify the message and run a **stackable action list** if it is flagged.

| Area | What you get |
|------|----------------|
| **Detection** | Async chat checks against configurable categories (e.g. sexual, hate). |
| **Actions (in order)** | `CLEAR_CHAT`, `MUTE`, `KICK`, `BAN`, `TIMEOUT`, `GIVE_ROLE`, `TAKE_ROLE`, `TEMP_ROLE`, `COMMAND` — each step can use placeholders `%PLAYER%`, `%UUID%`, `%ROLE%`, `%DURATION%`, `%REASON%`. |
| **Defaults** | Example defaults include broadcast blank lines for chat clear, and console commands for mute / timeout / LuckPerms-style roles unless you override the command string per action. |
| **Safety valve** | Players with `neoperformance.chatmoderation.bypass` skip moderation. |
| **Tuning** | Enable/disable module, API key, endpoint URL, connect/read timeouts, category toggles, and the full action pipeline live in YAML. |

---

## OPTIONAL REQUIREMENTS:

Spark - If you have spark installed on your server, NeoPerformance will hook into it and add extra metrics. Download it here
DiscordSRV - If DiscordSRV is installed and configured, NeoPerformance will notify your channel if the server is halted. Download it here

---

## COMMANDS AND PERMISSIONS:

/Neoperformance or /np (neoperformance.admin) - Shows server status for admins
/np help (neoperformance.help) - Shows all the commands their use
/np halt (neoperformance.halt) - Manually halt the server
/np chunks <world> (neoperformance.chunks) - Show the chunks with the most entities. Type /np chunks to show the top 10 for all worlds and /np chunks <world> for the top ten of the specified world.
/np reload (neoperformance.reload) - Reload the plugins config file
/np bypass <player> (neoperformance.bypass or use neoperformance.bypass.auto) - Bypass server halt
/np smartclear <flags> <neoperformance.smartclear> Destroy entity clusters
For SmartNotify, an integrated notify system with chunks and clusters, either have op or neoperformance.smartnotify
/np config (neoperformance.config) - Open the in-game config editor
/np report (neoperformance.report) - Generate a report of your severs overall performance.
/np insight (neoperformance.insight) - Show recommendations for server configuration, and automatically apply the recommendation to enhance server performance.
/np insight fix (neoperformance.insight.fix) [category] [elementName] - Apply recommendation for specified
/np insight fix all (neoperformance.insight) - Apply recommendation for all.
/np insight sources (neoperformance.insight.sources) - Show sources used to determine recommended values

**Note (this repository):** The registered subcommand is `/np insights` (plural), including `/np insights fix …` and `/np insights sources …`. The permission nodes you listed above still apply.

---

## LAG PREVENTION:

Prevents too many explosions
Prevents too many mob spawns
Prevents lag machines, such as too many minecarts.

---

## CRASH PREVENTION:

If the plugin fails to prevent lag, the server will enable halt mode and stop the following activities until the server was reached a stable tps:

Teleportation
Players moving too fast (speed hacks, etc)
Entity explosions
Redstone (Redstone activity is cached and then restored once the server reaches a stable tps, meaning redstone contraptions won't break)
Chunk loading
Entity spawn events (and items from being dropped)
Command blocks executing commands
Block being broken (because items can't be dropped)
Projectiles
Entity targetting
Block physics
Players from joining (extreme lag can be caused by too many players, the plugin stops players from joining until the server is at a playable state). Again, all of these are optional, this one is off by default.

---

## YML FILE

Find here https://www.spigotmc.org/resources/neoperformance.103183/

---

## CONTACT ME:

admin@neomechanical.com
or on the discord server: https://discord.com/invite/PCsEQm7yKw (Fixed)

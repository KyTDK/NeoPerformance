# NeoMechanical Plugin Release Workflow

This is the canonical release order for the dependency chain:

1. `NeoUtils`
2. `NeoConfig`
3. `NeoPerformance`

## Why This Order

- `NeoConfig` depends on `NeoUtils`.
- `NeoPerformance` depends on `NeoConfig`.
- Releasing in a different order can produce broken artifacts or stale runtime behavior.

## CI/CD Workflows Added

Each repository now has:

- `CI` workflow: compile/package validation on pushes and PRs.
- `Publish` workflow: deploy to Nexus on manual dispatch and version tags.

`NeoPerformance` also has:

- `Deploy To Pterodactyl` workflow: build, upload, restart target server, and print logs.

## Required GitHub Secrets

### For publishing in all 3 repos

- `NEXUS_USERNAME`
- `NEXUS_PASSWORD`

These credentials are used for:

- `neomechanical-releases`
- `neomechanical-snapshots`

### For NeoPerformance deployment workflow

- `PTERO_SSH_HOST`
- `PTERO_SSH_USER`
- `PTERO_SSH_PRIVATE_KEY`

## Recommended Release Procedure

### 1) Release NeoUtils

- Create a tag (for example `v2.0.1`) in `NeoUtils`.
- Wait for `Publish` to finish.

### 2) Bump NeoConfig dependency

- Update `NeoConfig/api/pom.xml` dependency `com.neomechanical:NeoUtils` to the newly released version.
- Tag release in `NeoConfig` (for example `v1.5.9`).
- Wait for `Publish` to finish.

### 3) Bump NeoPerformance dependency

- Update `NeoPerformance/pom.xml` dependency `com.neomechanical:NeoConfig` to the newly released version.
- Tag release in `NeoPerformance` (for example `v1.15.6`).
- Wait for `Publish` to finish.

### 4) Deploy NeoPerformance to server

- Run `Deploy To Pterodactyl` in `NeoPerformance`.
- Input:
  - `server_uuid` = your target Pterodactyl server UUID
  - `plugin_filename` = `NeoPerformance.jar` (default)

The deployment workflow preserves a rollback copy as:

- `plugins/NeoPerformance.jar.prev`

## Rollback

If needed, on the server:

1. Stop/restart container safely.
2. Restore `plugins/NeoPerformance.jar.prev` to `plugins/NeoPerformance.jar`.
3. Restart server container.


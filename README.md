# BOSS Plugin - NanoRx

A **BOSS Console** panel plugin that embeds the **NanoRx** precision-oncology +
DNA-nanobot dashboard (https://nanorx-discovery.web.app) in a right-side sidebar,
using the host's embedded browser. Built to the pattern in the repo's
[`CREATING_PLUGINS.md`](../CREATING_PLUGINS.md).

## What it does

Adds a **NanoRx** panel to BOSS that renders the live discovery dashboard:
mutation → drug-binding → resistance → mutant structure → DNA-nanobot simulation →
prior-auth readiness. This is the same "embed a RISA web app as a chat/panel plugin"
wedge described in the business case (bolt onto BOSS Console).

## Layout

```
boss-plugin-nanorx/
├── build.gradle.kts                      # build config + version (single source of truth)
├── settings.gradle.kts
├── .github/workflows/build.yml           # CI: build JAR + publish to BOSS store
└── src/main/
    ├── kotlin/ai/rever/boss/plugin/dynamic/nanorx/
    │   ├── NanoRxPlugin.kt               # entry point (DynamicPlugin)
    │   ├── NanoRxInfo.kt                 # panel identity (id, name, icon, dock)
    │   ├── NanoRxComponent.kt            # wires panel → Composable
    │   └── NanoRxContent.kt              # the @Composable UI (embedded browser)
    └── resources/META-INF/boss-plugin/plugin.json   # manifest
```

## Key identifiers

| Field | Value |
|---|---|
| Plugin id | `ai.rever.boss.plugin.dynamic.nanorx` |
| Main class | `ai.rever.boss.plugin.dynamic.nanorx.NanoRxPlugin` |
| Type | `panel` (right sidebar, top) |
| Embedded URL | `https://nanorx-discovery.web.app` |
| API version | `1.0.20` · minBoss `8.16.30` |

## Build

The plugin compiles against `boss-plugin-api`, so build that first (it must sit at
`../boss-plugin-api` relative to this folder, alongside the other `boss-plugins`).

```bash
# 1. Build the API the plugin compiles against (once, or when the API changes)
cd ../boss-plugin-api
./gradlew buildPluginJar          # → build/libs/boss-plugin-api-1.0.20.jar

# 2. Build this plugin
cd ../boss-plugin-nanorx
./gradlew buildPluginJar          # → build/libs/boss-plugin-nanorx-1.0.0.jar
```

## Install & test locally

```bash
cp build/libs/boss-plugin-nanorx-*.jar ~/.boss/plugins/
```

Restart BOSS (or reload via the in-app **Plugin Manager**). The **NanoRx** panel
appears at the top of the right sidebar.

## Publish

Pushing to `main` triggers `.github/workflows/build.yml`, which builds the JAR,
cuts a GitHub release, and publishes to the **BOSS Plugin Store**. Requires the
`BOSS_STORE_PLUGIN_PUBLISH_KEY` repo secret. Bump `version` in `build.gradle.kts`
before each release (it's the single source of truth; `plugin.json` is synced at
build time).

## Notes

- The host's `browserService` may be `null` (disabled/unavailable) - the UI handles
  that with a graceful fallback and never crashes.
- The embedded browser is created once and disposed when the panel closes.
- To add a back/forward/reload toolbar, copy the pattern from `fluck-chatgpt`'s
  `FluckContent.kt` (`BrowserHandle` exposes `goBack()`, `reload()`, listeners, …).

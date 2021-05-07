![](https://raw.githubusercontent.com/Maddin-M/MultiWeather/master/icon.png)
MultiWeather ![](https://tokei.rs/b1/github/Maddin-M/MultiWeather?category=code) ![](https://img.shields.io/bstats/servers/11077) ![](https://img.shields.io/spiget/downloads/91452)
=================

Minecraft Server plugin for world specific weather utilities

A simple Plugin to set, get, lock and unlock weather per world.

Download
---

[Download latest release here](https://www.spigotmc.org/resources/multiweather.91452/)

Usage
---

`/weather get {world/all}`  
`/weather clear {world/all}`  
`/weather rain {world/all}`  
`/weather thunder {world/all}`  
`/weather lock {world/all}`  
`/weather unlock {world/all}`

- The world parameter is always optional and defaults to the world the player is currently in.
- Locked worlds will **not** be affected by `/weather` commands

Permissions
---

`multiweather.admin` for all commands (default for ops)

Other
---

- Tested on [PaperMC](https://papermc.io/downloads)
- Compatible with [Java 11+](https://adoptopenjdk.net/)
- [bStats](https://bstats.org/plugin/bukkit/MultiWeather/11077)
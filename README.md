MultiWeather ![](https://tokei.rs/b1/github/Maddin-M/MultiWeather?category=code) ![](https://img.shields.io/bstats/servers/11077) ![](https://img.shields.io/spiget/downloads/90642)
=================

Minecraft Server plugin for world specific weather utilities

A simple Plugin to set, get, lock and unlock weather per world. Because when using Multiverse, the default `/time` command affects all worlds.

Download
---

[Download latest release here](https://www.spigotmc.org/resources/multitime.90642/)

Usage
---

`/weather get {world/all}`  
`/weather clear {world/all}`  
`/weather rain {world/all}`  
`/weather thunder {world/all}`  
`/weather lock {world/all}`  
`/weather unlock {world/all}`

- The world parameter is always optional and defaults to the world the player is currently in.
- Locked worlds will still be affected by `/weather` commands

Permissions
---

`multiweather.admin` for all commands (default for ops)

Other
---

- Tested on [PaperMC](https://papermc.io/downloads)
- [bStats](https://bstats.org/plugin/bukkit/MultiWeather/11077)
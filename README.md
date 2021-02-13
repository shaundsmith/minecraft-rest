# minecraft-rest
> Minecraft server mod for providing a REST-like interface for Minecraft servers.

## Overview
Minecraft server-side mod which provides REST-like endpoints for interacting with a Minecraft server.

Current endpoints include:
 * Retrieving the list of mods and their versions installed on a server
 * Retrieving information about players online on a server
 * Retrieving information about the server configuration

## Installation
1. Download the jar file from [CurseForge](http://www.curseforge.com/minecraft/modpacks).
2. Copy the jar file to your server's `mod` directory.

## Usage
Navigate to `http://*your-minecraft-server*:8080/static/redoc.html` for a complete list of endpoints supported by your installed version of minecraft-rest.

A generic list of the endpoints can be found at [https://shaundsmith.dev/static/docs/minecraft-rest/redoc.html](https://shaundsmith.dev/static/docs/minecraft-rest/redoc.html).

_Note, this version may not contain all endpoints for your installed version of minecraft-rest._

## Future Plans
* Endpoints for interacting with specific mods
* Write/POST endpoints for the Minecraft Server
* Suggestions are welcome by raising a feature suggestion or by raising a pull request.

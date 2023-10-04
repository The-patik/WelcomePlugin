# WelcomePlugin
### Updated to 1.20.2

This plugin gives you the option to display a Minecraft credits page to new players, which you can customize to your liking and display information about the server, for example.

### SUPPORTING PLACEHOLDERS!!!

## Features:
* You can show credits to player
* You can show own title message with placeholders
* You can show welcome text - when player joins first time and every time player joins again
* The plugin is counting player time spent on server
* The plugin is counting player joins, played time and messages sent

## Planned:
* Adding more placeholders for played time - showing only days, or hours, or minutes
* Adding custom join message for every player
* Adding MySQL support
* Adding custom join effects

# Commands
* **/welcome help** - shows help page
* **/welcome playedtime** - shows time spent on server
* **/welcome playerjoins** - shows count of player joins
* **/welcome playerjoins <playerName>** - shows count of joins of specified player
* **/welcome showcreditsto** - shows to who now showing credits and to who you can show credits
* **/welcome showcreditsto <who>** - changing config setting show-credits, don't forget to specify <who> argument - newcomers or everyone or nobody
* **/welcome sentmessages** - shows how many messages you sent
* **/welcome update** - check if update available
* **/welcome version** - check current version of plugin

# Permissions
* **welcomeplugin.help** - allows run help command
* **welcomeplugin.playedtime** - allows to run playedtime command
* **welcomeplugin.playerjoins** - allows to run playerjoins command
* **welcomeplugin.showcreditsto** - allows to run command /welcome showcreditsto
* **welcomeplugin.sentmessages** - allows to run /welcome sentmessages
* **welcomeplugin.reloadconfig** - allows to run /welcome reloadconfig
* **welcomeplugin.update** - allows run update command
* **welcomeplugin.version** - allows run version command
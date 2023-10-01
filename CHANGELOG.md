# Changelog

## 0.6-beta
* **Change credits to own text**
* Added showcreditsto option - nobody
* Added better Played Time showing - Days, hours, minutes, seconds
* Added new plugin placeholders:
  * Added placeholder for PlayTime ***%WelcomePlugin_played_time%***
  * Added placeholder for PlayerJoins ***%WelcomePlugin_player_joins%***
  * Added placeholder for PlayerJoins of player ***%WelcomePlugin_player_< playerName>_joins%***
* New commands:
  * Added command **/welcome playerjoins** - Shows count of joins of executing player
  * Added command **/welcome playerjoins *< who>*** - Shows count of joins of specified player
* Added tabcomplete for command showcreditsto
* Added tabcomplete for command playerjoins
* Added ReloadHandler - kick all players after /reload command executed to avoid problems
* Repaired PlayerID not autoincrement integer

## 0.5-beta
* **Added SQLite database**
* Counting players play time!
* Going beta test and upload to spigot
* Added counter of how many times player joined
* Added command to show config to who now showing credits (showing on /welcome showcreditsto)

## 0.4-alpha
* Added CommandManager
* New commands added
    * /welcome help - Shows list of commands
    * /welcome update - Checks for update
    * /welcome version - Shows installed version of plugin
    * /welcome showcreditsto < who> - Change option to who show credits (Now only for everyone or newcomers)
* Added option in config to display credits(or whatever you want) to newcomers
* Added permissions to commands
* Added PlaceholdersAPI hook - **NOW SUPPORTING PLACEHOLDERS!**
* Changes made to config file - **Backup and delete it!**

## 0.3-alpha
* Added onJoinEvent - when player joins first time, show credits
* Cleared code from junk
* Started to work on display message when player joins

## 0.2-alpha
* Added ProtocolLib dependency
* Added config
* Started to work on onJoinEvent

## 0.1-alpha
* Hello World!
* Started to write code
* Added versioning

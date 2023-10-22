# Page with commands

## /welcome help
> ### Permissions
> * **welcomeplugin.help** - allows usage of command
>
> ### Info
> In the future, it will show exact help for players permission group and will be split into more pages.      

## /welcome playedtime
> ### Permissions
> * **welcomeplugin.playedtime** - allows usage of command
>
> ### Info
> This command shows players played time on server. This information is stored in SQLite or MySQL database and for now
 it is updated once per second. In future there will be option to change it due to server overloading.    

## /welcome playerjoins <playerName>
> ### Usage
> * **/welcome playerjoins** - Shows joins count of executing player
> * **/welcome playerjoins <playerName>** - Shows joins count of specified player
> 
> ### Permissions
> * **welcomeplugin.playerjoins** - allows usage of command
> * **welcomeplugin.playerjoins.others** - In 1.0.1 - allows showing joins of other players
> 
> ### Info
> This command shows count of player joins. In 1.0.1 the permissions will be split like is written above.
 For now only works the first permission.      

## /welcome reloadconfig
> ### Permissions
> * **welcomeplugin.reloadconfig** - allows usage of command
> 
> ### Info
> **This command is for admins.**
> 
> It allows to reload config without server restart.      

## /welcome sentmessages
> ### Permissions
> * **welcomeplugin.sentmessages** - allows usage of command
> 
> ### Info
> This command shows count of player sent messages. In the future, there will be option to show
 sent messages of other players. For now, it only works for executing player.     

## /welcome showcreditsto <toWho>
> ### Usage
> * **/welcome showcreditsto** - shows option set in config
> * **/welcome showcreditsto <toWho>** - change option to who show credits - **newcomers** or **everyone**
 or **nobody**
> 
> ### Permissions
> * **welcomeplugin.showcreditsto** - allows usage of command
>
> ### Info
> **This command is for admins.** 
> 
> This command allows to change option show-credits in config without need of restarting or editing it.
 Now you can choose between **newcomers** - this will show credits only to players who firstly joins the server,
 **everyone** - will show credits to everybody who connects the server and **nobody** - to show credits to nobody.
> 
> **nobody** will be removed in 1.0.1 because there is a new option to turn off showing credits.      

## /welcome update
> ### Permissions
> * **welcomeplugin.update** - allows usage of command
> 
> ### Info
> **This command is for admins.**
> 
> This command check for updates.      

## /welcome version
> ### Permissions
> * **welcomeplugin.version** - allows usage of command
> 
> ### Info
> **This command is for admins.**
> 
> This command shows the plugin version.
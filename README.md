# XToolz
A simple way to execute complex commands.

## What is "XToolz"
XToolz is a simple-to-use interface plugin for minecraft Java Edition Servers. It has a few useful commands as of this release, and also has some funny ones. You can easily add
XToolz to your minecraft server using the following (if you have a Spigot/Bukkit server):

First, locate your server's folder, then do go to the folder `plugins`. From here, copy and paste the jar file in the `release` section of this respiratory. Then, you're done!
The only thing that you need to do now is to run the server!

## What are the commands included in XToolz?
Here is a listing of commands, with descriptions (sorry, but as of this release, `plugin.yml` has no defenitions):

`/gp`:
This command lists the players in a server, as well as if they are online.

`/taa`:
This command is highly experimental and will only be availiable in this version. It gives the player it is used by the permission `player.admin`, allowing them to execute commands.
as of the next release, this will be depricated.

`/giveadmin <player>`:
This command gives the permission `player.admin` to a specific player, and also has a safety feature - if you don't posess the permission `player.admin` already, you can't execute
this command.

`/amsg <message>`:
Sends an admin message to all players.

`/warn <player> <reason>`:
This command is highly useful if you need to warn a player for breaking the rules. This command can only be issued by players with the `player.admin` permission.

`/elevate <velocity>`:
It's not really that hard to understand - it shoots a player up into the air at a certain velocity. This can only be executed by moderators (`player.admin`).

`/gcube <cxheight>`:
This command is pretty useless - it generates a cube `cxheight` in the X, Y and Z directions made of cobblestone. Can only be executed by moderators.

`/egcube <cxheight>`:
Similar to `/gcube`, `/egcube` erases a cube in the X, Y and Z directions. Also can only be executed by moderators.

`/gsh`:
This command is highly experimental, and creates a GUI onscreen. So far, this command does nothing.

`/announce <colour(0-6)> <timeout> <message>`
The `/announce` command is relatively easy to use. Set a colour, a timeout (in ticks) and a message to display, and the command handles all the rest. It simply creates a
bossbar with an announcement written on it. The bossbar's health or `progress` is used to show how much time is left before it disappears. This command can only be issued by
moderators.

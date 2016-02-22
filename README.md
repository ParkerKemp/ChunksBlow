# ChunksBlow
ChunksBlow is a small plugin which allows chunks to be marked for deletion on Spinalcraft. When unexpected server crashes occur, world data can become corrupted. Often when this happens, the most effective solution is deleting the corrupt data and allowing it to fill in from the terrain generator again.

There are technical hurdles that prevent chunks from being deleted while a server is running, so ChunksBlow aims to solve the problem by recording chunks in a database where they can later be deleted by its stand-alone partner program, [BlowChunks](http://github.com/ParkerKemp/Blowchunks), while the server is shut down.

This plugin is part of the Spinalcraft Suite, and was created along with an early iteration of [Spinalpack](http://github.com/ParkerKemp/Spinalpack). As a result, much of the functionality described here was actually implemented in the Spinalpack plugin, and merely invoked from this plugin.

-

####Spinalcraft Suite
The Spinalcraft Suite is a collection of Minecraft plugins developed primarily for operation on the [Spinalcraft server](http://reddit.com/r/spinalcraft).

Other projects in this collection:

[Spinalpack](https://github.com/ParkerKemp/Spinalpack)<br>
[Slipdisk](https://github.com/ParkerKemp/Slipdisk)<br>
[UsernameHistory](https://github.com/ParkerKemp/UsernameHistory)<br>
[StopStart](https://github.com/ParkerKemp/Stopstart)<br>
[Spinalchat](https://github.com/ParkerKemp/Spinalchat)<br>
[Spinalvote](https://github.com/ParkerKemp/Spinalvote)<br>
[Spawnalcraft](https://github.com/ParkerKemp/Spawnalcraft)<br>
[Registrar](https://github.com/ParkerKemp/Registrar)


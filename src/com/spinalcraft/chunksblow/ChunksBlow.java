package com.spinalcraft.chunksblow;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import com.spinalcraft.spinalpack.Co;
import com.spinalcraft.spinalpack.Spinalpack;

public class ChunksBlow extends JavaPlugin{
	
	ConsoleCommandSender console;
	
	@Override
	public void onEnable(){
		console = Bukkit.getConsoleSender();
		
		console.sendMessage(Spinalpack.code(Co.BLUE) + "ChunksBlow online!");
		Spinalpack.createChunkTable();
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("chunk")){
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("list")){
					//sendChunkList(sender);
					return true;
				}
				
				if(args[0].equalsIgnoreCase("report")){
					if(sender instanceof Player){
						Player player = (Player)sender;
						console.sendMessage(Spinalpack.code(Co.GOLD) + "Player coords: (" + player.getLocation().getX() + ", " + player.getLocation().getZ() + ")");
						Chunk chunk = player.getLocation().getChunk();
						
						switch(Spinalpack.insertReportedChunk(player.getName(), chunk.getWorld().getName(), chunk.getX(), chunk.getZ())){
						case 0:
							player.sendMessage(Spinalpack.code(Co.RED) + "This chunk has already been reported!");
							break;
						case 1:
							player.sendMessage(Spinalpack.code(Co.GREEN) + "Chunk reported!");
							break;
						case 2:
							player.sendMessage(Spinalpack.code(Co.RED) + "Database error! Let Parker know!");
							break;
						}
					}
					return true;
				}
				
				if(args[0].equalsIgnoreCase("unreport")){
					if(sender instanceof Player){
						Player player = (Player)sender;
						
						Chunk chunk = player.getLocation().getChunk();
						
						switch(Spinalpack.deleteReportedChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ())){
						case 0:
							player.sendMessage(Spinalpack.code(Co.RED) + "This chunk was never reported!");
							break;
						case 1:
							player.sendMessage(Spinalpack.code(Co.GREEN) + "Chunk unreported!");
							break;
						case 2:
							player.sendMessage(Spinalpack.code(Co.RED) + "Database error! Let Parker know!");
							break;
						}
					}
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public void onDisable(){
		
	}
}

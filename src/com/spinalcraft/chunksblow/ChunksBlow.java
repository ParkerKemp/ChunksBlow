package com.spinalcraft.chunksblow;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.spinalcraft.spinalpack.Co;
import com.spinalcraft.spinalpack.Spinalpack;

public class ChunksBlow extends JavaPlugin{
	
	ConsoleCommandSender console;
	
	@Override
	public void onEnable(){
		console = Bukkit.getConsoleSender();
		
		console.sendMessage(Spinalpack.code(Co.BLUE) + "ChunksBlow online!");
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args){
		if(cmd.getName().equalsIgnoreCase("chunk")){
			if(args.length > 0){
				if(args[0].equalsIgnoreCase("list")){
					console.sendMessage("List!");
				}
			}
		}
		return false;
	}
	
	@Override
	public void onDisable(){
		
	}
}

package com.spinalcraft.chunksblow;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.spinalcraft.spinalpack.SpinalcraftPlugin;

public class ChunksBlow extends SpinalcraftPlugin{
	
	ConsoleCommandSender console;
	
	@Override
	public void onEnable(){
		console = Bukkit.getConsoleSender();
		
		createChunkTable();
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
						console.sendMessage(ChatColor.GOLD + "Player coords: (" + player.getLocation().getX() + ", " + player.getLocation().getZ() + ")");
						Chunk chunk = player.getLocation().getChunk();
						
						switch(insertReportedChunk(player.getName(), chunk.getWorld().getName(), chunk.getX(), chunk.getZ())){
						case 0:
							player.sendMessage(ChatColor.RED + "This chunk has already been reported!");
							break;
						case 1:
							player.sendMessage(ChatColor.GREEN + "Chunk reported!");
							break;
						case 2:
							player.sendMessage(ChatColor.RED + "Database error! Let Parker know!");
							break;
						}
					}
					return true;
				}
				
				if(args[0].equalsIgnoreCase("unreport")){
					if(sender instanceof Player){
						Player player = (Player)sender;
						
						Chunk chunk = player.getLocation().getChunk();
						
						switch(deleteReportedChunk(chunk.getWorld().getName(), chunk.getX(), chunk.getZ())){
						case 0:
							player.sendMessage(ChatColor.RED + "This chunk was never reported!");
							break;
						case 1:
							player.sendMessage(ChatColor.GREEN + "Chunk unreported!");
							break;
						case 2:
							player.sendMessage(ChatColor.RED + "Database error! Let Parker know!");
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
	
	private int deleteReportedChunk(String world, int x, int z){
		String query;
		
		query = "SELECT * FROM Chunks WHERE world = '" + world + "' AND x = '" + x + "' AND z = '" + z + "'";
		Statement stmt;
		try {
			stmt = prepareStatement(query);
			ResultSet rs = stmt.executeQuery(query);
			if(!rs.first())
				return 0;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 2;
		}
		
		query = "DELETE FROM Chunks WHERE world = '" + world + "' AND x = '" + x + "' AND z = '" + z + "'";
		try {
			stmt = prepareStatement(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return 2;
		}
		return 1;
	}
	
	private int insertReportedChunk(String username, String world, int x, int z){
		String query; 
		
		query = "SELECT * FROM Chunks WHERE world = '" + world + "' AND x = '" + x + "' AND z = '" + z + "' AND batchNum IS NULL";
		Statement stmt;
		try {
			stmt = prepareStatement(query);
			ResultSet rs = stmt.executeQuery(query);
			if(rs.first())
				return 0;
		} catch (SQLException e1) {
			e1.printStackTrace();
			return 2;
		}
		
		query = "INSERT INTO Chunks(username, date, world, x, z) values ('" + username + "', '" + System.currentTimeMillis() / 1000 + "', '" + world + "', '" + x + "', '" + z + "')";
		try {
			stmt = prepareStatement(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
			return 2;
		}
		console.sendMessage(ChatColor.GOLD + "Chunk coords: (" + x + ", " + z + ")");
		return 1;
	}
	
	private void createChunkTable(){
		String query;
		query = "CREATE TABLE IF NOT EXISTS Chunks (ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY, username VARCHAR(31), date VARCHAR(63), world VARCHAR(31), x INT, z INT)";
		try {
			Statement stmt = prepareStatement(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		query = "ALTER TABLE Chunks ADD batchNum INT";
		try {
			Statement stmt = prepareStatement(query);
			stmt.executeUpdate(query);
		} catch (SQLException e) {
			console.sendMessage("Chunks table already has a batchNum column. Moving on...");
		}
	}
}

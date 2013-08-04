package com.gmail.tilastokeskus;

import java.util.List;
import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    
	private TeamTeleport plugin; // pointer to your main class, unrequired if you don't need methods from the main class
 
	public Commands(TeamTeleport plugin) {
		
		this.plugin = plugin;
		
	}
 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("tt")) {
			
	        if (!(sender instanceof Player)) {
	            sender.sendMessage("Only players can use this command!");
	            return true;
	        }
	        
	        Player player = (Player) sender;
			
			if(args.length == 1) {
				
				Player targetPlayer = (Player) plugin.getServer().getPlayer(args[0]);
				
				if(targetPlayer != null) {
					
					String playerTeam = getTeam(player.getName());
					String targetPlayerTeam = getTeam(args[0]);
					
					if(playerTeam.equals(targetPlayerTeam)) {
						
						Location targetPlayerLocation = targetPlayer.getLocation();
						player.teleport(targetPlayerLocation);
						return true;
						
					}
					else {
						
						player.sendMessage(ChatColor.RED + "Player is not in your team!");
						return false;
						
					}
					
				}
				else {
					
					player.sendMessage(ChatColor.RED + "Player not online!");
					return false;
					
				}
				
			}
			else if(args.length == 0) {
				
				player.sendMessage(ChatColor.RED + "Not enough arguments! /tt <player>");
				
			}
			else {
				
				player.sendMessage(ChatColor.RED + "Too much arguments! /tt <player>");
				
			}
			
		}
		
		return false; 
		
	}
	
	public String getTeam(String player) {
		
		Set<String> teams = plugin.getConfig().getConfigurationSection("teams").getKeys(false);
		
		for (String t : teams) {
			
			List<String> players = plugin.getConfig().getStringList("teams."+t);
			
			for (String p : players) {
				
				if(p.toLowerCase().equals(player.toLowerCase())) {
					
					return t;
					
				}
				
			}
            
        }
		
		return "0";
		
	}

}
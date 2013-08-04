package com.gmail.tilastokeskus;

import java.util.Set;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    
	private TeamTeleport plugin;
 
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
			        String targetTeam = getTeam(args[0]);
			        Boolean hasPermission = hasPermission(playerTeam, targetTeam);
					
					if(hasPermission) {
						
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
	
	public Boolean hasPermission(String Super, String Child) {
		
		if(!Super.equals(Child)) {
			
			return Child.startsWith(Super+".");
			
		}
		else {
			
			return true;
			
		}
		
	}

	public String getTeam(String player) {
		
		ConfigurationSection root = plugin.getConfig().getConfigurationSection("teams");
		
		Set<String> test = root.getKeys(true);
		
		for (String t : test) {
			
			String name = "null";
			
			try { name = root.getConfigurationSection(t).getName(); }
			catch (CommandException e)     { }
			catch (NullPointerException e) { }
			finally {}
			
			if(name == "null") {
			
				String path = t.substring(0, t.length()-8);
				String _members = root.getString(t);
				String[] members = _members.split(", ");
				//plugin.getServer().getLogger().info(path + ": " + _members);
				//plugin.getServer().getLogger().info(members.length + "");
				
				for (String Member : members) {
					
					if(player.toLowerCase().equals(Member.toLowerCase())) {
						
						return path;
						
					}
					
				}
			
			}
            
        }
		
		return "0";
		
	}

}
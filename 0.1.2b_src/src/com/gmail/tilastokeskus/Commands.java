package com.gmail.tilastokeskus;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    
	private TeamTeleport plugin;
	public TeleportModule TpModule;
 
	public Commands(TeamTeleport plugin) {
		
		this.plugin = plugin;
		TpModule = new TeleportModule(plugin);
		
	}
 
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("tt")) {
			
	        if (!(sender instanceof Player)) {
	            sender.sendMessage("Only players can use this command!");
	            return true;
	        }
			
			if(args.length > 0) {
				
				if(args[0].equalsIgnoreCase("tele")) {
					
					Player player = (Player) sender;
					
					if(args.length == 2) {
				
						Player targetPlayer = (Player) plugin.getServer().getPlayer(args[1]);
						
						if(targetPlayer != null) {

							if(!targetPlayer.isDead()) {
							
								TpModule.Teleport(player, targetPlayer);
								return true;
								
							}
							else {
								
								sender.sendMessage(ChatColor.GOLD + targetPlayer.getName() + ChatColor.RED + " is dead! The poor bastard...");
								return true;
								
							}
							
						}
						else {
							
							sender.sendMessage(ChatColor.RED + "Player not online!");
							return true;
							
						}
						
					}
					else {
						
						sender.sendMessage(ChatColor.GOLD + "/tt tele <player>"+ ChatColor.GRAY +" - teleports to specified player, if he/she is in your team");
						return true;
						
					}
					
				}
				else if(args[0].equalsIgnoreCase("allow")) {
					
					if(args.length == 2) {
						
						Player requestingPlayer = (Player) plugin.getServer().getPlayer(args[1]);
						Player allowingPlayer = (Player) sender;
						String requestingPlayerName = requestingPlayer.getName();
						
						if(requestingPlayer != null) {
							
							TpModule.TpRequest.Allow(allowingPlayer, requestingPlayer);
							return true;
							
						}
						else {
							
							sender.sendMessage(ChatColor.GOLD + requestingPlayerName + ChatColor.RED + " is no longer online!");
							TeamTeleport.tpRequests.remove(allowingPlayer);
							return true;
							
						}
						
					}
					else {
						
						sender.sendMessage(ChatColor.GOLD + "/tt allow <player>"+ ChatColor.GRAY +" - allows the requesting player to teleport to your location.");
						
					}
					
				}
				else if(args[0].equalsIgnoreCase("deny")) {
					
					if(args.length == 2) {
						
						Player requestingPlayer = (Player) plugin.getServer().getPlayer(args[1]);
						Player denyingPlayer = (Player) sender;
						String requestingPlayerName = requestingPlayer.getName();
						
						if(requestingPlayer != null) {
							
								TpModule.TpRequest.Deny(denyingPlayer, requestingPlayer);
								return true;
							
						}
						else {
							
							sender.sendMessage(ChatColor.GOLD + requestingPlayerName + ChatColor.RED + " is no longer online!");
							TeamTeleport.tpRequests.remove(denyingPlayer);
							return true;
							
						}
						
					}
					else {
						
						sender.sendMessage(ChatColor.GOLD + "/tt deny <player>"+ ChatColor.GRAY +" - denies the requesting player from teleporting to your location.");
						
					}
					
				}
				else {
					
					new HelpInterface(plugin, (Player)sender);
					
				}
				
			}
			else {
				
				new HelpInterface(plugin, (Player)sender);
				
			}
			
		}
		
		return false; 
		
	}

}
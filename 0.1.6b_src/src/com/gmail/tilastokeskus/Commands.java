package com.gmail.tilastokeskus;

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
		
		if(cmd.getName().equalsIgnoreCase("teamteleport") || cmd.getName().equalsIgnoreCase("tt")) {
			
	        if (!(sender instanceof Player)) {
	            new Messages(new CommandSender[] {sender}, "PlayersOnly");
	            return true;
	        }
			
			if(args.length > 0) {
				
				if(args[0].equalsIgnoreCase("tele")) {
					
					if(sender.hasPermission("teamteleport.tele")) {
						
						Player player = (Player) sender;
					
						if(args.length == 2) {
					
							Player targetPlayer = (Player) plugin.getServer().getPlayer(args[1]);
							
							if(targetPlayer != null) {
	
								if(!targetPlayer.isDead()) {
								
									TpModule.Teleport(player, targetPlayer);
									return true;
									
								}
								else {
									
									new Messages(new CommandSender[] {sender, targetPlayer}, "PlayerIsDead");
									return true;
									
								}
								
							}
							else {
								
								new Messages(new CommandSender[] {sender}, "PlayerOffline");
								return true;
								
							}
							
						}
						else {
							
							new Messages(new CommandSender[] {sender}, "/tt tele");
							return true;
							
						}
						
					}
					else {
						
						new Messages(new CommandSender[] {sender}, "NoPermission");
						
					}
					
				}
				else if(args[0].equalsIgnoreCase("allow")) {
					
					if(sender.hasPermission("teamteleport.tele.allow")) {
					
						if(args.length == 2) {
							
							Player requestingPlayer = (Player) plugin.getServer().getPlayer(args[1]);
							Player allowingPlayer = (Player) sender;
							String requestingPlayerName = requestingPlayer.getName();
							String allowingPlayerName = allowingPlayer.getName();
							
							if(requestingPlayer != null) {
								
								TpModule.TpRequest.Allow(allowingPlayer, requestingPlayer);
								return true;
								
							}
							else {
								
								new Messages(new CommandSender[] {sender, requestingPlayer}, "PlayerNoLongerOnline");
								TeamTeleport.removeRequest(plugin, requestingPlayerName, allowingPlayerName);
								TeamTeleport.scheduleId = -1;
								return true;
								
							}
							
						}
						else if(args.length == 1) {
							
							Player allowingPlayer = (Player) sender;
							TpModule.TpRequest.Allow(allowingPlayer, "all");
							return true;
							
						}
						else {
							
							new Messages(new CommandSender[] {sender}, "/tt allow");
							
						}
					
					}
					else {
						
						new Messages(new CommandSender[] {sender}, "NoPermission");
						
					}
					
				}
				else if(args[0].equalsIgnoreCase("deny")) {
					
					if(sender.hasPermission("teamteleport.tele.deny")) {
					
						if(args.length == 2) {
							
							Player requestingPlayer = (Player) plugin.getServer().getPlayer(args[1]);
							Player denyingPlayer = (Player) sender;
							String requestingPlayerName = requestingPlayer.getName();
							String denyingPlayerName = denyingPlayer.getName();
							
							if(requestingPlayer != null) {
								
									TpModule.TpRequest.Deny(denyingPlayer, requestingPlayer);
									return true;
								
							}
							else {
								
								new Messages(new CommandSender[] {sender, requestingPlayer}, "PlayerNoLongerOnline");
								TeamTeleport.removeRequest(plugin, requestingPlayerName, denyingPlayerName);
								TeamTeleport.scheduleId = -1;
								return true;
								
							}
							
						}
						else if(args.length == 1) {
							
							Player denyingPlayer = (Player) sender;
								
							TpModule.TpRequest.Deny(denyingPlayer, "all");
							return true;
							
						}
						else {
							
							new Messages(new CommandSender[] {sender}, "/tt deny");
							
						}
						
					}
					else {
						
						new Messages(new CommandSender[] {sender}, "NoPermission");
						
					}
					
				}
				else if(args[0].equalsIgnoreCase("cancel")) {
					
					if(sender.hasPermission("teamteleport.tele.cancel")) {
						
						if(args.length == 2) {
							
							Player cancelledPlayer = (Player) plugin.getServer().getPlayer(args[1]);
							Player cancellingPlayer = (Player) sender;
							String cancelledPlayerName = args[1];
							String cancellingPlayerName = cancellingPlayer.getName();
							
							if(cancelledPlayer != null) {
								
								TpModule.TpRequest.Cancel(cancellingPlayer, cancelledPlayer);
								return true;
							
							}
							else {
								
								new Messages(new Object[] {sender, cancelledPlayerName}, "PlayerNoLongerOnline");
								TeamTeleport.removeRequest(plugin, cancellingPlayerName, cancelledPlayerName);
								TeamTeleport.scheduleId = -1;
								return true;
								
							}
							
						}
						else if(args.length == 1) {
							
							Player cancellingPlayer = (Player) sender;
							TpModule.TpRequest.Cancel(cancellingPlayer, "all");
							
						}
						
					}
					else {
						
						new Messages(new CommandSender[] {sender}, "NoPermission");
						
					}
					
				}
				else {
					
					new HelpInterface((Player)sender);
					
				}
				
			}
			else {
				
				new HelpInterface((Player)sender);
				
			}
			
		}
		
		return false; 
		
	}

}
package com.gmail.tilastokeskus;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    
	private TeamTeleport plugin;
	public TeleportModule tpModule;
	public TeleportRequest tpRequest;
 
	public Commands(TeamTeleport plugin) {
		
		this.plugin = plugin;
		tpModule = new TeleportModule(plugin);
		tpRequest = new TeleportRequest(plugin);
		
	}
 
	@SuppressWarnings("unused")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		
		if(cmd.getName().equalsIgnoreCase("teamteleport") || cmd.getName().equalsIgnoreCase("tt")) { // Check for the given commands
			
			if(args.length > 0) {
				
				if(args[0].equalsIgnoreCase("reload")) { // /tt reload
				
					if(sender.hasPermission("teamteleport.reload")) { // Check for permission
						
						TeamTeleport.reloadConfigs(plugin);
						
					}
					else {
						
						new Messages(new CommandSender[] {sender}, "NoPermission"); // All messages are stored in the same class. This one fetches the message "NoPermission".
						
					}
					
				}				
				else if(args[0].equalsIgnoreCase("tele")) { // /tt tele <player>
					
			        if (!(sender instanceof Player)) { // Check that the command-sender is a player, not the server console (not a good habit to teleport the console...)
			        	
			            new Messages(new CommandSender[] {sender}, "PlayersOnly");
			            
			            return true;
			            
			        }
					
					if(sender.hasPermission("teamteleport.tele")) {
						
						Player player = (Player) sender;
					
						if(args.length == 2) { // Check that we have the correct amount of arguments
					
							Player targetPlayer = (Player) plugin.getServer().getPlayer(args[1]); // Get the target player by name
							
							if(targetPlayer != null) { // Check if the player exists
	
								if(!targetPlayer.isDead()) { // Check whether the target is dead
								
									tpModule.TeleportCheck(player, targetPlayer); // Let the teleport-stuff begin
									
									return true;
									
								}
								else {
									
									new Messages(new CommandSender[] {sender, targetPlayer}, "PlayerIsDead"); // For now, teleporting to dead people is forbidden. It's kind of sick...
									
									return true;
									
								}
								
							}
							else {
								
								new Messages(new CommandSender[] {sender}, "PlayerNotOnline"); // Player is not online, or never has even existed (maybe typo?)
								
								return true;
								
							}
							
						}
						else {
							
							new Messages(new CommandSender[] {sender}, "/tt tele"); // Wrong number of arguments will produce a help-like-text with the correct way of doing things
							
							return true;
							
						}
						
					}
					else {
						
						new Messages(new CommandSender[] {sender}, "NoPermission");
						
					}
					
				}
				else if(args[0].equalsIgnoreCase("allow")) { // /tt allow [player]
					
			        if (!(sender instanceof Player)) { // Again, check it's not the console trying to be a hero
			        	
			            new Messages(new CommandSender[] {sender}, "PlayersOnly");
			            
			            return true;
			            
			        }
					
					if(sender.hasPermission("teamteleport.tele.allow")) {
					
						if(args.length == 2) { // Check for arguments. Two arguments means allow a specific request, and one argument means allows all requests. 
							
							Player requestingPlayer = (Player) plugin.getServer().getPlayer(args[1]);
							Player allowingPlayer = (Player) sender;
							String requestingPlayerName = requestingPlayer.getName();
							String allowingPlayerName = allowingPlayer.getName();
							
							if(requestingPlayer != null) { // Again check for imaginary friends. This time it's also possible that *the guy* disconnected mid-request
								
								if(!requestingPlayer.isDead()) {
								
									tpRequest.Allow(allowingPlayer, requestingPlayer); // Everything is fine, so carry on with the procedure
								
									return true;
								
								}
								else {
									
									new Messages(new CommandSender[] {sender, requestingPlayer}, "PlayerIsDead"); // Again with the dead people
									
									return true;
									
								}
								
							}
							else {
								
								new Messages(new CommandSender[] {sender, requestingPlayer}, "PlayerNoLongerOnline");
								
								TeamTeleport.removeRequest(plugin, requestingPlayerName, allowingPlayerName); // A request exists even though the player doesn't,
								TeamTeleport.scheduleId = -1;                                                 // so the request can (has to) be deleted right away.
								
								return true;
								
							}
							
						}
						else if(args.length == 1) { // Here we go on allowing everything and everyone.
							
							Player allowingPlayer = (Player) sender;
							tpRequest.Allow(allowingPlayer, "all");
							
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
				else if(args[0].equalsIgnoreCase("deny")) { // /tt deny [player]
					
			        if (!(sender instanceof Player)) { // the console stuff...
			        	
			            new Messages(new CommandSender[] {sender}, "PlayersOnly");
			            
			            return true;
			            
			        }
					
					if(sender.hasPermission("teamteleport.tele.deny")) {
					
						if(args.length == 2) {
							
							Player requestingPlayer = (Player) plugin.getServer().getPlayer(args[1]);
							Player denyingPlayer = (Player) sender;
							String requestingPlayerName = requestingPlayer.getName();
							String denyingPlayerName = denyingPlayer.getName();
							
							if(requestingPlayer != null) {
								
								tpRequest.Deny(denyingPlayer, requestingPlayer);
									
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
								
							tpRequest.Deny(denyingPlayer, "all");
							
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
				else if(args[0].equalsIgnoreCase("cancel")) { // /tt cancel [player]
					
					if (!(sender instanceof Player)) {
			        	
			            new Messages(new CommandSender[] {sender}, "PlayersOnly");
			            
			            return true;
			            
			        }
					
					if(sender.hasPermission("teamteleport.tele.cancel")) {
						
						if(args.length == 2) {
							
							Player cancelledPlayer = (Player) plugin.getServer().getPlayer(args[1]);
							Player cancellingPlayer = (Player) sender;
							String cancelledPlayerName = args[1];
							String cancellingPlayerName = cancellingPlayer.getName();
							
							if(cancelledPlayer != null) {
								
								tpRequest.Cancel(cancellingPlayer, cancelledPlayer);
								
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
							tpRequest.Cancel(cancellingPlayer, "all");
							
						}
						
					}
					else {
						
						new Messages(new CommandSender[] {sender}, "NoPermission");
						
					}
					
				}
				else {
					
					new HelpInterface((Player)sender); // When a wrong command is given, pop out the help-dialog
					
				}
				
			}
			else {
				
				new HelpInterface((Player)sender);
				
			}
			
		}
		
		return false; 
		
	}

}
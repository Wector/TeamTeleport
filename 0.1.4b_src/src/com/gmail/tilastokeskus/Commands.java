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
		
		if(cmd.getName().equalsIgnoreCase("tt")) {
			
	        if (!(sender instanceof Player)) {
	            new Messages(new CommandSender[] {sender}, "PlayersOnly");
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
				else if(args[0].equalsIgnoreCase("allow")) {
					
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
					else {
						
						new Messages(new CommandSender[] {sender}, "/tt allow");
						
					}
					
				}
				else if(args[0].equalsIgnoreCase("deny")) {
					
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
					else {
						
						new Messages(new CommandSender[] {sender}, "/tt deny");
						
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
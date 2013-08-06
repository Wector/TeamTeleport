package com.gmail.tilastokeskus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportRequest {
	
	private TeamTeleport plugin;
	
	public TeleportRequest(TeamTeleport plugin) {
		
		this.plugin = plugin;
		
	}
	
	public void Send(Player player, Player targetPlayer) {
		
		String playerName = player.getName();
		String targetPlayerName = targetPlayer.getName();
		
		if(TeamTeleport.tpRequests.containsKey(targetPlayerName)) {
			
			for(String s : TeamTeleport.tpRequests.get(targetPlayerName)) {
				
				if(s.equals(playerName)) {
					
					new Messages(new CommandSender[] {player}, "RequestExists");
					return;
					
				}
				
			}
			
		}
		else {
			
			TeamTeleport.tpRequests.put(targetPlayerName, new ArrayList<String>());
			
			new Messages(new CommandSender[] {player, targetPlayer}, "TeleportRequest");
			
			((List<String>)TeamTeleport.tpRequests.get(targetPlayerName)).add(playerName);
			
		}
		
	}
	
	public void Allow(final Player allowingPlayer, final Object requestingPlayer) {
		
		final String allowingPlayerName = allowingPlayer.getName();
		
		if(requestingPlayer instanceof Player) {
		
			final String requestingPlayerName = ((Player)requestingPlayer).getName();
			
			if(TeamTeleport.tpRequests.containsKey(allowingPlayerName)) {
				
				for(String s : TeamTeleport.tpRequests.get(allowingPlayerName)) {
					
					if(s.equals(requestingPlayerName)) {
						
						if(((Player)requestingPlayer).hasPermission("teamteleport.tele.noWait")) {
							
							((Player)requestingPlayer).teleport(allowingPlayer);
							new Messages(new CommandSender[] {(Player)requestingPlayer, allowingPlayer}, "TeleportSuccesful");
							TeamTeleport.removeRequest(plugin, requestingPlayerName, allowingPlayerName);
							
						}
						else {
							
							new Messages(new CommandSender[] {((Player)requestingPlayer), allowingPlayer}, "TeleportCountdownStarted");
							TeamTeleport.scheduleId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								public void run() {
									((Player)requestingPlayer).teleport(allowingPlayer);
									new Messages(new CommandSender[] {(Player)requestingPlayer, allowingPlayer}, "TeleportSuccesful");
									TeamTeleport.removeRequest(plugin, requestingPlayerName, allowingPlayerName);
									TeamTeleport.scheduleId = -1;
									return;
								}
							}, TeamTeleport.TeleportDelay * 20L);
						
						}
						
						return;
						
					}
					
				}
				
				new Messages(new CommandSender[] {allowingPlayer, ((Player)requestingPlayer)}, "TeleportRequestDoesNotExistA");
				return;
				
			}
			else {
				
				new Messages(new CommandSender[] {allowingPlayer, ((Player)requestingPlayer)}, "TeleportRequestDoesNotExistA2");
				return;
				
			}
			
		}
		else {
			
			if(TeamTeleport.tpRequests.containsKey(allowingPlayerName)) {
			
				for(final String s : TeamTeleport.tpRequests.get(allowingPlayerName)) {
					
					final Player player = plugin.getServer().getPlayer(s);
					if(player != null) {
						
						if(player.hasPermission("teamteleport.tele.noWait")) {
							
							player.teleport(allowingPlayer);
							new Messages(new CommandSender[] {player, allowingPlayer}, "TeleportSuccesful");
							TeamTeleport.removeRequest(plugin, s, allowingPlayerName);
							
						}
						else {
							
							new Messages(new CommandSender[] {player, allowingPlayer}, "TeleportCountdownStarted");
							TeamTeleport.scheduleId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
								public void run() {
									player.teleport(allowingPlayer);
									new Messages(new CommandSender[] {player, allowingPlayer}, "TeleportSuccesful");
									TeamTeleport.removeRequest(plugin, s, allowingPlayerName);
									TeamTeleport.scheduleId = -1;
									return;
								}
							}, TeamTeleport.TeleportDelay * 20L);
						
						}
						
					}
					
				}
				
				return;
			
			}
			else {
			
				new Messages(new CommandSender[] {allowingPlayer, ((Player)requestingPlayer)}, "TeleportRequestDoesNotExistA2");
				return;
				
			}
			
		}
		
	}
	
	public void Deny(Player denyingPlayer, Object requestingPlayer) {
		
		String denyingPlayerName = denyingPlayer.getName();
		
		if(requestingPlayer instanceof Player) {
		
			String requestingPlayerName = ((Player)requestingPlayer).getName();
			
			if(TeamTeleport.tpRequests.containsKey(denyingPlayerName)) {
				
				for(String s : TeamTeleport.tpRequests.get(denyingPlayerName)) {
					
					if(s.equals(requestingPlayerName)) {
						
						new Messages(new CommandSender[] {denyingPlayer, ((Player)requestingPlayer)}, "TeleportRequestDenied");
						TeamTeleport.removeRequest(plugin, requestingPlayerName, denyingPlayerName);
						TeamTeleport.scheduleId = -1;
						return;
						
					}
					
				}
				
			}
			
			new Messages(new CommandSender[] {denyingPlayer, (Player)requestingPlayer}, "TeleportRequestDoesNotExistD");
			
		}
		else {
			
			if(TeamTeleport.tpRequests.containsKey(denyingPlayerName)) {
			
				for(String s : TeamTeleport.tpRequests.get(denyingPlayerName)) {
					
					Player player = plugin.getServer().getPlayer(s);
					if(player != null) {
						
						new Messages(new CommandSender[] {denyingPlayer, player}, "TeleportRequestDenied-b");
						
					}
					
				}
				
				new Messages(new CommandSender[] {denyingPlayer}, "TeleportRequestDenied-a");
				TeamTeleport.tpRequests.remove(denyingPlayerName);
				return;
				
			}
			
			new Messages(new CommandSender[] {denyingPlayer}, "TeleportRequestDoesNotExistD");
			
		}
		
	}
	
	public void Cancel(Player cancellingPlayer, Object cancelledPlayer) {
		
		String cancellingPlayerName = cancellingPlayer.getName();
		
		if(cancelledPlayer instanceof Player) {
		
			String cancelledPlayerName = ((Player)cancelledPlayer).getName();
			
			if(TeamTeleport.tpRequests.containsKey(cancelledPlayerName)) {
				
				for(String s : TeamTeleport.tpRequests.get(cancelledPlayerName)) {
					
					if(s.equals(cancellingPlayerName)) {
						
						new Messages(new CommandSender[] {cancellingPlayer, ((Player)cancelledPlayer)}, "TeleportRequestCancelled");
						TeamTeleport.removeRequest(plugin, cancellingPlayerName, cancelledPlayerName);
						
						if(TeamTeleport.scheduleId != -1) {
							
							plugin.getServer().getScheduler().cancelTask(TeamTeleport.scheduleId);
							TeamTeleport.scheduleId = -1;
							
						}
						
						return;
						
					}
					
				}
				
			}
			
			new Messages(new CommandSender[] {cancellingPlayer, ((Player)cancelledPlayer)}, "TeleportRequestDoesNotExistC");
			
		}
		else {
			
			for(String k : TeamTeleport.tpRequests.keySet()) {
				
				for(String s : TeamTeleport.tpRequests.get(k)) {
					
					if(s.equals(cancellingPlayerName)) {
					
						Player player = plugin.getServer().getPlayer(k);
						if(player != null) {
							
							new Messages(new CommandSender[] {cancellingPlayer, player}, "TeleportRequestCancelled-b");
							
						}
						
					}
					
				}
				
				new Messages(new CommandSender[] {cancellingPlayer}, "TeleportRequestCancelled-a");
				TeamTeleport.removeRequest(plugin, cancellingPlayerName, "all");
				return;
				
			}
			
			new Messages(new CommandSender[] {cancellingPlayer}, "TeleportRequestDoesNotExistC");
			
		}
		
	}

}

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
	
	public void Allow(final Player allowingPlayer, final Player requestingPlayer) {
		
		final String allowingPlayerName = allowingPlayer.getName();
		final String requestingPlayerName = requestingPlayer.getName();
		
		if(TeamTeleport.tpRequests.containsKey(allowingPlayerName)) {
			
			for(String s : TeamTeleport.tpRequests.get(allowingPlayerName)) {
				
				if(s.equals(requestingPlayerName)) {
					
					new Messages(new CommandSender[] {requestingPlayer, allowingPlayer}, "TeleportCountdownStarted");
					TeamTeleport.scheduleId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							requestingPlayer.teleport(allowingPlayer);
							TeamTeleport.removeRequest(plugin, requestingPlayerName, allowingPlayerName);
							TeamTeleport.scheduleId = -1;
							return;
						}
					}, TeamTeleport.TeleportDelay * 20L);
					
					return;
					
				}
				
			}
			
			new Messages(new CommandSender[] {allowingPlayer, requestingPlayer}, "TeleportRequestDoesNotExist");
			return;
			
		}
		else {
			
			new Messages(new CommandSender[] {allowingPlayer, requestingPlayer}, "TeleportRequestDoesNotExist");
			return;
			
		}
		
	}
	
	public void Deny(Player denyingPlayer, Player requestingPlayer) {
		
		String requestingPlayerName = requestingPlayer.getName();
		String denyingPlayerName = denyingPlayer.getName();
		
		if(TeamTeleport.tpRequests.containsKey(denyingPlayerName)) {
			
			for(String s : TeamTeleport.tpRequests.get(denyingPlayerName)) {
				
				if(s.equals(requestingPlayerName)) {
					
					new Messages(new CommandSender[] {denyingPlayer, requestingPlayer}, "TeleportRequestDenied");
					TeamTeleport.removeRequest(plugin, requestingPlayerName, denyingPlayerName);
					TeamTeleport.scheduleId = -1;
					return;
					
				}
				
			}
			
		}
		
		new Messages(new CommandSender[] {denyingPlayer, requestingPlayer}, "TeleportRequestDoesNotExist");
		
	}

}

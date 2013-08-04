package com.gmail.tilastokeskus;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class TeleportRequest {
	
	//private TeamTeleport plugin;
	
	public TeleportRequest(TeamTeleport plugin) {
		
		//this.plugin = plugin;
		
	}
	
	public void Send(Player player, Player targetPlayer) {
		
		String playerName = player.getName();
		String targetPlayerName = targetPlayer.getName();
		
		if(TeamTeleport.tpRequests.containsKey(targetPlayerName)) {
			
			for(String s : TeamTeleport.tpRequests.get(targetPlayerName)) {
				
				if(s.equals(playerName)) {
					
					player.sendMessage(ChatColor.RED + "You have already sent a request to that player!");
					return;
					
				}
				
			}
			
		}
		else {
			
			TeamTeleport.tpRequests.put(targetPlayerName, new ArrayList<String>());
			
			player.sendMessage(ChatColor.AQUA + "Request sent to " + ChatColor.GOLD + targetPlayerName);
			targetPlayer.sendMessage(ChatColor.RED + playerName + ChatColor.AQUA + " has requested to teleport to your location.");
			targetPlayer.sendMessage(ChatColor.GOLD + "/tt allow <player>" + ChatColor.GRAY + " - Accept request.");
			targetPlayer.sendMessage(ChatColor.GOLD + "/tt deny <player>" + ChatColor.GRAY + " - Deny request.");
			
			((List<String>)TeamTeleport.tpRequests.get(targetPlayerName)).add(playerName);
			
		}
		
	}
	
	public void Allow(Player allowingPlayer, Player requestingPlayer) {
		
		String allowingPlayerName = allowingPlayer.getName();
		String requestingPlayerName = requestingPlayer.getName();
		
		if(TeamTeleport.tpRequests.containsKey(allowingPlayerName)) {
			
			for(String s : TeamTeleport.tpRequests.get(allowingPlayerName)) {
				
				if(s.equals(requestingPlayerName)) {
					
					requestingPlayer.teleport(allowingPlayer);
					TeamTeleport.tpRequests.remove(allowingPlayerName);
					return;
					
				}
				
			}
			
			allowingPlayer.sendMessage(ChatColor.GOLD + requestingPlayerName + ChatColor.RED + " has not sent a teleport-request to you...");
			return;
			
		}
		else {
			
			allowingPlayer.sendMessage(ChatColor.RED + "No one has sent a teleport-request to you...");
			return;
			
		}
		
	}
	
	public void Deny(Player denyingPlayer, Player requestingPlayer) {
		
		String requestingPlayerName = requestingPlayer.getName();
		String denyingPlayerName = denyingPlayer.getName();
		
		if(TeamTeleport.tpRequests.containsKey(denyingPlayerName)) {
			
			for(String s : TeamTeleport.tpRequests.get(denyingPlayerName)) {
				
				if(s.equals(requestingPlayerName)) {
					
					denyingPlayer.sendMessage(ChatColor.RED + "You have denied " + ChatColor.GOLD + requestingPlayerName + "'s " + ChatColor.RED + "teleport-request.");
					requestingPlayer.sendMessage(ChatColor.GOLD + denyingPlayerName + ChatColor.RED + " has denied your teleport-request.");
					TeamTeleport.tpRequests.remove(denyingPlayerName);
					return;
					
				}
				
			}
			
			denyingPlayer.sendMessage(ChatColor.GOLD + requestingPlayerName + ChatColor.RED + " has not sent a teleport-request to you...");
			return;
			
		}
		else {
			
			denyingPlayer.sendMessage(ChatColor.RED + "No one has sent a teleport-request to you...");
			return;
			
		}
		
	}

}

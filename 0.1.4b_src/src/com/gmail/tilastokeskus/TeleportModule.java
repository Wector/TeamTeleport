package com.gmail.tilastokeskus;

import java.util.Set;

import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class TeleportModule {
	
	private TeamTeleport plugin;
	public TeleportRequest TpRequest;
	
	public TeleportModule(TeamTeleport plugin) {
		
		this.plugin = plugin;
		TpRequest = new TeleportRequest(plugin);
		
	}
	
	public void Teleport(final Player player, final Player targetPlayer) {
		
        String playerTeam = getTeam(player.getName());
        String targetTeam = getTeam(targetPlayer.getName());
        Boolean hasPermission = hasPermission(playerTeam, targetTeam);
		
		if(hasPermission) {
			
			if(TeamTeleport.AskPermission && !player.hasPermission("teamteleport.tele.noAsk")) {
				
				TpRequest.Send(player, targetPlayer);
				
			}
			else {

				if(player.hasPermission("teamteleport.tele.noWait")) {
					
					player.teleport(targetPlayer);
					
				}
				else {
					
					new Messages(new CommandSender[] {player, targetPlayer}, "TeleportCountdownStarted");
					TeamTeleport.scheduleId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							player.teleport(targetPlayer);
						}
					}, TeamTeleport.TeleportDelay * 20L);
				
				}
					
			}
			
		}
		else {
			
			new Messages(new CommandSender[] {player}, "PlayerNotTeamMember");
			
		}
		
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
		
		ConfigurationSection root = plugin.getTeamsConfig();
		
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
				
				for (String Member : members) {
					
					if(player.equalsIgnoreCase(Member)) {
						
						return path;
						
					}
					
				}
			
			}
            
        }
		
		return "0";
		
	}
	
}

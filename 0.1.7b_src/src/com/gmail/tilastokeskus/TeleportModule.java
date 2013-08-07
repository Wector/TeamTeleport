package com.gmail.tilastokeskus;

import java.util.Set;

import org.bukkit.block.Block;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class TeleportModule {
	
	private TeamTeleport plugin;
	public TeleportRequest tpRequest;
	
	public TeleportModule(TeamTeleport plugin) {
		
		this.plugin = plugin;
		tpRequest = new TeleportRequest(plugin);
		
	}
	
	public static void Teleport(Player player, Player target) {
		
		for(int i = 0; i < 255; i++) {
			
			if(target.getLocation().add(TeamTeleport.TeleportOffset).add(new Vector(0, i, 0)).getY() > 254) break;
			
			Block block = player.getWorld().getBlockAt(target.getLocation().add(TeamTeleport.TeleportOffset).add(new Vector(0, i, 0)));
			Block block2 = player.getWorld().getBlockAt(target.getLocation().add(TeamTeleport.TeleportOffset).add(new Vector(0, i+1, 0)));
			
			if(block.getTypeId() == 0 && block2.getTypeId() == 0) {
				
				player.teleport(target.getLocation().add(TeamTeleport.TeleportOffset).add(new Vector(0, i, 0)));
				
				new Messages(new CommandSender[] {player, target}, "TeleportSuccesful");
				
				return;
				
			}
			
		}
		
		new Messages(new CommandSender[] {player, target}, "CannotTeleportSafely");
		
	}
	
	public void TeleportCheck(final Player player, final Player targetPlayer) {
		
        String playerTeam = getTeam(player.getName());
        String targetTeam = getTeam(targetPlayer.getName());
        Boolean hasPermission = hasPermission(playerTeam, targetTeam);
		
		if(hasPermission) {
			
			if(TeamTeleport.AskPermission && !player.hasPermission("teamteleport.tele.noAsk")) {
				
				tpRequest.Send(player, targetPlayer);
				
			}
			else {

				if(player.hasPermission("teamteleport.tele.noWait")) {
					
					Teleport(player, targetPlayer);
					
				}
				else {
					
					new Messages(new CommandSender[] {player, targetPlayer}, "TeleportCountdownStarted");
					
					TeamTeleport.scheduleId = plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
						public void run() {
							
							Teleport(player, targetPlayer);
							
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
		
		ConfigurationSection root = TeamTeleport.getTeamsConfig();
		
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

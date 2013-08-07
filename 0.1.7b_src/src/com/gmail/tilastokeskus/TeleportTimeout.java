package com.gmail.tilastokeskus;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportTimeout {
	
	TeamTeleport plugin;
	
	public TeleportTimeout(final TeamTeleport plugin, final Player player, final Player target) {
		
		this.plugin = plugin;
		
		if(TeamTeleport.TeleportRequestTimeout != 0) {
		
			plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
				
				public void run() {
					
					TeamTeleport.removeRequest(plugin, player.getName(), target.getName());
					
					new Messages(new CommandSender[] {player, target}, "TeleportRequestTimeout");
					
					return;
					
				}
				
			}, TeamTeleport.TeleportRequestTimeout * 20L);
			
		}
		
	}

}

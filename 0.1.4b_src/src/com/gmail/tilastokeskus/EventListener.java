package com.gmail.tilastokeskus;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EventListener implements Listener {
	
	private TeamTeleport plugin;
	
	public EventListener(TeamTeleport plugin) {
		
		this.plugin = plugin;
		plugin.getServer().getPluginManager().registerEvents(this, plugin);
		
	}
	
	@EventHandler(priority = EventPriority.NORMAL)
	public void dmg(final EntityDamageEvent event) {
		
		Entity e = event.getEntity();
		if(e instanceof Player) {
			
			Player player = (Player)e;
			if(TeamTeleport.scheduleId != -1) {
				
				if(event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK || event.getCause() == EntityDamageEvent.DamageCause.PROJECTILE) {
				
					new Messages(new CommandSender[] {player}, "TeleportCancelled");
					
					TeamTeleport.removeRequest(plugin, player.getName(), "all");
					
					plugin.getServer().getScheduler().cancelTask(TeamTeleport.scheduleId);
					TeamTeleport.scheduleId = -1;
					
				}
				
			}
				
		}
		
	}

}

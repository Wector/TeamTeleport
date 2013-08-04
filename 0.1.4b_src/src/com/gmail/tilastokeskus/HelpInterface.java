package com.gmail.tilastokeskus;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class HelpInterface {
	
	public HelpInterface(TeamTeleport plugin, Player player) {
		
		player.sendMessage(ChatColor.RED + "=== TeamTeleport help ===");
		player.sendMessage(ChatColor.GOLD + "/tt tele <player>"+ ChatColor.GRAY +" - teleports to specified player, if he/she is in your team");
		player.sendMessage(ChatColor.GOLD + "/tt allow <player>"+ ChatColor.GRAY +" - allows a requested teleport");
		player.sendMessage(ChatColor.GOLD + "/tt deny <player>"+ ChatColor.GRAY +" - denies a requested teleport");
		
	}

}

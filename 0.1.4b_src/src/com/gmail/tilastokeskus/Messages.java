package com.gmail.tilastokeskus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messages {
	
	public Messages(CommandSender[] sendee, String message) {
		
		switch(message) {
		
				/* # # # */
		
			case "PlayersOnly":
				sendee[0].sendMessage("Only players can use this command!");
				break;
			case "PlayerNotOnline":
				sendee[0].sendMessage(ChatColor.RED + "Player not online!");
				break;
			case "PlayerNotTeamMember":
				sendee[0].sendMessage(ChatColor.RED + "Player is not in your team!");
				break;
			case "PlayerIsDead":
				sendee[0].sendMessage(ChatColor.GOLD + sendee[1].getName() + ChatColor.RED + " is dead! The poor bastard...");
				break;
			case "PlayerNoLongerOnline":
				sendee[0].sendMessage(ChatColor.GOLD + sendee[1].getName() + ChatColor.RED + " is no longer online!");
				break;
				
				/* # # # */
				
			case "/tt tele":
				sendee[0].sendMessage(ChatColor.GOLD + "/tt tele <player>"+ ChatColor.GRAY +" - teleports to specified player, if he/she is in your team");
				break;
			case "/tt allow":
				sendee[0].sendMessage(ChatColor.GOLD + "/tt allow <player>"+ ChatColor.GRAY +" - allows the requesting player to teleport to your location.");
				break;
			case "/tt deny":
				sendee[0].sendMessage(ChatColor.GOLD + "/tt deny <player>"+ ChatColor.GRAY +" - denies the requesting player from teleporting to your location.");
				break;
				
				/* # # # */
				
				
			case "TeleportCountdownStarted":
				sendee[0].sendMessage(ChatColor.GOLD + "" + TeamTeleport.TeleportDelay + ChatColor.RED + (TeamTeleport.TeleportDelay == 1 ? " second" : " seconds") + " until teleport.");
				sendee[1].sendMessage(ChatColor.GOLD + sendee[0].getName() + ChatColor.RED + " will teleport to you in " + ChatColor.GOLD + TeamTeleport.TeleportDelay + ChatColor.RED + (TeamTeleport.TeleportDelay == 1 ? " second." : " seconds."));
				break;
			case "TeleportRequestExists":
				sendee[0].sendMessage(ChatColor.RED + "You have already sent a request to that player!");
				break;
			case "TeleportRequest":
				sendee[0].sendMessage(ChatColor.RED + "Request sent to " + ChatColor.GOLD + sendee[1].getName());
				sendee[1].sendMessage(ChatColor.GOLD + sendee[0].getName() + ChatColor.RED + " has requested to teleport to your location.");
				sendee[1].sendMessage(ChatColor.GOLD + "/tt allow <player>" + ChatColor.GRAY + " - Accept request.");
				sendee[1].sendMessage(ChatColor.GOLD + "/tt deny <player>" + ChatColor.GRAY + " - Deny request.");
				break;
			case "TeleportRequestDenied":
				sendee[0].sendMessage(ChatColor.RED + "You have denied " + ChatColor.GOLD + sendee[1].getName() + "'s " + ChatColor.RED + "teleport-request.");
				sendee[1].sendMessage(ChatColor.GOLD + sendee[0].getName() + ChatColor.RED + " has denied your teleport-request.");
				break;
			case "TeleportRequestDoesNotExist":
				sendee[0].sendMessage(ChatColor.GOLD + sendee[1].getName() + ChatColor.RED + " has not sent a teleport-request to you...");
				break;
			case "TeleportCancelled":
				sendee[0].sendMessage(ChatColor.RED + "Teleport cancelled.");
				break;
				
				/* # # # */
				
			case "NoPermission":
				sendee[0].sendMessage(ChatColor.RED + "You do not have permission to use this command.");
				break;
				
				/* # # # */
				
			default:
				break;
				
		}
		
	}

}

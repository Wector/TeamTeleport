package com.gmail.tilastokeskus;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Messages {
	
	public Messages(Object[] sendee, String message) {
		
		switch(message) {
		
				/* # # # */
		
			case "PlayersOnly":
				((CommandSender)sendee[0]).sendMessage("Only players can use this command!");
				break;
			case "PlayerNotOnline":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "Player not online!");
				break;
			case "PlayerNotTeamMember":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "Player is not in your team!");
				break;
			case "PlayerIsDead":
				((CommandSender)sendee[0]).sendMessage(ChatColor.GOLD + ((CommandSender)sendee[1]).getName() + ChatColor.RED + " is dead! The poor bastard...");
				break;
			case "PlayerNoLongerOnline":
				((CommandSender)sendee[0]).sendMessage(ChatColor.GOLD + ((String)sendee[1]) + ChatColor.RED + " is no longer online!");
				break;
				
				/* # # # */
				
			case "/tt tele":
				((CommandSender)sendee[0]).sendMessage(ChatColor.GOLD + "/tt tele <player>"+ ChatColor.GRAY +" - teleports to specified player, if he/she is in your team");
				break;
			case "/tt allow":
				((CommandSender)sendee[0]).sendMessage(ChatColor.GOLD + "/tt allow <player>"+ ChatColor.GRAY +" - allows the requesting player to teleport to your location.");
				break;
			case "/tt deny":
				((CommandSender)sendee[0]).sendMessage(ChatColor.GOLD + "/tt deny <player>"+ ChatColor.GRAY +" - denies the requesting player from teleporting to your location.");
				break;
				
				/* # # # */
				
				
			case "TeleportCountdownStarted":
				((CommandSender)sendee[0]).sendMessage(ChatColor.GOLD + "" + TeamTeleport.TeleportDelay + ChatColor.RED + (TeamTeleport.TeleportDelay == 1 ? " second" : " seconds") + " until teleport.");
				((CommandSender)sendee[1]).sendMessage(ChatColor.GOLD + ((CommandSender)sendee[0]).getName() + ChatColor.RED + " will teleport to you in " + ChatColor.GOLD + TeamTeleport.TeleportDelay + ChatColor.RED + (TeamTeleport.TeleportDelay == 1 ? " second." : " seconds."));
				break;
			case "TeleportRequestExists":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "You have already sent a request to that player!");
				break;
			case "TeleportRequest":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "Request sent to " + ChatColor.GOLD + ((CommandSender)sendee[1]).getName());
				((CommandSender)sendee[1]).sendMessage(ChatColor.GOLD + ((CommandSender)sendee[0]).getName() + ChatColor.RED + " has requested to teleport to your location.");
				((CommandSender)sendee[1]).sendMessage(ChatColor.GOLD + "/tt allow <player>" + ChatColor.GRAY + " - Accept request.");
				((CommandSender)sendee[1]).sendMessage(ChatColor.GOLD + "/tt deny <player>" + ChatColor.GRAY + " - Deny request.");
				break;
			case "TeleportRequestDenied":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "You have denied " + ChatColor.GOLD + ((CommandSender)sendee[1]).getName() + "'s " + ChatColor.RED + "teleport-request.");
				((CommandSender)sendee[1]).sendMessage(ChatColor.GOLD + ((CommandSender)sendee[0]).getName() + ChatColor.RED + " has denied your teleport-request.");
				break;
			case "TeleportRequestDenied-a":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "You have denied all teleport-requests.");
				break;
			case "TeleportRequestDenied-b":
				((CommandSender)sendee[1]).sendMessage(ChatColor.GOLD + ((CommandSender)sendee[0]).getName() + ChatColor.RED + " has denied your teleport-request.");
				break;
			case "TeleportRequestCancelled":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "Teleport cancelled.");
				((CommandSender)sendee[1]).sendMessage(ChatColor.GOLD + ((CommandSender)sendee[0]).getName() + ChatColor.RED + " has cancelled his/her teleport-request.");
				break;
			case "TeleportRequestCancelled-a":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "All teleport-requests cancelled.");
				break;
			case "TeleportRequestCancelled-b":
				((CommandSender)sendee[1]).sendMessage(ChatColor.GOLD + ((CommandSender)sendee[0]).getName() + ChatColor.RED + " has cancelled his/her teleport-request.");
				break;
			case "TeleportRequestDoesNotExistA":
				((CommandSender)sendee[0]).sendMessage(ChatColor.GOLD + ((CommandSender)sendee[1]).getName() + ChatColor.RED + " has not sent a teleport-request to you...");
				break;
			case "TeleportRequestDoesNotExistA2":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "No one has sent you a teleport-request...");
				break;
			case "TeleportRequestDoesNotExistD":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "Nothing to deny...");
				break;	
			case "TeleportRequestDoesNotExistC":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "Nothing to cancel...");
				break;
			case "TeleportSuccesful":
				((CommandSender)sendee[0]).sendMessage(ChatColor.GREEN + "Teleported succesfully to " + ChatColor.GOLD + ((CommandSender)sendee[1]).getName());
				((CommandSender)sendee[1]).sendMessage(ChatColor.GOLD + ((CommandSender)sendee[0]).getName() + ChatColor.GREEN + " teleported to you.");
				break;
				
				/* # # # */
				
			case "NoPermission":
				((CommandSender)sendee[0]).sendMessage(ChatColor.RED + "You do not have permission to use this command.");
				break;
				
				/* # # # */
				
			default:
				break;
				
		}
		
	}

}

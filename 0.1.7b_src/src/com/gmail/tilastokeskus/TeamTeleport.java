package com.gmail.tilastokeskus;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public final class TeamTeleport extends JavaPlugin {
	
	// Configuration-files
	public static File teamsConfigFile;
    public static FileConfiguration teamsConfig;
	
    // Configurations
	public static Boolean AskPermission;
	public static int TeleportDelay;
	public static int TeleportRequestTimeout;
	public static Boolean CancelTeleportWhenAttacked;
	public static Vector TeleportOffset;
	
	// Will hold all teleport-requests as follows: {target, [requester1, requester2, ...]}, {target2, [...]}
	public static HashMap<String, List<String>> tpRequests = new HashMap<String, List<String>>();
	
	// The ID for a scheduled task, namely teleport delay
	public static int scheduleId;
	
	// Will run when the plugin is loaded
    @Override
    public void onEnable() {
    	
    	// Load the configs upon start
    	this.saveDefaultConfig();
    	this.saveDefaultConfigs();
    	
    	new EventListener(this); // Calls the eventlistener which handles teleport-request cancellations upon attack
    	
    	// Set the configurations
    	AskPermission = this.getConfig().getBoolean("AskPermission");
    	TeleportDelay = this.getConfig().getInt("TeleportDelay");
    	CancelTeleportWhenAttacked = this.getConfig().getBoolean("CancelTeleportWhenAttacked");
    	TeleportOffset = new Vector(this.getConfig().getInt("TeleportOffset.x"), this.getConfig().getInt("TeleportOffset.y"), this.getConfig().getInt("TeleportOffset.z"));
    	
    	// Commands will be dispatched to the Commands class
    	getCommand("teamteleport").setExecutor(new Commands(this));
    	getCommand("tt").setExecutor(new Commands(this));
    	
    }
 
    // Will run when the plugin is unloaded
    @Override
    public void onDisable() {
    }
    
    // Load the teams.yml config-file
    public void saveDefaultConfigs() {
    	
    	teamsConfigFile = new File(this.getDataFolder(), "teams.yml");
        teamsConfig = YamlConfiguration.loadConfiguration(teamsConfigFile);

        // If teams.yml doesn't exist in the folder, create it
        if (!(teamsConfigFile.exists())) {
        	
        	this.saveResource("teams.yml", false);
        	
        }
        
    }
    
    public static FileConfiguration getTeamsConfig() {

        return teamsConfig;
        
    }
    
    public static void reloadConfigs(TeamTeleport plugin) {

    	plugin.reloadConfig();
    	teamsConfig = YamlConfiguration.loadConfiguration(teamsConfigFile);
    	
    	// Reset the configs
    	AskPermission = plugin.getConfig().getBoolean("AskPermission");
    	TeleportDelay = plugin.getConfig().getInt("TeleportDelay");
    	CancelTeleportWhenAttacked = plugin.getConfig().getBoolean("CancelTeleportWhenAttacked");
    	TeleportOffset = new Vector(plugin.getConfig().getInt("TeleportOffset.x"), plugin.getConfig().getInt("TeleportOffset.y"), plugin.getConfig().getInt("TeleportOffset.z"));
    	
    	// Broadcast a message to everyone that the plugin has been reloaded
    	Bukkit.getServer().broadcast(ChatColor.GREEN + "TeamTeleport reloaded", "teamteleport.broadcast");
        
    }
    
    public static void removeRequest(Plugin plugin, String requestingPlayer, String targetPlayer) {
    	
    	if(targetPlayer.equals("all")) { // If we want to search all players for possible pending teleport-requests
    	
	    	for(String key : tpRequests.keySet()) { // Loop through all of those who hold at least one pending request
	    		
	    		if(tpRequests.get(key).size() > 1) { // If a player has more than one teleport-request
	    		
		    		for(String s : tpRequests.get(key)) { // Loop through all of those who have made a teleport-request
		    			
		    			if(s.equals(requestingPlayer)) {
		    				
		    				tpRequests.get(key).remove(requestingPlayer); // If we found the correct requester, remove the request
		    				
		    			}
		    			
		    			if(tpRequests.get(key).size() < 1) { // See how many requesters a target has after every loop
		    				
		    				tpRequests.remove(key); // If we have someone with zero or less (???) pending requests, we can remove the whole 'target'
		    				
		    			}
		    			
		    		}
		    		
	    		}
	    		else {
	    			
	    			if(tpRequests.get(key).contains(requestingPlayer)) {
	    				
	    				tpRequests.remove(key); // If we have someone with zero or less (???) pending requests, we can remove the whole 'target'
	    				
	    			}
	    			
	    		}
	    		
	    	}
    	
    	}
    	else { // If we want to remove a specific entry from a specific target
    		
    		if(tpRequests.get(targetPlayer) != null) { // We'll make sure an entry exists in the first place for the target
    		
	    		if(tpRequests.get(targetPlayer).size() > 1) { // If the target has more than one requests pending...
		    		
		    		for(String s : tpRequests.get(targetPlayer)) { // Loop through all the requests
		    			
		    			if(s.equals(requestingPlayer)) {
		    				
		    				tpRequests.get(targetPlayer).remove(requestingPlayer); // If we find the wanted entry, remove it
		    				
		    			}
		    			
		    			if(tpRequests.get(targetPlayer).size() < 1) {
		    				
		    				tpRequests.remove(targetPlayer); // If the target no longer has requests pending, remove the target. This is supposedly
		    				                                 // impossible to happen here, since we had two or more requests and only removed one,
		    			}                                    // but being familiar with Murphy's law I dare not remove this bit of code.

		    		}
		    		
	    		}
	    		else {
	    			
	    			if(tpRequests.get(targetPlayer).contains(requestingPlayer)) {
	    				
	    				tpRequests.remove(targetPlayer); // If the target has no requests pending, remove the target.
	    				
	    			}
	    			
	    		}
	    		
    		}
    		
    	}
	    	
    }
    
}
package com.gmail.tilastokeskus;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeamTeleport extends JavaPlugin {
	
	public File teamsConfigFile;
    public FileConfiguration teamsConfig;
	
	public static Boolean AskPermission;
	public static int TeleportDelay;
	public static Boolean CancelTeleportWhenAttacked;
	
	public static HashMap<String, List<String>> tpRequests = new HashMap<String, List<String>>();
	public static int scheduleId;
	
    @Override
    public void onEnable() {
    	
    	this.saveDefaultConfig();
    	this.saveDefaultConfigs();
    	
    	new EventListener(this);
    	
    	AskPermission = this.getConfig().getBoolean("AskPermission");
    	TeleportDelay = this.getConfig().getInt("TeleportDelay");
    	CancelTeleportWhenAttacked = this.getConfig().getBoolean("CancelTeleportWhenAttacked");
    	
    	getCommand("teamteleport").setExecutor(new Commands(this));
    	getCommand("tt").setExecutor(new Commands(this));
    	
    }
 
    @Override
    public void onDisable() {
    }
    
    public void saveDefaultConfigs() {
    	
    	teamsConfigFile = new File(this.getDataFolder(), "teams.yml");
        teamsConfig = YamlConfiguration.loadConfiguration(teamsConfigFile);

        if (!(teamsConfigFile.exists())) {
        	
        	this.saveResource("teams.yml", false);
        	
        }
        
    }
    
    public FileConfiguration getTeamsConfig() {

        return teamsConfig;
        
    }
    
    public static void removeRequest(Plugin plugin, String requestingPlayer, String targetPlayer) {
    	
    	if(targetPlayer.equals("all")) {
    	
	    	for(String key : tpRequests.keySet()) {
	    		
	    		if(tpRequests.get(key).size() > 1) {
	    		
		    		for(String s : tpRequests.get(key)) {
		    			
		    			if(s.equals(requestingPlayer)) {
		    				
		    				tpRequests.get(key).remove(requestingPlayer);
		    				
		    			}
		    			
		    			if(tpRequests.get(key).size() < 1) {
		    				
		    				tpRequests.remove(key);
		    				
		    			}
		    			
		    		}
		    		
	    		}
	    		else {
	    			
	    			if(tpRequests.get(key).contains(requestingPlayer)) {
	    				
	    				tpRequests.remove(key);
	    				
	    			}
	    			
	    		}
	    		
	    	}
    	
    	}
    	else {
    		
    		if(tpRequests.get(targetPlayer) != null) {
    		
	    		if(tpRequests.get(targetPlayer).size() > 1) {
		    		
		    		for(String s : tpRequests.get(targetPlayer)) {
		    			
		    			if(s.equals(requestingPlayer)) {
		    				
		    				tpRequests.get(targetPlayer).remove(requestingPlayer);
		    				
		    			}
		    			
		    			if(tpRequests.get(targetPlayer).size() < 1) {
		    				
		    				tpRequests.remove(targetPlayer);
		    				
		    			}
		    			
		    		}
		    		
	    		}
	    		else {
	    			
	    			if(tpRequests.get(targetPlayer).contains(requestingPlayer)) {
	    				
	    				tpRequests.remove(targetPlayer);
	    				
	    			}
	    			
	    		}
	    		
    		}
    		
    	}
	    	
    }
    
}
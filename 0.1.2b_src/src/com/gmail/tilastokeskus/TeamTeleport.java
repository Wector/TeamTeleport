package com.gmail.tilastokeskus;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public final class TeamTeleport extends JavaPlugin {
	
	public File teamsConfigFile;
    public FileConfiguration teamsConfig;
	
	public static Boolean askPermission = false;
	public static HashMap<String, List<String>> tpRequests = new HashMap<String, List<String>>();
	
    @Override
    public void onEnable() {
    	
    	this.saveDefaultConfig();
    	this.saveDefaultConfigs();
    	
    	if(this.getConfig().getString("AskPermission").equalsIgnoreCase("true")) askPermission = true;
    	
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
    
}
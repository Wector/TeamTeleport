package com.gmail.tilastokeskus;

import org.bukkit.plugin.java.JavaPlugin;

public final class TeamTeleport extends JavaPlugin {
	
    @Override
    public void onEnable() {
    	
    	this.saveDefaultConfig();
    	getCommand("tt").setExecutor(new Commands(this));
    	
    }
 
    @Override
    public void onDisable() {
    }
    
}
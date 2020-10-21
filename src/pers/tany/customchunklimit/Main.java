package pers.tany.customchunklimit;

import java.io.File;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import pers.tany.customchunklimit.command.Commands;
import pers.tany.customchunklimit.listenevent.Event;

public class Main extends JavaPlugin{
	public static Boolean open = false;
	public static Plugin plugin;
	public static HashMap<String, Integer> Create = new HashMap<String, Integer>();
	public static HashMap<String, Integer> CreateAll = new HashMap<String, Integer>();
	public void onEnable() {
	    plugin = this;
		Bukkit.getConsoleSender().sendMessage("§e[§6Custom§eChunk§6Limit§e]§a插件已加载");
	    if (!new File(getDataFolder(), "config.yml").exists()) 
	    	saveResource("config.yml",false);
	    
	    if (!new File(getDataFolder(), "data.yml").exists())
	    	saveResource("data.yml",false);
	    
	    if (!new File(getDataFolder(), "message.yml").exists())
	    	saveResource("message.yml",false);
	    getCommand("ccl").setExecutor(new Commands());
	    getServer().getPluginManager().registerEvents(new Event(), this);
	    new BasicLibrary();
	}
	public void onDisable() {
		Bukkit.getConsoleSender().sendMessage("§e[§6Chunk§eBlock§6Limit§e]§c插件已卸载");
	}
}

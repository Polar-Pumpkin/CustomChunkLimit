package com.tany.customchunklimit;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class Other {
    static Plugin configs = Bukkit.getPluginManager().getPlugin("CustomChunkLimit");
    static File file=new File(configs.getDataFolder(),"config.yml");
    static File file1=new File(configs.getDataFolder(),"data.yml");
    static File file2=new File(configs.getDataFolder(),"message.yml");
	public static FileConfiguration config=YamlConfiguration.loadConfiguration(file);
	public static FileConfiguration data=YamlConfiguration.loadConfiguration(file1);
	public static FileConfiguration message=YamlConfiguration.loadConfiguration(file2);
}

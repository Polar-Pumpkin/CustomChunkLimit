package pers.tany.customchunklimit;

import org.bukkit.Bukkit;
import org.serverct.parrot.parrotx.PPlugin;
import org.serverct.parrot.parrotx.listener.InventoryListener;
import org.serverct.parrot.parrotx.utils.I18n;
import pers.tany.customchunklimit.command.CommandHandler;
import pers.tany.customchunklimit.config.ConfigManager;
import pers.tany.customchunklimit.config.DataManager;
import pers.tany.customchunklimit.listener.BlockPlaceListener;
import pers.tany.customchunklimit.listener.PlayerInteractListener;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CustomChunkLimit extends PPlugin {

    public final static Map<UUID, Integer> creatingMap = new HashMap<>();
    public final static Map<UUID, Integer> creatingAllMap = new HashMap<>();

    @Override
    protected void registerListener() {
        Bukkit.getPluginManager().registerEvents(new PlayerInteractListener(), this);
        Bukkit.getPluginManager().registerEvents(new BlockPlaceListener(), this);
        Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
    }

    @Override
    protected void preload() {
        pConfig = new ConfigManager();
    }

    @Override
    protected void load() {
        DataManager.getInstance().init();

        lang.log("&a插件已加载。", I18n.Type.INFO, false);

        super.registerCommand(new CommandHandler());
    }

    @Override
    public void onDisable() {
        lang.log("&c插件已卸载。", I18n.Type.INFO, false);
    }
}

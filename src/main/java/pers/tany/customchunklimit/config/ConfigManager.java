package pers.tany.customchunklimit.config;

import org.serverct.parrot.parrotx.config.PConfig;
import pers.tany.customchunklimit.CustomChunkLimit;

public class ConfigManager extends PConfig {

    public ConfigManager() {
        super(CustomChunkLimit.getInstance(), "config", "主配置文件");
        addItem("NiceChunk", ItemType.BOOLEAN, "niceChunk");
        addItem("NiceChunkImprove", ItemType.BOOLEAN, "niceChunkImprove");
        addItem("Optimization", ItemType.BOOLEAN, "optimization");
        addItem("YAxis", ItemType.INT, "yAxis");
        addItem("Tile", ItemType.BOOLEAN, "tile");
        addItem("Botania", ItemType.BOOLEAN, "botania");
        addItem("AutoClear", ItemType.BOOLEAN, "autoClear");
    }

    public static boolean niceChunk;
    public static boolean niceChunkImprove;
    public static boolean optimization;
    public static int yAxis;
    public static boolean tile;
    public static boolean botania;
    public static boolean autoClear;

    @Override
    public void saveDefault() {
        plugin.saveDefaultConfig();
    }
}

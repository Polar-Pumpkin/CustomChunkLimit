package pers.tany.customchunklimit.config;

import lombok.Getter;
import lombok.NonNull;
import org.bukkit.configuration.ConfigurationSection;
import org.serverct.parrot.parrotx.config.PConfig;
import org.serverct.parrot.parrotx.enums.Position;
import org.serverct.parrot.parrotx.utils.I18n;
import pers.tany.customchunklimit.CustomChunkLimit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataManager extends PConfig {

    private static DataManager instance;

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

    public DataManager() {
        super(CustomChunkLimit.getInstance(), "data", "数据文件夹");
    }

    @Getter
    private final List<String> blocks = new ArrayList<>();

    @Override
    public void load(@NonNull File file) {
        ConfigurationSection section = config.getConfigurationSection("Block");
        if (section != null) {
            blocks.addAll(section.getKeys(false));
        }
    }

    public Map<Integer, Map<Integer, String>> pages() {
        final Map<Integer, Map<Integer, String>> result = new HashMap<>();
        final List<Integer> area = Position.getPositionList("1-9", "1-5");

        int pageNumber = 1;
        Map<Integer, String> page = null;
        int counter = 0;

        for (String block : this.blocks) {
            if (page == null) {
                page = new HashMap<>();
            }
            if (counter >= area.size() || page.size() >= area.size()) {
                result.put(pageNumber, page);

                counter = 0;
                page = new HashMap<>();
                pageNumber++;
            }

            page.put(area.get(counter), block);
            counter++;
        }

        return result;
    }

    @Override
    public void reload() {
        save();
        init();
    }

    @Override
    public void saveDefault() {
        plugin.saveResource("data.yml", false);
    }

    @Override
    public void save() {
        try {
            this.config.save(file);
        } catch (IOException e) {
            plugin.lang.logError(I18n.SAVE, getTypeName(), e, null);
        }
    }
}

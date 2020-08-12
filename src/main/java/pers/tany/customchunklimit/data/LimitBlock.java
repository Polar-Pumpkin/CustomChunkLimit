package pers.tany.customchunklimit.data;

import lombok.NonNull;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.serverct.parrot.parrotx.data.PData;
import org.serverct.parrot.parrotx.data.PID;
import org.serverct.parrot.parrotx.utils.BasicUtil;
import org.serverct.parrot.parrotx.utils.EnumUtil;
import org.serverct.parrot.parrotx.utils.I18n;
import pers.tany.customchunklimit.CustomChunkLimit;
import pers.tany.customchunklimit.config.DataManager;

import java.io.File;
import java.util.ArrayList;

public class LimitBlock extends PData {

    public Material block;
    public int id;
    public int dataId;
    public int limit;
    public String tile = null;
    public String botania = null;
    public Material display;

    public LimitBlock(File file, PID id) {
        super(file, id);
        load(file);
    }

    public LimitBlock(Material material, int id, int dataId, int limit, String tile, String botania, Material display) {
        super(DataManager.getInstance().getFile(), new PID(CustomChunkLimit.getInstance(), "LIMIT_BLOCK", material.name()));

        this.block = material;
        this.id = id;
        this.dataId = dataId;
        this.limit = limit;
        this.tile = tile;
        this.botania = botania;
        this.display = display;
    }

    @Override
    public String getTypeName() {
        return "限制方块数据/" + getFileName();
    }

    @Override
    public void init() {

    }

    @Override
    public void saveDefault() {

    }

    @Override
    public void load(@NonNull File file) {
        ConfigurationSection section = data.getConfigurationSection(super.id.getId());
        if (BasicUtil.isNull(plugin, section, I18n.LOAD, getTypeName(), "未找到数据节。")) {
            return;
        }
        this.block = EnumUtil.getMaterial(section.getName());
        this.id = section.getInt("ID");
        this.dataId = section.getInt("DataID");
        this.limit = section.getInt("Limit");
        this.tile = section.getString("Tile");
        this.botania = section.getString("Botania");
        this.display = EnumUtil.getMaterial(section.getString("Display"));
    }

    @Override
    public void save() {
        ConfigurationSection section = data.createSection(this.block.name());
        section.set("ID", this.id);
        section.set("DataID", this.dataId);
        section.set("Limit", this.limit);
        section.set("Tile", this.tile);
        section.set("Botania", this.botania);
        section.set("Display", this.display.name());
    }

    public ItemStack buildItem(Material material, Player viewer) {
        return new ItemStack(material == null ? Material.BARRIER : material) {{
            ItemMeta meta = getItemMeta();
            if (meta != null) {
                meta.setLore(new ArrayList<String>() {{
                    if (dataId != 999) {
                        add(I18n.color("&a被限制的方块ID: &6" + id + ":" + dataId));
                        if (tile != null) {
                            add(I18n.color("&a被禁用方块的 Tile: &6" + tile));
                            if (botania != null) {
                                add(I18n.color("&a被禁用的产能花为: &6" + botania));
                            }
                        }
                    } else {
                        add(I18n.color("&a被限制的方块ID: &6" + id + " &a下的所有同主ID"));
                    }

                    add(I18n.color("&a每设置区块被限制的数量: &e" + limit + " &a个方块"));

                    if (viewer.isOp()) {
                        add("");
                        add("&e点击移除此限制");
                    }
                }});
                setItemMeta(meta);
            }
        }};
    }
}

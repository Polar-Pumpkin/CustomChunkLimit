package pers.tany.customchunklimit.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import org.serverct.parrot.parrotx.PPlugin;
import org.serverct.parrot.parrotx.data.InventoryExecutor;
import org.serverct.parrot.parrotx.enums.Position;
import org.serverct.parrot.parrotx.utils.I18n;
import pers.tany.customchunklimit.CustomChunkLimit;
import pers.tany.customchunklimit.config.DataManager;
import pers.tany.customchunklimit.utils.BasicUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListInventory implements InventoryExecutor {

    private final Map<Integer, Map<Integer, String>> pageMap = DataManager.getInstance().pages();
    private final int NEXT_PAGE_SLOT = Position.getPosition(8, 6);
    private final int PREVIOUS_PAGE_SLOT = Position.getPosition(2, 6);
    private final Map<Integer, KeyWord> keyWordMap = new HashMap<>();
    protected PPlugin plugin = CustomChunkLimit.getInstance();
    protected Inventory inventory;
    protected Player viewer;
    protected int page;

    public ListInventory(final Player viewer, final int page) {
        this.viewer = viewer;
        this.page = Math.max(page, 1);
        this.inventory = construct();
    }

    @Override
    public Inventory construct() {
        Inventory result = Bukkit.createInventory(this, 6 * 9, "已被限制的物品 - " + page);
        Map<Integer, String> pageContent = pageMap.getOrDefault(page, new HashMap<>());
        DataManager.getInstance().reload();
        FileConfiguration data = DataManager.getInstance().getConfig();

        pageContent.forEach(
                (slot, block) -> {
                    ConfigurationSection section = data.getConfigurationSection("Block." + block);
                    if (section != null) {
                        ItemStack item = BasicUtil.getItemStack(section.getString("item"));
                        if (item == null) {
                            item = new ItemStack(Material.BARRIER);
                        }
                        ItemMeta meta = item.getItemMeta();

                        String[] dataSet = block.split("[:]");
                        int id = Integer.parseInt(dataSet[0]);
                        int dataId = Integer.parseInt(dataSet[1]);
                        int limit = section.getInt("limit");

                        if (meta != null) {
                            meta.setLore(new ArrayList<String>() {{
                                if (dataId != 999) {
                                    add(I18n.color("&a被限制的方块ID: &6" + id + ":" + dataId));
                                    if (section.getString("tile") != null) {
                                        add(I18n.color("&a被禁用方块的 Tile: &6" + section.getString("tile")));
                                        if (section.getString("botania") != null) {
                                            add(I18n.color("&a被禁用的产能花为: &6" + section.getString("botania")));
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
                            item.setItemMeta(meta);
                        }

                        result.setItem(slot, item);
                        this.keyWordMap.put(slot, KeyWord.ITEM);
                    }
                }
        );

        if (pageMap.size() > page) {
            result.setItem(NEXT_PAGE_SLOT, new ItemStack(Material.LIME_STAINED_GLASS_PANE) {{
                ItemMeta meta = getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(I18n.color("&a下"));
                    meta.setLore(new ArrayList<String>() {{
                        add(I18n.color("&c查看下一页"));
                    }});
                    setItemMeta(meta);
                }
            }});
            this.keyWordMap.put(NEXT_PAGE_SLOT, KeyWord.NEXT_PAGE);
        }

        if (pageMap.size() > 1 && page > 1) {
            result.setItem(PREVIOUS_PAGE_SLOT, new ItemStack(Material.RED_STAINED_GLASS_PANE) {{
                ItemMeta meta = getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(I18n.color("&a上"));
                    meta.setLore(new ArrayList<String>() {{
                        add(I18n.color("&c查看上一页"));
                    }});
                    setItemMeta(meta);
                }
            }});
            this.keyWordMap.put(PREVIOUS_PAGE_SLOT, KeyWord.PREVIOUS_PAGE);
        }

        return result;
    }

    @Override
    public void execute(InventoryClickEvent event) {
        event.setCancelled(true);
        KeyWord keyWord = this.keyWordMap.get(event.getSlot());
        if (keyWord == null) {
            return;
        }

        switch (keyWord) {
            case PREVIOUS_PAGE:
                org.serverct.parrot.parrotx.utils.BasicUtil.openInventory(plugin, viewer, new ListInventory(viewer, page - 1).getInventory());
                break;
            case NEXT_PAGE:
                org.serverct.parrot.parrotx.utils.BasicUtil.openInventory(plugin, viewer, new ListInventory(viewer, page + 1).getInventory());
                break;
            case ITEM:
                if (!viewer.isOp()) {
                    break;
                }
                String block = pageMap.getOrDefault(page, new HashMap<>()).getOrDefault(event.getSlot(), null);

                if (org.serverct.parrot.parrotx.utils.BasicUtil.isNull(plugin, block, I18n.LOAD, "限制方块名称", "未找到名称。(Page " + page + ", Slot " + event.getSlot() + ")")) {
                    break;
                }

                FileConfiguration data = DataManager.getInstance().getConfig();
                data.set("Block." + block, null);

                I18n.send(viewer, plugin.lang.build(plugin.localeKey, I18n.Type.INFO, "&a成功移除。"));
                refresh(event.getInventory());
                break;
        }
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    private enum KeyWord {
        ITEM, NEXT_PAGE, PREVIOUS_PAGE;
    }
}

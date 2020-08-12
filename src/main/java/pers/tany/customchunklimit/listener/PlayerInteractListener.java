package pers.tany.customchunklimit.listener;

import de.tr7zw.nbtapi.NBTTileEntity;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.serverct.parrot.parrotx.utils.I18n;
import pers.tany.customchunklimit.CustomChunkLimit;
import pers.tany.customchunklimit.config.ConfigManager;
import pers.tany.customchunklimit.config.DataManager;
import pers.tany.customchunklimit.utils.BasicUtil;

import java.util.List;
import java.util.UUID;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void Interact(PlayerInteractEvent event) {
        switch (event.getAction()) {
            case PHYSICAL:
            case LEFT_CLICK_AIR:
            case RIGHT_CLICK_AIR:
            default:
                return;
            case RIGHT_CLICK_BLOCK:
            case LEFT_CLICK_BLOCK:
                break;
        }

        Player user = event.getPlayer();
        UUID uuid = user.getUniqueId();
        FileConfiguration data = DataManager.getInstance().getConfig();
        ItemStack hold = event.getItem();
        Block clickedBlock = event.getClickedBlock();

        if (hold == null || hold.getType() == Material.AIR) {
            I18n.send(user, I18n.color("&c手持物品不能为空！"));
            return;
        }

        if (clickedBlock == null) {
            I18n.send(user, I18n.color("&cd点击了无效方块！"));
            return;
        }

        if (CustomChunkLimit.creatingMap.containsKey(uuid)) {
            event.setCancelled(true);

            String item = BasicUtil.getItemData(hold);
            int limit = CustomChunkLimit.creatingMap.get(uuid);
            String block = clickedBlock.getType().getId() + ":" + clickedBlock.getData();
            String a = "";
            String b = "";

            List<String> blocks = DataManager.getInstance().getBlocks();
            if (blocks.size() != 0) {
                for (String blockExist : blocks) {
                    if (!blockExist.equals(block)) {
                        continue;
                    }

                    ConfigurationSection section = data.getConfigurationSection(blockExist);
                    if (section == null) {
                        continue;
                    }

                    if (ConfigManager.tile) {
                        try {
                            String nbt = new NBTTileEntity(clickedBlock.getState()).toString().replace(".", "");
                            String tileExist = section.getString("tile");
                            if (tileExist != null) {
                                nbt = nbt.replace("id:\"minecraft:air\"", "");
                                nbt = nbt.split("id:\"")[1];
                                nbt = nbt.split("\"")[0];
                                if (tileExist.equals(nbt)) {
                                    if (ConfigManager.botania) {
                                        String botnaiaExist = section.getString("botania");
                                        if (nbt.contains("botania:") && botnaiaExist != null) {
                                            nbt = new NBTTileEntity(clickedBlock.getState()).toString().replace(".", "");
                                            nbt = nbt.split("subTileName:\"")[1];
                                            nbt = nbt.split("\"")[0];
                                            if (!botnaiaExist.equals(nbt))
                                                continue;
                                        }
                                    }
                                } else {
                                    continue;
                                }
                            }
                        } catch (Exception e) {
                            if (section.getString("tile") != null) {
                                continue;
                            }
                        }
                    }
                    I18n.send(user, I18n.color("&c这个方块已经被限制过了。"));
                    return;
                }
            }

            if (ConfigManager.tile) {
                try {
                    NBTTileEntity tile = new NBTTileEntity(clickedBlock.getState());
                    String nbt = tile.toString().replace(".", "");
                    nbt = nbt.replace("id:\"minecraft:air\"", "");
                    nbt = nbt.split("id:\"")[1];
                    nbt = nbt.split("\"")[0];
                    String c = nbt;
                    a = ":" + nbt;
                    I18n.send(user, I18n.color("&a检测到 Tile 存在，已记录为： &e" + nbt));
                    if (nbt.contains("botania:")) {
                        nbt = tile.toString().replace(".", "");
                        nbt = nbt.split("subTileName:\"")[1];
                        nbt = nbt.split("\"")[0];
                        b = ":" + nbt;
                        I18n.send(user, I18n.color("&a检测到为植魔产能花，已记录为： &e" + nbt));
                        data.set("Block." + block + a + b + ".botania", nbt);
                    }
                    data.set("Block." + block + a + b + ".tile", c);
                } catch (Exception ignored) {
                }
            }

            data.set("Block." + block + a + b + ".item", item);
            data.set("Block." + block + a + b + ".limit", limit);
            I18n.send(user, I18n.color("&a添加方块成功！"));
            CustomChunkLimit.creatingMap.remove(uuid);
            return;
        }
        if (CustomChunkLimit.creatingAllMap.containsKey(uuid)) {
            event.setCancelled(true);

            String item = BasicUtil.getItemData(hold);
            int limit = CustomChunkLimit.creatingAllMap.get(uuid);
            String block = clickedBlock.getType().getId() + ":999";

            List<String> blocks = DataManager.getInstance().getBlocks();
            if (!blocks.isEmpty()) {
                for (String blockExist : blocks) {
                    if (blockExist.equals(block)) {
                        I18n.send(user, I18n.color("&c这个方块已经被限制过了。"));
                        return;
                    }
                }
            }

            data.set("Block." + block + ".item", item);
            data.set("Block." + block + ".limit", limit);
            I18n.send(user, I18n.color("&a添加方块成功！"));
            CustomChunkLimit.creatingAllMap.remove(uuid);
        }
    }
}

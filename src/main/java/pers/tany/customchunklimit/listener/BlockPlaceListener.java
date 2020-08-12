package pers.tany.customchunklimit.listener;

import de.tr7zw.nbtapi.NBTTileEntity;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.serverct.parrot.parrotx.PPlugin;
import org.serverct.parrot.parrotx.utils.I18n;
import pers.tany.customchunklimit.CustomChunkLimit;
import pers.tany.customchunklimit.config.ConfigManager;
import pers.tany.customchunklimit.config.DataManager;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class BlockPlaceListener implements Listener {

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player user = event.getPlayer();
        PPlugin plugin = CustomChunkLimit.getInstance();
        FileConfiguration data = DataManager.getInstance().getConfig();
        List<String> blocks = DataManager.getInstance().getBlocks();
        Block placeBlock = event.getBlock();

        if (user.isOp()) {
            return;
        }
        if (blocks.size() == 0) {
            return;
        }
        if (placeBlock == null || placeBlock.getType() == Material.AIR) {
            return;
        }

        for (String block : blocks) {
            int id = Integer.parseInt(block.split(":")[0]);
            int dataId = Integer.parseInt(block.split(":")[1]);
            if (dataId == 999) {
                if (placeBlock.getType().getId() == id) {
                    break;
                }
            } else {
                if (placeBlock.getType().getId() == id && placeBlock.getData() == dataId) {
                    break;
                }
            }
        }

        Chunk chunk = event.getBlock().getChunk();
        if (!ConfigManager.optimization) {
            Chunk[] chunklist = new Chunk[1];
            if (!ConfigManager.niceChunk) {
                chunklist[0] = chunk;
            } else if (ConfigManager.niceChunkImprove) {
                chunklist = new Chunk[25];
                Location location = placeBlock.getLocation();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[0] = location.getChunk();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[1] = location.getChunk();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16);
                chunklist[2] = location.getChunk();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[3] = location.getChunk();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[4] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[5] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[6] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16);
                chunklist[7] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[8] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[9] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[10] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[11] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16);
                chunklist[12] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[13] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[14] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[15] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[16] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16);
                chunklist[17] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[18] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[19] = location.getChunk();


                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[20] = location.getChunk();

                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[21] = location.getChunk();

                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16);
                chunklist[22] = location.getChunk();


                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[23] = location.getChunk();

                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[24] = location.getChunk();
            } else {
                chunklist = new Chunk[9];
                Location location = placeBlock.getLocation();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[0] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[1] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[2] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[3] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16);
                chunklist[4] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16);
                chunklist[5] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[6] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[7] = location.getChunk();

                chunklist[8] = event.getBlock().getChunk();
            }
            AtomicInteger number = new AtomicInteger(0);
            int[] limit = new int[1];
            limit[0] = 99999;
            ConcurrentLinkedQueue<Block> blockList = new ConcurrentLinkedQueue<>();
            Stream<Chunk> stream = Stream.of(chunklist);
            if (chunklist.length > 1) {
                stream = stream.parallel();
            }
            stream.forEach(chunks -> {
                Block block;
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = 0; y < 256; y++) {
                            block = chunks.getBlock(x, y, z);
                            if (block.getType() != event.getBlock().getType())
                                continue;
                            for (String limitBlock : blocks) {
                                int id = Integer.parseInt(limitBlock.split(":")[0]);
                                int dataId = Integer.parseInt(limitBlock.split(":")[1]);
                                if (dataId == 999) {
                                    if (block.getType().getId() == id) {
                                        if (number.get() == 0) {
                                            limit[0] = data.getInt("Block." + limitBlock + ".limit");
                                            for (PermissionAttachmentInfo p : event.getPlayer().getEffectivePermissions()) {
                                                if (p.getPermission().startsWith("ccl." + id + "." + dataId + ".")) {
                                                    try {
                                                        limit[0] = limit[0] + Integer.parseInt(p.getPermission().split("\\.")[3]);
                                                        break;
                                                    } catch (NumberFormatException ignored) {
                                                    }
                                                }
                                            }
                                        }
                                        if (event.getBlock().equals(block)) {
                                            continue;
                                        }
                                        number.incrementAndGet();
                                        if (number.get() > limit[0] && ConfigManager.autoClear) {
                                            blockList.offer(block);
                                        }
                                    }
                                } else {
                                    if (ConfigManager.tile) {
                                        try {
                                            String nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
                                            String getnbt = new NBTTileEntity(event.getBlock().getState()).toString().replace(".", "");
                                            nbt = nbt.replace("id:\"minecraft:air\"", "");
                                            nbt = nbt.split("id:\"")[1];
                                            nbt = nbt.split("\"")[0];
                                            getnbt = getnbt.replace("id:\"minecraft:air\"", "");
                                            getnbt = getnbt.split("id:\"")[1];
                                            getnbt = getnbt.split("\"")[0];
                                            if (nbt.equals(getnbt)) {
                                                if (ConfigManager.botania && nbt.contains("botania:") && getnbt.contains("botania:")) {
                                                    nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
                                                    getnbt = new NBTTileEntity(event.getBlock().getState()).toString().replace(".", "");
                                                    nbt = nbt.split("subTileName:\"")[1];
                                                    nbt = nbt.split("\"")[0];
                                                    getnbt = getnbt.split("subTileName:\"")[1];
                                                    getnbt = getnbt.split("\"")[0];
                                                    if (!nbt.equals(getnbt)) {
                                                        continue;
                                                    }
                                                }
                                            } else {
                                                continue;
                                            }
                                        } catch (Exception d) {
                                            continue;
                                        }
                                    }
                                    if (block.getType().getId() == id && block.getData() == dataId) {
                                        if (ConfigManager.tile) {
                                            try {
                                                String nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
                                                if (data.getString("Block." + limitBlock + ".tile") != null) {
                                                    nbt = nbt.replace("id:\"minecraft:air\"", "");
                                                    nbt = nbt.split("id:\"")[1];
                                                    nbt = nbt.split("\"")[0];
                                                    if (data.getString("Block." + limitBlock + ".tile").equals(nbt)) {
                                                        if (ConfigManager.botania) {
                                                            if (nbt.contains("botania:") && data.getString("Block." + limitBlock + ".botania") != null) {
                                                                nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
                                                                nbt = nbt.split("subTileName:\"")[1];
                                                                nbt = nbt.split("\"")[0];
                                                                if (!data.getString("Block." + limitBlock + ".botania").equals(nbt)) {
                                                                    continue;
                                                                }
                                                            }
                                                        }
                                                    } else {
                                                        continue;
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (data.getString("Block." + limitBlock + ".tile") != null) {
                                                    continue;
                                                }
                                            }
                                        }
                                        if (number.get() == 0) {
                                            limit[0] = data.getInt("Block." + limitBlock + ".limit");
                                            for (PermissionAttachmentInfo p : event.getPlayer().getEffectivePermissions()) {
                                                if (p.getPermission().startsWith("ccl." + id + "." + dataId + ".")) {
                                                    try {
                                                        limit[0] = limit[0] + Integer.parseInt(p.getPermission().split("\\.")[3]);
                                                        break;
                                                    } catch (NumberFormatException ignored) {
                                                    }
                                                }
                                            }
                                        }
                                        if (event.getBlock().equals(block)) {
                                            continue;
                                        }
                                        number.incrementAndGet();
                                        if (number.get() > limit[0] && ConfigManager.autoClear) {
                                            blockList.offer(block);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
            if (number.get() >= limit[0]) {
                event.setCancelled(true);
                I18n.send(user, plugin.lang.get(plugin.localeKey, I18n.Type.WARN, "Limit", "Exceed").replace("[limit]", String.valueOf(limit[0])));
                if (number.get() > limit[0] && ConfigManager.autoClear) {
                    for (Block block : blockList) {
                        block.breakNaturally();
                    }
                }
            }
        } else {
            int customy;
            int limity;
            if (event.getBlock().getY() >= ConfigManager.yAxis) {
                customy = event.getBlock().getY() - ConfigManager.yAxis;
            } else {
                customy = 0;
            }
            if (event.getBlock().getY() + ConfigManager.yAxis >= 256) {
                limity = 255;
            } else {
                limity = event.getBlock().getY() + ConfigManager.yAxis;
            }
            Chunk[] chunklist = new Chunk[1];
            if (!ConfigManager.niceChunk) {
                chunklist[0] = chunk;
            } else if (ConfigManager.niceChunkImprove) {
                chunklist = new Chunk[25];
                Location location = event.getBlock().getLocation();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[0] = location.getChunk();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[1] = location.getChunk();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16);
                chunklist[2] = location.getChunk();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[3] = location.getChunk();

                location.setX(chunk.getX() * 16 - 17);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[4] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[5] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[6] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16);
                chunklist[7] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[8] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[9] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[10] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[11] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16);
                chunklist[12] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[13] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[14] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[15] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[16] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16);
                chunklist[17] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[18] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[19] = location.getChunk();


                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16 - 17);
                chunklist[20] = location.getChunk();

                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[21] = location.getChunk();

                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16);
                chunklist[22] = location.getChunk();


                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[23] = location.getChunk();

                location.setX(chunk.getX() * 16 + 32);
                location.setZ(chunk.getZ() * 16 + 32);
                chunklist[24] = location.getChunk();
            } else {
                chunklist = new Chunk[9];
                Location location = event.getBlock().getLocation();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[0] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[1] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[2] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[3] = location.getChunk();

                location.setX(chunk.getX() * 16 + 16);
                location.setZ(chunk.getZ() * 16);
                chunklist[4] = location.getChunk();

                location.setX(chunk.getX() * 16 - 1);
                location.setZ(chunk.getZ() * 16);
                chunklist[5] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 + 16);
                chunklist[6] = location.getChunk();

                location.setX(chunk.getX() * 16);
                location.setZ(chunk.getZ() * 16 - 1);
                chunklist[7] = location.getChunk();

                chunklist[8] = event.getBlock().getChunk();
            }
            AtomicInteger number = new AtomicInteger(0);
            int[] limit = new int[1];
            limit[0] = 99999;
            ConcurrentLinkedQueue<Block> blockList = new ConcurrentLinkedQueue<>();
            Stream<Chunk> stream = Stream.of(chunklist);
            if (chunklist.length > 1) {
                stream = stream.parallel();
            }
            stream.forEach(chunks -> {
                Block block;
                for (int x = 0; x < 16; x++) {
                    for (int z = 0; z < 16; z++) {
                        for (int y = customy; y <= limity; y++) {
                            block = chunks.getBlock(x, y, z);
                            if (block.getType() != event.getBlock().getType())
                                continue;
                            for (String limitBlock : blocks) {
                                int id = Integer.parseInt(limitBlock.split(":")[0]);
                                int dataId = Integer.parseInt(limitBlock.split(":")[1]);
                                if (dataId == 999) {
                                    if (block.getType().getId() == id) {
                                        if (number.get() == 0) {
                                            limit[0] = data.getInt("Block." + limitBlock + ".limit");
                                            for (PermissionAttachmentInfo p : event.getPlayer().getEffectivePermissions()) {
                                                if (p.getPermission().startsWith("ccl." + id + "." + dataId + ".")) {
                                                    try {
                                                        limit[0] = limit[0] + Integer.parseInt(p.getPermission().split("\\.")[3]);
                                                        break;
                                                    } catch (NumberFormatException ignored) {
                                                    }
                                                }
                                            }
                                        }
                                        if (event.getBlock().equals(block)) {
                                            continue;
                                        }
                                        number.incrementAndGet();
                                        if (number.get() > limit[0] && ConfigManager.autoClear) {
                                            blockList.offer(block);
                                        }
                                    }
                                } else {
                                    if (ConfigManager.tile) {
                                        try {
                                            new NBTTileEntity(event.getBlock().getState()).toString().replace(".", "");
                                            String nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
                                            String getnbt = new NBTTileEntity(event.getBlock().getState()).toString().replace(".", "");
                                            nbt = nbt.replace("id:\"minecraft:air\"", "");
                                            nbt = nbt.split("id:\"")[1];
                                            nbt = nbt.split("\"")[0];
                                            getnbt = getnbt.replace("id:\"minecraft:air\"", "");
                                            getnbt = getnbt.split("id:\"")[1];
                                            getnbt = getnbt.split("\"")[0];
                                            if (nbt.equals(getnbt)) {
                                                if (ConfigManager.botania && nbt.contains("botania:") && getnbt.contains("botania:")) {
                                                    nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
                                                    getnbt = new NBTTileEntity(event.getBlock().getState()).toString().replace(".", "");
                                                    nbt = nbt.split("subTileName:\"")[1];
                                                    nbt = nbt.split("\"")[0];
                                                    getnbt = getnbt.split("subTileName:\"")[1];
                                                    getnbt = getnbt.split("\"")[0];
                                                    if (!nbt.equals(getnbt)) {
                                                        continue;
                                                    }
                                                }
                                            } else {
                                                continue;
                                            }
                                        } catch (Exception d) {
                                            continue;
                                        }
                                    }
                                    if (block.getType().getId() == id && block.getData() == dataId) {
                                        if (ConfigManager.tile) {
                                            try {
                                                String nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
                                                if (data.getString("Block." + limitBlock + ".tile") != null) {
                                                    nbt = nbt.replace("id:\"minecraft:air\"", "");
                                                    nbt = nbt.split("id:\"")[1];
                                                    nbt = nbt.split("\"")[0];
                                                    if (data.getString("Block." + limitBlock + ".tile").equals(nbt)) {
                                                        if (ConfigManager.botania) {
                                                            if (nbt.contains("botania:") && data.getString("Block." + limitBlock + ".botania") != null) {
                                                                nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
                                                                nbt = nbt.split("subTileName:\"")[1];
                                                                nbt = nbt.split("\"")[0];
                                                                if (!data.getString("Block." + limitBlock + ".botania").equals(nbt))
                                                                    continue;
                                                            }
                                                        }
                                                    } else {
                                                        continue;
                                                    }
                                                }
                                            } catch (Exception e) {
                                                if (data.getString("Block." + limitBlock + ".tile") != null) {
                                                    continue;
                                                }
                                            }
                                        }
                                        if (number.get() == 0) {
                                            limit[0] = data.getInt("Block." + limitBlock + ".limit");
                                            for (PermissionAttachmentInfo p : event.getPlayer().getEffectivePermissions()) {
                                                if (p.getPermission().startsWith("ccl." + id + "." + dataId + ".")) {
                                                    try {
                                                        limit[0] = limit[0] + Integer.parseInt(p.getPermission().split("\\.")[3]);
                                                        break;
                                                    } catch (NumberFormatException ignored) {
                                                    }
                                                }
                                            }
                                        }
                                        if (event.getBlock().equals(block)) {
                                            continue;
                                        }
                                        number.incrementAndGet();
                                        if (number.get() > limit[0] && ConfigManager.autoClear) {
                                            blockList.offer(block);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            });
            if (number.get() >= limit[0]) {
                event.setCancelled(true);
                I18n.send(user, plugin.lang.get(plugin.localeKey, I18n.Type.WARN, "Limit", "Exceed").replace("[limit]", String.valueOf(limit[0])));
                if (number.get() > limit[0] && ConfigManager.autoClear) {
                    for (Block block : blockList) {
                        block.breakNaturally();
                    }
                }
            }
        }
    }

}

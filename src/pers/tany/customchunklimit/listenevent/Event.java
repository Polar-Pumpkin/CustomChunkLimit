package pers.tany.customchunklimit.listenevent;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.permissions.PermissionAttachmentInfo;

import de.tr7zw.nbtapi.NBTTileEntity;
import pers.tany.customchunklimit.CommonlyWay;
import pers.tany.customchunklimit.Main;
import pers.tany.customchunklimit.Other;
import pers.tany.customchunklimit.gui.Gui;

public class Event implements Listener {
    File file=new File(Main.plugin.getDataFolder(),"data.yml");
    
    @EventHandler
    public void Interact(PlayerInteractEvent evt) {
		if(Main.Create.containsKey(evt.getPlayer().getName())) {
			if(!(evt.getPlayer().getInventory().getItemInHand() == null || evt.getPlayer().getInventory().getItemInHand().getType() == Material.AIR)) {
				evt.setCancelled(true);
				if(evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)||evt.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					String item = CommonlyWay.GetItemData(evt.getPlayer().getInventory().getItemInHand());
					int limit = Main.Create.get(evt.getPlayer().getName());
					String block = evt.getClickedBlock().getType().getId()+":"+evt.getClickedBlock().getData();
					String a = "";
					String b = "";
					if(Other.data.getConfigurationSection("Block").getKeys(false).size()!=0) {
						for(String blocks:Other.data.getConfigurationSection("Block").getKeys(false)) {
							if(blocks.equals(block)) {
								if(Other.config.getBoolean("Tile")) {
									try {
										String nbt = new NBTTileEntity(evt.getClickedBlock().getState()).toString().replace(".", "");
										if(Other.data.getString("Block."+blocks+".tile")!=null) {
								    		nbt = nbt.replace("id:\"minecraft:air\"", "");
								    		nbt = nbt.split("id:\"")[1];
								    		nbt = nbt.split("\"")[0];
								    		if(Other.data.getString("Block."+blocks+".tile").equals(nbt)) {
								    			if(Other.config.getBoolean("Botania")) {
									    			if(nbt.contains("botania:")&&Other.data.getString("Block."+blocks+".botania")!=null) {
									    				nbt = new NBTTileEntity(evt.getClickedBlock().getState()).toString().replace(".", "");
									    				nbt = nbt.split("subTileName:\"")[1];
									    				nbt = nbt.split("\"")[0];
									    				if(!Other.data.getString("Block."+blocks+".botania").equals(nbt))
										        			continue;
									    			}
								    			}
								    		} else {
								    			continue;
								    		}
										}
									} catch (Exception e) {
										if(Other.data.getString("Block."+blocks+".tile")!=null) {
											continue;
										}
						        	}
								}
								evt.getPlayer().sendMessage("��c��������Ѿ������ƹ���");
								evt.setCancelled(true);
								return;
							}
						}
					}
					if(Other.config.getBoolean("Tile")) {
				  		try {
				  			NBTTileEntity  tile = new NBTTileEntity(evt.getClickedBlock().getState());
				    		String nbt = tile.toString().replace(".", "");
				    		nbt = nbt.replace("id:\"minecraft:air\"", "");
				    		nbt = nbt.split("id:\"")[1];
				    		nbt = nbt.split("\"")[0];
				    		String c = nbt;
				    		a = ":"+nbt;
							evt.getPlayer().sendMessage("��a��⵽tile���ڣ��Ѽ�¼Ϊ����e"+nbt);
				    		if(nbt.contains("botania:")) {
				    			nbt = tile.toString().replace(".", "");
				    			nbt = nbt.split("subTileName:\"")[1];
				    			nbt = nbt.split("\"")[0];
				        		b = ":"+nbt;
								evt.getPlayer().sendMessage("��a��⵽Ϊֲħ���ܻ����Ѽ�¼Ϊ����6"+nbt);
								Other.data.set("Block."+block+a+b+".botania", nbt);
				    		}
							Other.data.set("Block."+block+a+b+".tile", c);
				  		} catch (Exception e) {
			        		
			        	}
					}
					Other.data.set("Block."+block+a+b+".item", item);
					Other.data.set("Block."+block+a+b+".limit", limit);
			  		try {
			  			Other.data.save(file);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		evt.getPlayer().sendMessage("��a��ӷ���ɹ���");
			  		Main.Create.remove(evt.getPlayer().getName());
				}
			} else {
				evt.getPlayer().sendMessage("��c���ϲ���Ϊ�գ�");
			}
			evt.setCancelled(true);
			return;
		}
		if(Main.CreateAll.containsKey(evt.getPlayer().getName())) {
			if(!(evt.getPlayer().getInventory().getItemInHand() == null || evt.getPlayer().getInventory().getItemInHand().getType() == Material.AIR)) {
				if(evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)||evt.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
					String item = CommonlyWay.GetItemData(evt.getPlayer().getInventory().getItemInHand());
					int limit = Main.CreateAll.get(evt.getPlayer().getName());
					String block = evt.getClickedBlock().getType().getId()+":999";
					if(Other.data.getConfigurationSection("Block").getKeys(false).size()>0) {
						for(String blocks:Other.data.getConfigurationSection("Block").getKeys(false)) {
							if(blocks.equals(block)) {
								evt.getPlayer().sendMessage("��c��������Ѿ������ƹ���");
								evt.setCancelled(true);
								return;
							}
						}
					}
					Other.data.set("Block."+block+".item", item);
					Other.data.set("Block."+block+".limit", limit);
			  		try {
			  			Other.data.save(file);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		evt.getPlayer().sendMessage("��a��ӷ���ɹ���");
			  		Main.CreateAll.remove(evt.getPlayer().getName());
				} else {
					evt.getPlayer().sendMessage("��c��������");
				}
			}else {
				evt.getPlayer().sendMessage("��c���ϲ���Ϊ�գ�");
			}
			evt.setCancelled(true);
			return;
		}
		if(Other.data.getConfigurationSection("Block").getKeys(false).size()==0) {
			return;
		}
    }
	@EventHandler
	public void Place(BlockPlaceEvent evt) {
		if(evt.getPlayer().hasPermission("ccl.op")){
			return;
		}
		if(Other.data.getConfigurationSection("Block").getKeys(false).size()==0) {
			return;
		}
		int a=0;
		for(String limitstring:Other.data.getConfigurationSection("Block").getKeys(false)) {
			int id = Integer.parseInt(limitstring.split(":")[0]);
			int data = Integer.parseInt(limitstring.split(":")[1]);
			if(data==999) {
				if(evt.getBlock().getType().getId()==id) {
					break;
				}
			} else {
				if(evt.getBlock().getType().getId()==id&&evt.getBlock().getData()==data) {
					break;
				}
			}
			a++;
			if(a==Other.data.getConfigurationSection("Block").getKeys(false).size()) {
				return;
			}
		}
		Chunk chunk = evt.getBlock().getChunk();
		if(!Other.config.getBoolean("Optimization")) {
			Chunk[] chunklist = new Chunk[1];
			if(!Other.config.getBoolean("NineChunk")) {
				chunklist[0] = chunk;
			} else if(Other.config.getBoolean("NineChunkImprove")) {
				chunklist = new Chunk[25];
				Location location = evt.getBlock().getLocation();
				
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16-17);
				chunklist[0] = location.getChunk();
				
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16-1);		
				chunklist[1] = location.getChunk();
				
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16);	
				chunklist[2] = location.getChunk();
					
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16+16);
				chunklist[3] = location.getChunk();
					
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16+32);
				chunklist[4] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16-17);
				chunklist[5] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16-1);
				chunklist[6] = location.getChunk();
						
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16);
				chunklist[7] = location.getChunk();
						
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16+16);
				chunklist[8] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16+32);
				chunklist[9] = location.getChunk();
				
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16-17);
				chunklist[10] = location.getChunk();
						
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16-1);
				chunklist[11] = location.getChunk();
						
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16);
				chunklist[12] = location.getChunk();
						
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16+16);
				chunklist[13] = location.getChunk();
						
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16+32);
				chunklist[14] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16-17);
				chunklist[15] = location.getChunk();
						
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16-1);
				chunklist[16] = location.getChunk();
						
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16);
				chunklist[17] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16+16);
				chunklist[18] = location.getChunk();
						
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16+32);
				chunklist[19] = location.getChunk();
						
				
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16-17);
				chunklist[20] = location.getChunk();
						
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16-1);
				chunklist[21] = location.getChunk();
						
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16);
				chunklist[22] = location.getChunk();
						
				
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16+16);
				chunklist[23] = location.getChunk();
						
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16+32);
				chunklist[24] = location.getChunk();
			} else {
				chunklist = new Chunk[9];
				Location location = evt.getBlock().getLocation();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16-1);
				chunklist[0] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16+16);
				chunklist[1] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16-1);
				chunklist[2] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16+16);			
				chunklist[3] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16);
				chunklist[4] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16);
				chunklist[5] = location.getChunk();
				
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16+16);
				chunklist[6] = location.getChunk();
				
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16-1);
				chunklist[7] = location.getChunk();
				
				chunklist[8] = evt.getBlock().getChunk();
			}
			AtomicInteger number = new AtomicInteger(0);
			int[] limit = new int[1];
			limit[0] = 99999;
			ConcurrentLinkedQueue<Block> blocklist = new ConcurrentLinkedQueue<Block>();
			Stream<Chunk> stream = Stream.of(chunklist);
			if (chunklist.length > 1) { 
				stream = stream.parallel();
			}
			stream.forEach(chunks -> {
				Block block;
				for(int x=0;x<16;x++) {
					for(int z=0;z<16;z++) {
						for(int y=0;y<256;y++) {
							block = chunks.getBlock(x, y, z);
							if(block.getType()!=evt.getBlock().getType())
								continue;
							for(String limitstring:Other.data.getConfigurationSection("Block").getKeys(false)) {
								int id = Integer.parseInt(limitstring.split(":")[0]);
								int data = Integer.parseInt(limitstring.split(":")[1]);
								if(data==999) {
									if(block.getType().getId()==id) {
										if(number.get()==0) {
											limit[0] = Other.data.getInt("Block."+limitstring+".limit");
											for(PermissionAttachmentInfo p:evt.getPlayer().getEffectivePermissions()) {
												if(p.getPermission().startsWith("ccl."+id+"."+data+".")) {
													try {
														limit[0] = limit[0]+Integer.parseInt(p.getPermission().split("\\.")[3]);
														break;
														}	catch (NumberFormatException e)	{
														continue;
													}
												}
											}
										}
										if(evt.getBlock().equals(block)) 
										{
											continue;
										}
										number.incrementAndGet();
										if(number.get()>limit[0]&&Other.data.getBoolean("Clear")) {
											blocklist.offer(block);
										}
									}
								} else {
									if(Other.config.getBoolean("Tile")) {
										try {
											new NBTTileEntity(block.getState()).toString().replace(".", "");
											try {
												new NBTTileEntity(evt.getBlock().getState()).toString().replace(".", "");
												String nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
												String getnbt = new NBTTileEntity(evt.getBlock().getState()).toString().replace(".", "");
									    		nbt = nbt.replace("id:\"minecraft:air\"", "");
									    		nbt = nbt.split("id:\"")[1];
									    		nbt = nbt.split("\"")[0];
									    		getnbt = getnbt.replace("id:\"minecraft:air\"", "");
									    		getnbt = getnbt.split("id:\"")[1];
									    		getnbt = getnbt.split("\"")[0];
									    		if(nbt.equals(getnbt)) {
									    			if(Other.config.getBoolean("Botania")&&nbt.contains("botania:")&&getnbt.contains("botania:")) {
														nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
														getnbt = new NBTTileEntity(evt.getBlock().getState()).toString().replace(".", "");
														nbt = nbt.split("subTileName:\"")[1];
														nbt = nbt.split("\"")[0];
														getnbt = getnbt.split("subTileName:\"")[1];
														getnbt = getnbt.split("\"")[0];
														if(!nbt.equals(getnbt)) {
															continue;
														}
									    			}
									    		} else {
									    			continue;
									    		}
											} catch (Exception d) {
												continue;
											}
										} catch (Exception e) {
											try {
												new NBTTileEntity(evt.getBlock().getState()).toString().replace(".", "");
												continue;
											} catch (Exception d) {
												
											}
										}
									}
									if(block.getType().getId()==id&&block.getData()==data) {
										if(Other.config.getBoolean("Tile")) {
											try {
												String nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
												if(Other.data.getString("Block."+limitstring+".tile")!=null) {
										    		nbt = nbt.replace("id:\"minecraft:air\"", "");
										    		nbt = nbt.split("id:\"")[1];
										    		nbt = nbt.split("\"")[0];
										    		if(Other.data.getString("Block."+limitstring+".tile").equals(nbt)) {
										    			if(Other.config.getBoolean("Botania")) {
											    			if(nbt.contains("botania:")&&Other.data.getString("Block."+limitstring+".botania")!=null) {
											    				nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
											    				nbt = nbt.split("subTileName:\"")[1];
											    				nbt = nbt.split("\"")[0];
												        		if(!Other.data.getString("Block."+limitstring+".botania").equals(nbt)) {
												        			continue;
												        		}
											    			}
										    			}
										    		} else {
										    			continue;
										    		}
												}
											} catch (Exception e) {
												if(Other.data.getString("Block."+limitstring+".tile")!=null) {
													continue;
												}
								        	}
										}
										if(number.get()==0) {
											limit[0] = Other.data.getInt("Block."+limitstring+".limit");
											for(PermissionAttachmentInfo p:evt.getPlayer().getEffectivePermissions()) {
												if(p.getPermission().startsWith("ccl."+id+"."+data+".")) {
													try {
														limit[0] = limit[0]+Integer.parseInt(p.getPermission().split("\\.")[3]);
														break;
														}	catch (NumberFormatException e)	{
														continue;
													}
												}
											}
										}
										if(evt.getBlock().equals(block)) 
										{
											continue;
										}
										number.incrementAndGet();
										if(number.get()>limit[0]&&Other.data.getBoolean("Clear")) {
											blocklist.offer(block);
										}
									}
								}
							}
						}
					}
				}
			});
			if(number.get()>=limit[0]) {
				evt.setCancelled(true);
				evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ExceedLimit").replace("[limit]", limit[0]+"")));
				if(number.get()>limit[0]&&Other.data.getBoolean("Clear")) {
					for(Block block:blocklist) {
						block.breakNaturally();
					}
				}
				return;
			}
		} else {
			int customy;
			int limity;
			if(evt.getBlock().getY()>=Other.config.getInt("YAxis")) {
				customy = evt.getBlock().getY()-Other.config.getInt("YAxis");
			} else {
				customy = 0;
			}
			if(evt.getBlock().getY()+Other.config.getInt("YAxis")>=256) {
				limity = 255;
			} else {
				limity = evt.getBlock().getY()+Other.config.getInt("YAxis");
			}
			Chunk[] chunklist = new Chunk[1];
			if(!Other.config.getBoolean("NineChunk")) {
				chunklist[0] = chunk;
			} else if(Other.config.getBoolean("NineChunkImprove")) {
				chunklist = new Chunk[25];
				Location location = evt.getBlock().getLocation();
				
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16-17);
				chunklist[0] = location.getChunk();
				
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16-1);		
				chunklist[1] = location.getChunk();
				
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16);	
				chunklist[2] = location.getChunk();
					
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16+16);
				chunklist[3] = location.getChunk();
					
				location.setX(chunk.getX()*16-17);
				location.setZ(chunk.getZ()*16+32);
				chunklist[4] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16-17);
				chunklist[5] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16-1);
				chunklist[6] = location.getChunk();
						
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16);
				chunklist[7] = location.getChunk();
						
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16+16);
				chunklist[8] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16+32);
				chunklist[9] = location.getChunk();
				
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16-17);
				chunklist[10] = location.getChunk();
						
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16-1);
				chunklist[11] = location.getChunk();
						
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16);
				chunklist[12] = location.getChunk();
						
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16+16);
				chunklist[13] = location.getChunk();
						
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16+32);
				chunklist[14] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16-17);
				chunklist[15] = location.getChunk();
						
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16-1);
				chunklist[16] = location.getChunk();
						
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16);
				chunklist[17] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16+16);
				chunklist[18] = location.getChunk();
						
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16+32);
				chunklist[19] = location.getChunk();
						
				
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16-17);
				chunklist[20] = location.getChunk();
						
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16-1);
				chunklist[21] = location.getChunk();
						
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16);
				chunklist[22] = location.getChunk();
						
				
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16+16);
				chunklist[23] = location.getChunk();
						
				location.setX(chunk.getX()*16+32);
				location.setZ(chunk.getZ()*16+32);
				chunklist[24] = location.getChunk();
			} else {
				chunklist = new Chunk[9];
				Location location = evt.getBlock().getLocation();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16-1);
				chunklist[0] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16+16);
				chunklist[1] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16-1);
				chunklist[2] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16+16);			
				chunklist[3] = location.getChunk();
				
				location.setX(chunk.getX()*16+16);
				location.setZ(chunk.getZ()*16);
				chunklist[4] = location.getChunk();
				
				location.setX(chunk.getX()*16-1);
				location.setZ(chunk.getZ()*16);
				chunklist[5] = location.getChunk();
				
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16+16);
				chunklist[6] = location.getChunk();
				
				location.setX(chunk.getX()*16);
				location.setZ(chunk.getZ()*16-1);
				chunklist[7] = location.getChunk();
				
				chunklist[8] = evt.getBlock().getChunk();
			}
			AtomicInteger number = new AtomicInteger(0);
			int[] limit = new int[1];
			limit[0] = 99999;
			ConcurrentLinkedQueue<Block> blocklist = new ConcurrentLinkedQueue<Block>();
			Stream<Chunk> stream = Stream.of(chunklist);
			if (chunklist.length > 1) { 
				stream = stream.parallel();
			}
			stream.forEach(chunks -> {
				Block block;
				for(int x=0;x<16;x++) {
					for(int z=0;z<16;z++) {
						for(int y=customy;y<=limity;y++) {
							block = chunks.getBlock(x, y, z);
							if(block.getType()!=evt.getBlock().getType())
								continue;
							for(String limitstring:Other.data.getConfigurationSection("Block").getKeys(false)) {
								int id = Integer.parseInt(limitstring.split(":")[0]);
								int data = Integer.parseInt(limitstring.split(":")[1]);
								if(data==999) {
									if(block.getType().getId()==id) {
										if(number.get()==0) {
											limit[0] = Other.data.getInt("Block."+limitstring+".limit");
											for(PermissionAttachmentInfo p:evt.getPlayer().getEffectivePermissions()) {
												if(p.getPermission().startsWith("ccl."+id+"."+data+".")) {
													try {
														limit[0] = limit[0]+Integer.parseInt(p.getPermission().split("\\.")[3]);
														break;
														}	catch (NumberFormatException e)	{
														continue;
													}
												}
											}
										}
										if(evt.getBlock().equals(block)) 
										{
											continue;
										}
										number.incrementAndGet();
										if(number.get()>limit[0]&&Other.data.getBoolean("Clear")) {
											blocklist.offer(block);
										}
									}
								} else {
									if(Other.config.getBoolean("Tile")) {
										try {
											new NBTTileEntity(block.getState()).toString().replace(".", "");
											try {
												new NBTTileEntity(evt.getBlock().getState()).toString().replace(".", "");
												String nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
												String getnbt = new NBTTileEntity(evt.getBlock().getState()).toString().replace(".", "");
									    		nbt = nbt.replace("id:\"minecraft:air\"", "");
									    		nbt = nbt.split("id:\"")[1];
									    		nbt = nbt.split("\"")[0];
									    		getnbt = getnbt.replace("id:\"minecraft:air\"", "");
									    		getnbt = getnbt.split("id:\"")[1];
									    		getnbt = getnbt.split("\"")[0];
									    		if(nbt.equals(getnbt)) {
									    			if(Other.config.getBoolean("Botania")&&nbt.contains("botania:")&&getnbt.contains("botania:")) {
														nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
														getnbt = new NBTTileEntity(evt.getBlock().getState()).toString().replace(".", "");
														nbt = nbt.split("subTileName:\"")[1];
														nbt = nbt.split("\"")[0];
														getnbt = getnbt.split("subTileName:\"")[1];
														getnbt = getnbt.split("\"")[0];
														if(!nbt.equals(getnbt)) {
															continue;
														}
									    			}
									    		} else {
									    			continue;
									    		}
											} catch (Exception d) {
												continue;
											}
										} catch (Exception e) {
											try {
												new NBTTileEntity(evt.getBlock().getState()).toString().replace(".", "");
												continue;
											} catch (Exception d) {
												
											}
										}
									}
									if(block.getType().getId()==id&&block.getData()==data) {
										if(Other.config.getBoolean("Tile")) {
											try {
												String nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
												if(Other.data.getString("Block."+limitstring+".tile")!=null) {
										    		nbt = nbt.replace("id:\"minecraft:air\"", "");
										    		nbt = nbt.split("id:\"")[1];
										    		nbt = nbt.split("\"")[0];
										    		if(Other.data.getString("Block."+limitstring+".tile").equals(nbt)) {
										    			if(Other.config.getBoolean("Botania")) {
											    			if(nbt.contains("botania:")&&Other.data.getString("Block."+limitstring+".botania")!=null) {
											    				nbt = new NBTTileEntity(block.getState()).toString().replace(".", "");
											    				nbt = nbt.split("subTileName:\"")[1];
												        		nbt = nbt.split("\"")[0];
												        		if(!Other.data.getString("Block."+limitstring+".botania").equals(nbt))
												        			continue;
											    			}
										    			}
										    		} else {
										    			continue;
										    		}
												}
											} catch (Exception e) {
												if(Other.data.getString("Block."+limitstring+".tile")!=null) {
													continue;
												}
								        	}
										}
										if(number.get()==0) {
											limit[0] = Other.data.getInt("Block."+limitstring+".limit");
											for(PermissionAttachmentInfo p:evt.getPlayer().getEffectivePermissions()) {
												if(p.getPermission().startsWith("ccl."+id+"."+data+".")) {
													try {
														limit[0] = limit[0]+Integer.parseInt(p.getPermission().split("\\.")[3]);
														break;
														}	catch (NumberFormatException e)	{
														continue;
													}
												}
											}
										}
										if(evt.getBlock().equals(block)) 
										{
											continue;
										}
										number.incrementAndGet();
										if(number.get()>limit[0]&&Other.data.getBoolean("Clear")) {
											blocklist.offer(block);
										}
									}
								}
							}
						}
					}
				}
			});
			if(number.get()>=limit[0]) {
				evt.setCancelled(true);
				evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ExceedLimit").replace("[limit]", limit[0]+"")));
				if(number.get()>limit[0]&&Other.data.getBoolean("Clear")) {
					for(Block block:blocklist) {
						block.breakNaturally();
					}
				}
				return;
			}
		}
	
		
		
	}
	@EventHandler
	public void Click(InventoryClickEvent evt) {
		Inventory inventory = evt.getClickedInventory();
		if(inventory == null) {
			return;
		}
		Player player = (Player) evt.getWhoClicked();
		if(evt.getClickedInventory()==null) {
			return;
		}
		try {
		    if(evt.getView().getTitle().startsWith("��a�ѱ���c���ơ�a��Ʒ�б��")) {
		    	evt.setCancelled(true);
		    } else {
		    	return;
		    }
		} catch(NullPointerException a) {
			return;
		}
	    if(evt.getCurrentItem().getType().equals(Material.AIR)) {
	    	return;
	    }
	    if(evt.getCurrentItem().getItemMeta().hasDisplayName()&&evt.getCurrentItem().getItemMeta().getDisplayName().equals("��a��")) {
	    	int pager = Integer.parseInt(player.getOpenInventory().getTitle().replace("��a�ѱ���c���ơ�a��Ʒ�б��", "").replace("ҳ", ""));
	    	Gui.list(player, pager--);
	    	return;
	    }
	    if(evt.getCurrentItem().getItemMeta().hasDisplayName()&&evt.getCurrentItem().getItemMeta().getDisplayName().equals("��a��")) {
	    	int pager = Integer.parseInt(player.getOpenInventory().getTitle().replace("��a�ѱ���c���ơ�a��Ʒ�б��", "").replace("ҳ", ""));
	    	Gui.list(player, pager++);
	    	return;
	    }
	    if(evt.getRawSlot()>44) {
	    	return;
	    }
	    if(player.isOp()) {
			for(String lore:evt.getCurrentItem().getItemMeta().getLore()) {
				if(lore.startsWith("��a�����Ƶķ���ID: ��6")) {
					if(lore.contains("��a�µ�����ͬ��ID")) {
		    			String id = lore.replace("��a�����Ƶķ���ID: ��6", "").replace("��a�µ�����ͬ��ID", "");
		    			Other.data.set("Block."+id+":999", null);
		    			try {
		    				Other.data.save(file);
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    			}
		    			player.sendMessage("��a�ɹ��Ƴ�");
		    			for(Player players:Bukkit.getServer().getOnlinePlayers()) {
		    				if(players.getOpenInventory().getTitle().startsWith("��a�ѱ���c���ơ�a��Ʒ�б��")) {
		    					int pager = Integer.parseInt(player.getOpenInventory().getTitle().replace("��a�ѱ���c���ơ�a��Ʒ�б��", "").replace("ҳ", ""));
		    					players.closeInventory();
		    					Gui.list(players, pager);
		    				}
		    			}
		    			return;
					} else {
		    			String id = lore.replace("��a�����Ƶķ���ID: ��6", "");
		    			String tile = "";
		    			String botania = "";
		    			for(String lores:evt.getCurrentItem().getItemMeta().getLore()) {
		    				if(lores.startsWith("��a�����÷����tile��2: ��6")) {
		    					tile = ":"+lores.replace("��a�����÷����tile��2: ��6", "");
		    				}
		    			}
		    			for(String loress:evt.getCurrentItem().getItemMeta().getLore()) {
		    				if(loress.startsWith("��a�����õĲ��ܻ�Ϊ��2����6")) {
		    					botania = ":"+loress.replace("��a�����õĲ��ܻ�Ϊ��2����6", "");
		    				}
		    			}
		    			Other.data.set("Block."+id+tile+botania, null);
		    			try {
		    				Other.data.save(file);
		    			} catch (IOException e) {
		    				e.printStackTrace();
		    			}
		    			player.sendMessage("��a�ɹ��Ƴ�");
		    			for(Player players:Bukkit.getServer().getOnlinePlayers()) {
		    				if(players.getOpenInventory().getTitle().startsWith("��a�ѱ���c���ơ�a��Ʒ�б��")) {
		    					int pager = Integer.parseInt(player.getOpenInventory().getTitle().replace("��a�ѱ���c���ơ�a��Ʒ�б��", "").replace("ҳ", ""));
		    					players.closeInventory();
		    					Gui.list(players, pager);
		    				}
		    			}
		    			return;
					}
				}
			}
	    }
	}
}

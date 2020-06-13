package com.tany.customchunklimit.listenevent;

import java.io.File;
import java.io.IOException;

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
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.Plugin;

import com.comphenix.protocol.utility.StreamSerializer;
import com.tany.customchunklimit.Main;
import com.tany.customchunklimit.Other;
import com.tany.customchunklimit.gui.Gui;

public class Event implements Listener {
    Plugin config = Bukkit.getPluginManager().getPlugin("CustomChunkLimit");
//    File file=new File(config.getDataFolder(),"config.yml");
    File file1=new File(config.getDataFolder(),"data.yml");
//    File file2=new File(config.getDataFolder(),"message.yml");
    
    @EventHandler
    public void Interact(PlayerInteractEvent evt) {
		if(Main.Create.containsKey(evt.getPlayer().getName())) {
			if(!(evt.getPlayer().getInventory().getItemInHand() == null || evt.getPlayer().getInventory().getItemInHand().getType() == Material.AIR)) {
				if(evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					String item = GetItemData(evt.getPlayer().getInventory().getItemInHand());
					int limit = Main.Create.get(evt.getPlayer().getName());
					String block = evt.getClickedBlock().getType().getId()+":"+evt.getClickedBlock().getData();
					if(Other.data.getConfigurationSection("Block").getKeys(false).size()!=0) {
						for(String blocks:Other.data.getConfigurationSection("Block").getKeys(false)) {
							if(blocks.equals(block)) {
								evt.getPlayer().sendMessage("§c这个方块已经被限制过了");
								evt.setCancelled(true);
								return;
							}
						}
					}
					Other.data.set("Block."+block+".item", item);
					Other.data.set("Block."+block+".limit", limit);
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		evt.getPlayer().sendMessage("§a添加方块成功！");
			  		Main.Create.remove(evt.getPlayer().getName());
				} else {
					evt.getPlayer().sendMessage("§c请右击方块");
				}
			}else {
				evt.getPlayer().sendMessage("§c手上不能为空！");
			}
			evt.setCancelled(true);
			return;
		}
		if(Main.CreateAll.containsKey(evt.getPlayer().getName())) {
			if(!(evt.getPlayer().getInventory().getItemInHand() == null || evt.getPlayer().getInventory().getItemInHand().getType() == Material.AIR)) {
				if(evt.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
					String item = GetItemData(evt.getPlayer().getInventory().getItemInHand());
					int limit = Main.CreateAll.get(evt.getPlayer().getName());
					String block = evt.getClickedBlock().getType().getId()+":999";
					if(Other.data.getConfigurationSection("Block").getKeys(false).size()>0) {
						for(String blocks:Other.data.getConfigurationSection("Block").getKeys(false)) {
							if(blocks.equals(block)) {
								evt.getPlayer().sendMessage("§c这个方块已经被限制过了");
								evt.setCancelled(true);
								return;
							}
						}
					}
					Other.data.set("Block."+block+".item", item);
					Other.data.set("Block."+block+".limit", limit);
			  		try {
			  			Other.data.save(file1);
			  		} catch (IOException e) {
			  			e.printStackTrace();
		        	}
			  		evt.getPlayer().sendMessage("§a添加方块成功！");
			  		Main.CreateAll.remove(evt.getPlayer().getName());
				} else {
					evt.getPlayer().sendMessage("§c请右击方块");
				}
			}else {
				evt.getPlayer().sendMessage("§c手上不能为空！");
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
				if(evt.getBlock().getTypeId()==id) {
					break;
				}
			} else {
				if(evt.getBlock().getTypeId()==id&&evt.getBlock().getData()==data) {
					break;
				}
			}
			a++;
			if(a==Other.data.getConfigurationSection("Block").getKeys(false).size()) {
				return;
			}
		}
		Chunk chunk = evt.getBlock().getChunk();
		Block block;
		int limit=99999;
		int number = 0;
		if(!Other.config.getBoolean("Optimization")) {
			Chunk[] chunklist = new Chunk[1];
			if(Other.config.getBoolean("NineChunk")) {
				chunklist[0] = chunk;
			} else if(Other.config.getBoolean("NineChunkImprove")) {
				chunklist = new Chunk[25];
				for(int i=0;i<25;i++) {
					if(i==0) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==1) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==2) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==3) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==4) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==5) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==6) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==7) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==8) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==9) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==10) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==11) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==12) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==13) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==14) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==15) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==16) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==17) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==18) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==19) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==20) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==21) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==22) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==23) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==24) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
				}
				
			} else {
				chunklist = new Chunk[9];
				for(int i=0;i<9;i++) {
					if(i==0) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==1) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==2) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==3) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==4) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==5) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==6) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==7) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==8) {
						chunklist[i] = evt.getBlock().getChunk();
						continue;
					}
				}
			}
			for(Chunk chunks:chunklist) {
				for(int x=0;x<16;x++) {
					for(int z=0;z<16;z++) {
						for(int y=0;y<256;y++) {
							block = chunks.getBlock(x, y, z);
							if(block.getType()!=evt.getBlock().getType()||block.getData()!=evt.getBlock().getData())
								continue;
							for(String limitstring:Other.data.getConfigurationSection("Block").getKeys(false)) {
								int id = Integer.parseInt(limitstring.split(":")[0]);
								int data = Integer.parseInt(limitstring.split(":")[1]);
								if(data==999) {
									if(block.getTypeId()==id) {
										if(number==0) {
											limit = Other.data.getInt("Block."+limitstring+".limit");
											for(PermissionAttachmentInfo p:evt.getPlayer().getEffectivePermissions()) {
												if(p.getPermission().startsWith("ccl."+id+"."+data+".")) {
													try {
														limit = limit+Integer.parseInt(p.getPermission().split("\\.")[3]);
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
										number++;
										if(number>limit&&Other.data.getBoolean("Clear")) {
											block.breakNaturally();
										}
									}
								} else {
									if(block.getTypeId()==id&&block.getData()==data) {
										if(number==0) {
											limit = Other.data.getInt("Block."+limitstring+".limit");
											for(PermissionAttachmentInfo p:evt.getPlayer().getEffectivePermissions()) {
												if(p.getPermission().startsWith("ccl."+id+"."+data+".")) {
													try {
														limit = limit+Integer.parseInt(p.getPermission().split("\\.")[3]);
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
										number++;
										if(number>limit&&Other.data.getBoolean("Clear")) {
											block.breakNaturally();
										}
									}
								}
							}
						}
					}
				}
			}
			if(number>=limit) {
				evt.setCancelled(true);
				evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ExceedLimit").replace("[limit]", limit+"")));
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
			if(Other.config.getBoolean("NineChunk")) {
				chunklist[0] = chunk;
			} else if(Other.config.getBoolean("NineChunkImprove")) {
				chunklist = new Chunk[25];
				for(int i=0;i<25;i++) {
					if(i==0) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==1) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==2) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==3) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==4) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-17);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==5) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==6) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==7) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==8) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==9) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==10) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==11) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==12) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==13) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==14) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==15) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==16) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==17) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==18) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==19) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==20) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16-17);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==21) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==22) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==23) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==24) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+32);
						location.setZ(chunk.getZ()*16+32);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
				}
			} else {
				chunklist = new Chunk[9];
				for(int i=0;i<9;i++) {
					if(i==0) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==1) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==2) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==3) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==4) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16+16);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==5) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16-1);
						location.setZ(chunk.getZ()*16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==6) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16+16);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==7) {
						Location location = evt.getBlock().getLocation();
						location.setX(chunk.getX()*16);
						location.setZ(chunk.getZ()*16-1);
						location.setY(0);
						chunklist[i] = location.getChunk();
						continue;
					}
					if(i==8) {
						chunklist[i] = evt.getBlock().getChunk();
						continue;
					}
				}
			}
			for(Chunk chunks:chunklist) {
				for(int x=0;x<16;x++) {
					for(int z=0;z<16;z++) {
						for(int y=customy;y<=limity;y++) {
							block = chunks.getBlock(x, y, z);
							if(block.getType()!=evt.getBlock().getType()||block.getData()!=evt.getBlock().getData())
								continue;
							for(String limitstring:Other.data.getConfigurationSection("Block").getKeys(false)) {
								int id = Integer.parseInt(limitstring.split(":")[0]);
								int data = Integer.parseInt(limitstring.split(":")[1]);
								if(data==999) {
									if(block.getTypeId()==id) {
										if(number==0) {
											limit = Other.data.getInt("Block."+limitstring+".limit");
											for(PermissionAttachmentInfo p:evt.getPlayer().getEffectivePermissions()) {
												if(p.getPermission().startsWith("ccl."+id+"."+data+".")) {
													try {
														limit = limit+Integer.parseInt(p.getPermission().split("\\.")[3]);
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
										number++;
										if(number>limit&&Other.data.getBoolean("Clear")) {
											block.breakNaturally();
										}
									}
								} else {
									if(block.getTypeId()==id&&block.getData()==data) {
										if(number==0) {
											limit = Other.data.getInt("Block."+limitstring+".limit");
											for(PermissionAttachmentInfo p:evt.getPlayer().getEffectivePermissions()) {
												if(p.getPermission().startsWith("ccl."+id+"."+data+".")) {
													try {
														limit = limit+Integer.parseInt(p.getPermission().split("\\.")[3]);
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
										number++;
										if(number>limit&&Other.data.getBoolean("Clear")) {
											block.breakNaturally();
										}
									}
								}
							}
						}
					}
				}
			}
			if(number>=limit) {
				evt.setCancelled(true);
				evt.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("ExceedLimit").replace("[limit]", limit+"")));
				return;
			}
		}
	
		
		
	}
	@EventHandler
	public void Click(InventoryClickEvent evt) 
	{
		Inventory inventory = evt.getClickedInventory();
		if(inventory == null) {
			return;
		}
		Player player = (Player) evt.getWhoClicked();
		if(evt.getClickedInventory()==null) 
		{
			return;
		}
	    if(evt.getClickedInventory().getTitle().startsWith("§a已被§c限制§a物品列表第")) 
	    {
	    	evt.setCancelled(true);
	    }
	    else 
	    {
	    	return;
	    }
	    if(evt.getCurrentItem().getType().equals(Material.AIR)) 
	    {
	    	return;
	    }
	    if(evt.getCurrentItem().getItemMeta().hasDisplayName()&&evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a上")) 
	    {
	    	int pager = Integer.parseInt(player.getOpenInventory().getTitle().replace("§a已被§c限制§a物品列表第", "").replace("页", ""));
	    	Gui.list(player, pager--);
	    	return;
	    }
	    if(evt.getCurrentItem().getItemMeta().hasDisplayName()&&evt.getCurrentItem().getItemMeta().getDisplayName().equals("§a下")) 
	    {
	    	int pager = Integer.parseInt(player.getOpenInventory().getTitle().replace("§a已被§c限制§a物品列表第", "").replace("页", ""));
	    	Gui.list(player, pager++);
	    	return;
	    }
	    if(evt.getRawSlot()>44) 
	    {
	    	return;
	    }
	    if(player.isOp()) 
	    {
	    	for(String lore:evt.getCurrentItem().getItemMeta().getLore()) {
	    		if(lore.startsWith("§a被限制的方块ID:§6")) {
					if(lore.contains("§a下的所有同主ID")) {
		    			String id = lore.replace("§a被限制的方块ID:§6", "").replace("§a下的所有同主ID", "");
		    			Other.data.set("Block."+id+":999", null);
					} else {
		    			String id = lore.replace("§a被限制的方块ID:§6", "").replace("§a下的所有同主ID", "");
		    			Other.data.set("Block."+id, null);
					}
	    			try {
						Other.data.save(file1);
					} catch (IOException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
	    			player.sendMessage("§a成功移除");
	    			for(Player players:Bukkit.getServer().getOnlinePlayers()) {
	    				if(players.getOpenInventory().getTitle().startsWith("§a已被§c限制§a物品列表第")) {
	    					int pager = Integer.parseInt(player.getOpenInventory().getTitle().replace("§a已被§c限制§a物品列表第", "").replace("页", ""));
	    					players.closeInventory();
	    					Gui.list(players, pager);
	    				}
	    			}
	    			return;
	    		}
	    	}
	    }
	    
	}
	
//	ItemStack转String
	public String GetItemData(ItemStack item) {
		String a;
		int amount = item.getAmount();
		item.setAmount(1);
		try {
		    a = new StreamSerializer().serializeItemStack(item);
		} catch (Exception e) {
		    a = null;
		}
		item.setAmount(amount);
		return a;
	}
//	String转ItemStack
	public ItemStack GetItemStack(String data) {
		try {
			return new StreamSerializer().deserializeItemStack(data);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}

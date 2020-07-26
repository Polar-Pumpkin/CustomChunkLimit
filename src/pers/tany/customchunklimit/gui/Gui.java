package pers.tany.customchunklimit.gui;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pers.tany.customchunklimit.Other;
import pers.tany.customchunklimit.CommonlyWay;

public class Gui {
	public static void list(Player player,int type){
		Inventory gui = Bukkit.createInventory(null, 54, "§a已被§c限制§a物品列表第"+type+"页");
		ItemStack info = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack last = new ItemStack(Material.STAINED_GLASS_PANE);
		ItemStack next = new ItemStack(Material.STAINED_GLASS_PANE);
		
		ItemMeta meta = info.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();
        ArrayList<String> Block = new ArrayList<String>();
        for(String a:Other.data.getConfigurationSection("Block").getKeys(false)) {
        	Block.add(a);
        }
        
        if(Other.data.getConfigurationSection("Block").getKeys(false).size()<(type-1)*45+1) {
        	if(type>1) {
        		player.closeInventory();
        		Gui.list(player, --type);
        		return;
        	} else {
        	player.sendMessage(ChatColor.translateAlternateColorCodes('&', Other.message.getString("NoList").replace("[type]", type+"")));
        	return;
        	}
        }
        
        meta.setDisplayName("§a这里展示了被限制的方块信息");
        lore.add("§2可以查看相关数值");
        if(player.isOp())
        	lore.add("§e点击移除对应限制");
        info.setDurability((short) 1);
        meta.setLore(lore);
        info.setItemMeta(meta);
        lore.clear();
        
        meta.setDisplayName("§a下");
        lore.add("§c查看下一页");
        next.setDurability((short) 14);
        meta.setLore(lore);
        next.setItemMeta(meta);
        lore.clear();
        
        meta.setDisplayName("§a上");
        lore.add("§c返回下一页");
        last.setDurability((short) 11);
        meta.setLore(lore);
        last.setItemMeta(meta);
        lore.clear();
        
        int i=(type-1)*45;
        int a=0;
        int size = Block.size()-1;
        while(i<=size&&i<=44+(type-1)*45) {
        	String types = Block.get(i);
        	int ID = Integer.parseInt(types.split(":")[0]);
        	int DataID = Integer.parseInt(types.split(":")[1]);
        	int limit = Other.data.getInt("Block."+types+".limit");
        	ItemStack item = CommonlyWay.GetItemStack(Other.data.getString("Block."+types+".item"));
        	ItemMeta itemdata = item.getItemMeta();
        	if(DataID!=999) {
        		lore.add("§a被限制的方块ID: §6"+ID+":"+DataID);
        		if(Other.data.getString("Block."+types+".tile")!=null) {
        			lore.add("§a被禁用方块的tile§2: §6"+Other.data.getString("Block."+types+".tile"));
        			if(Other.data.getString("Block."+types+".botania")!=null) {
        				lore.add("§a被禁用的产能花为§2：§6"+Other.data.getString("Block."+types+".botania"));
        			}
        		}
        	}
        	else
        		lore.add("§a被限制的方块ID: §6"+ID+"§a下的所有同主ID");
        	lore.add("§a每设置区块被限制的数量:§e"+limit+"§a个方块");
        	if(player.isOp()) {
        		lore.add("");
        		lore.add("§e点击移除此限制");
        	}
        	itemdata.setLore(lore);
        	lore.clear();
        	item.setItemMeta(itemdata);
        	gui.setItem(a, item);
        	a++;
        	i++;
        }
        
        if(type>1)
        gui.setItem(45, last);
        else
        gui.setItem(45, info);
        gui.setItem(46, info);
        gui.setItem(47, info);
        gui.setItem(48, info);
        gui.setItem(49, info);
        gui.setItem(50, info);
        gui.setItem(51, info);
        gui.setItem(52, info);
        if (Other.data.getConfigurationSection("Block").getKeys(false).size() < 46+(type-1)*45)
        gui.setItem(53, info);
        else
        gui.setItem(53, next);
        
        player.openInventory(gui);
	}
}

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
		Inventory gui = Bukkit.createInventory(null, 54, "��a�ѱ���c���ơ�a��Ʒ�б��"+type+"ҳ");
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
        
        meta.setDisplayName("��a����չʾ�˱����Ƶķ�����Ϣ");
        lore.add("��2���Բ鿴�����ֵ");
        if(player.isOp())
        	lore.add("��e����Ƴ���Ӧ����");
        info.setDurability((short) 1);
        meta.setLore(lore);
        info.setItemMeta(meta);
        lore.clear();
        
        meta.setDisplayName("��a��");
        lore.add("��c�鿴��һҳ");
        next.setDurability((short) 14);
        meta.setLore(lore);
        next.setItemMeta(meta);
        lore.clear();
        
        meta.setDisplayName("��a��");
        lore.add("��c������һҳ");
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
        		lore.add("��a�����Ƶķ���ID: ��6"+ID+":"+DataID);
        		if(Other.data.getString("Block."+types+".tile")!=null) {
        			lore.add("��a�����÷����tile��2: ��6"+Other.data.getString("Block."+types+".tile"));
        			if(Other.data.getString("Block."+types+".botania")!=null) {
        				lore.add("��a�����õĲ��ܻ�Ϊ��2����6"+Other.data.getString("Block."+types+".botania"));
        			}
        		}
        	}
        	else
        		lore.add("��a�����Ƶķ���ID: ��6"+ID+"��a�µ�����ͬ��ID");
        	lore.add("��aÿ�������鱻���Ƶ�����:��e"+limit+"��a������");
        	if(player.isOp()) {
        		lore.add("");
        		lore.add("��e����Ƴ�������");
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

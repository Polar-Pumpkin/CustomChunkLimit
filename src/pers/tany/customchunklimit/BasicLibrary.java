package pers.tany.customchunklimit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class BasicLibrary {
	public static List<ItemStack> stainedglass = new ArrayList<ItemStack>();
	public static List<ItemStack> stainedwool = new ArrayList<ItemStack>();
	
	BasicLibrary(){
//		如果需要使用请在plugin.yml里加上api-version: 1.13
		try {
			stainedglass.add(new ItemStack(Material.valueOf("WHITE_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("ORANGE_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("MAGENTA_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("LIGHT_BLUE_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("YELLOW_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("LIME_STAINED_GLASS_PANE")));
			
			stainedglass.add(new ItemStack(Material.valueOf("PINK_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("GRAY_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("LIGHT_GRAY_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("CYAN_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("PURPLE_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("BLUE_STAINED_GLASS_PANE")));
			
			stainedglass.add(new ItemStack(Material.valueOf("BROWN_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("GREEN_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("RED_STAINED_GLASS_PANE")));
			stainedglass.add(new ItemStack(Material.valueOf("BLACK_STAINED_GLASS_PANE")));
		} catch (Exception a) {
			for(int i = 0;i<=15;i++)
			{
				ItemStack glass = new ItemStack(Material.valueOf("STAINED_GLASS_PANE"));
				glass.setDurability((short) i);
				stainedglass.add(glass);
			}
		}
		try {
			stainedwool.add(new ItemStack(Material.valueOf("WHITE_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("ORANGE_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("MAGENTA_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("LIGHT_BLUE_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("YELLOW_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("LIME_WOOL")));
			
			stainedwool.add(new ItemStack(Material.valueOf("PINK_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("GRAY_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("LIGHT_GRAY_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("CYAN_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("PURPLE_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("BLUE_WOOL")));
			
			stainedwool.add(new ItemStack(Material.valueOf("BROWN_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("GREEN_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("RED_WOOL")));
			stainedwool.add(new ItemStack(Material.valueOf("BLACK_WOOL")));
		} catch (Exception a) {
			for(int i = 0;i<=15;i++)
			{
				ItemStack wool = new ItemStack(Material.valueOf("WOOL"));
				wool.setDurability((short) i);
				stainedwool.add(wool);
			}
		}
	}
	
}

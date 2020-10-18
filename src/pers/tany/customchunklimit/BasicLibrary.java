package pers.tany.customchunklimit;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class BasicLibrary {
	public static List<EntityType> uselessentitylist = new ArrayList<EntityType>();
	public static List<ItemStack> stainedglass = new ArrayList<ItemStack>();
	
	BasicLibrary(){
		uselessentitylist.add(EntityType.ZOMBIE);
		uselessentitylist.add(EntityType.ZOMBIE_VILLAGER);
		uselessentitylist.add(EntityType.PIG_ZOMBIE);
		uselessentitylist.add(EntityType.SKELETON);
		uselessentitylist.add(EntityType.SPIDER);
		uselessentitylist.add(EntityType.CAVE_SPIDER);
		uselessentitylist.add(EntityType.BAT);
		uselessentitylist.add(EntityType.ARROW);
		uselessentitylist.add(EntityType.SPLASH_POTION);
		uselessentitylist.add(EntityType.WITHER);
		uselessentitylist.add(EntityType.WITHER_SKULL);
		uselessentitylist.add(EntityType.CREEPER);
		uselessentitylist.add(EntityType.ENDERMAN);
		uselessentitylist.add(EntityType.SQUID);
		try {
			uselessentitylist.add(EntityType.RABBIT);
		}catch (Exception a) {
			
		}
		try {
			uselessentitylist.add(EntityType.valueOf("FLAMMPFEIL.SLASHBLADE_SUMMONEDSWORDATM"));
		}catch (Exception a) {
			
		}
		try {
			uselessentitylist.add(EntityType.valueOf("FLAMMPFEIL.SLASHBLADE_WITHERSWORD"));
		}catch (Exception a) {
			
		}
		try {
			uselessentitylist.add(EntityType.valueOf("FLAMMPFEIL.SLASHBLADE_SUMMONEDSWORDBASE"));
		}catch (Exception a) {
			
		}
		try {
			uselessentitylist.add(EntityType.valueOf("ESSENTIALCRAFT_COMMONENTITYMRUPRESENCE"));
		}catch (Exception a) {
			
		}
//		�����Ҫʹ������plugin.yml�����api-version: 1.13
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
	}
	
}

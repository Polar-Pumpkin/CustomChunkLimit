package pers.tany.customchunklimit;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import com.comphenix.protocol.utility.StreamSerializer;

public class Way {
//		命令判断是否为OP
		public static boolean OpUseCommand(CommandSender player) {
			if(player.isOp())
			return false;
			else
			return true;
		}
		
//		判断玩家是否为OP
		public static boolean OpUse(Player player) {
			if(player.isOp())
			return false;
			else
			return true;
		}
		
//		判断玩家是否为OP
		public static boolean ConsoleUse(CommandSender sender) {
			if(sender instanceof Player)
			return false;
			else
			return true;
		}
		
//		ItemStack转String
		public static String GetItemData(ItemStack item) {
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
//		String转ItemStack
		public static ItemStack GetItemStack(String data) {
			try {
				return new StreamSerializer().deserializeItemStack(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
//		获取玩家背包空格数
		public static int Backpack(Player player) {
			int a=0;
			int b=0;
			while(a<=35) {
				if(player.getInventory().getItem(a)==null) {
					b++;
				}
				a++;
			}
			return b;
		}
}

package pers.tany.customchunklimit;

import java.util.ArrayList;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.utility.StreamSerializer;

public class CommonlyWay {
	
		static boolean again = true;
//		�����ж��Ƿ�ΪOP��������true
		public static boolean OpUseCommand(CommandSender player) {
			if(player.isOp()) {
				return false;
			} else {
				return true;
			}
			
		}
		
//		�ж�����Ƿ�ΪOP��������true
		public static boolean OpUse(Player player) {
			if(player.isOp())
			return false;
			else
			return true;
		}
		
//		�����ж��Ƿ�Ϊ����̨��������true
		public static boolean ConsoleUse(CommandSender sender) {
			if(sender instanceof Player){
				return false;
			} else {
				return true;
			}
		}
		
//		���л�ItemStack
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
		
//		�����л�ItemStack
		public static ItemStack GetItemStack(String data) {
			try {
				return new StreamSerializer().deserializeItemStack(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}
		
//		���ɳ��ȹ̶�������ַ���
	    public static String CreateRandomString(int length)
	    {
	        String number = "";
	        Random random = new Random();
	        for (int i=0;i<length; i++)
	        {
	            String str = random.nextInt(2) % 2 == 0 ? "num" : "char";
	            if ( "char".equalsIgnoreCase(str))
	            {
	                int nextInt = random.nextInt(2) % 2 == 0 ? 65 : 97;
	                number += (char) (nextInt+random.nextInt(26));
	            }
	            else if ( "num".equalsIgnoreCase(str))
	            {
	            	number += String.valueOf(random.nextInt(10) );
	            }
	        }
	        return number;
	    }
		
//		��ȡ��ұ����ո���
		public static int BackpackEmptyNumber(Player player) {
			int number=0;
			int empty=0;
			while(number<=35) {
				if(player.getInventory().getItem(number)==null) {
					empty++;
				}
				number++;
			}
			return empty;
		}
		
//		����/�۳���ҵľ��飨֧�ָ���������
		public static  void GiveExp(Player player, int xp) {
			int level = player.getLevel();
			int toNextLevel = GetUpgradeExp(level);
			int floatExp = (int) (player.getExp() * toNextLevel) + xp;
          	while(floatExp >= toNextLevel) {
        	  	floatExp -= toNextLevel;
        	  	toNextLevel = GetUpgradeExp(++level);
          	}
          	while(floatExp < 0) {
          		floatExp += (toNextLevel = GetUpgradeExp(--level));
                  	if(level < 0) 
                  		level = floatExp = 0;
          	}
          	player.setLevel(level);
          	player.setExp( (float) floatExp / toNextLevel);
          	player.setTotalExperience(GetTotalExp(level) + floatExp);
	  	}
  
	  	public static int GetUpgradeExp(int level) {
	  		return level < 16  ? level * 2 + 7 : level < 30 ? level * 5 - 38 : level * 9 - 158;
  		}
  
  		public static int GetTotalExp(int level) {
  			return (int) (level < 17 ? level * (level + 6) : level < 31 ? level * (level * 2.5 - 40.5) + 360 : level * (level * 4.5 - 162.5) + 2220);
  		}
  		
//		���������Ʒ���������������ԭ�����ɵ�����
		public static void GiveItem(Player player,ItemStack item) {
			if(player.getInventory().firstEmpty()==-1) {
				player.getWorld().dropItemNaturally(player.getLocation(), item);
			} else {
				player.getInventory().addItem(item);
			}
		}
		
//		�ж�����Ƿ�Ϊ���֣�������true
		public static Boolean EmptyItem(Player player) {
			if(player.getInventory().getItemInHand()==null||player.getInventory().getItemInHand().getType() == Material.AIR) {
				return true;
			}
			return false;
		}

		
//				���������Ʒ��NBT
//				�跴��
//				public static Class<?> nbttagcompound;
//				public static Class<?> itemstack;
//				public static Method asNMSCopy;
//				public static Method save;
//				public static Class<?> saveitem;
//				public String version = org.bukkit.Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
//				
//				void Reflection() throws NoSuchMethodException, SecurityException, ClassNotFoundException {
//					saveitem = Class.forName("net.minecraft.server."+version+".ItemStack");
//					itemstack = Class.forName("org.bukkit.craftbukkit."+version+".inventory.CraftItemStack");
//					nbttagcompound = Class.forName("net.minecraft.server."+version+".NBTTagCompound");
//					asNMSCopy = itemstack.getMethod("asNMSCopy", ItemStack.class);
//					save = saveitem.getMethod("save", nbttagcompound);
//				}
//				public static String GetItemNBT(ItemStack item) {
//					String message;
//					try {
//						Object tag = Main.nbttagcompound.newInstance();
//						Object nmsitem = Main.asNMSCopy.invoke(null, item);
//						Object save = Main.save.invoke(nmsitem, tag);
//						message = save.toString();
//						return message;
//					} catch (Exception e) {
//						e.printStackTrace();
//						return "null";
//					}
//				}	
				
//				�������飬�滻�����ַ���
				public static java.util.List<String> ListReplace(java.util.List<String> list,String WasReplaced,String replace){
					ArrayList<String> news = new ArrayList<>();
					for(String s:list) {
						news.add(s.replace(WasReplaced, replace));
					}
					return news;
				}
				
//		  		�жϽ�����
		  		public static boolean getInteractHand(PlayerInteractEvent evt)
		  		{
		  			if(evt.getHand()==null||!evt.getHand().equals(EquipmentSlot.HAND))
					return true;
		  			else
		  			return false;
		  		}
		  		
//		  		�жϷ�����
		  		public static boolean getPlaceHand(BlockPlaceEvent evt)
		  		{
		  			if(evt.getHand()==null||!evt.getHand().equals(EquipmentSlot.HAND))
					return true;
		  			else
		  			return false;
		  		}
		  		
//				�Ѽ���%ת��Ϊ�����İٷֱ�
				public static double PercentageNumber(String percentage) {
					double a = 0;
					try {
						a = Double.parseDouble(percentage.replace("%", ""))/100;
					} catch (Exception b) {
						return 9.99;
					}
					return a;
				}
				
//				�Ѽ���%ת��Ϊ�����İٷֱ�,���ҽ��г˷�����
				public static double Percentage(double number,String percentage) {
					try {
						number = number * (Double.parseDouble(percentage.replace("%", ""))/100);
					} catch (Exception a) {
						return 9.99;
					}
					return number;
				}
}
package pers.tany.customchunklimit.command;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import pers.tany.customchunklimit.Main;
import pers.tany.customchunklimit.Other;
import pers.tany.customchunklimit.gui.Gui;


public class Commands implements CommandExecutor {
    Plugin config = Bukkit.getPluginManager().getPlugin("CustomChunkLimit");
    File file=new File(config.getDataFolder(),"config.yml");
    File file1=new File(config.getDataFolder(),"data.yml");
    File file2=new File(config.getDataFolder(),"message.yml");
    
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(args.length==1) {
			if(args[0].equalsIgnoreCase("list")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("§c控制台不能使用此命令");
					return true;
				}
				if(!sender.hasPermission("ccl.list")) {
					sender.sendMessage("§c你没有权限使用此指令");
					return true;
				}
				Gui.list((Player) sender, 1);
				return true;
			}
			if(args[0].equalsIgnoreCase("reload")) {
				Other.config = YamlConfiguration.loadConfiguration(file);
				Other.data = YamlConfiguration.loadConfiguration(file1);
				Other.message = YamlConfiguration.loadConfiguration(file2);
				sender.sendMessage("§a重载成功");
				return true;
			}
		}
		if(args.length==2) {
			if(args[0].equalsIgnoreCase("add")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("§c控制台不能使用此命令");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("§c你没有权限使用此指令");
					return true;
				}
				Player player = (Player) sender;
				int limit = 1;
				try {
					limit =Integer.parseInt(args[1]);
				}catch(NumberFormatException e) {
					player.sendMessage("§c请输入数字");
					return true;
				}
				if(limit<=0) {
					player.sendMessage("§c数量必须大于0！");
					return true;
				}
				if(Main.Create.containsKey(player.getName())||Main.CreateAll.containsKey(player.getName())) {
					player.sendMessage("§c你已经正在执行选择！");
					return true;
				}
				Main.Create.put(player.getName(),limit);
				player.sendMessage("§a请拿着你要展示的物品，右键你要限制的方块");
				return true;
			}
			if(args[0].equalsIgnoreCase("addall")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("§c控制台不能使用此命令");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("§c你没有权限使用此指令");
					return true;
				}
				Player player = (Player) sender;
				int limit = 1;
				try {
					limit =Integer.parseInt(args[1]);
				}catch(NumberFormatException e) {
					player.sendMessage("§c请输入数字");
					return true;
				}
				if(limit<=0) {
					player.sendMessage("§c数量必须大于0！");
					return true;
				}
				if(Main.CreateAll.containsKey(player.getName())||Main.Create.containsKey(player.getName())) {
					player.sendMessage("§c你已经正在执行选择！");
					return true;
				}
				Main.CreateAll.put(player.getName(),limit);
				player.sendMessage("§a请拿着你要展示的物品，点击你要限制的方块");
				return true;
			}
			return true;
		}
		sender.sendMessage("§e[]-------------§e[§6Custom§eChunk§6Limit§e]§6---------------[]");
		sender.sendMessage("§e/ccl add 数量  §a添加手上的物品和下次点击的方块到限制");
		sender.sendMessage("§e/ccl addall 数量  §a添加手上的物品和下次点击的方块包括所有子ID到限制");
		sender.sendMessage("§e/ccl list  §a查看已限制摆放数量的方块");
		sender.sendMessage("§e/ccl reload  §a重载配置文件");
		sender.sendMessage("§e/ccl clear true/false  §a开关清理已有方块模式");
		sender.sendMessage("§e[]-------------------------------------------[]");
		return true;
	}
}

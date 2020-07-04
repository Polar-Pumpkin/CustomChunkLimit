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
					sender.sendMessage("��c����̨����ʹ�ô�����");
					return true;
				}
				if(!sender.hasPermission("ccl.list")) {
					sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
					return true;
				}
				Gui.list((Player) sender, 1);
				return true;
			}
			if(args[0].equalsIgnoreCase("reload")) {
				Other.config = YamlConfiguration.loadConfiguration(file);
				Other.data = YamlConfiguration.loadConfiguration(file1);
				Other.message = YamlConfiguration.loadConfiguration(file2);
				sender.sendMessage("��a���سɹ�");
				return true;
			}
		}
		if(args.length==2) {
			if(args[0].equalsIgnoreCase("add")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("��c����̨����ʹ�ô�����");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
					return true;
				}
				Player player = (Player) sender;
				int limit = 1;
				try {
					limit =Integer.parseInt(args[1]);
				}catch(NumberFormatException e) {
					player.sendMessage("��c����������");
					return true;
				}
				if(limit<=0) {
					player.sendMessage("��c�����������0��");
					return true;
				}
				if(Main.Create.containsKey(player.getName())||Main.CreateAll.containsKey(player.getName())) {
					player.sendMessage("��c���Ѿ�����ִ��ѡ��");
					return true;
				}
				Main.Create.put(player.getName(),limit);
				player.sendMessage("��a��������Ҫչʾ����Ʒ���Ҽ���Ҫ���Ƶķ���");
				return true;
			}
			if(args[0].equalsIgnoreCase("addall")) {
				if(!(sender instanceof Player)) {
					sender.sendMessage("��c����̨����ʹ�ô�����");
					return true;
				}
				if(!sender.isOp()) {
					sender.sendMessage("��c��û��Ȩ��ʹ�ô�ָ��");
					return true;
				}
				Player player = (Player) sender;
				int limit = 1;
				try {
					limit =Integer.parseInt(args[1]);
				}catch(NumberFormatException e) {
					player.sendMessage("��c����������");
					return true;
				}
				if(limit<=0) {
					player.sendMessage("��c�����������0��");
					return true;
				}
				if(Main.CreateAll.containsKey(player.getName())||Main.Create.containsKey(player.getName())) {
					player.sendMessage("��c���Ѿ�����ִ��ѡ��");
					return true;
				}
				Main.CreateAll.put(player.getName(),limit);
				player.sendMessage("��a��������Ҫչʾ����Ʒ�������Ҫ���Ƶķ���");
				return true;
			}
			return true;
		}
		sender.sendMessage("��e[]-------------��e[��6Custom��eChunk��6Limit��e]��6---------------[]");
		sender.sendMessage("��e/ccl add ����  ��a������ϵ���Ʒ���´ε���ķ��鵽����");
		sender.sendMessage("��e/ccl addall ����  ��a������ϵ���Ʒ���´ε���ķ������������ID������");
		sender.sendMessage("��e/ccl list  ��a�鿴�����ưڷ������ķ���");
		sender.sendMessage("��e/ccl reload  ��a���������ļ�");
		sender.sendMessage("��e/ccl clear true/false  ��a�����������з���ģʽ");
		sender.sendMessage("��e[]-------------------------------------------[]");
		return true;
	}
}

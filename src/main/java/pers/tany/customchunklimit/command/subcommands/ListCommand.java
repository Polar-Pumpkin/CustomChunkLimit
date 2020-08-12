package pers.tany.customchunklimit.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.serverct.parrot.parrotx.PPlugin;
import org.serverct.parrot.parrotx.command.PCommand;
import org.serverct.parrot.parrotx.utils.BasicUtil;
import org.serverct.parrot.parrotx.utils.I18n;
import pers.tany.customchunklimit.inventory.ListInventory;

public class ListCommand implements PCommand {
    @Override
    public String getPermission() {
        return "ccl.list";
    }

    @Override
    public String getDescription() {
        return "查看已限制摆放数量的方块。";
    }

    @Override
    public boolean execute(PPlugin plugin, CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            Player user = (Player) sender;
            BasicUtil.openInventory(plugin, user, new ListInventory(user, 1).getInventory());
        } else {
            sender.sendMessage(plugin.lang.build(plugin.localeKey, I18n.Type.ERROR, "&c控制台不能使用此命令。"));
        }
        return true;
    }
}

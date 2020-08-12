package pers.tany.customchunklimit.command.subcommands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.serverct.parrot.parrotx.PPlugin;
import org.serverct.parrot.parrotx.command.PCommand;
import org.serverct.parrot.parrotx.utils.I18n;
import pers.tany.customchunklimit.CustomChunkLimit;

public class AddAllCommand implements PCommand {
    @Override
    public String getPermission() {
        return "ccl.addall";
    }

    @Override
    public String getDescription() {
        return "指定限制数量，将手上物品作为展示物品，限制下次点击的方块(及其所有子 ID)。";
    }

    @Override
    public boolean execute(PPlugin plugin, CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(plugin.lang.build(plugin.localeKey, I18n.Type.ERROR, "&c控制台不能使用此命令。"));
            return true;
        }

        Player user = (Player) sender;

        if (args.length != 2) {
            plugin.getCmdHandler().formatHelp().forEach(user::sendMessage);
            return true;
        }

        int limit;
        try {
            limit = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            I18n.send(user, plugin.lang.build(plugin.localeKey, I18n.Type.WARN, "&c请输入数字。"));
            return true;
        }

        if (limit <= 0) {
            I18n.send(user, plugin.lang.build(plugin.localeKey, I18n.Type.WARN, "&c数字必须大于 0。"));
            return true;
        }

        if (CustomChunkLimit.creatingMap.containsKey(user.getUniqueId()) || CustomChunkLimit.creatingAllMap.containsKey(user.getUniqueId())) {
            I18n.send(user, plugin.lang.build(plugin.localeKey, I18n.Type.WARN, "&c你已经正在执行选择。"));
            return true;
        }

        CustomChunkLimit.creatingAllMap.put(user.getUniqueId(), limit);
        I18n.send(user, plugin.lang.build(plugin.localeKey, I18n.Type.WARN, "&a请拿着展示物品，右键选择需要限制的方块。"));
        return true;
    }
}

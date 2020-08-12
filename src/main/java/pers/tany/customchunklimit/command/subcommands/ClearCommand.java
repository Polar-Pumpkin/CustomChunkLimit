package pers.tany.customchunklimit.command.subcommands;

import org.bukkit.command.CommandSender;
import org.serverct.parrot.parrotx.PPlugin;
import org.serverct.parrot.parrotx.command.PCommand;
import org.serverct.parrot.parrotx.utils.I18n;
import pers.tany.customchunklimit.config.ConfigManager;

public class ClearCommand implements PCommand {
    @Override
    public String getPermission() {
        return "ccl.clear";
    }

    @Override
    public String getDescription() {
        return "开关自动清理已有方块模式。";
    }

    @Override
    public boolean execute(PPlugin plugin, CommandSender sender, String[] args) {
        boolean mode = !ConfigManager.autoClear;
        ConfigManager.autoClear = mode;
        sender.sendMessage(plugin.lang.build(plugin.localeKey, I18n.Type.INFO, "&a成功" + (mode ? "开启" : "关闭") + "自动清理模式。"));
        return true;
    }
}

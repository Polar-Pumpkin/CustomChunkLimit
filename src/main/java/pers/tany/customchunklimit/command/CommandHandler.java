package pers.tany.customchunklimit.command;

import org.serverct.parrot.parrotx.command.subcommands.HelpCommand;
import org.serverct.parrot.parrotx.command.subcommands.ReloadCommand;
import pers.tany.customchunklimit.CustomChunkLimit;
import pers.tany.customchunklimit.command.subcommands.AddAllCommand;
import pers.tany.customchunklimit.command.subcommands.AddCommand;
import pers.tany.customchunklimit.command.subcommands.ClearCommand;
import pers.tany.customchunklimit.command.subcommands.ListCommand;


public class CommandHandler extends org.serverct.parrot.parrotx.command.CommandHandler {

    public CommandHandler() {
        super(CustomChunkLimit.getInstance(), "ccl");
        registerSubCommand("list", new ListCommand());
        registerSubCommand("reload", new ReloadCommand(CustomChunkLimit.getInstance(), "ccl.reload"));
        registerSubCommand("help", new HelpCommand(CustomChunkLimit.getInstance(), null, this));
        registerSubCommand("clear", new ClearCommand());
        registerSubCommand("add", new AddCommand());
        registerSubCommand("addall", new AddAllCommand());
    }

}

package com.envyclient.mapcha.impl.commands.subcommands;

import com.envyclient.mapcha.Mapcha;
import com.envyclient.mapcha.api.subcommand.SubCommand;
import com.envyclient.mapcha.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class HelpSubCommand extends SubCommand {

    public HelpSubCommand(String usage) {
        super(usage);
    }

    @Override
    public boolean onCommand(Command command, CommandSender commandSender, String[] args) {
        commandSender.sendMessage(Util.PREFIX + " Here is a list of all the commands:");
        Mapcha.INSTANCE.SUB_COMMAND_MANAGER.getContents().forEach(subCommand -> commandSender.sendMessage("-> " + subCommand.getUsage(command)));
        return true;
    }

}
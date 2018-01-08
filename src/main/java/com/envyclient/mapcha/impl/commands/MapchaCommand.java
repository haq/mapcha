package com.envyclient.mapcha.impl.commands;

import com.envyclient.mapcha.Mapcha;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class MapchaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        return Mapcha.INSTANCE.SUB_COMMAND_MANAGER.processSubCommand(command, commandSender, strings);
    }
}

package com.envyclient.mapcha.impl.commands.subcommands;

import com.envyclient.mapcha.Mapcha;
import com.envyclient.mapcha.api.subcommand.SubCommand;
import com.envyclient.mapcha.util.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadSubCommand extends SubCommand {

    public ReloadSubCommand(String usage) {
        super(usage);
    }

    @Override
    public boolean onCommand(Command command, CommandSender commandSender, String[] args) {

        if (commandSender instanceof Player) {

            Player player = (Player) commandSender;

            if (player.hasPermission("mapcha.reload")) {
                if (Mapcha.INSTANCE.FILE_MANAGER.loadFiles())
                    player.sendMessage(Util.PREFIX + " File reloaded.");
                else
                    player.sendMessage(Util.PREFIX + " Error loading file, please check console.");
                return true;
            }

            player.sendMessage(Util.PREFIX + " You do not have permission to use this command.");
            return true;
        }


        if (Mapcha.INSTANCE.FILE_MANAGER.loadFiles())
            commandSender.sendMessage(Util.PREFIX + " File reloaded.");
        else
            commandSender.sendMessage(Util.PREFIX + " Error loading file, please check console.");


        return true;
    }

}
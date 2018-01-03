package com.envyclient.mapcha.api.subcommand;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    private String name, usage;

    public SubCommand(String usage) {
        this.usage = usage;
        this.name = usage.split(" ")[0];
    }

    public abstract boolean onCommand(Command command, CommandSender commandSender, String[] args);

    public String getUsage(Command parentCommand) {
        return parentCommand.getName() + " " + usage;
    }

    public String getName() {
        return name;
    }
}

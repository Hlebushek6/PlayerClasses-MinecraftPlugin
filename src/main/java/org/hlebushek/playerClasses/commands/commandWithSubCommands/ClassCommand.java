package org.hlebushek.playerClasses.commands.commandWithSubCommands;

import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.CommandWithSubCommands;
import org.hlebushek.playerClasses.commands.classSubCommands.ClassSetCommand;
import org.hlebushek.playerClasses.commands.classSubCommands.ClassShowCommand;

public class ClassCommand extends CommandWithSubCommands {
    public ClassCommand(PlayerClasses plugin) {
        super(plugin);
        register(new ClassShowCommand(plugin));
        register(new ClassSetCommand(plugin));
    }
}

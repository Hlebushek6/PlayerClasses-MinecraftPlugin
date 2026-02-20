package org.hlebushek.playerClasses.commands.commandWithSubCommands;

import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.CommandWithSubCommands;
import org.hlebushek.playerClasses.commands.lvlSubCommands.LvlAddCommand;
import org.hlebushek.playerClasses.commands.lvlSubCommands.LvlSetCommand;
import org.hlebushek.playerClasses.commands.lvlSubCommands.LvlShowCommand;

public class LvlCommand extends CommandWithSubCommands {
    public LvlCommand (PlayerClasses plugin) {
        super(plugin);
        register(new LvlAddCommand(plugin));
        register(new LvlSetCommand(plugin));
        register(new LvlShowCommand(plugin));
    }
}

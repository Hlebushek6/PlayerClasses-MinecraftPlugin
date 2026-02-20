package org.hlebushek.playerClasses.commands.commandsWithSubCommands;

import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.CommandWithSubCommands;
import org.hlebushek.playerClasses.commands.pointsSubCommands.PointsAddCommand;
import org.hlebushek.playerClasses.commands.pointsSubCommands.PointsSetCommand;
import org.hlebushek.playerClasses.commands.pointsSubCommands.PointsShowCommand;

public class PointsCommand extends CommandWithSubCommands {
    public PointsCommand (PlayerClasses plugin) {
        super(plugin);
        register(new PointsAddCommand(plugin));
        register(new PointsSetCommand(plugin));
        register(new PointsShowCommand(plugin));
    }
}
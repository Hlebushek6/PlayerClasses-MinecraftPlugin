package org.hlebushek.playerClasses.commands.pointsSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.SubCommand;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.dataManage.MessagesManager;

import java.util.ArrayList;
import java.util.List;

public class PointsAddCommand implements SubCommand {
    private final DataManager dataManager;
    private final MessagesManager messages;

    public PointsAddCommand(PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
        messages = plugin.getMessagesManager();
    }

    @Override
    public String getName() {
        return "add";
    }

    @Override
    public List<String> getArguments(String[] args) {
        List<String> completions = new ArrayList<>();
        if (args.length == 2) for (Player p: Bukkit.getOnlinePlayers())
            if(p.getName().toLowerCase().startsWith(args[1].toLowerCase())) completions.add(p.getName());
        return completions;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length != 3) {
            sender.sendMessage(messages.getMessage("invalid_syntax"));
            return;
        }

        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(messages.getMessage("player_not_found"));
            return;
        }

        try {
            int currPoints = dataManager.getPoints(player);
            int points = Integer.parseInt(args[2]) + currPoints;
            dataManager.setPoints(player, points);
            String defaultMessage = messages.getMessage("points_added");
            String playerName = player.getName();
            String message = defaultMessage.replace("%player%", playerName).replace("%points%", args[2]);
            sender.sendMessage(message);
        } catch (NumberFormatException e) {
            sender.sendMessage(messages.getMessage("NaN"));
        }
    }
}

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

public class PointsShowCommand implements SubCommand {
    private final DataManager dataManager;
    private final MessagesManager messages;

    public PointsShowCommand(PlayerClasses plugin) {
        dataManager = plugin.getDataManager();
        messages = plugin.getMessagesManager();
    }

    @Override
    public String getName() {
        return "show";
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
        if (args.length != 2) {
            sender.sendMessage(messages.getMessage("invalid_syntax"));
            return;
        }
        Player player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(messages.getMessage("player_not_found"));
            return;
        }
        String points = Integer.toString(dataManager.getPoints(player));
        String defaultMessage = messages.getMessage("show_points");
        String message = defaultMessage.replace("%player%", player.getName()).replace("%points%", points);
        sender.sendMessage(message);
    }
}

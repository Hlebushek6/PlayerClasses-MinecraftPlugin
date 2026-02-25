package org.hlebushek.playerClasses.commands.lvlSubCommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.commands.SubCommand;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.dataManage.MessagesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LvlShowCommand implements SubCommand {
    private final DataManager dataManager;
    private final MessagesManager messages;

    public LvlShowCommand(PlayerClasses plugin) {
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
        if (args.length < 1 || args.length > 2 || args.length == 1 && !(sender instanceof Player)) {
            sender.sendMessage(messages.getMessage("invalid_syntax"));
            return;
        }
        Player player;
        if (args.length == 1) player = (Player) sender;
        else if (!sender.hasPermission("playerClasses.admin")) {
            sender.sendMessage(messages.getMessage("no_permission"));
            return;
        }
        else player = Bukkit.getPlayer(args[1]);
        if (player == null) {
            sender.sendMessage(messages.getMessage("player_not_found"));
            return;
        }
        String lvl = Integer.toString(dataManager.getLevel(player));
        Map<String, String> placeholders = Map.of("%player%", player.getName(), "%lvl%", lvl);
        sender.sendMessage(messages.getMessage("show_lvl", placeholders));
    }
}

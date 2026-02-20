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

public class LvlAddCommand implements SubCommand {
    private final DataManager dataManager;
    private final MessagesManager messages;

    public LvlAddCommand(PlayerClasses plugin) {
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
        //TODO
    }
}

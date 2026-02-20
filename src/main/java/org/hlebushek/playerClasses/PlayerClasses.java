package org.hlebushek.playerClasses;

import org.bukkit.plugin.java.JavaPlugin;
import org.hlebushek.playerClasses.commands.commandWithSubCommands.ClassCommand;
import org.hlebushek.playerClasses.commands.commandWithSubCommands.LvlCommand;
import org.hlebushek.playerClasses.commands.commandWithSubCommands.PointsCommand;
import org.hlebushek.playerClasses.dataManage.DataManager;
import org.hlebushek.playerClasses.dataManage.MessagesManager;
import org.hlebushek.playerClasses.dataManage.ConfigManager;
import org.hlebushek.playerClasses.listeners.CrafterListener;
import org.hlebushek.playerClasses.listeners.HobbitListener;
import org.hlebushek.playerClasses.listeners.RunnerListener;

public final class PlayerClasses extends JavaPlugin {
    private DataManager dataManager;
    private ConfigManager configManager;
    private MessagesManager messagesManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("Плагин запущен!");

        dataManager = new DataManager(this);
        configManager = new ConfigManager(this);
        messagesManager = new MessagesManager(this);

        getServer().getPluginManager().registerEvents(new RunnerListener(this), this);
        getServer().getPluginManager().registerEvents(new CrafterListener(this), this);
        getServer().getPluginManager().registerEvents(new HobbitListener(this), this);

        getCommand("class").setExecutor(new ClassCommand(this));
        getCommand("points").setExecutor(new PointsCommand(this));
        getCommand("lvl").setExecutor(new LvlCommand(this));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Плагин отключен!");
    }

    public DataManager getDataManager() {
        return dataManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public MessagesManager getMessagesManager() {
        return messagesManager;
    }
}

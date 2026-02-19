package org.hlebushek.playerClasses.dataManage;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class MessagesManager {
    private final JavaPlugin plugin;
    private File messagesFile;
    private FileConfiguration messages;

    public MessagesManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    private void loadMessages() {
        messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) plugin.saveResource("messages.yml", false);
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&', messages.getString(key, "default"));
    }
}

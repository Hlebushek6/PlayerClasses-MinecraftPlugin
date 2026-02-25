package org.hlebushek.playerClasses.dataManage;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Map;

public class MessagesManager {
    private final JavaPlugin plugin;
    private FileConfiguration messages;

    public MessagesManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadMessages();
    }

    private void loadMessages() {
        File messagesFile = new File(plugin.getDataFolder(), "messages.yml");
        if (!messagesFile.exists()) plugin.saveResource("messages.yml", false);
        messages = YamlConfiguration.loadConfiguration(messagesFile);
    }

    public Component getMessage(String key) {
        String message = messages.getString("prefix") + messages.getString(key, key);
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }

    public Component getMessage(String key, Map<String, String> placeholders) {
        String message = messages.getString("prefix") + messages.getString(key, key);
        for (Map.Entry<String, String> entry: placeholders.entrySet())
            message = message.replace(entry.getKey(), entry.getValue());
        return LegacyComponentSerializer.legacyAmpersand().deserialize(message);
    }
}

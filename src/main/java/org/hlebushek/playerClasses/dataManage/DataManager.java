package org.hlebushek.playerClasses.dataManage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.hlebushek.playerClasses.model.Classes;

import java.io.File;

public class DataManager {
    private final JavaPlugin plugin;
    private File dataFile;
    private FileConfiguration data;

    public DataManager(JavaPlugin plugin) {
        this.plugin = plugin;
        loadData();
    }

    private void loadData() {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        if (!dataFile.exists()) plugin.saveResource("data.yml", false);
        data = YamlConfiguration.loadConfiguration(dataFile);
    }

    public Classes getClass(Player player) {
        return Classes.valueOf(data.getString("players." + player.getUniqueId() + ".class", "NONE)"));
    }

    public void setClass(Player player, Classes playerClass) {
        data.set("players." + player + ".class", playerClass.toString());
    }
}

package org.hlebushek.playerClasses.dataManage;

import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.hlebushek.playerClasses.PlayerClasses;
import org.hlebushek.playerClasses.model.Classes;

import java.io.File;

public class DataManager {
    private final PlayerClasses plugin;
    private final ConfigManager configManager;
    private File dataFile;
    private FileConfiguration data;

    public DataManager(PlayerClasses plugin) {
        this.plugin = plugin;
        configManager = plugin.getConfigManager();
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

    public int getPoints(Player player) {
        return data.getInt("players." + player.getUniqueId() + ".points", 0);
    }

    public void setPoints(Player player, int points) {
        int explvl = points / configManager.getPointsToUpgrade() + 1;
        if (explvl != player.getLevel()) player.setLevel(explvl);
        data.set("players." + player + ".points", points);
    }

    public int getLevel(Player player) {
        return data.getInt("players." + player.getUniqueId() + ".lvl", 1);
    }

    private void setLevel(Player player, int lvl) {
        data.set("players." + player.getUniqueId() + ".lvl", lvl);
        player.playSound(player, Sound.ENTITY_PLAYER_LEVELUP, 1 , 1);
    }
}

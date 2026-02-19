package org.hlebushek.playerClasses.dataManage;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.hlebushek.playerClasses.model.RunnerConfig;

import java.io.File;

public class ConfigManager {
    private final FileConfiguration config;

    public ConfigManager(JavaPlugin plugin) {
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public RunnerConfig getRunnerConfig() {
        int speed_level = config.getInt("runner.speed_level");
        int fall_damage_multiplier = config.getInt("runner.fall_damage_multiplier");
        return new RunnerConfig(speed_level, fall_damage_multiplier);
    }
}

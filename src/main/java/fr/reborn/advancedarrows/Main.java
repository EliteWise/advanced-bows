package fr.reborn.advancedarrows;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    public YmlConfiguration ymlConfigRequest;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ymlConfigRequest = new YmlConfiguration(this);
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ArrowEffect(),this);
        pluginManager.registerEvents(new ArrowParticle(this),this);

       CraftManagement craftManagement = new CraftManagement();
        craftManagement.createRecipe();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

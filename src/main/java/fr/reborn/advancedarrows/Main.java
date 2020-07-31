package fr.reborn.advancedarrows;

import fr.reborn.advancedarrows.config.YmlConfiguration;
import fr.reborn.advancedarrows.listener.ArrowEffect;
import fr.reborn.advancedarrows.listener.ArrowParticle;
import fr.reborn.advancedarrows.listener.CancelAnvil;
import fr.reborn.advancedarrows.listener.LootableArrow;
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
        pluginManager.registerEvents(new ArrowEffect(this),this);
        pluginManager.registerEvents(new ArrowParticle(this),this);
        pluginManager.registerEvents(new CancelAnvil(), this);
        pluginManager.registerEvents(new LootableArrow(this), this);

       CraftManagement craftManagement = new CraftManagement(this);
       craftManagement.createRecipe();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

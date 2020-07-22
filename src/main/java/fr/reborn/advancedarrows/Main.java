package fr.reborn.advancedarrows;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public final class Main extends JavaPlugin {


    public YmlConfiguration ymlConfigRequest;
    private Object Player;

    @Override
    public void onEnable() {
        // Plugin startup logic
        ymlConfigRequest = new YmlConfiguration(this);
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new ArrowEffect(),this);
        pluginManager.registerEvents(new ArrowParticle(),this);

       CraftManagement items = new CraftManagement();
       items.customRecipe();
       items.unShape();
       items.unShapes();





    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

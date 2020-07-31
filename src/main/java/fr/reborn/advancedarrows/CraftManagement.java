package fr.reborn.advancedarrows;

import fr.reborn.advancedarrows.config.YmlConfiguration;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class CraftManagement implements Listener {

    private Main main;
    private Plugin plugin = Main.getPlugin(Main.class);

    public CraftManagement(Main main) {
        this.main = main;
    }

    @SuppressWarnings("deprecation")
    public void createRecipe() {
        YmlConfiguration ymlConfiguration = new YmlConfiguration(main);
        for(Bow bow : Bow.values()) {
            if(ymlConfiguration.isCraftEnable(bow.getBowName())) {
                ItemStack item = new ItemStack(bow.getStack());

                ShapelessRecipe slrr = new ShapelessRecipe(item);
                slrr.addIngredient(3, Material.STRING);
                slrr.addIngredient(3, Material.STICK);
                slrr.addIngredient(bow.getNumber(), bow.getCustomRecipe());
                plugin.getServer().addRecipe(slrr);
            }
        }
    }

}























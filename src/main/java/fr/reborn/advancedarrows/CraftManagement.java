package fr.reborn.advancedarrows;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;

public class CraftManagement implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    @SuppressWarnings("deprecation")
    public void createRecipe() {
        ItemStack item = new ItemStack(Material.ARROW);
        ShapelessRecipe slrr = new ShapelessRecipe(item);
        slrr.addIngredient(2, Material.ENDER_PEARL);
        slrr.addIngredient(2, Material.GOLD_NUGGET);
        plugin.getServer().addRecipe(slrr);
    }

}























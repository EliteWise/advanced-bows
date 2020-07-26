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
        for(Bow bow : Bow.values()) {
            ItemStack item = new ItemStack(bow.getStack());

            ShapelessRecipe slrr = new ShapelessRecipe(item);
            slrr.addIngredient(3, Material.STRING);
            slrr.addIngredient(3, Material.STICK);
            slrr.addIngredient(bow.getNumber(), bow.getCustomRecipe());
            plugin.getServer().addRecipe(slrr);
        }

    }

}























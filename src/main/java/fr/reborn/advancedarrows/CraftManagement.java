package fr.reborn.advancedarrows;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class CraftManagement implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    @SuppressWarnings("deprecation")
    public void createRecipe() {
        ItemStack item = new ItemStack(Material.BOW);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(Bow.INFLAMED.getBowName());
        item.setItemMeta(itemMeta);


        ShapelessRecipe slrr = new ShapelessRecipe(item);
        slrr.addIngredient(3, Material.STRING);
        slrr.addIngredient(3, Material.STICK);
        slrr.addIngredient(3, Material.FIRE_CHARGE);
        plugin.getServer().addRecipe(slrr);
    }

}























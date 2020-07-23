package fr.reborn.advancedarrows;

import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

public class CraftManagement implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    @SuppressWarnings("deprecation")
    public void customRecipe() {

    ItemStack item = new ItemStack(Material.BOW, 1);
    ItemMeta meta = item.getItemMeta();
    meta.setDisplayName(Bow.EXPLOSIVE_BOW.getBowName());
    item.setItemMeta(meta);

    ShapedRecipe r = new ShapedRecipe(item);
    r.shape(" # "," $ ","   ");
    r.setIngredient('#', Material.LAVA_BUCKET);
    r.setIngredient('$', Material.BOW);
        plugin.getServer().addRecipe(r);


    }
    @SuppressWarnings("deprecation")
    public void unShape() {
        ItemStack item = new ItemStack(Material.SPECTRAL_ARROW);
        ShapelessRecipe slr = new ShapelessRecipe(item);
        slr.addIngredient(2, Material.BAMBOO);
        slr.addIngredient(2, Material.GOLD_NUGGET);
        plugin.getServer().addRecipe(slr);
    }
    @SuppressWarnings("deprecation")
    public void unShapes() {
        ItemStack item = new ItemStack(Material.ARROW);
        ShapelessRecipe slrr = new ShapelessRecipe(item);
        slrr.addIngredient(2, Material.ENDER_PEARL);
        slrr.addIngredient(2, Material.GOLD_NUGGET);
        plugin.getServer().addRecipe(slrr);


    }

}























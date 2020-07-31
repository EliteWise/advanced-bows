package fr.reborn.advancedarrows.listener;

import fr.reborn.advancedarrows.Bow;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class CancelAnvil implements Listener {

    @EventHandler
    public void onRenameBowInAnvil(InventoryClickEvent e) {
        Inventory inventory = e.getInventory();
        ItemStack clickedItem = e.getCurrentItem();

        if(inventory.getType() == InventoryType.ANVIL && clickedItem.getType() == Material.BOW && e.getSlotType() == InventoryType.SlotType.RESULT) {
            for(Bow bow : Bow.values()) {
                if(clickedItem.getItemMeta().getDisplayName().equalsIgnoreCase(bow.getBowName())) {
                    e.setCancelled(true);
                }
            }
        }
    }
}

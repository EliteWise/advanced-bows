package fr.reborn.advancedarrows.listener;

import fr.reborn.advancedarrows.Bow;
import fr.reborn.advancedarrows.Main;
import fr.reborn.advancedarrows.config.YmlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupArrowEvent;

public class LootableArrow implements Listener {

    private Main main;

    public LootableArrow(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onArrowIsLoot(PlayerPickupArrowEvent e) {
        Entity arrow = e.getArrow();
        YmlConfiguration ymlConfiguration = new YmlConfiguration(main);
        if(!ymlConfiguration.isArrowLootable()) {
            for(Bow bow : Bow.values()) {
                if(arrow.hasMetadata(bow.getBowName())) {
                    e.setCancelled(true);
                }
            }
        }
    }

}

package fr.reborn.advancedarrows;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import java.util.HashMap;

public class ArrowEffect implements Listener {

    HashMap<String, String> bow = new HashMap<>();

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent a) {
        Entity entity = a.getEntity();

        if (a.getEntity().getType() == EntityType.PLAYER && a.getBow().hasItemMeta()) {
            bow.put(entity.getName(), a.getBow().getItemMeta().getDisplayName());
        }
    }


    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Block b = e.getHitBlock();

        if (e.getEntity().getShooter() instanceof Player && bow.containsKey((((Player) e.getEntity().getShooter()).getName()))) {
            String bowName = bow.get((((Player) e.getEntity().getShooter()).getName()));
            b.setType(Material.FIRE);

            bow.remove((((Player) e.getEntity().getShooter()).getName()));

        }

    }

}









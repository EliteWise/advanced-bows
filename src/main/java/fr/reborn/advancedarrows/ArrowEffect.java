package fr.reborn.advancedarrows;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
public class ArrowEffect implements Listener {


        @EventHandler
        public void onProjectileHit(ProjectileHitEvent e) {

            Block b = e.getHitBlock();
            Entity entity = e.getEntity();
            if (entity.getType() == EntityType.ARROW && entity.getCustomName().equalsIgnoreCase(Arrow.EXPLOSIVEARROW.getArrowName())) {
                b.setType(Material.LAVA);


            }
        }
    }




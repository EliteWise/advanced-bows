package fr.reborn.advancedarrows;


import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.projectiles.ProjectileSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ArrowEffect implements Listener {

    private Plugin plugin = Main.getPlugin(Main.class);

    @EventHandler
    public void onEntityShootBow(EntityShootBowEvent a) {
        Entity entity = a.getEntity();
        Entity projectile = a.getProjectile();
        ItemStack bow = a.getBow();
        if (entity.getType() == EntityType.PLAYER && bow.hasItemMeta()) {
            String bowCustomName = bow.getItemMeta().getDisplayName();
            projectile.setMetadata(bowCustomName, new FixedMetadataValue(plugin, "ArrowData"));
        }
    }


    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Block b = e.getHitBlock();
        Entity projectile = e.getEntity();
        ProjectileSource shooter = e.getEntity().getShooter();

        if (projectile instanceof Arrow && shooter instanceof Player && b != null) {
            Class<?>[] paramTypes = {Block.class, Entity.class, ProjectileSource.class};
            for(Bow bow : Bow.values()) {
                if(projectile.hasMetadata(bow.getBowName())) {
                    Method method = this.getClass().getDeclaredMethod(bow.getBowName().toLowerCase() + "Effect", paramTypes);
                    method.invoke(this, b, projectile, shooter);
                }
            }
        }

    }

    public void iceEffect (Block block, Entity projectile, ProjectileSource shooter) {
        block.getWorld().getBlockAt(block.getLocation()).getRelative(BlockFace.UP).setType(Material.ICE);
    }

}









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
    public void onProjectileHit(ProjectileHitEvent e) {
        Block b = e.getHitBlock();
        Entity projectile = e.getEntity();
        ProjectileSource entity = e.getEntity().getShooter();

        if (projectile instanceof Arrow && entity instanceof Player && projectile.hasMetadata(Bow.EXPLOSIVE_BOW.getBowName()) && b != null) {
            b.getWorld().getBlockAt(b.getLocation()).getRelative(BlockFace.UP).setType(Material.FIRE);
        }
    }

}









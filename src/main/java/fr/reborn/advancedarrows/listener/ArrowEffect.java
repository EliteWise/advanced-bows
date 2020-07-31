package fr.reborn.advancedarrows.listener;


import fr.reborn.advancedarrows.Bow;
import fr.reborn.advancedarrows.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
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
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ArrowEffect implements Listener {

    private Main main;
    private Plugin plugin = Main.getPlugin(Main.class);

    public ArrowEffect(Main main) {
        this.main = main;
    }

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
        Entity entityHit = e.getHitEntity();
        if (projectile instanceof Arrow && shooter instanceof Player && b != null) {
            Class<?>[] paramTypes = {Block.class, Entity.class, ProjectileSource.class, Entity.class};
            for(Bow bow : Bow.values()) {
                if(projectile.hasMetadata(bow.getBowName())) {
                    Method method = this.getClass().getDeclaredMethod(bow.getBowName().toLowerCase() + "Effect", paramTypes);
                    method.invoke(this, b, projectile, shooter, entityHit);
                }
            }
        }
    }

    public void iceEffect (Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        if(entityHit instanceof Player) {
            Player player = (Player)entityHit;
            Bukkit.broadcastMessage("Walk speed value: " + player.getWalkSpeed());
        }
    }

    public void inflamedEffect (Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        block.getWorld().getBlockAt(block.getLocation()).getRelative(BlockFace.UP).setType(Material.FIRE);


        Location loc = block.getLocation();

        int rayon = 5;

        for (int i = 0; i < rayon; i++) {
            Vector v = loc.toVector();
            v.setX(v.getX() + i);
            for (int a = 0; a < 360; a += 360 / rayon) {
                Block select = block.getWorld().getBlockAt(v.toLocation(block.getWorld()));
                Bukkit.getServer().broadcastMessage(select.getLocation().toString());
                block.getWorld().getBlockAt(select.getLocation()).getRelative(BlockFace.UP).setType(Material.FIRE);

                double rad = Math.toRadians(360 / rayon); //vu que j'edit le vecteur v directement
                double x = v.getX();
                double z = v.getZ();

                double cos = Math.cos(rad);
                double sin = Math.sin(rad);

                v = new Vector((cos * x - sin * z), v.getY(), (sin * x + cos * z));
                Bukkit.broadcastMessage("ca marche l'arc de feu avec zone");
            }
        }
    }
    public void hungerEffect (Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        Player player = (Player)shooter;
        player.setFoodLevel(5);
        


    }
    public void explosiveEffect (Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        Location loc = block.getLocation();
        block.getWorld().createExplosion(loc,2F,true);

    }
    public void bumpEffect (Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {

    }
    public void blockplacementEffect (Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {

    }
    public void teleportationEffect (Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        Location loc = block.getLocation();
        Player player = (Player)shooter;
        player.teleport(loc);
        Bukkit.broadcastMessage("Ca marche l'arc de tp");

    }
    public void removeequipementEffect (Block block, Entity projectile, ProjectileSource player, Entity entityHit)  {

    }


}










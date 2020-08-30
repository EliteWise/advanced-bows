package fr.reborn.advancedarrows.listener;

import fr.reborn.advancedarrows.Bow;
import fr.reborn.advancedarrows.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.server.BroadcastMessageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;

import static org.apache.commons.lang.math.RandomUtils.nextInt;

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
        if (projectile instanceof Arrow && shooter instanceof Player) {
            Class<?>[] paramTypes = {Block.class, Entity.class, ProjectileSource.class, Entity.class};
            for (Bow bow : Bow.values()) {
                if (projectile.hasMetadata(bow.getBowName())) {
                    Method method = this.getClass().getDeclaredMethod(bow.getBowName().toLowerCase().replace(" ", "") + "Effect", paramTypes);
                    method.invoke(this, b, projectile, shooter, entityHit);
                }
            }
        }
    }

    private float walkSpeed = 0.2f;

    public void iceEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        if (entityHit instanceof Player) {
            Player player = (Player) entityHit;

            player.setWalkSpeed(0);
            player.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, Integer.MAX_VALUE, 250));

            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    player.setWalkSpeed(walkSpeed);
                    player.removePotionEffect(PotionEffectType.JUMP);
                }
            }, 40);

        }
    }

    public void inflamedEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        block.getWorld().getBlockAt(block.getLocation()).getRelative(BlockFace.UP).setType(Material.FIRE);

        Location loc = block.getLocation();

        int rayon = 5;
        int tmp = rayon;
        

            for (int i = 0; i < rayon; i++) {
                Vector v = new Vector(tmp, 0, 0);
                for (int a = 0; a < 360; a += 5) {

                    loc.add(v.getX(), v.getY(), v.getZ());

                    Block select = loc.getBlock();
                    if (block.getType() == Material.AIR) {
                    select.getRelative(BlockFace.UP).setType(Material.FIRE);

                    double rad = Math.toRadians(5);
                    double x = v.getX();
                    double z = v.getZ();

                    double cos = Math.cos(rad);
                    double sin = Math.sin(rad);

                    v = new Vector((cos * x - sin * z), 0, (sin * x + cos * z));
                    loc = block.getLocation();
                }
                tmp -= 1;
            }
        }

    }

    public void hungerEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        if (entityHit instanceof Player) {
            Player player = (Player) entityHit;
            int foodLevel = player.getFoodLevel();
            player.setFoodLevel(foodLevel <= 1 ? 0 : foodLevel - 1);
        }
    }

    public void explosiveEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        Location loc = block.getLocation();
        block.getWorld().createExplosion(loc, 2F, true);
    }

    public void bumpEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        if (entityHit instanceof Player && shooter instanceof Player) {
            Player player = (Player) entityHit;
            Player shooterPlayer = (Player) shooter;
            Location centerLocation = shooterPlayer.getLocation();
            Vector entV = player.getLocation().toVector(); // other player which should get knockback
            Vector plV = shooterPlayer.getLocation().toVector(); // your player
            Vector v = entV.clone().subtract(plV).multiply(2 / entV.distance(plV));
            v.setY(0.6);
            player.setVelocity(v);
        }
    }

    public void blockplacementEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        Location loc = block.getLocation().add(0, 1, 0);
        loc.getWorld().getBlockAt(loc).setType(Material.STONE);
    }

    public void teleportationEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        Location loc = block.getLocation().add(0, 1, 0);
        Player player = (Player) shooter;
        player.teleport(loc);
    }

    public void removeequipmentEffect(Block block, Entity projectile, ProjectileSource player, Entity entityHit) {
        Player playerHit = (Player) entityHit;
        Random r = new Random();
        if (r.nextInt(100)<10) {

            playerHit.getInventory().setItem(nextInt(4) + 100, new ItemStack(Material.AIR));

        }

    }


    public void lightningEffect(Block block, Entity projectile, ProjectileSource player, Entity entityHit) {
        entityHit.getWorld().strikeLightning(entityHit.getLocation());
        block.getWorld().strikeLightning(block.getLocation());


    }

    /*   public void setEntityNoAI(Entity entity) {
       final net.minecraft.server.v1_15_R1.Entity nms = ((CraftEntity) entity).getHandle();
         final NBTTagCompound tag = new NBTTagCompound();
          nms.c(tag);
           tag.setBoolean("NoAI", true);
           final EntityLiving entitys = (EntityLiving) nms;
           entitys.a(tag);
       }


       public void hallucinogenicEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
           if(entityHit instanceof Player) {
               Player playerHit = (Player) entityHit;
               Location hitLoc = playerHit.getLocation();

               playerHit.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 60, 2));
               playerHit.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, 2));
               playerHit.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 60, 2));

               ArrayList<EntityType> aliveEntities = new ArrayList<>();
               for (EntityType value : EntityType.values()) {
                   if (value.isAlive())
                       aliveEntities.add(value);
               }
               aliveEntities.remove(EntityType.PLAYER);
               aliveEntities.remove(EntityType.ARMOR_STAND);
               aliveEntities.remove(EntityType.WITHER);
               aliveEntities.remove(EntityType.GIANT);

               ArrayList<Vector> mobsLocs = new ArrayList<Vector>() {{
                   add(new Vector(-3, 0, 0));
                   add(new Vector(0, 0, -3));
                   add(new Vector(3, 0, 0));
                   add(new Vector(0, 0, 3));
               }};

               ArrayList<Entity> tempEntities = new ArrayList<>();

               int randomEntity = new Random().nextInt(aliveEntities.size());
               int index = 0;
               for(Vector vectorLoc : mobsLocs) {
                   Entity entity = hitLoc.getWorld().spawnEntity(hitLoc.add(vectorLoc), aliveEntities.get(randomEntity));
                   hitLoc.subtract(vectorLoc);
                   tempEntities.add(entity);
                   setEntityNoAI(entity);
                   entity.setRotation(index < 2 ? hitLoc.getYaw() + 180 : hitLoc.getYaw(), index < 2 ? hitLoc.getPitch() + 180 : hitLoc.getPitch());
                   index++;
               }

               Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                   @Override
                   public void run() {
                       tempEntities.forEach(entity -> entity.remove());
                   }
               }, 60);

           }
       }
       */
    public void playerinfosEffect(Block block, Entity projectile, ProjectileSource player, Entity entityHit) {
        if (entityHit instanceof Player) {
            Player playerHit = (Player) entityHit;
            Player shooter = (Player) player;


            shooter.sendMessage(playerHit.getDisplayName()+ "\n" + playerHit.getFoodLevel() + "\n" + Math.round(playerHit.getHealth()) + "\n" + playerHit.getInventory().getBoots().getType().name() +  "\n" + " message");
            playerHit.getActivePotionEffects().forEach(effects -> shooter.sendMessage("" + effects.getType().getName()));


        }
    }

    public void roundholeEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        block.getWorld().getBlockAt(block.getLocation()).getRelative(BlockFace.UP).setType(Material.AIR);

        Location loc = block.getLocation();

        int rayon = 2;
        int tmp = rayon;

        for (int i = 0; i < rayon; i++) {
            Vector v = new Vector(tmp, -1, 0);
            for (int a = 0; a < 360; a += 2) {

                loc.add(v.getX(), v.getY(), v.getZ());

                Block select = loc.getBlock();

                select.getRelative(BlockFace.UP).setType(Material.AIR);

                double rad = Math.toRadians(5);
                double x = v.getX();
                double z = v.getZ();

                double cos = Math.cos(rad);
                double sin = Math.sin(rad);

                v = new Vector((cos * x - sin * z), -1, (sin * x + cos * z));
                loc = block.getLocation();
            }
            tmp -= 1;
        }
    }
}










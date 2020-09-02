package fr.reborn.advancedarrows.listener;

import fr.reborn.advancedarrows.Bow;
import fr.reborn.advancedarrows.Main;
import fr.reborn.advancedarrows.config.YmlConfiguration;
import net.minecraft.server.v1_15_R1.EntityLiving;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

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

        YmlConfiguration ymlConfiguration = new YmlConfiguration(main);

        if (projectile instanceof Arrow && shooter instanceof Player) {
            Class<?>[] paramTypes = {Block.class, Entity.class, ProjectileSource.class, Entity.class};
            for (Bow bow : Bow.values()) {
                if (ymlConfiguration.isCraftEnable(bow.getBowName().toUpperCase().replace(" ", "_")) && projectile.hasMetadata(bow.getBowName())) {
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

            YmlConfiguration ymlConfiguration = new YmlConfiguration(main);

            Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                @Override
                public void run() {
                    player.setWalkSpeed(walkSpeed);
                    player.removePotionEffect(PotionEffectType.JUMP);
                }
            }, 20 * (int) ymlConfiguration.getEffectParamByName("ICE", "delay"));

        }
    }

    public void inflamedEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        YmlConfiguration ymlConfiguration = new YmlConfiguration(main);
        boolean entities_only = (boolean) ymlConfiguration.getEffectParamByName("INFLAMED", "entities-only");

        ArrayList<Block> inflamedBlocks = new ArrayList<>();
        inflamedBlocks.clear();

            Location hitLoc = entities_only ? (entityHit != null ? entityHit.getLocation() : null) : (entityHit != null ? entityHit.getLocation() : block.getLocation());
            World worldBlock = block.getWorld();
            if(hitLoc != null) {
                for(int i = 0; i < 3; i++) {
                    Block currentXBlock = worldBlock.getBlockAt(hitLoc.add(-1 + i, 0, 0)).getRelative(BlockFace.UP);
                    Block currentZBlock = worldBlock.getBlockAt(hitLoc.add(0, 0, -1 + i)).getRelative(BlockFace.UP);

                    if(currentXBlock.getType() == Material.AIR) {
                        currentXBlock.setType(Material.FIRE);
                        inflamedBlocks.add(currentXBlock);
                        if(currentZBlock.getType() == Material.AIR) {
                            currentZBlock.setType(Material.FIRE);
                            inflamedBlocks.add(currentZBlock);
                        }
                    }
                }
                Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                    @Override
                    public void run() {
                        inflamedBlocks.forEach(fire -> fire.setType(Material.AIR));
                    }
                }, 20 * (int) ymlConfiguration.getEffectParamByName("INFLAMED", "delay"));
            }
    }

    public void hungerEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        if (entityHit instanceof Player) {
            Player player = (Player) entityHit;
            int foodLevel = player.getFoodLevel();

            YmlConfiguration ymlConfiguration = new YmlConfiguration(main);
            player.setFoodLevel(foodLevel <= 1 ? 0 : foodLevel - (int) ymlConfiguration.getEffectParamByName("HUNGER", "food"));
        }
    }

    public void explosiveEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        Location loc = block.getLocation();
        YmlConfiguration ymlConfiguration = new YmlConfiguration(main);
        block.getWorld().createExplosion(loc, (Float.valueOf((String) ymlConfiguration.getEffectParamByName("EXPLOSIVE", "power"))), false);
    }

    public void bumpEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        if (entityHit instanceof Player && shooter instanceof Player) {
            Player player = (Player) entityHit;
            Player shooterPlayer = (Player) shooter;
            Location centerLocation = shooterPlayer.getLocation();
            Vector entV = player.getLocation().toVector(); // other player which should get knockback
            Vector plV = shooterPlayer.getLocation().toVector(); // your player
            Vector v = entV.clone().subtract(plV).multiply(2 / entV.distance(plV));

            YmlConfiguration ymlConfiguration = new YmlConfiguration(main);
            double bump_y = (double) ymlConfiguration.getEffectParamByName("BUMP", "vertical-bump");

            v.setY(bump_y);
            player.setVelocity(v);
        }
    }

    public void blockplacementEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        Location loc = block.getLocation().add(0, 1, 0);

        YmlConfiguration ymlConfiguration = new YmlConfiguration(main);
        loc.getWorld().getBlockAt(loc).setType(Material.getMaterial(ymlConfiguration.getEffectParamByName("BLOCK_PLACEMENT", "block").toString()));
    }

    public void teleportationEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        YmlConfiguration ymlConfiguration = new YmlConfiguration(main);
        boolean entities_only = (boolean) ymlConfiguration.getEffectParamByName("TELEPORTATION", "entities-only");
        Location loc = entities_only ? entityHit.getLocation() : (entityHit != null ? entityHit.getLocation() : block.getLocation().add(0, 1, 0));
        Player player = (Player) shooter;
        player.teleport(loc);
    }

    public void removeequipmentEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        if(entityHit instanceof Player) {
            Player playerHit = (Player) entityHit;

            Random r = new Random();
            YmlConfiguration ymlConfiguration = new YmlConfiguration(main);

            if (r.nextInt(100) < (int) ymlConfiguration.getEffectParamByName("REMOVE_EQUIPMENT", "percent")) {
                playerHit.getInventory().setItem(r.nextInt(4) + 36, new ItemStack(Material.AIR));
            }
        }
    }

    public void lightningEffect(Block block, Entity projectile, ProjectileSource player, Entity entityHit) {
        YmlConfiguration ymlConfiguration = new YmlConfiguration(main);
        boolean entities_only = (boolean) ymlConfiguration.getEffectParamByName("LIGHTNING", "entities-only");

        Location hitLoc = entities_only ? (entityHit != null ? entityHit.getLocation() : null) : (entityHit != null ? entityHit.getLocation() : block.getLocation());
        if(hitLoc != null) {
            hitLoc.getWorld().strikeLightning(hitLoc);
        }
    }

       public void setEntityNoAI(Entity entity) {
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

               YmlConfiguration ymlConfiguration = new YmlConfiguration(main);

               Bukkit.getScheduler().runTaskLater(plugin, new Runnable() {
                   @Override
                   public void run() {
                       tempEntities.forEach(entity -> entity.remove());
                   }
               }, 20 * (int) ymlConfiguration.getEffectParamByName("HALLUCINOGENIC", "delay"));

           }
       }

    public void playerinfosEffect(Block block, Entity projectile, ProjectileSource player, Entity entityHit) {
        if (entityHit instanceof Player) {
            Player playerHit = (Player) entityHit;
            Player shooter = (Player) player;

            AtomicInteger index = new AtomicInteger(1);

            shooter.sendMessage("§6Name: §f" + playerHit.getDisplayName() + "\n" + "§6Health: §f" + Math.round(playerHit.getHealth()) + "\n" + "§6Food Level: §f" + playerHit.getFoodLevel());
            playerHit.getActivePotionEffects().forEach(effects -> shooter.sendMessage("§6" + index.getAndIncrement() + ". Potion Effect: §f" + effects.getType().getName()));
        }
    }

    public void roundholeEffect(Block block, Entity projectile, ProjectileSource shooter, Entity entityHit) {
        block.getWorld().getBlockAt(block.getLocation()).getRelative(BlockFace.UP).setType(Material.AIR);

        Location loc = block.getLocation();
        YmlConfiguration ymlConfiguration = new YmlConfiguration(main);

        int rayon = (int) ymlConfiguration.getEffectParamByName("ROUND_HOLE", "radius");
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










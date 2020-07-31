package fr.reborn.advancedarrows.listener;

import fr.reborn.advancedarrows.Bow;
import fr.reborn.advancedarrows.Main;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class ArrowParticle implements Listener {

    private Main main;

    public ArrowParticle(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onProjectilLaunch(ProjectileLaunchEvent e) {
        Projectile projectile = e.getEntity();
        Entity shooter = (Entity) e.getEntity().getShooter();

        if(projectile instanceof Arrow && shooter instanceof Player) {
            for(Bow bow : Bow.values()) {
                if(projectile.hasMetadata(bow.getBowName())) {
                    new BukkitRunnable() {

                        @Override
                        public void run() {
                            if(projectile.isOnGround() || projectile == null || projectile.isDead()) {
                                cancel();
                            } else {
                                projectile.getWorld().spawnParticle(bow.getParticle(), projectile.getLocation(), 1);
                            }
                        }
                    }.runTaskTimerAsynchronously(main, 0, 1);
                }
            }
        }
    }

}

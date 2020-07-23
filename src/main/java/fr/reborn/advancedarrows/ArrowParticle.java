package fr.reborn.advancedarrows;

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

        if(projectile instanceof Arrow && shooter instanceof Player && projectile.hasMetadata(Bow.EXPLOSIVE_BOW.getBowName())) {
            new BukkitRunnable() {

                @Override
                public void run() {
                    if(!projectile.isOnGround()) {
                        projectile.getWorld().spawnParticle(Bow.EXPLOSIVE_BOW.getParticle(), projectile.getLocation(), 1);
                    } else {
                        cancel();
                    }

                }
            }.runTaskTimerAsynchronously(main, 0, 1);
        }
    }

}

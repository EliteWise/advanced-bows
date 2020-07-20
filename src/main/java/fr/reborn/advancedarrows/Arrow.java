package fr.reborn.advancedarrows;

import org.bukkit.Particle;

public enum Arrow {
    EXPLOSIVEARROW("Lava arrow", Particle.DRIP_LAVA);
    private String arrowName;
    private Particle particle;

    Arrow(String arrowName, Particle particle) {
        this.particle = particle;
        this.arrowName = arrowName;

    }

    public String getArrowName() {
        return arrowName;
    }
    public Particle getParticle() {
        return particle;

    }

}

package fr.reborn.advancedarrows;
import org.bukkit.Particle;

public enum Bow {

    EXPLOSIVE_BOW("Lava Bow", Particle.DRIP_LAVA);

    private String bowName;
    private Particle particle;

    Bow(String bowName, Particle particle) {
        this.particle = particle;
        this.bowName = bowName;
    }

    public String getBowName() {
        return bowName;
    }

    public Particle getParticle() {
        return particle;

    }

}


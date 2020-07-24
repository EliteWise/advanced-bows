package fr.reborn.advancedarrows;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Bow {
    EXPLOSIVE_BOW("Lava bow", Particle.DRIP_LAVA, Material.LAVA_BUCKET, 3),
    FREEZ_BOW("Freeze bow", Particle.DRIP_WATER, Material.ICE, 3);

    private final String bowName;
    private final Particle particle;
    private final Material material;
    private final int number;
    private final ItemStack stack;
    EXPLOSIVE("Lava", Particle.DRIP_LAVA),
    TELEPORTATION("Teleportation"),
    ICE("Ice"),
    INFLAMED("Inflamed"),
    HUNGER("Hunger"),
    PREVENT_DAMAGE("Prevent Damage"),
    BLOCK_PLACEMENT("Block Placement"),
    BUMP("Bump"),
    PLAYER_INFOS("Player Infos"),
    REMOVE_EQUIPMENT("Remove Equipment"),
    FLYING_SHOT("Flying Shot"),
    ROUND_HOLE("Round Hole"),
    LIGHTNING("Lightning");

    Bow(String bowName, Particle particle, Material material, int number) {
        this.particle = particle;
        this.bowName = bowName;
        this.material = material;
        this.number = number;
        this.stack = new ItemStack(Material.BOW);
        ItemMeta meta = this.stack.getItemMeta();
        meta.setDisplayName(bowName);
        this.stack.setItemMeta(meta);
    }

    public String getBowName() {
        return bowName;
    }
    public ItemStack getStack(){
        return stack;
    }
    public Material getMaterial(){
        return material;
    }

    public Integer getNumber(){
        return number;
    }


    public Particle getParticle() {
        return particle ;
    }
}


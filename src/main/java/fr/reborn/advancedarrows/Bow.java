package fr.reborn.advancedarrows;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Bow {

    EXPLOSIVE("Explosive", Particle.CRIT, Material.TNT, 3),
    TELEPORTATION("Teleportation", Particle.SPELL_WITCH, Material.ENDER_PEARL, 9),
    ICE("Ice", Particle.SNOWBALL, Material.ICE, 6),
    INFLAMED("Inflamed", Particle.DRIP_LAVA, Material.LAVA_BUCKET, 3),
    HUNGER("Hunger", Particle.VILLAGER_ANGRY, Material.COOKED_CHICKEN, 60),
    PREVENT_DAMAGE("Prevent Damage", Particle.CRIT_MAGIC, Material.SHIELD, 2),
    BLOCK_PLACEMENT("Block Placement", Particle.BLOCK_CRACK, Material.OBSIDIAN, 20),
    BUMP("Bump", Particle.EXPLOSION_NORMAL, Material.TNT, 30),
    PLAYER_INFOS("Player Infos", Particle.ENCHANTMENT_TABLE, Material.BOOK, 35),
    REMOVE_EQUIPMENT("Remove Equipment", Particle.DRAGON_BREATH, Material.DIAMOND_CHESTPLATE, 2),
    FLYING_SHOT("Flying Shot", Particle.FIREWORKS_SPARK, Material.FIREWORK_ROCKET, 15),
    ROUND_HOLE("Round Hole", Particle.END_ROD, Material.DIAMOND_SHOVEL, 2),
    LIGHTNING("Lightning", Particle.CLOUD, Material.REDSTONE, 60);

    private final String bowName;
    private final Particle particle;
    private final Material customRecipe;
    private final int number;
    private final ItemStack stack;

    Bow(String bowName, Particle particle, Material customRecipe, int number) {
        this.particle = particle;
        this.bowName = bowName;
        this.customRecipe = customRecipe;
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
    public Material getCustomRecipe(){
        return customRecipe;
    }

    public Integer getNumber(){
        return number;
    }


    public Particle getParticle() {
        return particle ;
    }
}


package fr.reborn.advancedarrows;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public enum Bow {

    EXPLOSIVE("Explosive", Particle.CRIT, Material.TNT, 1),
    TELEPORTATION("Teleportation", Particle.SPELL_WITCH, Material.ENDER_PEARL, 1),
    ICE("Ice", Particle.SNOWBALL, Material.ICE, 1),
    INFLAMED("Inflamed", Particle.DRIP_LAVA, Material.LAVA_BUCKET, 1),
    HUNGER("Hunger", Particle.VILLAGER_ANGRY, Material.COOKED_CHICKEN, 1),
    BLOCK_PLACEMENT("Block Placement", Particle.SPELL, Material.OBSIDIAN, 1),
    BUMP("Bump", Particle.EXPLOSION_NORMAL, Material.ELYTRA, 1),
    PLAYER_INFOS("Player Infos", Particle.ENCHANTMENT_TABLE, Material.BOOK, 1),
    REMOVE_EQUIPMENT("Remove Equipment", Particle.DRAGON_BREATH, Material.DIAMOND_CHESTPLATE, 1),
    ROUND_HOLE("Round Hole", Particle.END_ROD, Material.DIAMOND_SHOVEL, 1),
    LIGHTNING("Lightning", Particle.CLOUD, Material.REDSTONE, 1),
    HALLUCINOGENIC("Hallucinogenic", Particle.TOTEM, Material.CHORUS_FLOWER, 1);

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


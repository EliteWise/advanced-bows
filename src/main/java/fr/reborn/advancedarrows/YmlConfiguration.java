package fr.reborn.advancedarrows;

public class YmlConfiguration {

    private Main main;

    public YmlConfiguration(Main main) {
        this.main = main;
    }

    public Object getEffectParamByName(String effect, String paramName) {
        return main.getConfig().get(effect + "." + paramName);
    }

    public boolean isArrowsLootable() {
        return main.getConfig().getBoolean("lootableArrows");
    }
}

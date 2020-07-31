package fr.reborn.advancedarrows.config;

import fr.reborn.advancedarrows.Main;

public class YmlConfiguration {

    private Main main;

    public YmlConfiguration(Main main) {
        this.main = main;
    }

    public Object getEffectParamByName(String effect, String paramName) {
        return main.getConfig().get(effect + "." + paramName);
    }

    public boolean isCraftEnable(String bow) {
        return main.getConfig().getBoolean(bow + ".enable");
    }

    public boolean isArrowLootable() {
        return main.getConfig().getBoolean("lootableArrows");
    }
}

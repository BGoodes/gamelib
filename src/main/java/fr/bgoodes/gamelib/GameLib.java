package fr.bgoodes.gamelib;

import org.bukkit.plugin.java.JavaPlugin;

public class GameLib extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("GameLib enabled");
    }

    @Override
    public void onDisable() {
        getLogger().info("GameLib disabled");
    }
}

package fr.bgoodes.gamelib;

import fr.bgoodes.gamelib.services.GameServicesManager;
import fr.bgoodes.gamelib.services.state.StateService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GameLib extends JavaPlugin {

    private final @NotNull GameServicesManager servicesManager;

    public GameLib() {
        this.servicesManager = new GameServicesManager(this);
    }

    @Override
    public final void onEnable() {
        this.getLogger().info("===== GameLib initialization =====");

        // Services registration
        this.servicesManager.register(StateService.class, new StateService());

        this.getLogger().info("==================================");
    }

    @Override
    public final void onDisable() {
        this.getLogger().info("===== GameLib deactivation =====");

        this.servicesManager.unregister(this.getClass());

        this.onStop();
        this.getLogger().info("================================");
    }

    public abstract void onStart();
    public abstract void onStop();

    @NotNull
    public static GameLib get() {
        final @Nullable GameLib gameLib = Bukkit.getServicesManager().load(GameLib.class);
        if(gameLib == null)
            throw new IllegalStateException("Tried to access to the GameLib whereas the plugin was disabled.");

        return gameLib;
    }

    @NotNull
    public final <T> T getService(final @NotNull Class<T> clazz) {
        final @Nullable T service = this.getServer().getServicesManager().load(clazz);
        if(service == null)
            throw new IllegalPluginAccessException("Tried to access to a GameLib service whereas the plugin was disabled!");

        return service;
    }
}

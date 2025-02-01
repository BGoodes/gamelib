package fr.bgoodes.gamelib;

import fr.bgoodes.gamelib.services.GameServicesManager;
import fr.bgoodes.gamelib.services.player.AbstractPlayerService;
import fr.bgoodes.gamelib.services.player.GamePlayer;
import fr.bgoodes.gamelib.services.state.StateService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.IllegalPluginAccessException;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class GameLib<GP extends GamePlayer> extends JavaPlugin {

    private final @NotNull GameServicesManager servicesManager;

    public GameLib(final @NotNull AbstractPlayerService<GP> playerService) {
        this.servicesManager = new GameServicesManager(this);

        this.servicesManager.register(AbstractPlayerService.class, playerService);

    }

    @Override
    public final void onEnable() {
        this.getLogger().info("===== GameLib initialization =====");

        // Services registration
        this.servicesManager.register(StateService.class, new StateService());

        this.onStart();
    }

    @Override
    public final void onDisable() {
        this.getLogger().info("===== GameLib deactivation =====");

        this.servicesManager.unregister(this.getClass());

        this.onStop();
    }

    public abstract void onStart();
    public abstract void onStop();

    @NotNull
    public static GameLib<?> get() {
        final @Nullable GameLib<?> gameLib = Bukkit.getServicesManager().load(GameLib.class);
        if(gameLib == null)
            throw new IllegalStateException("Tried to access to the GameLib whereas the plugin was disabled.");

        return gameLib;
    }

    @NotNull
    public GameServicesManager getGameServicesManager() {
        return this.servicesManager;
    }

    @NotNull
    public StateService getStateService() {
        return this.getService(StateService.class);
    }

    @NotNull
    public final <T> T getService(final @NotNull Class<T> clazz) {
        final @Nullable T service = this.getServer().getServicesManager().load(clazz);
        if(service == null)
            throw new IllegalPluginAccessException("Tried to access to a GameLib service whereas the plugin was disabled!");

        return service;
    }
}

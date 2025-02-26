package fr.bgoodes.gamelib.services;

import fr.bgoodes.gamelib.GameLib;
import org.bukkit.plugin.ServicesManager;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Set;

public class GameServicesManager extends GameService {

    @NotNull
    private final Set<GameService> registeredServices;

    @NotNull
    private final GameLib<?> gameLib;

    @NotNull
    private final ServicesManager servicesManager;

    public GameServicesManager(final GameLib<?> gameLib) {
        this.registeredServices = new HashSet<>();
        this.gameLib = gameLib;

        this.servicesManager = gameLib.getServer().getServicesManager();
        this.servicesManager.register(GameLib.class, this.gameLib, this.gameLib, org.bukkit.plugin.ServicePriority.Normal);
    }

    public <T extends GameService> void register(final @NotNull Class<T> clazz, final @NotNull T instance) {

        if (this.registeredServices.stream().anyMatch(s -> s.getClass().equals(clazz))) {
            unregister(clazz);
        }

        this.servicesManager.register(clazz, instance, this.gameLib, org.bukkit.plugin.ServicePriority.Normal);
        this.registeredServices.add(instance);

        instance.onLoad();
    }

    public void unregister(final @NotNull Class<?> clazz) {
        this.registeredServices.stream()
                .filter(s -> s.getClass().equals(clazz))
                .forEach(this.registeredServices::remove);

        this.servicesManager.unregister(clazz);
    }

    public void unregisterAll() {
        this.registeredServices.forEach(s -> this.servicesManager.unregister(s.getClass()));
        this.registeredServices.clear();
    }
}

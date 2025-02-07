package fr.bgoodes.gamelib.services.state.model;

import fr.bgoodes.gamelib.GameLib;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractGameState {

    @NotNull
    private final Listener[] listeners;

    public AbstractGameState() {
        this.listeners = new Listener[]{};
    }

    public AbstractGameState(@NotNull Listener[] listeners) {
        this.listeners = listeners;
    }

    public final void start() {
        this.onStart();

        final @NotNull GameLib<?> gameLib = GameLib.get();

        for(final @NotNull Listener listener : this.listeners)
            gameLib.getServer().getPluginManager().registerEvents(listener, gameLib);
    }

    public final void end() {
        this.onEnd();

        for(final @NotNull Listener listener : this.listeners)
            HandlerList.unregisterAll(listener);
    }

    public void onStart() {}

    public void onEnd() {}
}

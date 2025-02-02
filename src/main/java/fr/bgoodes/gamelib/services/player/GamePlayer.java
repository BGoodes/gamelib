package fr.bgoodes.gamelib.services.player;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class GamePlayer {

    @NotNull
    private final UUID uniqueId;
    private boolean spectator;

    public GamePlayer(final @NotNull UUID uuid, final boolean spectator) {
        this.uniqueId = uuid;
        this.spectator = spectator;
    }

    @NotNull
    public UUID getUUID() {
        return this.uniqueId;
    }

    @Nullable
    public Player getBukkitPlayer() {
        return Bukkit.getPlayer(this.uniqueId);
    }

    public boolean isOnline() {
        final @Nullable Player bukkitPlayer = this.getBukkitPlayer();
        return bukkitPlayer != null && bukkitPlayer.isOnline() && bukkitPlayer.isValid();
    }

    public boolean isSpectator() {
        return this.spectator;
    }

    public void setSpectator(final boolean spectator) {
        this.spectator = spectator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final @NotNull GamePlayer p = (GamePlayer) o;
        return this.uniqueId.equals(p.uniqueId);
    }
}

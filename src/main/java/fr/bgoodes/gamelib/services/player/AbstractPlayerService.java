package fr.bgoodes.gamelib.services.player;

import fr.bgoodes.gamelib.services.GameService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public abstract class AbstractPlayerService<GP extends GamePlayer> extends GameService {

    @NotNull
    private final Map<UUID, GP> playersMap = new HashMap<>();

    public abstract @NotNull GP createPlayer(final @NotNull UUID uniqueId);

    public @NotNull GP getOrCreatePlayer(final @NotNull UUID uniqueId) {
        return this.playersMap.computeIfAbsent(uniqueId, this::createPlayer);
    }

    @Nullable
    public GP getGamePlayer(final @NotNull UUID uniqueId) {
        return this.playersMap.get(uniqueId);
    }

    public void removePlayer(final @NotNull UUID uniqueId) {
        this.playersMap.remove(uniqueId);
    }

    @NotNull
    public Collection<GP> getPlayers() {
        return this.playersMap.values();
    }

    @NotNull
    public Collection<GP> getOnlinePlayers() {
        return this.playersMap.values().stream()
                .filter(GamePlayer::isOnline)
                .toList();
    }
}

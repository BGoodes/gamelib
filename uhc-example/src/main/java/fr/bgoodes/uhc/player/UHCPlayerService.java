package fr.bgoodes.uhc.player;

import fr.bgoodes.gamelib.services.player.AbstractPlayerService;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UHCPlayerService extends AbstractPlayerService<UHCPlayer> {

    @Override
    public @NotNull UHCPlayer createPlayer(@NotNull UUID uniqueId) {
        return new UHCPlayer(uniqueId);
    }
}

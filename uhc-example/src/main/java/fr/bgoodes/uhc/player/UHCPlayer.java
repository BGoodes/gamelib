package fr.bgoodes.uhc.player;

import fr.bgoodes.gamelib.services.player.GamePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class UHCPlayer extends GamePlayer {

    public UHCPlayer(@NotNull UUID uniqueId) {
        super(uniqueId, false);
    }
}

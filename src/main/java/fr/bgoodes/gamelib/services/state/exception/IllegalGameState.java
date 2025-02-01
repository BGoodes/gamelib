package fr.bgoodes.gamelib.services.state.exception;

import org.jetbrains.annotations.NotNull;

public class IllegalGameState extends RuntimeException {

    public IllegalGameState(final @NotNull String gameStateId) {
        super("Can't change to '%s' state because is not registered on state service.".formatted(gameStateId));
    }
}

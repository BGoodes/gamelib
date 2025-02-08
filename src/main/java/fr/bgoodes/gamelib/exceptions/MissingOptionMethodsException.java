package fr.bgoodes.gamelib.exceptions;

import fr.bgoodes.gamelib.services.config.IGameConfig;
import org.jetbrains.annotations.NotNull;

public class MissingOptionMethodsException extends Exception {

    public MissingOptionMethodsException(final @NotNull Class<? extends IGameConfig> clazz) {
        super("No option methods annotated with @Option found in class %s".formatted(clazz.getName()));
    }
}

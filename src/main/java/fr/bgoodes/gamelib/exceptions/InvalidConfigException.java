package fr.bgoodes.gamelib.exceptions;

import fr.bgoodes.gamelib.services.config.IGameConfig;
import org.jetbrains.annotations.NotNull;

public class InvalidConfigException extends RuntimeException {

    public InvalidConfigException(final @NotNull Class<? extends IGameConfig> configClass) {
        super("The config interface %s is invalid.".formatted(configClass.getName()));
    }
}

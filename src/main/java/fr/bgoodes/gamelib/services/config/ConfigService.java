package fr.bgoodes.gamelib.services.config;

import fr.bgoodes.gamelib.exceptions.InvalidConfigException;
import fr.bgoodes.gamelib.exceptions.MissingOptionMethodsException;
import fr.bgoodes.gamelib.services.GameService;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ConfigService extends GameService {

    @NotNull
    private final Map<Class<?>, IGameConfig> configsMap = new HashMap<>();

    public @NotNull <T extends IGameConfig> T getConfig(final @NotNull Class<T> configClass) throws InvalidConfigException {
        if (!configsMap.containsKey(configClass)) {
            try {
                configsMap.put(configClass, new ConfigFactory<>(configClass).createInstance());
            } catch (MissingOptionMethodsException e) {
                throw new InvalidConfigException(configClass);
            }
        }

        return (T) configsMap.get(configClass);
    }
}

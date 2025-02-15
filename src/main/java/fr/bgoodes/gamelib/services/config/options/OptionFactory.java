package fr.bgoodes.gamelib.services.config.options;

import fr.bgoodes.gamelib.GameLib;
import fr.bgoodes.gamelib.services.config.options.impl.BooleanOption;
import fr.bgoodes.gamelib.services.config.options.impl.StringOption;
import fr.bgoodes.gamelib.services.config.options.model.AbstractOption;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

public class OptionFactory {

    private @NotNull final Class<?> clazz;

    private static @NotNull final Map<Class<?>, Class<? extends AbstractOption>> optionsMap = new HashMap<>();

    public OptionFactory(@NotNull Class<?> clazz) {
        this.clazz = clazz;
    }

    // default options
    static {
        optionsMap.put(boolean.class, BooleanOption.class);
        optionsMap.put(Boolean.class, BooleanOption.class);
        optionsMap.put(String.class, StringOption.class);
        GameLib.get().getLogger().info("Options registered");
    }

    public static void registerOption(@NotNull Class<?> clazz, @NotNull Class<? extends AbstractOption> option) {
        optionsMap.put(clazz, option);
    }

    public @NotNull AbstractOption getInstance() {
        if (!optionsMap.containsKey(clazz)) {
            throw new UnsupportedOperationException("Not implemented yet");
        }

        /*TODO: better exception handling*/

        try {
            return optionsMap.get(clazz).getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedOperationException("Error while creating option instance", e);
        }
    }
}

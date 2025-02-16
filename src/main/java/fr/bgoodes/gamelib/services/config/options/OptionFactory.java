package fr.bgoodes.gamelib.services.config.options;

import fr.bgoodes.gamelib.GameLib;
import fr.bgoodes.gamelib.services.config.options.impl.*;
import fr.bgoodes.gamelib.services.config.options.model.AbstractOption;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

// TODO : refactor this class, this is a mess
public class OptionFactory {

    private @NotNull final Class<?> clazz;

    private static @NotNull final Map<Class<?>, Class<? extends AbstractOption>> optionsMap = new HashMap<>();
    private static @NotNull final Map<Class<?>, Function<String, ? extends Number>> numberParsers = new HashMap<>();

    public OptionFactory(@NotNull Class<?> clazz) {
        this.clazz = clazz;
    }

    // default options
    static {
        // default types
        optionsMap.put(boolean.class, BooleanOption.class);
        optionsMap.put(Boolean.class, BooleanOption.class);
        optionsMap.put(char.class, CharOption.class);
        optionsMap.put(Character.class, CharOption.class);
        optionsMap.put(String.class, StringOption.class);

        // number types
        numberParsers.put(byte.class, Byte::parseByte);
        numberParsers.put(Byte.class, Byte::parseByte);
        numberParsers.put(short.class, Short::parseShort);
        numberParsers.put(Short.class, Short::parseShort);
        numberParsers.put(int.class, Integer::parseInt);
        numberParsers.put(Integer.class, Integer::parseInt);
        numberParsers.put(long.class, Long::parseLong);
        numberParsers.put(Long.class, Long::parseLong);
        numberParsers.put(float.class, Float::parseFloat);
        numberParsers.put(Float.class, Float::parseFloat);
        numberParsers.put(double.class, Double::parseDouble);
        numberParsers.put(Double.class, Double::parseDouble);
    }

    public static void registerOption(@NotNull Class<?> clazz, @NotNull Class<? extends AbstractOption> option) {
        optionsMap.put(clazz, option);
    }

    public @NotNull AbstractOption getInstance() {
        //enum options
        if (Enum.class.isAssignableFrom(clazz)) {
            return new EnumOption<>(clazz.asSubclass(Enum.class));
        }

        // number options
        if (numberParsers.containsKey(clazz)) {
            return new NumberOption<>(clazz, numberParsers.get(clazz));
        }

        if (!optionsMap.containsKey(clazz)) {
            throw new UnsupportedOperationException("Not implemented yet: %s".formatted(clazz.getSimpleName()));
        }

        /*TODO: better exception handling*/

        try {
            return optionsMap.get(clazz).getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedOperationException("Error while creating option instance", e);
        }
    }
}

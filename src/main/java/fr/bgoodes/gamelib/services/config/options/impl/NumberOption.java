package fr.bgoodes.gamelib.services.config.options.impl;

import fr.bgoodes.gamelib.exceptions.DeserializationException;
import fr.bgoodes.gamelib.services.config.options.model.AbstractOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.function.Function;

public class NumberOption<T extends Number> extends AbstractOption {

    private @NotNull final Class<?> clazz;
    private @NotNull final Function<String, T> parser;

    public NumberOption(@NotNull Class<?> clazz, @NotNull Function<String, T> parser) {
        this.clazz = clazz;
        this.parser = parser;
    }

    @Override
    public String serialize(@Nullable Object o) {
        return (o == null) ? null : o.toString();
    }

    @Override
    public T deserialize(@Nullable String s) throws DeserializationException {
        if (s == null) return null;

        try {
            return parser.apply(s);
        } catch (NumberFormatException e) {
            throw new DeserializationException("String %s is not a valid %s".formatted(s, clazz.getSimpleName()));
        }
    }
}


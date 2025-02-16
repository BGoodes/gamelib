package fr.bgoodes.gamelib.services.config.options.impl;

import fr.bgoodes.gamelib.exceptions.DeserializationException;
import fr.bgoodes.gamelib.services.config.options.model.AbstractOption;
import org.jetbrains.annotations.Nullable;

public class EnumOption<T extends Enum<T>> extends AbstractOption {

    private final Class<T> enumType;

    public EnumOption(Class<T> enumType) {
        this.enumType = enumType;
    }

    @Override
    public @Nullable String serialize(@Nullable Object o) {
        if (o == null) return null;
        return ((Enum<?>) o).name();
    }

    @Override
    public @Nullable Object deserialize(@Nullable String s) throws DeserializationException {
        if (s == null || s.isEmpty()) return null;

        try {
            return Enum.valueOf(enumType, s);
        } catch (IllegalArgumentException e) {
            throw new DeserializationException("String %s is not a valid %s".formatted(s, enumType.getSimpleName()));
        }
    }
}

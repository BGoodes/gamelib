package fr.bgoodes.gamelib.services.config.options.impl;

import fr.bgoodes.gamelib.exceptions.DeserializationException;
import fr.bgoodes.gamelib.services.config.options.model.AbstractOption;
import org.jetbrains.annotations.Nullable;

public class CharOption extends AbstractOption {

    public CharOption() {}

    @Override
    public @Nullable String serialize(@Nullable Object o) {
        return (o == null) ? null : o.toString();
    }

    @Override
    public @Nullable Object deserialize(@Nullable String s) throws DeserializationException {
        if (s != null && s.length() > 1) {
            throw new DeserializationException("String %s is too long to be a char".formatted(s));
        }
        return (s == null) ? null : s.charAt(0);
    }
}

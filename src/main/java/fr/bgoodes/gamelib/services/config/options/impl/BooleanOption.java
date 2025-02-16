package fr.bgoodes.gamelib.services.config.options.impl;

import fr.bgoodes.gamelib.services.config.exception.DeserializationException;
import fr.bgoodes.gamelib.services.config.options.model.AbstractOption;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BooleanOption extends AbstractOption {

    private @NotNull final String[] TRUE_STRINGS = {"true", "1", "y", "yes", "on"};
    private @NotNull final String[] FALSE_STRINGS = {"false", "0", "n", "no", "off"};

    public BooleanOption() {}

    @Override
    public @Nullable String serialize(@Nullable Object o) {
        if (o != null) {
            return (Boolean) o ? TRUE_STRINGS[0] : FALSE_STRINGS[0];
        }
        return null;
    }

    @Override
    public @Nullable Object deserialize(@Nullable String s) throws DeserializationException {
        if (s == null || s.isEmpty()) return null;

        for (String trueString : TRUE_STRINGS) {
            if (trueString.equalsIgnoreCase(s)) {
                return true;
            }
        }

        for (String falseString : FALSE_STRINGS) {
            if (falseString.equalsIgnoreCase(s)) {
                return false;
            }
        }

        throw new DeserializationException("Invalid boolean value: %s".formatted(s));
    }
}
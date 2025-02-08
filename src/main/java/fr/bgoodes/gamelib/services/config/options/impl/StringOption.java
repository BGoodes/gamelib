package fr.bgoodes.gamelib.services.config.options.impl;

import fr.bgoodes.gamelib.services.config.options.model.AbstractOption;
import org.jetbrains.annotations.Nullable;

public class StringOption extends AbstractOption {

    public StringOption() {}

    @Override
    public @Nullable String serialize(@Nullable Object o) {
        return (String) o;
    }

    @Override
    public @Nullable Object deserialize(@Nullable String s) {
        return s;
    }
}

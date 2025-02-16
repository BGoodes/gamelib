package fr.bgoodes.gamelib.services.config.options.model;

import fr.bgoodes.gamelib.services.config.exception.DeserializationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractOption {

    @NotNull private String key;
    @Nullable private Object value;

    public AbstractOption() {
        this.key = "";
    }

    public abstract @Nullable String serialize(@Nullable Object o);
    public abstract @Nullable Object deserialize(@Nullable String s) throws DeserializationException;

    @Nullable
    public Object getValue() {
        return value;
    }

    public void setValue(@Nullable Object value) {
        this.value = value;
    }

    @NotNull
    public String getKey() {
        return key;
    }

    public void setKey(@NotNull String key) {
        this.key = key;
    }
}
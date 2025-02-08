package fr.bgoodes.gamelib.services.config;

import fr.bgoodes.gamelib.exceptions.DeserializationException;
import fr.bgoodes.gamelib.exceptions.MissingOptionMethodsException;
import fr.bgoodes.gamelib.services.config.options.Option;
import fr.bgoodes.gamelib.services.config.options.impl.StringOption;
import fr.bgoodes.gamelib.services.config.options.model.AbstractOption;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConfigFactory<T extends IGameConfig> {

    private final @NotNull Class<T> configClass;

    public ConfigFactory(@NotNull Class<T> configClass) {
        this.configClass = configClass;
    }

    public @NotNull T createInstance() throws MissingOptionMethodsException {
        final List<Method> getters = findGetters();
        final Map<Method, Method> setters = findMatchingSetters(getters);

        Map<Method, AbstractOption> gettersMap = new HashMap<>();
        Map<Method, AbstractOption> settersMap = new HashMap<>();

        fillMaps(setters, gettersMap, settersMap);

        return new ConfigProxyHandler(gettersMap, settersMap).getInstance(configClass);
    }

    private @NotNull List<Method> findGetters() throws MissingOptionMethodsException {
        final ArrayList<Method> methods = new ArrayList<>();

        for (Method m : this.configClass.getDeclaredMethods()) {
            if (isGetter(m)) {
                methods.add(m);
            }
        }

        if (methods.isEmpty()) throw new MissingOptionMethodsException(this.configClass);
        return methods;
    }

    private boolean isGetter(final @NotNull Method method) {
        if (!method.isAnnotationPresent(Option.class)) {
            return false;
        }

        if (method.getName().startsWith("get") && method.getParameterTypes().length == 0) {
            return true;
        }

        return method.getName().startsWith("is") && method.getReturnType().equals(boolean.class);
    }

    private @NotNull Map<Method, Method> findMatchingSetters(final @NotNull List<Method> getters) {
        final Map<Method, Method> setters = new HashMap<>();

        for (Method getter : getters) {
            final String optionName = getOptionName(getter);
            Method setter = null;

            try {
                setter = findSetterMethod(getter, optionName);
            } catch (NoSuchMethodException ignored) {}

            setters.put(getter, setter);
        }

        return setters;
    }

    private @NotNull Method findSetterMethod(final @NotNull Method getter, final @NotNull String optionName) throws NoSuchMethodException {
        if (getter.getReturnType().equals(boolean.class) && getter.getName().startsWith("is"))
            return configClass.getMethod(optionName, getter.getReturnType());
        return configClass.getMethod("set" + optionName, getter.getReturnType());
    }

    private @NotNull String getOptionName(final @NotNull Method getter) {
        if (getter.getName().startsWith("is"))
            return getter.getName().substring(2);
        return getter.getName().substring(3);
    }

    private void fillMaps(final @NotNull Map<Method, Method> options, final @NotNull Map<Method, AbstractOption> gettersMap, final @NotNull Map<Method, AbstractOption> settersMap) {
        for (Method getter : options.keySet()) {
            AbstractOption option = createAndInitializeOption(getter);

            gettersMap.put(getter, option);
            Method setter = options.get(getter);

            if (setter != null) {
                settersMap.put(setter, option);
            }
        }
    }

    private @NotNull AbstractOption createAndInitializeOption(final @NotNull Method method) {
        AbstractOption option = new StringOption();

        Option annotation = method.getAnnotation(Option.class);
        try {
            Object deserializedValue = option.deserialize(annotation.defaultValue());
            option.setValue(deserializedValue);
        } catch (DeserializationException ignored) { /* TODO */ }

        return option;
    }
}

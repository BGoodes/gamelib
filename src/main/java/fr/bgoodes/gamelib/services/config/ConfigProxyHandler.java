package fr.bgoodes.gamelib.services.config;

import fr.bgoodes.gamelib.services.config.options.model.AbstractOption;
import fr.bgoodes.gamelib.utils.ConfUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

public class ConfigProxyHandler implements InvocationHandler {

    private final @NotNull Map<Method, AbstractOption> gettersMap;
    private final @NotNull Map<Method, AbstractOption> settersMap;

    public ConfigProxyHandler(final @NotNull Map<Method, AbstractOption> gettersMap, final @NotNull Map<Method, AbstractOption> settersMap) {
        this.gettersMap = gettersMap;
        this.settersMap = settersMap;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {

        if (gettersMap.containsKey(method)) {
            return getValue(gettersMap.get(method), method.getReturnType());
        }

        if (settersMap.containsKey(method)) {
            settersMap.get(method).setValue(args[0]);
            return null;
        }

        throw new UnsupportedOperationException("Method not supported: " + method);
    }

    private Object getValue(AbstractOption option, Class<?> type) {
        return option.getValue() == null && ConfUtil.isPrimitive(type) ?
                ConfUtil.getDefaultPrimitiveValue(type) : option.getValue();
    }

    @SuppressWarnings("unchecked")
    public <T extends IGameConfig> T getInstance(Class<T> configClass) {
        return (T) Proxy.newProxyInstance(configClass.getClassLoader(), new Class<?>[]{configClass}, this);
    }
}

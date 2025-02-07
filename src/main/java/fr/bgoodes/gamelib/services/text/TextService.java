package fr.bgoodes.gamelib.services.text;

import fr.bgoodes.gamelib.services.GameService;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

public class TextService extends GameService {

    @NotNull
    private final TranslationRegistry registry = TranslationRegistry.create(Key.key("gamelib:general"));

    @NotNull
    private final MiniMessage MINI_MESSAGE = MiniMessage.builder().build();


    @Override
    public void onLoad() {

        // TEMP -----
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle", Locale.US, UTF8ResourceBundleControl.get());
        this.registry.registerAll(Locale.US, bundle, true);

        ResourceBundle bundleFR = ResourceBundle.getBundle("Bundle", Locale.FRANCE, UTF8ResourceBundleControl.get());
        this.registry.registerAll(Locale.FRANCE, bundleFR, true);
        // -----

        registry.defaultLocale(Locale.FRANCE);
        GlobalTranslator.translator().addSource(registry);
    }

    public @NotNull TranslationRegistry getRegistry() {
        return registry;
    }

    public void setDefaultLocale(final @NotNull Locale locale) {
        registry.defaultLocale(locale);
    }

    public void addTranslation(final @NotNull String key, final @NotNull Locale locale, final @NotNull MessageFormat messageFormat) {
        registry.register(key, locale, messageFormat);
    }

    public void addTranslations(final @NotNull Map<String, MessageFormat> translations, final @NotNull Locale locale) {
        registry.registerAll(locale, translations);
    }

    public void addTranslations(final @NotNull ResourceBundle bundle, final @NotNull Locale locale) {
        registry.registerAll(locale, bundle, true);
    }

    public void removeTranslation(final @NotNull String key) {
        registry.unregister(key);
    }

    public @Nullable MessageFormat getMessageFormat(final @NotNull String key, final @NotNull Locale locale) {
        return registry.translate(key, locale);
    }

    public @NotNull Component tr(final @NotNull String key, final @NotNull Locale locale) {
        final @Nullable MessageFormat message = registry.translate(key, locale);
        return message != null ? MINI_MESSAGE.deserialize(message.toPattern()) :
                Component.text(key, Style.style(TextDecoration.UNDERLINED));
    }

    public @NotNull Component tr(final @NotNull String key, final @NotNull Locale locale, final @NotNull TagResolver tagResolver) {
        final @Nullable MessageFormat message = registry.translate(key, locale);
        return message != null ? MINI_MESSAGE.deserialize(message.toPattern(), tagResolver) :
                Component.text(key, Style.style(TextDecoration.UNDERLINED))
                        .append(Component.text(" (1 tag)", Style.empty()));
    }

    public @NotNull Component tr(final @NotNull String key, final @NotNull Locale locale, final @NotNull TagResolver... tagResolvers) {
        final @Nullable MessageFormat message = registry.translate(key, locale);
        return message != null ? MINI_MESSAGE.deserialize(message.toPattern(), tagResolvers) :
                Component.text(key, Style.style(TextDecoration.UNDERLINED))
                        .append(Component.text(" (" + tagResolvers.length + " tags)", Style.empty()));
    }
}

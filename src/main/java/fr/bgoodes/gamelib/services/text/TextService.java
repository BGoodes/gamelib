package fr.bgoodes.gamelib.services.text;

import fr.bgoodes.gamelib.services.GameService;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.translation.GlobalTranslator;
import net.kyori.adventure.translation.TranslationRegistry;
import net.kyori.adventure.util.UTF8ResourceBundleControl;

import java.util.Locale;
import java.util.ResourceBundle;

public class TextService extends GameService {

    private TranslationRegistry registry;

    @Override
    public void onLoad() {
        this.registry = TranslationRegistry.create(Key.key("gamelib:general"));

        // TEMP -----
        ResourceBundle bundle = ResourceBundle.getBundle("Bundle", Locale.US, UTF8ResourceBundleControl.get());
        this.registry.registerAll(Locale.US, bundle, true);

        ResourceBundle bundleFR = ResourceBundle.getBundle("Bundle", Locale.FRANCE, UTF8ResourceBundleControl.get());
        this.registry.registerAll(Locale.FRANCE, bundleFR, true);
        // -----

        registry.defaultLocale(Locale.FRANCE);
        GlobalTranslator.translator().addSource(registry);
    }

    public TranslationRegistry getRegistry() {
        return registry;
    }
}

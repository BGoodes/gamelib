package fr.bgoodes.gamelib.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.translation.GlobalTranslator;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.MessageFormat;

public class GlobalChatListener implements Listener {

    public GlobalChatListener() {}

    private static final MiniMessage MINI_MESSAGE = MiniMessage.builder().build();

    @EventHandler
    public void onChat(@NotNull AsyncChatEvent event) {

        final @Nullable MessageFormat message = GlobalTranslator.translator().translate("test", event.getPlayer().locale());

        assert message != null;
        final Component deserialized = MINI_MESSAGE.deserialize(message.toPattern());

        event.getPlayer().sendMessage(deserialized);
    }
}

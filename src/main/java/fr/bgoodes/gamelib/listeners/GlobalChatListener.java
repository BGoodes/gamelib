package fr.bgoodes.gamelib.listeners;

import fr.bgoodes.gamelib.GameLib;
import fr.bgoodes.gamelib.services.text.TextService;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class GlobalChatListener implements Listener {

    public GlobalChatListener() {}

    private final TextService textService = GameLib.get().getTextService();

    @EventHandler
    public void onChat(@NotNull AsyncChatEvent event) {
        event.getPlayer().sendMessage(textService.tr("test", event.getPlayer().locale()));
    }
}

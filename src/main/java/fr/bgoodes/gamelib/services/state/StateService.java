package fr.bgoodes.gamelib.services.state;

import fr.bgoodes.gamelib.services.GameService;
import fr.bgoodes.gamelib.services.state.exception.IllegalGameState;
import fr.bgoodes.gamelib.services.state.impl.EndState;
import fr.bgoodes.gamelib.services.state.impl.LobbyState;
import fr.bgoodes.gamelib.services.state.model.AbstractGameState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class StateService extends GameService {

    @Nullable
    private AbstractGameState currentState;

    @NotNull
    private final Map<String, AbstractGameState> statesMap = new HashMap<>();

    @Override
    public void onLoad() {
        this.registerGameState(LobbyState.STATE_ID, new LobbyState());
        this.registerGameState(EndState.STATE_ID, new EndState());

        this.changeGameState(LobbyState.STATE_ID);
    }
    
    public @Nullable AbstractGameState getCurrentState() {
        return this.currentState;
    }

    public void registerGameState(final @NotNull String id, final @NotNull AbstractGameState gameState) {
        this.statesMap.put(id, gameState);
    }

    public void unregisterGameState(final @NotNull String id) {
        this.statesMap.remove(id);
    }

    public void changeGameState(final @NotNull String stateId) {
        final @Nullable AbstractGameState newState = this.statesMap.get(stateId);

        if (newState == null)
            throw new IllegalGameState(stateId);

        if (this.currentState != null)
            this.currentState.end();

        this.currentState = newState;
        this.currentState.start();
    }
}

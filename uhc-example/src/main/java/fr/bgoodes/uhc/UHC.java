package fr.bgoodes.uhc;

import fr.bgoodes.gamelib.GameLib;
import fr.bgoodes.gamelib.services.state.StateService;
import fr.bgoodes.uhc.state.PlayingState;
import fr.bgoodes.uhc.state.StartingState;

public class UHC extends GameLib {

    @Override
    public void onStart() {
        this.getLogger().info("===== UHC initialization =====");

        // Register custom states
        final StateService stateService = this.getStateService();

        stateService.registerGameState(StartingState.STATE_ID, new StartingState());
        stateService.registerGameState(PlayingState.STATE_ID, new PlayingState());
    }

    @Override
    public void onStop() {
        this.getLogger().info("===== UHC deactivation =====");
    }
}

package fr.bgoodes.uhc;

import fr.bgoodes.gamelib.GameLib;
import fr.bgoodes.gamelib.config.ITestConfig;
import fr.bgoodes.gamelib.exceptions.InvalidConfigException;
import fr.bgoodes.gamelib.services.state.StateService;
import fr.bgoodes.uhc.player.UHCPlayer;
import fr.bgoodes.uhc.player.UHCPlayerService;
import fr.bgoodes.uhc.state.PlayingState;
import fr.bgoodes.uhc.state.StartingState;

public class UHC extends GameLib<UHCPlayer> {

    public UHC() {
        super(new UHCPlayerService());
    }

    @Override
    public void onStart() {
        this.getLogger().info("===== UHC initialization =====");

        // Register custom states
        final StateService stateService = this.getStateService();

        try {
            ITestConfig config = getConfigService().getConfig(ITestConfig.class);
            this.getLogger().info(config.getTest());
            this.getLogger().info(config.getTest3());
            config.setTest("test2");
            this.getLogger().info(config.getTest());

        } catch (InvalidConfigException e) {
            throw new RuntimeException(e);
        }

        stateService.registerGameState(StartingState.STATE_ID, new StartingState());
        stateService.registerGameState(PlayingState.STATE_ID, new PlayingState());
    }

    @Override
    public void onStop() {
        this.getLogger().info("===== UHC deactivation =====");
    }
}

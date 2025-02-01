package fr.bgoodes.uhc;

import fr.bgoodes.gamelib.GameLib;

public class UHC extends GameLib {

    @Override
    public void onStart() {
        getLogger().info("Initializing UHC example...");
        getLogger().info("UHC example initialized.");
    }

    @Override
    public void onStop() {
        getLogger().info("UHC example deactivated.");
    }
}

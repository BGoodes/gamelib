package fr.bgoodes.gamelib.config;

import fr.bgoodes.gamelib.services.config.IGameConfig;
import fr.bgoodes.gamelib.services.config.options.Option;

public interface ITestConfig extends IGameConfig {

    @Option(key = "test", defaultValue = "Hello World!")
    String getTest();
    void setTest(String test);

    @Option(key = "test2", defaultValue = "Hello World 2!")
    String getTest2();
    void setTest2(String test2);

    @Option(key = "test3", defaultValue = "n")
    boolean getTest3();
    void setTest3(boolean test3);
}
package fr.bgoodes.gamelib.config;

import fr.bgoodes.gamelib.services.config.IGameConfig;
import fr.bgoodes.gamelib.services.config.options.Option;

public interface ITestConfig extends IGameConfig {

    @Option(key = "test", defaultValue = "3.14")
    Double getTest();
    void setTest(Double test);

    @Option(key = "test2", defaultValue = "n")
    Character getTest2();
    void setTest2(char test2);

    @Option(key = "test3", defaultValue = "n")
    boolean getTest3();
    void setTest3(boolean test3);
}
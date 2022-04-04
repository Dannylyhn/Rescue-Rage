package rescuerage.common.services;

import rescuerage.common.data.GameData;
import rescuerage.common.data.World;

public interface IGamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}

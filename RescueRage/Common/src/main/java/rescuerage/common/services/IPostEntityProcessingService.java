package rescuerage.common.services;

import rescuerage.common.data.GameData;
import rescuerage.common.data.World;


public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}

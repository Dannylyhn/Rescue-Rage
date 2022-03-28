package dk.sdu.mmmi.sem.common.services;

import dk.sdu.mmmi.sem.common.data.GameData;
import dk.sdu.mmmi.sem.common.data.World;

/**
 *Manges the add/removal of components
 * preconditions: Component gets added
 * postconditons: Component gets removed
 */
public interface IGamePluginService {
    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}

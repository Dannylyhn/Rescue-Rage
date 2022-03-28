package dk.sdu.mmmi.sem.common.services;

import dk.sdu.mmmi.sem.common.data.GameData;
import dk.sdu.mmmi.sem.common.data.World;

/**
 * Komponenter man gerne vil lave på den state der er kommet efter den er procserert
 * If hit then die efter fx.
 * Something to do with colission?
 * preconditions:
 * postconditions:
 */
public interface IPostEntityProcessingService  {
        void process(GameData gameData, World world);
}

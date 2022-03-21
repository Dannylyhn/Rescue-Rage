package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

/**
 * Vectors in the space
 * The difference of width and height in the game world
 * preconditions: Takes the intial positions of the object. Tager data fra gameworld og laver vektor beregninger på den entity
 * postconditions: Updatas the object. Færdig med at procerer for hvert enkelt entity så den proceret i game world
 */
public interface IEntityProcessingService {
    void process(GameData gameData, World world);
}

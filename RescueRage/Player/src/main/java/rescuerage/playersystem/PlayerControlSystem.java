package rescuerage.playersystem;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.GameKeys;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.MovingPart;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.services.IEntityProcessingService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            MovingPart movingPart = player.getPart(MovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);

            movingPart.setLeft(gameData.getKeys().isDown(GameKeys.LEFT));
            movingPart.setRight(gameData.getKeys().isDown(GameKeys.RIGHT));
            movingPart.setUp(gameData.getKeys().isDown(GameKeys.UP));

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);

            updateShape(player);
            
           
            //This should be inventory instead
            Entity weapon = world.getEntity(world.getWeapon());
            PositionPart weaponPos = weapon.getPart(PositionPart.class);
            weaponPos.setPosition(positionPart.getX(), positionPart.getY());
            weaponPos.setRadians(positionPart.getRadians());
        }
    }

    private void updateShape(Entity entity) {
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }

}
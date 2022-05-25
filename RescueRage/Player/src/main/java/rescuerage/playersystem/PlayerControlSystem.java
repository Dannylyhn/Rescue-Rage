package rescuerage.playersystem;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.GameKeys;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.services.IEntityProcessingService;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.data.entityparts.LoadoutPart;
import rescuerage.common.data.entityparts.PlayerMovingPart;

@ServiceProviders(value = {
    @ServiceProvider(service = IEntityProcessingService.class),})
public class PlayerControlSystem implements IEntityProcessingService {
        
    @Override
    public void process(GameData gameData, World world) {

        for (Entity player : world.getEntities(Player.class)) {
            PositionPart positionPart = player.getPart(PositionPart.class);
            PlayerMovingPart movingPart = player.getPart(PlayerMovingPart.class);
            LifePart lifePart = player.getPart(LifePart.class);
            LoadoutPart loadoutPart = player.getPart(LoadoutPart.class);

            if(gameData.getKeys().isDown(GameKeys.A)){
                movingPart.setLeft(true);
                movingPart.setRight(false);
                movingPart.setUp(false);
                movingPart.setDown(false);
                
            }
            
            if(gameData.getKeys().isDown(GameKeys.D)){
                movingPart.setRight(true);
                movingPart.setLeft(false);
                movingPart.setUp(false);
                movingPart.setDown(false);
            }
            
            
            if(gameData.getKeys().isDown(GameKeys.S)){
                movingPart.setDown(true);
                movingPart.setRight(false);
                movingPart.setUp(false);
                movingPart.setLeft(false);
            }
            
            
            if(gameData.getKeys().isDown(GameKeys.W)){
                movingPart.setUp(true);
                movingPart.setRight(false);
                movingPart.setLeft(false);
                movingPart.setDown(false);
            }

            for(Entity weapon : loadoutPart.getWeapons())
            {
                PositionPart weaponPos = weapon.getPart(PositionPart.class);
                weaponPos.setPosition(positionPart.getX(), positionPart.getY());
                weaponPos.setRadians(positionPart.getRadians()); 
            }
            
            loadoutPart.setQ(gameData.getKeys().isDown(GameKeys.Q));
            loadoutPart.setE(gameData.getKeys().isDown(GameKeys.E));

            movingPart.process(gameData, player);
            positionPart.process(gameData, player);
            lifePart.process(gameData, player);
            loadoutPart.process(gameData, player);
            

            updateShape(player);
            

        }
    }
    
    public void updateShape(Entity entity) {
        
        
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        //float radius = entity.getRadius();
        float sizeX = entity.getSizeX();
        float sizeY = entity.getSizeY();
        
        shapex[0] = x + sizeX;
        shapey[0] = y + sizeY;
        
        shapex[1] = x + sizeX;
        shapey[1] = y - sizeY;
        
        shapex[2] = x - sizeX;
        shapey[2] = y - sizeY;
        
        shapex[3] = x - sizeX;
        shapey[3] = y + sizeY;
        
        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
        
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rescuerage.item;

import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.services.IEntityProcessingService;
import rescuerage.common.services.IGamePluginService;

import java.util.ArrayList;
import rescuerage.common.data.Entity;
import rescuerage.common.data.entityparts.PositionPart;

/**
 *
 * @author ander
 */
@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class)})
public class ItemProcessor implements IEntityProcessingService {
    private int level = 1;

    @Override
    public void process(GameData gameData, World world) {
        if(level!=world.level){
            //MapPlugin.createLevel();
            //world.clearRoomMap();
            Lookup.getDefault().lookup(ItemPlugin.class).createItemsInLevel();
            //Entity bullet = Lookup.getDefault().lookup(BulletSPI.class).createBullet(x, y, radians, radius, gameData);
            if(world.level == 0){
                //world.level = 1;
            }
            level = world.level;
            
        }
        //for(java.util.Map<String, Entity> entityMap : world.getLevel()){
        for (Entity item : world.getEntities(Item.class)) {
            //for (Entity entity : entityMap.values()) {
                setShape(item);
            //}
        }
    }
    private void setShape(Entity entity) {
        
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

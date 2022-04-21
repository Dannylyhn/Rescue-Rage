/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.map;

import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.services.IEntityProcessingService;

/**
 *
 * @author ander
 */
@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class)})
public class MapProcessor implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        //MapPlugin.start(gameData, world);
        /*int level = 1;
        if(level!=world.level){
            MapPlugin.createLevel();
        }*/
        for(java.util.Map<String, Entity> entityMap : world.getLevel()){
            //System.out.println("draw 2");
            for (Entity entity : entityMap.values()) {
                setShape(entity);
            }
        }
        /*
        for (Entity map : world.getEntities(Map.class)) {
            //PositionPart positionPart = map.getPart(PositionPart.class);
            
            setShape(map);
        }
        */
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

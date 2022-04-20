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
        for(java.util.Map<String, Entity> entityMap : world.getRooms()){
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
        float radius = entity.getRadius();
        
        shapex[0] = x + radius;
        shapey[0] = y + radius;
        
        shapex[1] = x + radius;
        shapey[1] = y - radius;
        
        shapex[2] = x - radius;
        shapey[2] = y - radius;
        
        shapex[3] = x - radius;
        shapey[3] = y + radius;
        
        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
        
    }
}

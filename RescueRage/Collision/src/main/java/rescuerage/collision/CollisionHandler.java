/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.collision;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
//import rescuerage.map
/**
 *
 * @author ander
 */

import java.util.*;

@ServiceProvider(service = IPostEntityProcessingService.class)
public class CollisionHandler implements IPostEntityProcessingService {

    private final List<String> ignoredEntities
            //= new ArrayList<String>(Collections.singletonList("Player"));
            = new ArrayList<String>(Collections.singletonList("Weapon"));
            //= new ArrayList<String>();

    @Override
    public void process(GameData gameData, World world) {
        world.getEntities().forEach(e1 -> world.getEntities().forEach(e2 -> {
            if (isIgnoredEntity(e1,e2) || isSameEntityType(e1, e2) || !isCollision(e1, e2)) {
                return;
            }


            System.out.println("Collision detected");
            System.out.println("Entity 1: " + e1.getID() + " (" + e1.getClass().getSimpleName() + ")");
            System.out.println("Entity 2: " + e2.getID() + " (" + e2.getClass().getSimpleName() + ")");
            
            
            if (e1.getClass().getSimpleName().equals("Map")) {
                if (e2.getClass().getSimpleName().equals("Player")) {
                    unWalkable(e1,e2);
                }
                //world.removeEntity(e1);
                return;
            }
            if (e2.getClass().getSimpleName().equals("Map")) {
                if (e1.getClass().getSimpleName().equals("Player")) {
                    unWalkable(e2,e1);
                }
                //world.removeEntity(e1);
                return;
            }
            
            if (e1.getClass().getSimpleName().equals("Bullet")) {
                if (e2.getClass().getSimpleName().equals("Player")) {
                    
                }
                else{
                    //world.removeEntity(e1);
                    //return;
                }
            }
            
        }));
    }
    
    private void unWalkable(Entity wall1, Entity player1){
        //System.out.println("here");
        float distX;
        float distY;
        PositionPart player = player1.getPart(PositionPart.class);
        PositionPart wall = wall1.getPart(PositionPart.class);
        if(player.getX() > wall.getX()){
            distX = player.getX()-wall.getX();
        }else{
            distX = wall.getX()-player.getX();
        }

        if(player.getY() > wall.getY()){
            distY = player.getY()-wall.getY();
        }else{
            distY = wall.getY()-player.getY();
        }
        
        if(distX > distY){
            if(player.getX() > wall.getX()){
                player.setX(wall.getX()+(wall1.getRadius())+player1.getRadius());
            }
            else if(player.getY() < wall.getY()){
                player.setX(wall.getX()-(wall1.getRadius())-player1.getRadius());
            }
        }else{
            if(player.getY() < wall.getY()){
                player.setY(wall.getY()-(wall1.getRadius())-player1.getRadius());
            }
            else if(player.getY() > wall.getY()){
                player.setY(wall.getY()+((wall1.getRadius())+player1.getRadius()));
            }
        }
    }

    private boolean isIgnoredEntity(Entity e1, Entity e2) {
        return
            ignoredEntities.contains(e1.getClass().getSimpleName()) ||
            ignoredEntities.contains(e2.getClass().getSimpleName())
        ;
    }

    private boolean isSameEntityType(Entity entity, Entity entity2) {
        return Objects.equals(entity.getClass().getSimpleName(), entity2.getClass().getSimpleName());
    }

    private boolean isCollision(Entity entity, Entity entity2) {
        /*
        if (entity.getID().equals(entity2.getID())) {
            return false;
        }

        PositionPart entMov = entity.getPart(PositionPart.class);
        PositionPart entMov2 = entity2.getPart(PositionPart.class);
        float dx = (float) entMov.getX() - (float) entMov2.getX();
        float dy = (float) entMov.getY() - (float) entMov2.getY();
        float distance = (float) Math.sqrt(dx * dx + dy * dy);

        return distance < (entity.getRadius() + entity2.getRadius());
*/
        float[] sx = entity.getShapeX();
            float[] sy = entity.getShapeY();
            for(int i = 0; i < sx.length; i++){
                if(entity2.contains(sx[i], sy[i])){
                    return true;
                }
            }
            return false;
    }
}

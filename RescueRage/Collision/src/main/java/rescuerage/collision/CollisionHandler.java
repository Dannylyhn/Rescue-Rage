/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.collision;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.data.entityparts.TilePart;
import rescuerage.common.services.IPostEntityProcessingService;
import org.openide.util.lookup.ServiceProvider;
//import rescuerage.map
/**
 *
 * @author ander
 */

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import rescuerage.common.data.entityparts.LifePart;

@ServiceProvider(service = IPostEntityProcessingService.class)
public class CollisionHandler implements IPostEntityProcessingService {

    private final List<String> ignoredEntities
            //= new ArrayList<String>(Collections.singletonList("Player"));
            = new ArrayList<String>(Collections.singletonList("Weapon"));
            //= new ArrayList<String>();

    @Override
    public void process(GameData gameData, World world) {
        world.getCollisionEntities().forEach(e1 -> world.getCollisionEntities().forEach(e2 -> {
            if(ignoreWalkableTiles(e1,e2)){
                return;
            }
            if(!e1.getClass().getSimpleName().equals("Map") || !e2.getClass().getSimpleName().equals("Map")){
                if (isIgnoredEntity(e1,e2) || isSameEntityType(e1, e2) || !isCollision(e1, e2)) {
                    return;
                }

                if(e1.getClass().getSimpleName().equals("Map")){
                    TilePart tile = e1.getPart(TilePart.class);
                    if(tile.getType().equals("door")){
                        System.out.println("Colliding with door");
                    }
                }
                /*System.out.println("Collision detected");
                System.out.println("Entity 1: " + e1.getID() + " (" + e1.getClass().getSimpleName() + ")");
                System.out.println("Entity 2: " + e2.getID() + " (" + e2.getClass().getSimpleName() + ")");*/


                if (e1.getClass().getSimpleName().equals("Map")) {
                    if (e2.getClass().getSimpleName().equals("Player")) {
                        TilePart tile = e1.getPart(TilePart.class);
                        switch (tile.getType()) {
                            case "box":
                                unWalkable(e2,e1);
                                break;
                            case "roomInfo":
                                //System.out.println("in room: " + tile.getRoom());
                                break;
                            default:
                                unWalkable(e1,e2);
                                break;
                        }
                    }
                    //world.removeEntity(e1);
                    //return;
                }
                if (e2.getClass().getSimpleName().equals("Map")) {
                    if (e1.getClass().getSimpleName().equals("Player")) {
                        //unWalkable(e2,e1);
                        TilePart tile = e2.getPart(TilePart.class);
                        switch (tile.getType()) {
                            case "box":
                                unWalkable(e1,e2);
                                break;
                            case "roomInfo":
                                //System.out.println("in room: " + tile.getRoom());
                                world.currentRoom = tile.getRoom();
                                if(tile.getState().equals("unexplored")){
                                    world.lockDoors();
                                }   break;
                            default:
                                unWalkable(e2,e1);
                                break;
                        }
                    }
                    //world.removeEntity(e1);
                    //return;
                }

                if (e1.getClass().getSimpleName().equals("Bullet")) {
                    if (e2.getClass().getSimpleName().equals("Player")) {

                    }
                    else if(e2.getClass().getSimpleName().equals("Map")){
                        TilePart tp = e2.getPart(TilePart.class);
                        if(tp.type.equals("box")){
                            //System.out.println("\nhere\n\nhere\n\nhere\n");
                            LifePart l = e2.getPart(LifePart.class);
                            //System.out.println("pre hit: life int: " + l.getLife() + " | dead: " + l.isDead());
                            l.hit(1);
                            //System.out.println("life int: " + l.getLife() + " | dead: " + l.isDead());
                            if(l.isDead()){
                                world.removeEntity(e2);
                                //world.
                            }
                        }
                        else if(tp.type.equals("roomInfo")){
                            return;
                        }
                        world.removeEntity(e1);
                    }
                    else{
                        world.removeEntity(e1);
                        //return;
                    }
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
            else if(player.getX() < wall.getX()){
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
    private boolean ignoreWalkableTiles(Entity e1, Entity e2){
        if (e1.getClass().getSimpleName().equals("Map")) {
            TilePart tile1 = e1.getPart(TilePart.class);
            switch(tile1.getType()){
                case "wall":
                    return false;
                case "door":
                    return !tile1.locked;
                case "floor":
                    return true;
            }
        }
        if (e2.getClass().getSimpleName().equals("Map")) {
            TilePart tile2 = e2.getPart(TilePart.class);
            switch(tile2.getType()){
                case "wall":
                    return false;
                case "door":
                    return !tile2.locked;
                case "floor":
                    return true;
            }
        }
        return false;
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

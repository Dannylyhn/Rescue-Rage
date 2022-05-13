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
import static rescuerage.core.main.Sounds.deathSound;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.data.entityparts.InventoryPart;
import rescuerage.common.data.entityparts.ItemPart;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.LoadoutPart;

@ServiceProvider(service = IPostEntityProcessingService.class)
public class CollisionHandler implements IPostEntityProcessingService {

    private final List<String> ignoredEntities
            //= new ArrayList<String>(Collections.singletonList("Player"));
            = new ArrayList<String>(Collections.singletonList(""));
            //= new ArrayList<String>();

    @Override
    public void process(GameData gameData, World world) {
        Entity player = world.getEntity(world.getPlayerID());
        for(Entity roomIdentifier : world.roomIdentifiers){
            if (isCollision(player, roomIdentifier)) {
                TilePart tile = roomIdentifier.getPart(TilePart.class);

                world.currentRoom = tile.getRoom();
                //System.out.println("current room: " + world.currentRoom);
                if(tile.getState().equals("unexplored")){
                    world.lockDoors();
                }
            }
        }
        //System.out.println("world.currentRoom: " + world.currentRoom);
        //for(Entity e : world.getLevel().get(world.currentRoom).values()){
        world.getLevel().get(world.currentRoom).put(world.getPlayerID(), player);
        world.getLevel().get(world.currentRoom).values().forEach(e1 -> world.getLevel().get(world.currentRoom).values().forEach(e2 -> {
        //world.getCollisionEntities().forEach(e1 -> world.getCollisionEntities().forEach(e2 -> {
            if(ignoreWalkableTiles(e1,e2)){
                return;
            }
            if(!e1.getClass().getSimpleName().equals("Map") || !e2.getClass().getSimpleName().equals("Map")){
                if (isIgnoredEntity(e1,e2) || isSameEntityType(e1, e2) || !isCollision(e1, e2)) {
                    return;
                }
                
                if(e1.getClass().getSimpleName().equals("Item")){
                    if(e2.getClass().getSimpleName().equals("Player")){
                        ItemPart i = e1.getPart(ItemPart.class);
                        String type = i.getType();
                        InventoryPart ip = e2.getPart(InventoryPart.class);
                        switch (type) {
                            case "healthInc":
                                LifePart lp = e2.getPart(LifePart.class);
                                if(lp.getLife()<lp.getMax()){
                                    lp.incLife(i.getValue());
                                    world.removeEntity(e1);
                                }   break;
                            case "key":
                                ip.takeKey();
                                world.removeEntity(e1);
                                break;
                            case "chest":
                                unWalkable(e2, e1);
                                if(ip.keys>0){
                                    if(i.E){
                                        ip.useKey();
                                        i.open();
                                    }
                                    //world.removeEntity(e1);
                                }
                                break;
                            default:
                                break;
                        }
                    }
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
                    String temp = e2.getClass().getSimpleName();
                    if (temp.equals("Player") || temp.equals("Enemy")) {
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
                
                if(e1.getClass().getSimpleName().equals("Weapon")){
                    if(e2.getClass().getSimpleName().equals("Player"))
                    {
                        LoadoutPart lp = e2.getPart(LoadoutPart.class);
                        if(!lp.getWeapons().contains(e1))
                        {
                            lp.addWeapon(e1);
                            GunPart gunPart = e1.getPart(GunPart.class);
                            gunPart.setPickedUp(true);
                        }
                    }
                }
                    
                if (e2.getClass().getSimpleName().equals("Map")) {
                    String temp = e1.getClass().getSimpleName();
                    if (temp.equals("Player") || temp.equals("Enemy")) {
                        //unWalkable(e2,e1);
                        TilePart tile = e2.getPart(TilePart.class);
                        switch (tile.getType()) {
                            case "box":
                                unWalkable(e1,e2);
                                break;
                            case "roomInfo":
                                //System.out.println("in room: " + tile.getRoom());
                                if(temp.equals("Player")){
                                    world.currentRoom = tile.getRoom();
                                    if(tile.getState().equals("unexplored")){
                                        world.lockDoors();
                                    }
                                }
                                break;
                            default:
                                unWalkable(e2,e1);
                                break;
                        }
                    }
                    //world.removeEntity(e1);
                    //return;
                }
                if (e1.getClass().getSimpleName().equals("Enemy")) {
                    if (e2.getClass().getSimpleName().equals("Enemy")) {
                        unWalkable(e1,e2);
                    }
                    else if(e2.getClass().getSimpleName().equals("Palyer")){
                        LifePart l = e2.getPart(LifePart.class);
                        l.hit(1);
                        if(l.isDead()){
                            world.restartGame();
                        }
                    }
                }
                if (e1.getClass().getSimpleName().equals("Player")) {
                    if (e2.getClass().getSimpleName().equals("Enemy")) {
                        LifePart l = e1.getPart(LifePart.class);
                        l.hit(1);
                        if(l.isDead()){
                            world.restartGame();
                        }
                    }
                }

                if (e1.getClass().getSimpleName().equals("Bullet")) {
                    String temp = e2.getClass().getSimpleName();
                    if (temp.equals("Player")) {

                    }
                    else if(temp.equals("Item")){
                        
                    }
                    else if(temp.equals("Map")){
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
                    else if(temp.equals("Enemy")){
                        LifePart l = e2.getPart(LifePart.class);
                        //System.out.println("pre hit: life int: " + l.getLife() + " | dead: " + l.isDead());
                        l.hit(1);
                        //System.out.println("life int: " + l.getLife() + " | dead: " + l.isDead());
                        if(l.isDead()){
                            Entity p = world.getEntity(world.getPlayerID());
                            InventoryPart ip = p.getPart(InventoryPart.class);
                            ip.incMoney(100);
                            deathSound();
                            world.removeEntity(e2);
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
        world.getLevel().get(world.currentRoom).remove(world.getPlayerID());
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
        if(entity.getClass().getSimpleName().equals("Enemy")){
            return false;
        }
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

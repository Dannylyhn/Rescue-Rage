/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.map;

import java.util.concurrent.ConcurrentHashMap;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import rescuerage.Room;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.data.entityparts.TilePart;
import rescuerage.common.services.IGamePluginService;

/**
 *
 * @author ander
 */
@ServiceProviders(value = {
        @ServiceProvider(service = IGamePluginService.class),})
public class MapPlugin implements  IGamePluginService{
    
    private int houseH;
    private int houseW;
    private int roomH = 9; // must be uneven number, because the doors are being set at the center of the wall
    private int roomW = 11; // must be uneven number, because the doors are being set at the center of the wall
    private int tileSize = 16; // must be even number, because the top and right rows are depending on it
    private int WIDTH = roomW * tileSize;
    private int HEIGHT = roomH * tileSize;
    private int level;
    
    

    private World world;
    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        this.level = 0;
        createLevel();
    }
    private void initLevel(){
        this.world = world;
        this.houseH = world.houseH;
        this.houseW = world.houseW + 1;
        this.roomH = world.roomH;
        this.roomW = world.roomW;
        this.tileSize = world.tileSize;
    }

    @Override
    public void stop(GameData gameData, World world) {
        
    }
    
    private void createLevel(){
        if (level != world.level){
            level = world.level;
            initLevel();
        }
        // starting area
        
        createHouse();
        
        // end area
        
    }
    
    private void createHouse(){
        // create a room for each houseH
        for(int k = 0; k < houseH; k++){
            // create a room for each houseW
            for(int l = 0; l < houseW+1; l++){
                java.util.Map<String, Entity> roomEntityMap = new ConcurrentHashMap<>();
                createRoom(k,l, roomEntityMap);
                world.addRoom(roomEntityMap);
                //System.out.println(world.getRooms().size());
                //System.out.println(roomEntityMap.size());
            }
        }
    }
    
    private void createRoom(int k, int l, java.util.Map<String, Entity> roomEntityMap){
        
        Room r = new Room("", 1, 1, 1);
        /*
        System.out.println("tile size: " + tileSize);
        System.out.println("level: " + level);
        System.out.println("WIDTH: " + WIDTH);
        System.out.println("HEIGHT: " + HEIGHT);
        System.out.println("houseW: " + houseW);
        System.out.println("houseH: " + houseH);
        */
        createTile(60, 60, tileSize/2, "box", roomEntityMap);
        
        // set tiles from left to right
        for(int i = tileSize/2; i < WIDTH; i+=tileSize){
            // set tiles from top to bottom
            for(int j = tileSize/2; j < HEIGHT; j+=tileSize){
                // if tile is at the left, bottom, right or top of the room
                if((i==tileSize/2 || j==tileSize/2 || i==WIDTH-tileSize/2 || j==HEIGHT-tileSize/2)){
                    // ignore rooms above the starting area
                    if(l==0 && k != 0){
                        
                    }
                    // ignore rooms above the boss area
                    else if(l==houseW && k != 0){
                        
                    }
                    // if the tile is not at the center of a wall
                    else if((i!=WIDTH/2 && j!=HEIGHT/2)){
                        createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "wall", roomEntityMap);
                    }
                    // if a room is at the edge of the house (no doors at the edges)
                    else if( (k == houseH-1 || k == 0) || (l == houseW-1 || l == 1) ){
                        // if the room is at the bottom and the tile is at the bottom 
                        if((j==tileSize/2 && k == 0))
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "wall", roomEntityMap);
                        // if the room is at the top and the tile is at the top
                        else if((j==HEIGHT-tileSize/2 && k == houseH-1))
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "wall", roomEntityMap);
                        // if the room is at the left and the tile is at the left
                        else if((i==tileSize/2 && l == 1)){
                            if(k!=0) // enter house
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "wall", roomEntityMap);
                            else
                                // door
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "door", roomEntityMap);
                        }
                        // close of the starting area, but not the house door
                        else if(l==0 && i!=WIDTH-tileSize/2){
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "wall", roomEntityMap);
                        }
                        // if the room is at the right and the tile is at the right
                        else if((i == WIDTH-tileSize/2 && l == houseW-1)){
                            if(k!=0) // enter boos
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "wall", roomEntityMap);
                            else
                                // door
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "door", roomEntityMap);
                        }
                        // close of the boss area, but not the boss door
                        else if(l==houseW && i != tileSize/2){
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "wall", roomEntityMap);
                        }
                        else{
                            // door
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "door", roomEntityMap);
                        }
                    }
                    else{
                        // door (or not maybe)
                    }
                }
                else{
                    // floor
                    //createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "floor", roomEntityMap);
                }
            }
        }
    }
    
    private Entity createTile(float x, float y, float size, String type, java.util.Map<String, Entity> roomEntityMap){
        Entity map = new Map();
        
        map.setRadius(size);
        //System.out.println("x: " + x);
        //System.out.println("y: " + y);
        map.add(new PositionPart(x,y,0));
        map.add(new TilePart(type));
        if(type.equals("box")){
            map.add(new LifePart(1));
        }
        world.addEntity(map);
        addEntity(map, roomEntityMap);
        return map;
    }
    
    public String addEntity(Entity entity, java.util.Map<String, Entity> roomEntityMap) {
        roomEntityMap.put(entity.getID(), entity);
        /*if(ignoreWalkableTiles(entity)){
            collisionMap.put(entity.getID(), entity);
        }*/
        return entity.getID();
    }
}

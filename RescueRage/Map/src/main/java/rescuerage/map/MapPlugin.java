/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
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
    private int roomH = 19; // must be uneven number, because the doors are being set at the center of the wall
    private int roomW = 23; // must be uneven number, because the doors are being set at the center of the wall
    private int tileSize = 48; // must be even number, because the top and right rows are depending on it
    private int WIDTH = roomW * tileSize;
    private int HEIGHT = roomH * tileSize;
    private int level;
    private int levelChanger;
    
    

    private World world;
    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        this.level = 0;
        this.levelChanger = 0;
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
    
    public void createLevel(){
        /*if (level != world.level){
            level = world.level;
        }*/
        world.clearRoomMap();
        initLevel();
        //initLevel();
        // starting area
        
        createHouse();
        
        //createBoxInRoomIndex(4);
        
        createBoxInRoomIndex(3);
        createBoxInRoomIndex(0);
        createBoxInRoomBossArea();
        
        // end area
        
    }
    private void createBoxInRoomIndex(int index){
        world.addEntityInRoom(createBox(), index);
    }
    private void createBoxInRoomBossArea(){
        world.addEntityInBossArea(createBox());
    }
    
    private void createHouse(){
        // create a room for each houseH
        for(int k = 0; k < houseH; k++){
            // create a room for each houseW
            for(int l = 0; l < houseW+1; l++){
                
                if(k==0 || (l!=0 && l!= houseW)){
                    java.util.Map<String, Entity> roomEntityMap = new ConcurrentHashMap<>();
                    createRoom(k,l, roomEntityMap);
                    if(l==houseW){
                        world.addBossarea(roomEntityMap);
                        world.lockBossRoom();
                    }
                    else{
                        world.addRoom(roomEntityMap);
                    }
                }
                //System.out.println(world.getRooms().size());
                //System.out.println(roomEntityMap.size());
            }
        }
    }
    
    private void createRoom(int k, int l, java.util.Map<String, Entity> roomEntityMap){
        //createTile((l*roomW*tileSize)+roomW/2, (k*roomH*tileSize)+roomH/2, roomW*tileSize, roomH*tileSize, "roomInfo", roomEntityMap);
        createRoom((l*roomW*tileSize)+(roomW*tileSize)/2, 
                   (k*roomH*tileSize)+(roomH*tileSize)/2, 
                   //(roomW*tileSize)-(tileSize*2), 
                   (roomW-2)*tileSize/2,
                   //(roomH*tileSize)-(tileSize*2), 
                   (roomH-2)*tileSize/2,
                   roomEntityMap, k, l);
        //Room r = new Room("", 1, 1, 1);
        Random random = new Random();
        int rand = random.nextInt(layout.length);
        /*
        System.out.println("tile size: " + tileSize);
        System.out.println("level: " + level);
        System.out.println("WIDTH: " + WIDTH);
        System.out.println("HEIGHT: " + HEIGHT);
        System.out.println("houseW: " + houseW);
        System.out.println("houseH: " + houseH);
        */
        //createTile(60, 60, "box", roomEntityMap);
        
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
                        createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "wall", roomEntityMap);
                    }
                    // if a room is at the edge of the house (no doors at the edges)
                    else if( (k == houseH-1 || k == 0) || (l == houseW-1 || l == 1) ){
                        // if the room is at the bottom and the tile is at the bottom 
                        if((j==tileSize/2 && k == 0))
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "wall", roomEntityMap);
                        // if the room is at the top and the tile is at the top
                        else if((j==HEIGHT-tileSize/2 && k == houseH-1))
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "wall", roomEntityMap);
                        // if the room is at the left and the tile is at the left
                        else if((i==tileSize/2 && l == 1)){
                            if(k!=0) // enter house
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "wall", roomEntityMap);
                            else
                                // door
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "door", roomEntityMap);
                        }
                        // close of the starting area, but not the house door
                        else if(l==0 && i!=WIDTH-tileSize/2){
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "wall", roomEntityMap);
                        }
                        // if the room is at the right and the tile is at the right
                        else if((i == WIDTH-tileSize/2 && l == houseW-1)){
                            if(k!=0) // enter boos
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "wall", roomEntityMap);
                            else
                                // door
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "door", roomEntityMap);
                        }
                        // close of the boss area, but not the boss door
                        else if(l==houseW && i != tileSize/2){
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "wall", roomEntityMap);
                        }
                        else{
                            // door
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "door", roomEntityMap);
                        }
                    }
                    else{
                        // door (or not maybe)
                        createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "door", roomEntityMap);
                    }
                }
                else{
                    // floor
                    //createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "floor", roomEntityMap);
                    //createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2, "floor", roomEntityMap);
                    //if(layout.get(l).get(k) == -1){
                    if( (l!=0 && l!=houseW)){
                        if(layout[rand][(j-tileSize/2)/tileSize][(i-tileSize/2)/tileSize] == -1){
                        //if(layout[3][(j-tileSize/2)/tileSize][(i-tileSize/2)/tileSize] == -1){
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "wall", roomEntityMap);
                        }
                        else{
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "floor", roomEntityMap);
                        }
                    }
                    else{
                        createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), "floor", roomEntityMap);
                    }
                }
            }
        }
    }
    
    private Entity createTile(float x, float y, String type, java.util.Map<String, Entity> roomEntityMap){
        Entity map = new Map();
        
        map.setRadius(tileSize/2);
        map.setSizeX(tileSize/2);
        map.setSizeY(tileSize/2);
        //System.out.println("x: " + x);
        //System.out.println("y: " + y);
        map.add(new PositionPart(x,y,0));
        map.add(new TilePart(type));
        if(type.equals("box")){
            map.add(new LifePart(1));
        }
        setShape(map);
        world.addEntity(map);
        addEntity(map, roomEntityMap);
        return map;
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
    private Entity createRoom(float x, float y, float sizeX, float sizeY, java.util.Map<String, Entity> roomEntityMap, int k, int l){
        Entity map = new Map();
        
        map.setRadius(sizeX);
        map.setSizeX(sizeX);
        map.setSizeY(sizeY);
        map.add(new PositionPart(x,y,0));
        TilePart tp = new TilePart("roomInfo");
        int i = k+l;
        if(k==0 && l==(houseW)){
            i=1;
            
        }
        else if(i>0){
            i++;
        }
        if(k>0){
            if(k==1){
                i=(k*(houseW+1))+l-1;
            }
            else
                i=(k*(houseW+1))+l+1-(k*2);
        }
        tp.setRoom(i);
        if(k==0&&l==0){
            tp.setState("explored");
        }
        else{
            tp.setState("unexplored");
        }
        map.add(tp);
        
        world.addEntity(map);
        addEntity(map, roomEntityMap);
        world.addRoomIdentifier(map);
        return map;
    }
    private Entity createBox(){
        Entity map = new Map();
        
        map.setRadius(tileSize/2);
        map.setSizeX(tileSize/2);
        map.setSizeY(tileSize/2);
        //System.out.println("x: " + x);
        //System.out.println("y: " + y);
        //map.add(new PositionPart(x,y,0));
        map.add(new TilePart("box"));
        map.add(new LifePart(10));
        
        world.addEntity(map);
        //addEntity(map, roomEntityMap);
        return map;
    }
    
    public String addEntity(Entity entity, java.util.Map<String, Entity> roomEntityMap) {
        roomEntityMap.put(entity.getID(), entity);
        /*if(ignoreWalkableTiles(entity)){
            collisionMap.put(entity.getID(), entity);
        }*/
        return entity.getID();
    }
    private int[][][] layout = {
        { // 1
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1}, //   0
            {-1,1,2,3,4,5,6,7,8,-1,10,11,12, -1,14,15,16,17,18,19,20,21,-1}, //   1
            {-1,1,2,3,4,5,6,7,8,-1,10,11,12, -1,14,15,16,17,18,19,20,21,-1}, //   2
            {-1,1,2,3,4,5,6,7,8,-1,10,11,12, -1,14,15,16,17,18,19,20,21,-1}, //   3
            {-1,1,2,3,4,5,6,7,8,-1,10,11,12, -1,14,15,16,17,18,19,20,21,-1}, //   4
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //   5
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //   6
            {-1,-1,-1,-1,-1,-1,-1,7,8,-1, -1, -1, -1, -1,14,15, -1, -1, -1, -1, -1, -1,-1}, //   7
            {-1,1,2,3,4,5,6,7,8,-1, -1, -1, -1, -1,14,15,16,17,18,19,20,21,-1}, //   8
            {-2,1,2,3,4,5,6,7,8,-1, -1, -1, -1, -1,14,15,16,17,18,19,20,21,-2}, //   9
            {-1,1,2,3,4,5,6,7,8,-1, -1, -1, -1, -1,14,15,16,17,18,19,20,21,-1}, //  10
            {-1,-1,-1,-1,-1,-1,-1,7,8,-1, -1, -1, -1, -1,14,15, -1, -1, -1, -1, -1, -1,-1}, //  11
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  12
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  13
            {-1,1,2,3,4,5,6,7,8,-1,10,11,12, -1,14,15,16,17,18,19,20,21,-1}, //  14
            {-1,1,2,3,4,5,6,7,8,-1,10,11,12, -1,14,15,16,17,18,19,20,21,-1}, //  15
            {-1,1,2,3,4,5,6,7,8,-1,10,11,12, -1,14,15,16,17,18,19,20,21,-1}, //  16
            {-1,1,2,3,4,5,6,7,8,-1,10,11,12, -1,14,15,16,17,18,19,20,21,-1}, //  17
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1} //  18
        },
        { // 2
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1}, //  0
            {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  1
            {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  2
            {-1, 1, 2,-1,-1,-1,-1, 7, 8, 9,10,11,12,13,14,15,-1,-1,-1,-1,20,21,-1}, //  3
            {-1, 1, 2,-1,-1,-1,-1, 7, 8, 9,10,11,12,13,14,15,-1,-1,-1,-1,20,21,-1}, //  4
            {-1, 1, 2,-1,-1,-1,-1, 7, 8, 9,10,11,12,13,14,15,-1,-1,-1,-1,20,21,-1}, //  5
            {-1, 1, 2,-1,-1,-1,-1, 7, 8, 9,10,11,12,13,14,15,-1,-1,-1,-1,20,21,-1}, //  6
            {-1, 1, 2, 3, 4, 5, 6, 7, 8,-1,-1,-1,-1,-1,14,15,16,17,18,19,20,21,-1}, //  7
            {-1, 1, 2, 3, 4, 5, 6, 7, 8,-1,-1,-1,-1,-1,14,15,16,17,18,19,20,21,-1}, //  8
            {-2, 1, 2, 3, 4, 5, 6, 7, 8,-1,-1,-1,-1,-1,14,15,16,17,18,19,20,21,-2}, //  9
            {-1, 1, 2, 3, 4, 5, 6, 7, 8,-1,-1,-1,-1,-1,14,15,16,17,18,19,20,21,-1}, // 10
            {-1, 1, 2, 3, 4, 5, 6, 7, 8,-1,-1,-1,-1,-1,14,15,16,17,18,19,20,21,-1}, // 11
            {-1, 1, 2,-1,-1,-1,-1, 7, 8, 9,10,11,12,13,14,15,-1,-1,-1,-1,20,21,-1}, // 12
            {-1, 1, 2,-1,-1,-1,-1, 7, 8, 9,10,11,12,13,14,15,-1,-1,-1,-1,20,21,-1}, // 13
            {-1, 1, 2,-1,-1,-1,-1, 7, 8, 9,10,11,12,13,14,15,-1,-1,-1,-1,20,21,-1}, // 14
            {-1, 1, 2,-1,-1,-1,-1, 7, 8, 9,10,11,12,13,14,15,-1,-1,-1,-1,20,21,-1}, // 15
            {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 16
            {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 17
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-2,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1} // 18
        },
        /*{ // 3
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1}, //  0
            {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, 11,-1,13,14,15,16,17,18,19,20,21,-1}, //  1
            {-1,1,2,3,4,5,6,7,8,9,-1,11,-1,13,14,15,16,17,18,19,20,21,-1}, //  2
            {-1,1,2,3,4,5,6,7,-1,9,-1,11,-1,13,-1,15,16,17,18,19,20,21,-1}, //  3
            {-1,1,2,3,4,5,6,7,-1,9,-1,11,-1,13,-1,15,16,17,18,19,20,21,-1}, //  4
            {-1,1,2, 3, 4, 5, 6, 7,-1,9,-1,11,-1,13,-1,15,16,17,18,19,20,21,-1}, //  5
            {-1,1,2,-1,-1,-1,-1,-1,-1,9,10,11,12,13,-1,-1,-1,-1,-1,-1,20,21,-1}, //  6
            {-1, 1, 2, 3, 4, 5,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,16,17,18,19,20,21,-1}, //  7
            {-1,-1,-1,-1,-1,-1,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,16,-1,-1,-1,-1,-1,-1}, //  8
            {-2, 1, 2, 3, 4, 5,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,16,17,18,19,20,21,-2}, //  9
            {-1,-1,-1,-1,-1,-1,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,16,-1,-1,-1,-1,-1,-1}, // 10
            {-1, 1, 2, 3, 4, 5,6,-1,-1,-1,-1,-1,-1,-1,-1,-1,16,17,18,19,20,21,-1}, // 11
            {-1,1,2,-1,-1,-1,-1,-1,-1,9,10,11,12,13,-1,-1,-1,-1,-1,-1,20,21,-1}, // 12
            {-1,1,2, 3, 4, 5, 6, 7,-1,9,-1,11,-1,13,-1,15,16,17,18,19,20,21,-1}, // 13
            {-1,1,2,3,4,5,6,7,-1,9,-1,11,-1,13,-1,15,16,17,18,19,20,21,-1}, // 14
            {-1,1,2,3,4,5,6,7,-1,9,-1,11,-1,13,-1,15,16,17,18,19,20,21,-1}, // 15
            {-1,1,2,3,4,5,6,7,8,9,-1,11,-1,13,14,15,16,17,18,19,20,21,-1}, // 16
            {-1, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, 11,-1,13,14,15,16,17,18,19,20,21,-1}, // 17
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1} // 18
        },*/
        { // 4
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1}, //  0
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  1
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  2
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  3
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  4
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  5
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  6
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  7
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  8
            {-2,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-2}, //  9
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, // 10
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, // 11
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, // 12
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, // 13
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, // 14
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, // 15
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 16
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 17
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1} // 18
        },
        { // 5
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1}, //  0
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  1
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  2
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  3
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  4
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  5
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  6
            {-1,1,2,-1,-1,-1,-1,-1,-1,-1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,20,21,-1}, //  7
            {-1,1,2,3,4,5,6,7,8,-1, -1, -1, -1, -1,14,15,16,17,18,19,20,21,-1}, //  8
            {-2,1,2,3,4,5,6,7,8,-1, -1, -1, -1, -1,14,15,16,17,18,19,20,21,-2}, //  9
            {-1,1,2,3,4,5,6,7,8,-1, -1, -1, -1, -1,14,15,16,17,18,19,20,21,-1}, // 10
            {-1,1,2,3,4,5,6,7,8,-1, -1, -1, -1, -1,14,15,16,17,18,19,20,21,-1}, // 11
            {-1,1,2,-1,-1,-1,-1,7,8,-1, -1, -1, -1, -1,14,15, -1, -1, -1, -1,20,21,-1}, // 12
            {-1,1,2,-1,-1,-1,-1,7,8,-1, -1, -1, -1, -1,14,15, -1, -1, -1, -1,20,21,-1}, // 13
            {-1,1,2,-1,-1,-1,-1,7,8,-1, -1, -1, -1, -1,14,15, -1, -1, -1, -1,20,21,-1}, // 14
            {-1,1,2,-1,-1,-1,-1,7,8,-1, -1, -1, -1, -1,14,15, -1, -1, -1, -1,20,21,-1}, // 15
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 16
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 17
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1} // 18
        },
        {
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1}, //  0
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  1
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  2
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  3
            {-1,1,2,3,4,5,6,7,-1,-1,10,11,12,-1,-1,15,16,17,18,19,20,21,-1}, //  4
            {-1,1,2,3,4,5,6,-1,8,9,-1,-1,-1,13,14,-1,16,17,18,19,20,21,-1}, //  5
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  6
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, //  7
            {-1,1,2,3,-1,-1,6,7,8,9,-1,-1,-1,13,14,15,16,-1,-1,19,20,21,-1}, //  8
            {-2,1,2,-1,-1,-1,-1,7,8,9,10,-1,12,13,14,15,-1,-1,-1,-1,20,21,-2}, //  9
            {-1,1,2,-1,-1,5,-1,7,8,9,10,11,12,13,14,15,-1,-1,18,-1,20,21,-1}, // 10
            {-1,1,2,3,-1,-1,6,7,8,9,10,11,12,13,14,15,16,-1,-1,19,20,21,-1}, // 11
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 12
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 13
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 14
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 15
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 16
            {-1,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,-1}, // 17
            {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1, -1, -2, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,-1} // 18
        }
    };
}

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
import rescuerage.common.services.IGamePluginService;

/**
 *
 * @author ander
 */
@ServiceProviders(value = {
        @ServiceProvider(service = IGamePluginService.class),})
public class MapPlugin implements  IGamePluginService{
    
    private int houseH = 3;
    private int houseW = 2;
    private int roomH = 9; // must be uneven number, because the doors are being set at the center of the wall
    private int roomW = 11; // must be uneven number, because the doors are being set at the center of the wall
    private int tileSize = 16; // must be even number, because the top and right rows are depending on it
    private int WIDTH = roomW * tileSize;
    private int HEIGHT = roomH * tileSize;
    private World world;

    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        this.houseW = this.houseW + 1;
        createLevel();
    }

    @Override
    public void stop(GameData gameData, World world) {
        
    }
    
    private void createLevel(){
        
        // starting area
        
        createHouse();
        
        // end area
        
    }
    
    private void createHouse(){
        // create a room for each houseH
        for(int k = 0; k < houseH; k++){
            // create a room for each houseW
            for(int l = 0; l < houseW+1; l++){
                createRoom(k,l);
                
            }
        }
    }
    
    private void createRoom(int k, int l){
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
                        createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2);
                    }
                    // if a room is at the edge of the house (no doors at the edges)
                    else if( (k == houseH-1 || k == 0) || (l == houseW-1 || l == 1) ){
                        // if the room is at the bottom and the tile is at the bottom 
                        if((j==tileSize/2 && k == 0))
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2);
                        // if the room is at the top and the tile is at the top
                        else if((j==HEIGHT-tileSize/2 && k == houseH-1))
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2);
                        // if the room is at the left and the tile is at the left
                        else if((i==tileSize/2 && l == 1)){
                            if(k!=0) // enter house
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2);
                        }
                        // close of the starting area, but not the house door
                        else if(l==0 && i!=WIDTH-tileSize/2){
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2);
                        }
                        // if the room is at the right and the tile is at the right
                        else if((i == WIDTH-tileSize/2 && l == houseW-1)){
                            if(k!=0) // enter boos
                                createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2);
                        }
                        // close of the boss area, but not the boss door
                        else if(l==houseW && i != tileSize/2){
                            createTile(i+(l*roomW*tileSize), j+(k*roomH*tileSize), tileSize/2);
                        }
                    }
                    else{
                        // door
                    }
                    
                }
                else{
                    // floor
                }
            }
        }
    }
    
    private Entity createTile(float x, float y, float size){
        Entity map = new Map();
        
        map.setRadius(size);
        
        map.add(new PositionPart(x,y,0));
        world.addEntity(map);
        return map;
    }
    
}

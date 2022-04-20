/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage;

import java.util.ArrayList;
import rescuerage.map.Map;
import rescuerage.common.data.entityparts.TilePart;

/**
 *
 * @author ander
 */
public class Room {
    public String type; // room / start / end / shop
    public String state; // unexplored / exploring / explored
    public int x;
    public int y;
    public int tileSize;
    ArrayList<Map> tiles = new ArrayList<Map>();
    ArrayList<Map> doors = new ArrayList<Map>();
    ArrayList<Map> boxes = new ArrayList<Map>();
    
    public Room(String type, int x, int y, int tileSize){
        this.type = type;
        this.x = x;
        this.y = y;
        this.tileSize = tileSize;
        
        if(type.equals("start")){
            this.state = "explored";
        }
        else{
            this.state = "unexplored";
        }
    }
    public void addDoor(Map door){
        doors.add(door);
    }
    public void addBox(Map box){
        boxes.add(box);
    }
    public void removeBox(Map box){
        boxes.remove(box);
        if(boxes.isEmpty()){
            System.out.println("EMPTY");
            explored();
        }
    }
    public void explore(){
        this.state = "exploring";
        for(Map tile : doors){
            TilePart door = tile.getPart(TilePart.class);
            door.lock();
        }
        spawn();
    }
    public void explored(){
        this.state = "explored";
        for(Map tile : doors){
            TilePart door = tile.getPart(TilePart.class);
            door.unlock();
        }
    }
    public void spawn(){
        //createTile(60, 60, tileSize/2, "box");
    }
}

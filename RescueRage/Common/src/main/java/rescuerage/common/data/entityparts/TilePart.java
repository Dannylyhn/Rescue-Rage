/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.common.data.entityparts;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;

/**
 *
 * @author ander
 */
public class TilePart implements EntityPart{
    
    public String type;
    public boolean locked;

    public TilePart(String type) {
        this.type = type;
        // if door then lock
        //locked = type.equals("door");
        locked = false;
    }
    
    public String getType() {
        return type;
    }
    
    public void unlock(){
        locked = false;
    }
    
    public void lock(){
        locked = true;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
    
}

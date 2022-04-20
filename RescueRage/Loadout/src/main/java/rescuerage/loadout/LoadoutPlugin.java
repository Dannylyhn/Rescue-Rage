/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.loadout;

import rescuerage.common.data.Entity;
import rescuerage.common.data.World;
import rescuerage.commonloadout.LoadoutSPI;

/**
 *
 * @author dan
 */
public class LoadoutPlugin implements LoadoutSPI{

    @Override
    public Entity createLoadout(World world) {
        Entity loadout = new Entity();
        
        world.addEntity(loadout);
        return loadout;
    }
    
    
    
}

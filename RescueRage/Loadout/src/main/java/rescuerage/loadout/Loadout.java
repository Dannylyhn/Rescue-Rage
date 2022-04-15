/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.loadout;
import java.util.ArrayList;
import rescuerage.common.data.Entity;


/**
 *
 * @author dan
 */
public class Loadout extends Entity{
    
    //TEMP
    ArrayList<Entity> weapons;
    
    public ArrayList<Entity> getWeapons() {
        return weapons;
    }
    
    public void setWeapons(ArrayList<Entity> weapons) {
        this.weapons = weapons;
    }
    
    public void addToLoadout(Entity weapon)
    {
        weapons.add(weapon);
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.common.data.entityparts;
import java.util.ArrayList;
import java.util.Map;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;

/**
 *
 * @author dan
 */
public class LoadoutPart implements EntityPart{
    public ArrayList<Entity> weapons = new ArrayList<Entity>();
    public Entity currentWeapon = null;
    
    private boolean Q,E;
    
    private float swapCD = 30;
    private float currentSwapCD = swapCD;

    public void setQ(boolean Q) {
        this.Q = Q;
    }
    
    public void setE(boolean E) {
        this.E = E;
    }

    public ArrayList<Entity> getWeapons() {
        return weapons;
    }

    public void setWeapons(ArrayList<Entity> weapons) {
        this.weapons = weapons;
    }
    
    public void addWeapon(Entity weapon){
        weapons.add(weapon);
    }
    
    public void removeWeapon(Entity weapon){
        weapons.remove(weapon);
    }

    public Entity getCurrentWeapon() {
        return currentWeapon;
    }

    public void setCurrentWeapon(Entity currentWeapon) {
        this.currentWeapon = currentWeapon;
    }
    
    public void decrementSwapCD()
    {
        this.currentSwapCD--;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
        swapWeapon();
    }
    
    public void swapWeapon(){
        for(Entity e : weapons)
        {
            GunPart gunpart = e.getPart(GunPart.class);
       
        }

        int indexOfCurrentWeapon = getWeapons().indexOf(currentWeapon);
        int loadoutLength = getWeapons().size();
        GunPart gunPart = null;
        
        //Get the gunPart of the current weapon. 
        //If there is no current weapon we cant get a gunpart
        if(currentWeapon!=null)
        {
             gunPart = currentWeapon.getPart(GunPart.class);
        }
        
        //If the cooldown is over we can swap. Meaning if its 0 or below.
        if(this.currentSwapCD<0)
        {
            //Changes weapon to previous
            if(Q && weapons.size()>=2)
            {
                //Set current weapon equip to false. Now it cannot shoot
                gunPart.setEquipped(false);
                int indexOfPreviousWeapon = indexOfCurrentWeapon-1;
                //Loop back in the array if we press Q for the first weapon.
                if(indexOfPreviousWeapon<0)
                {
                    indexOfPreviousWeapon = loadoutLength-1;
                }
                //New current weapon to previous and set its equip to true.
                setCurrentWeapon(getWeapons().get(indexOfPreviousWeapon));
                gunPart = currentWeapon.getPart(GunPart.class);
                gunPart.setEquipped(true);
                
                //Swapped weapons set cooldown back
                this.currentSwapCD = this.swapCD;
            }
            //Changes weapon to next
            if(E && weapons.size()>=2)
            {
                gunPart.setEquipped(false);
                //Loop back in the array if we press Q for the last weapon.
                setCurrentWeapon(getWeapons().get((indexOfCurrentWeapon+1)%loadoutLength));
                gunPart = currentWeapon.getPart(GunPart.class);
                gunPart.setEquipped(true);
                
                //We have swapped weapons set cooldown back
                this.currentSwapCD = this.swapCD;
            }
        }
        else
        {
            this.currentSwapCD--;
        }
    }
}

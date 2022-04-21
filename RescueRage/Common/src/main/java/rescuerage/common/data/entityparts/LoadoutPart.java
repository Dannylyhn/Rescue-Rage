/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.common.data.entityparts;
import java.util.ArrayList;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;

/**
 *
 * @author dan
 */
public class LoadoutPart implements EntityPart{
    public ArrayList<Entity> weapons = new ArrayList<Entity>();
    public Entity currentWeapon;
    
    private boolean Q,E;

    
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
    
    

    @Override
    public void process(GameData gameData, Entity entity) {
        int indexOfCurrentWeapon = getWeapons().indexOf(currentWeapon);
        int loadoutLength = getWeapons().size();
        GunPart gunPart = currentWeapon.getPart(GunPart.class);

        //Changes weapon to previous
        if(Q)
        {
            gunPart.setEquipped(false);
            int indexOfPreviousWeapon = indexOfCurrentWeapon-1;
            //Loop back in the array if we press Q for the first weapon.
            if(indexOfPreviousWeapon<0)
            {
                indexOfPreviousWeapon = loadoutLength-1;
            }
            setCurrentWeapon(getWeapons().get(indexOfPreviousWeapon));
            gunPart = currentWeapon.getPart(GunPart.class);
            gunPart.setEquipped(true);
        }
        //Changes weapon to next
        if(E)
        {
            gunPart.setEquipped(false);
            //Loop back in the array if we press Q for the last weapon.
            setCurrentWeapon(getWeapons().get((indexOfCurrentWeapon+1)%loadoutLength));
            gunPart = currentWeapon.getPart(GunPart.class);
            gunPart.setEquipped(true);
        }
    }
}
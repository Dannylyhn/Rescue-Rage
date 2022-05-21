/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.common.data.entityparts;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;

/**
 *
 * @author dan
 */
public class GunPart implements EntityPart{
    public String name; 
    public boolean pickedUp = false;
    public boolean equipped = false;
    public int bulletsPerShot;
    public int maxAmmo;
    public int ammo;
    public int magazineLength;
    public int magazine;
    public float[] sprayPattern;

    public GunPart(String name, int bulletsPerShot, int ammo, int magazineLength, float[] sprayPattern) {
        this.name = name;
        this.bulletsPerShot = bulletsPerShot;
        this.ammo = ammo;
        this.maxAmmo = ammo;
        this.magazineLength = magazineLength;
        this.magazine = magazineLength;
        this.sprayPattern = sprayPattern;
    }
    
    public int getBulletsPerShot() {
        return bulletsPerShot;
    }

    public void setBulletsPerShot(int bulletsPerShot) {
        this.bulletsPerShot = bulletsPerShot;
    }

    public int getAmmo() {
        return this.ammo;
    }

    public void setAmmo(int ammo) {
        this.ammo = ammo;
    }
    
    public void minusAmmo(int amount){
        if(ammo>0)
        {
            this.ammo = ammo-amount;
            //When we minus our ammo and its below 0 just set it to 0.
            if(ammo<0)
            {
                this.ammo = 0;
            }
        }
        
    }

    public int getMagazineLength() {
        return this.magazineLength;
    }
    
    public int getMagazine() {
        return this.magazine;
    }

    public void setMagazine(int reloadedAmount) {
        if(this.ammo>0)
        {
            this.magazine = this.magazineLength;
        }
        else
        {
            this.magazine = reloadedAmount;
        }
    }
    
    public void minusMagazine(){
        if(this.magazine!=0)
        {
            this.magazine--;
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public float[] getSprayPattern() {
        return sprayPattern;
    }

    public void setSprayPattern(float[] sprayPattern) {
        this.sprayPattern = sprayPattern;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }
    
    

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}

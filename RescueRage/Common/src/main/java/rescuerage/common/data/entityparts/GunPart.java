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
    
    public int bulletsPerShot;
    public int ammo;
    public float[] sprayPattern;

    public GunPart(int bulletsPerShot, int ammo, float[] sprayPattern) {
        this.bulletsPerShot = bulletsPerShot;
        this.ammo = ammo;
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
    
    public void minusAmmo(){
        this.ammo--;
    }

    public float[] getSprayPattern() {
        return sprayPattern;
    }

    public void setSprayPattern(float[] sprayPattern) {
        this.sprayPattern = sprayPattern;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
    }
}

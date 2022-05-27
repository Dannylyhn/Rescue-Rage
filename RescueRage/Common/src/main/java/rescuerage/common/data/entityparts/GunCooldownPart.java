/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rescuerage.common.data.entityparts;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;

/**
 *
 * @author ander
 */
public class GunCooldownPart
        implements EntityPart {
    private boolean shot = false;
    private boolean reloaded = false;
    
    private float currentShootingCD = 0;
    private float currentReloadCD = 0;
   
    private float shootingCD;
    private float reloadCD;
    

    public GunCooldownPart(float shootingCooldown, float reloadCooldown) {
        this.shootingCD = shootingCooldown;
        this.reloadCD = reloadCooldown;
        
    }

    public boolean isShot() {
        return shot;
    }

    public void setShot(boolean shot) {
        this.shot = shot;
    }

    public boolean isReloaded() {
        return reloaded;
    }

    public void setReloaded(boolean reloaded) {
        this.reloaded = reloaded;
    }

    public float getCurrentShootingCD() {
        return currentShootingCD;
    }

    public void setCurrentShootingCD(float currentShootingCD) {
        this.currentShootingCD = currentShootingCD;
    }

    public float getCurrentReloadCD() {
        return currentReloadCD;
    }

    public void setCurrentReloadCD(float currentReloadCD) {
        this.currentReloadCD = currentReloadCD;
    }

    public float getShootingCD() {
        return shootingCD;
    }    

    public void setShootingCD(float shootingCD) {
        this.shootingCD = shootingCD;
    }
    

    public float getReloadCD() {
        return reloadCD;
    }

    public void setReloadCD(float reloadCD) {
        this.reloadCD = reloadCD;
    }
    
    public void decrementShootingCooldown()
    {
        this.currentShootingCD--;
    }
    
    public void decrementReloadCooldown()
    {
        this.currentReloadCD--;
    }
    
    
    @Override
    public void process(GameData gameData, Entity entity) {
        if(isShot() == true && getCurrentShootingCD()>0)
        {
            decrementShootingCooldown();
        }
        else
        {
            setShot(false);
        }
        if(isReloaded() == true)
        {
            decrementReloadCooldown();
        }
        else
        {
            setReloaded(false);
        }
    }
}


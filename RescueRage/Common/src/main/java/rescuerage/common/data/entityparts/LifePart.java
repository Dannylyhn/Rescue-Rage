package rescuerage.common.data.entityparts;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;


public class LifePart implements EntityPart {

    private boolean dead = false;
    private int maxLife;
    private int life;
    private boolean isHit = false;
    private boolean isPlayer = false;
    private int cooldown = 0;

    public LifePart(int life) {
        this.life = life;
        this.maxLife = life;
    }

    public int getLife() {
        return life;
    }
    public void incLife(int i){
        this.life = life + i;
    }
    public int getMax() {
        return maxLife;
    }
    public void incMax(int i){
        this.maxLife = maxLife + i;
        this.life = maxLife;
    }

    public void setLife(int life) {
        this.life = life;
    }
    public void setIsPlayer() {
        this.isPlayer = true;
    }

    public boolean isHit() {
        return isHit;
    }
    public void hit(int damage){
        if(cooldown == 0){
            life = life - damage;
            if (life <= 0) {
                dead = true;
            }
            if(isPlayer)
                cooldown = 50;
        }
    }

    public void decCooldown() {
        this.cooldown = cooldown - 1;
    }
    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean isDead() {
        return dead;
    }
    public void reincarnate(){
        this.dead = false;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if (isHit) {
            life = - 1;
            isHit = false;
        }
        if (life <= 0) {
            dead = true;
        }
        if(cooldown>0){
            decCooldown();
        }
    }
}

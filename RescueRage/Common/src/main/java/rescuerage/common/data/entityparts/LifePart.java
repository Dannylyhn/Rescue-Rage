package rescuerage.common.data.entityparts;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;

/**
 *
 * @author Someone
 */
public class LifePart implements EntityPart {

    private boolean dead = false;
    private int life;
    private boolean isHit = false;

    public LifePart(int life) {
        this.life = life;
    }

    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }

    public boolean isHit() {
        return isHit;
    }
    public void hit(int damage){
        life = life - damage;
        if (life <= 0) {
            dead = true;
        }
    }

    public void setIsHit(boolean isHit) {
        this.isHit = isHit;
    }

    public boolean isDead() {
        return dead;
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

    }
}

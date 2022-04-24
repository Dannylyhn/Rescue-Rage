package rescuerage.common.data.entityparts;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import rescuerage.common.data.GameKeys;

/**
 *
 * @author Alexander
 */
public class PlayerMovingPart
        implements EntityPart {

    private float dx, dy;
    private float maxSpeed, rotationSpeed;
    private boolean left, right, up, down;

    public PlayerMovingPart(float maxSpeed) {
        this.maxSpeed = maxSpeed;
       
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }
    

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setSpeed(float speed) {
        this.maxSpeed = speed;
    }
    
 

    public void setLeft(boolean left) {
        this.left = left;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public void setUp(boolean up) {
        this.up = up;
    }
    
    public void setDown(boolean down){
        this.down = down;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float dt = gameData.getDelta();

        // Movement
        if (gameData.getKeys().isDown(GameKeys.A)) {
            x += -maxSpeed*dt;
        
        }

        if (gameData.getKeys().isDown(GameKeys.D)) {
            x += maxSpeed*dt;
            
        }
          
        if (gameData.getKeys().isDown(GameKeys.W)) {
            y += maxSpeed*dt;
        }
        
        if (gameData.getKeys().isDown(GameKeys.S)){
            y += -maxSpeed*dt;
        }
        
        // set position
          x += dx * dt;
          y += dy * dt;
        

        positionPart.setX(x);
        positionPart.setY(y);

        positionPart.setRadians(radians);
    }

}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rescuerage.common.data.entityparts;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.GameData;
import static rescuerage.common.data.GameKeys.A;
import static rescuerage.common.data.GameKeys.D;
import static rescuerage.common.data.GameKeys.W;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import java.util.ArrayList;

/**
 *
 * @author ander
 */
public class EnemyMovingPart implements EntityPart {

    private float dx, dy;
    //private float deceleration, acceleration;
    private float maxSpeed;
    //private float rotationSpeed;
    private boolean left, right, up, down, upLeft, upRight, downLeft, downRight;
    
    private int playerTile = 0;
    private int roomNR = 0;
    private int tile = 0;
    public boolean newTile = false;
    private boolean enter = false;
    
    private boolean changed = false;
    private ArrayList<String> path = new ArrayList();
    
    public void setPlayerTile(int tileNR, int currentRoom){
        if(currentRoom == roomNR){
            enter = true;
            if(playerTile != tileNR){
                changed = true;
            }
            playerTile = tileNR;
        }
    }

    public EnemyMovingPart(float maxSpeed, int roomNR) {
       
        this.maxSpeed = maxSpeed;
        this.roomNR = roomNR;
       
    }

    public float getDx() {
        return dx;
    }

    public float getDy() {
        return dy;
    }

    /*public void setDeceleration(float deceleration) {
        this.deceleration = deceleration;
    }*/

    /*public void setAcceleration(float acceleration) {
        this.acceleration = acceleration;
    }*/

    public void setMaxSpeed(float maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public void setSpeed(float speed) {
        //this.acceleration = speed;
        this.maxSpeed = speed;
    }

    /*public void setRotationSpeed(float rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }*/

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
    //upLeft, upRight, downLeft, downRight
    public void setUpLeft(boolean upLeft){
        this.upLeft = upLeft;
    }
    public void setUpRight(boolean upRight){
        this.upRight = upRight;
    }
    public void setDownLeft(boolean downLeft){
        this.downLeft = downLeft;
    }
    public void setDownRight(boolean downRight){
        this.downRight = downRight;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        if(enter){
            if(changed){
                System.out.println("In new tile: " + playerTile);
                changed = false;
            }
            PositionPart positionPart = entity.getPart(PositionPart.class);
            float x = positionPart.getX();
            float y = positionPart.getY();
            float radians = positionPart.getRadians();
            float dt = gameData.getDelta();
            /*tile = (int)x*16;
            tile = tile * ((int)y/16);*/
            int tempTile = ((int)x/16) * ((int)y/16);
            if(tile != tempTile){
                newTile = true;
                tile = tempTile;
                System.out.println(tile);
                // update new movement
            }

            // turning
            if (left) {
                //dx = -maxSpeed;
                x -= maxSpeed*dt;
            }

            if (right) {
                //dx = maxSpeed;
                x += maxSpeed*dt;
            }

            // accelerating            
            if (up) {
                //dy =  maxSpeed;
                y += maxSpeed*dt;
            }

            if (down){
                //dy = -maxSpeed;
                y -= maxSpeed*dt;
            }
            if (upLeft){
                //dy =  maxSpeed;
                y += maxSpeed*dt;
                //dx = -maxSpeed;
                x -= maxSpeed*dt;
            }
            if (upRight){
                //dy =  maxSpeed;
                y += maxSpeed*dt;
                //dx = maxSpeed;
                x += maxSpeed*dt;
            }
            if (downLeft){
                //dy = -maxSpeed;
                y -= maxSpeed*dt;
                //dx = -maxSpeed;
                x -= maxSpeed*dt;
            }
            if (downRight){
                //dy = -maxSpeed;
                y -= maxSpeed*dt;
                //dx = maxSpeed;
                x += maxSpeed*dt;
            }

            // set position
            if(x!=0){
                x += dx * dt;
                if (x > gameData.getDisplayWidth()) {
                    x = 0;
                } else if (x < 0) {
                    x = gameData.getDisplayWidth();
                }

                y += dy * dt;
                if (y > gameData.getDisplayHeight()) {
                    y = 0;
                } else if (y < 0) {
                    y = gameData.getDisplayHeight();
                }

                positionPart.setX(x);
                positionPart.setY(y);
            }
            positionPart.setRadians(radians);
        }
    }

}

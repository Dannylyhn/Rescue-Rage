/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collision;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rescuerage.collision.CollisionHandler;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.playersystem.PlayerControlSystem;
import rescuerage.playersystem.PlayerPlugin;

/**
 *
 * @author danny
 */
public class CollisionTest {

    PlayerPlugin playerplugin;
    GameData gamedata;
    World world;
    Entity testPlayer;
    CollisionHandler collisionHandler;
    float playerX;
    float playerY;
    ArrayList<Entity> players;
    ArrayList<PositionPart> positionParts;

    @BeforeEach
    public void setUp(){
    playerplugin = new PlayerPlugin();
    gamedata = new GameData();
    world = new World();
    collisionHandler = new CollisionHandler();
    players = new ArrayList<>();
    positionParts = new ArrayList<>();
    
    }
    
    
    @Test
    @DisplayName("Testing the IsCollision() method in CollisionHandler")
    public void CollisionTest() {
        playerplugin.start(gamedata, world);
        playerplugin.start(gamedata, world);
        for(Entity player : world.getEntities()){ 
        if(player.getClass().getSimpleName().equals("Player")){
            players.add(player);
           }      
        }
        
        Entity player1 = players.get(0);
        Entity player2 = players.get(1);
        
        updateShape(player1);
        updateShape(player2);
       
        boolean collisionDetect = collisionHandler.isCollision(player1, player2);
        System.out.println(collisionDetect);
        
        assertTrue(collisionDetect);
 
    }
    
    public static void updateShape(Entity entity){
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        //float radius = entity.getRadius();
        float sizeX = entity.getSizeX();
        float sizeY = entity.getSizeY();
        
        shapex[0] = x + sizeX;
        shapey[0] = y + sizeY;
        
        shapex[1] = x + sizeX;
        shapey[1] = y - sizeY;
        
        shapex[2] = x - sizeX;
        shapey[2] = y - sizeY;
        
        shapex[3] = x - sizeX;
        shapey[3] = y + sizeY;
        
        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}

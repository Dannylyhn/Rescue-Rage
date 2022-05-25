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
    float[] sx;
    float[] sy;
    PositionPart pp;
    PlayerControlSystem pcs;

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
    @DisplayName("Collision testing")
    public void CollisionTest() {
        playerplugin.start(gamedata, world);
        playerplugin.start(gamedata, world);
        for(Entity player : world.getEntities()){ 
        if(player.getClass().getSimpleName().equals("Player")){
            players.add(player);
            pp = player.getPart(PositionPart.class);
            positionParts.add(pp);
           }      
        }
        
        Entity player1 = players.get(0);
        Entity player2 = players.get(1);
      
        float[] player1shapex = new float[4];
        float[] player1shapey = new float[4];
        float[] player2shapex = new float[4];
        float[] player2shapey = new float[4];
        
        PositionPart player1PP = player1.getPart(PositionPart.class);
        PositionPart player2PP = player2.getPart(PositionPart.class);
        
        float player1X = player1PP.getX();
        float player1Y = player1PP.getY();
        
        float player2X = player2PP.getX();
        float player2Y = player2PP.getY();
        
        
        float player1sizeX = player1.getSizeX();
        float player1sizeY = player1.getSizeY();
        
        float player2sizeX = player2.getSizeX();
        float player2sizeY = player2.getSizeY();
        
        
        player1shapex[0] = player1X + player1sizeX;
        player1shapey[0] = player1Y + player1sizeY;
        
        player1shapex[1] = player1X + player1sizeX;
        player1shapey[1] = player1Y - player1sizeY;
        
        player1shapex[2] = player1X - player1sizeX;
        player1shapey[2] = player1Y - player1sizeY;
        
        player1shapex[3] = player1X - player1sizeX;
        player1shapey[3] = player1Y + player1sizeY;
        
        
        
        player2shapex[0] = player2X + player2sizeX;
        player2shapey[0] = player2Y + player2sizeY;
        
        player2shapex[1] = player2X + player2sizeX;
        player2shapey[1] = player2Y - player2sizeY;
        
        player2shapex[2] = player2X - player2sizeX;
        player2shapey[2] = player2Y - player2sizeY;
        
        player2shapex[3] = player2X - player2sizeX;
        player2shapey[3] = player2Y + player2sizeY;
       
        
        player1.setShapeX(player1shapex);
        player1.setShapeY(player1shapey);
        
        player2.setShapeX(player2shapex);
        player2.setShapeY(player2shapey);
        

  
        boolean collisionDetect = collisionHandler.isCollision(player1, player2);
        
        assertTrue(collisionDetect);
 
    }
}

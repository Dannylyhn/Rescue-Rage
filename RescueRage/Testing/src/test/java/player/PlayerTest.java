package player;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.LoadoutPart;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.playersystem.PlayerPlugin;
/**
 *
 * @author danny
 */


public class PlayerTest{ 
    PlayerPlugin playerplugin;
    GameData gamedata;
    World world;
    Entity testPlayer;
    int life;
    float playerX;
    float playerY;

    
    @BeforeEach
    public void setUp(){
        playerplugin = new PlayerPlugin();
        gamedata = new GameData();
        world = new World();
        testPlayer = null;
        life = 0;
        //Random numbers for x and y
        playerX = 1123123;
        playerY = 3123123;
    }
    
    @Test
    @DisplayName("Ensure a player is created")
    public void PlayerCreatedTest() {
        playerplugin.start(gamedata, world);
        for(Entity player : world.getEntities()){ 
        if(player.getClass().getSimpleName().equals("Player")){
            //If Player is found
           // System.out.println("Checking the player object after check: " + testPlayer);
            testPlayer = player;
            // System.out.println(testPlayer);
           }      
        }
        assertNotNull(testPlayer, "A Player was not created");
    }
    
    @Test
    @DisplayName("Testing that an entity of Player is not present without creating it")
    public void PlayerNotCreatedTest(){
        for(Entity player : world.getEntities()){
        if(player.getClass().getSimpleName().equals("Player")){
            //If Player is found
           // System.out.println("Checking the player object after check: " + testPlayer);
            testPlayer = player;
           // System.out.println(testPlayer);
           }      
        }
       // System.out.println("TestPlayer: "+testPlayer);
        assertNull(testPlayer,"A Player is already created");
    }
    
    @Test
    @DisplayName("Checking players health is correct")
    public void LifePartTest(){
        playerplugin.start(gamedata, world);
        for(Entity player : world.getEntities()){
        if(player.getClass().getSimpleName().equals("Player")){
            LifePart lp = player.getPart(LifePart.class);
            life = lp.getLife();
            //System.out.println("Life: " + life);
           }      
        }
       assertEquals(5, life, "Player's health is not 5");
    }
    
    @Test
    @DisplayName("Tests that the Player spawns at the right position")
    public void positionTest(){
        playerplugin.start(gamedata, world);
        for(Entity player : world.getEntities()){
        if(player.getClass().getSimpleName().equals("Player")){
            PositionPart pp = player.getPart(PositionPart.class);
            playerX = pp.getX();
            playerY = pp.getY();
           }      
        }
        //System.out.println("PlayerX: " + playerX + "\n" + "PlayerY: " + playerY);
        assertEquals(0, playerX,"Player x start-position is wrong");
        assertEquals(0, playerY,"Player y start-position is wrong");
    }
   
}
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
import rescuerage.common.data.entityparts.LifePart;
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
    
    @BeforeEach
    public void setUp(){
    playerplugin = new PlayerPlugin();
    gamedata = new GameData();
    world = new World();
    testPlayer = null;
    life = 0;
    }
    
    @Test
    @DisplayName("Ensure a player is created")
    public void testPlayerCreation() {
        playerplugin.start(gamedata, world);
        for(Entity player : world.getEntities()){
              
        if(player.getClass().getSimpleName().equals("Player")){
            //If Player is found
           // System.out.println("Checking the player object after check: " + testPlayer);
            testPlayer = player;

            // System.out.println(testPlayer);
           }      
        }
        assertNotNull(testPlayer);
    }
    
    @Test
    @DisplayName("Testing")
    public void testPlayerNotCreated() {
        for(Entity player : world.getEntities()){
        if(player.getClass().getSimpleName().equals("Player")){
            //If Player is found
           // System.out.println("Checking the player object after check: " + testPlayer);
            testPlayer = player;
           // System.out.println(testPlayer);
           }      
        }
       // System.out.println("TestPlayer: "+testPlayer);
        assertNull(testPlayer);
    }
    
    @Test
    @DisplayName("Checking players health is correct")
    public void LifePart(){
        playerplugin.start(gamedata, world);
        for(Entity player : world.getEntities()){
        if(player.getClass().getSimpleName().equals("Player")){
            LifePart lp = player.getPart(LifePart.class);
            life = lp.getLife();
            //System.out.println("Life: " + life);
           }      
        }
       assertEquals(5, life);
    }
    
    
    /*
    //@RepeatedTest(5)                                    
    @DisplayName("")
    void testingPlayerCreation() {
        
        for(Entity player : world.getEntities()){
        if(player.getClass().getSimpleName().equals("Player")){
            assertNotNull(player);
           }      
        }

    }
*/

   
}
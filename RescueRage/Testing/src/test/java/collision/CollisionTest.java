/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package collision;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
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
    float playerX;
    float playerY;
    

    
    @BeforeEach
    public void setUp(){
    playerplugin = new PlayerPlugin();
    gamedata = new GameData();
    world = new World();
    }
    
    
    @Test
    @DisplayName("Collision testing")
    public void CollisionTest() {
        
        assertNotNull("penis");
    }
}

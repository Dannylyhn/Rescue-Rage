package player;

import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.GameKeys;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.GunCooldownPart;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.LoadoutPart;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.data.entityparts.PlayerMovingPart;
import rescuerage.common.data.entityparts.MovingPart;
import rescuerage.map.MapPlugin;
import rescuerage.playersystem.Player;
import rescuerage.playersystem.PlayerPlugin;
import rescuerage.weapon.WeaponPlugin;
import rescuerage.playersystem.PlayerControlSystem;
/**
 *
 * @author danny
 */


public class PlayerTest{ 
    PlayerPlugin playerplugin;
    PlayerControlSystem playercontrolsystem;
    WeaponPlugin weaponplugin;
    GameData gamedata;
    World world;
    GunCooldownPart gcd;
    Entity testPlayer;
    Entity weapon;
    GunPart gunPart;
    LoadoutPart lp;
        
    int life;
    float playerX;
    float playerY;

    
    @BeforeEach
    public void setUp(){
        playerplugin = new PlayerPlugin();
        playercontrolsystem = new PlayerControlSystem();
        gamedata = new GameData();
        world = new World();
        testPlayer = null;
        life = 0;
        //Random numbers for x and y
        playerX = 1123123;
        playerY = 3123123;
        weapon = null;
        gunPart = null;
        lp = null;
    }
    
    @Test
    @DisplayName("Test: Player is created")
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
    @DisplayName("Test: Player is not present before creation")
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
    @DisplayName("Test: Player health is correct")
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
    @DisplayName("Test: Player spawns at the right position")
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
    
    @Test
    @DisplayName("Test: Player dies test")
    public void PlayerDeathTest(){
        playerplugin.start(gamedata, world);
        world.currentRoom = 1;
        boolean dead = false;
        for(Entity player : world.getEntities()){
            if(player.getClass().getSimpleName().equals("Player")){
                LifePart lp = player.getPart(LifePart.class);
                lp.hit(5);
                testPlayer = player;
                dead = lp.isDead();
           }      
        } 
       assertTrue(dead, "Player is not dead");
    }
    
    
    @Test
    @DisplayName("Test: Player health is decreased correctly")
    public void PlayerDamageTest(){
        playerplugin.start(gamedata, world);
        for(Entity player : world.getEntities()){
            if(player.getClass().getSimpleName().equals("Player")){
                LifePart lp = player.getPart(LifePart.class);
                lp.hit(1);
                life = lp.getLife();
           }      
        }
       assertEquals(4, life, "Player's health is not 4");
    }
    
    
    @Test
    @DisplayName("Test: Player is removed")
    public void PlayerStoppedTest() {
        MapPlugin mp = new MapPlugin();
        mp.start(gamedata, world);
        playerplugin.start(gamedata, world); 
        weapon = new Entity();

        for(Entity player : world.getEntities()){
            if(player.getClass().getSimpleName().equals("Player")){
                testPlayer = player;
                lp = player.getPart(LoadoutPart.class);
                
           } 
        } 
        gunPart = new GunPart("Pistol",1,10000, 10, new float[]{0});
        gcd = new GunCooldownPart(20,5);
        weapon.add(gunPart);
        weapon.add(gcd);
        weapon.add(new PositionPart(0,0,0));
        world.setDefaultWeapon(weapon.getID());
        lp.addWeapon(weapon);
        lp.setCurrentWeapon(weapon);
        gunPart = weapon.getPart(GunPart.class);
        gunPart.setEquipped(true);
        world.getWeapons();
        world.addEntity(weapon);
       
        assertTrue(world.getEntities().contains(testPlayer), "Player is not in the world");
        
        playerplugin.stop(gamedata, world);
        
        assertFalse(world.getEntities().contains(testPlayer), "Player is in the world");
       
       
    }

   
   
}
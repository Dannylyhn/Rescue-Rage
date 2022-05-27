/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package enemy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;


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
import rescuerage.common.data.entityparts.EnemyMovingPart;
import rescuerage.enemysystem.EnemyPlugin;
import rescuerage.map.MapPlugin;

/**
 *
 * @author ander
 */
public class EnemyTest {
    EnemyPlugin enemyplugin;
    MapPlugin mapplugin;
    GameData gamedata;
    World world;
    Entity testEnemy;
    int life;
    float enemyX;
    float enemyY;

    
    @BeforeEach
    public void setUp(){
        enemyplugin = new EnemyPlugin();
        mapplugin = new MapPlugin();
        gamedata = new GameData();
        world = new World();
        mapplugin.start(gamedata, world);
        testEnemy = null;
        life = 0;
        //Random numbers for x and y
        enemyX = 1123123;
        enemyY = 3123123;
    }
    @Test
    @DisplayName("Testing that an entity of Enemy is not present without creating it")
    public void EnemyNotCreatedTest(){
        for(Entity enemy : world.getEntities()){
            if(enemy.getClass().getSimpleName().equals("Enemy")){
               testEnemy = enemy;
           }      
        }
       assertNull(testEnemy,"An enemy is already created");
    }
    
    @Test
    @DisplayName("Ensure a enemy is created")
    public void EnemyCreatedTest() {
        enemyplugin.start(gamedata, world);
        for(Entity enemy : world.getEntities()){ 
            if(enemy.getClass().getSimpleName().equals("Enemy")){
                testEnemy = enemy;
            }
        }
        assertNotNull(testEnemy, "An enemy was not created");
    }
    @Test
    @DisplayName("Ensure a enemy is able to be stopped")
    public void EnemyStoppedTest() {
        enemyplugin.start(gamedata, world);
        enemyplugin.stop(gamedata, world);
        for(Entity enemy : world.getEntities()){
            if(enemy.getClass().getSimpleName().equals("Enemy")){
               testEnemy = enemy;
           }      
        }
       assertNull(testEnemy,"An enemy is still in the game");
    }
    
    @Test
    @DisplayName("Checking enemys health is correct")
    public void LifePartTest(){
        enemyplugin.start(gamedata, world);
        for(Entity enemy : world.getEntities()){
            if(enemy.getClass().getSimpleName().equals("Enemy")){
                LifePart lp = enemy.getPart(LifePart.class);
                life = lp.getLife();
           }      
        }
       assertEquals(3, life, "Enemy's health is not 5");
    }
    
    @Test
    @DisplayName("Checking enemys health is decreased correctly")
    public void EnemyDamageTest(){
        enemyplugin.start(gamedata, world);
        for(Entity enemy : world.getEntities()){
            if(enemy.getClass().getSimpleName().equals("Enemy")){
                LifePart lp = enemy.getPart(LifePart.class);
                lp.hit(1);
                life = lp.getLife();
           }      
        }
       assertEquals(2, life, "Enemy's health is not 5");
    }
    
    @Test
    @DisplayName("Checking enemys health is decreased correctly")
    public void EnemyDeathTest(){
        enemyplugin.start(gamedata, world);
        world.currentRoom = 1;
        Boolean dead = false;
        for(Entity enemy : world.getEntities()){
            if(enemy.getClass().getSimpleName().equals("Enemy")){
                LifePart lp = enemy.getPart(LifePart.class);
                lp.hit(3);
                testEnemy = enemy;
                dead = lp.isDead();
           }      
        }
       assertTrue(dead, "Enemy's not dead");
    }
}

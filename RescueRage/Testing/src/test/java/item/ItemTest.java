/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package item;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.item.ItemPlugin;
import rescuerage.map.MapPlugin;

/**
 *
 * @author ander
 */
public class ItemTest {
    ItemPlugin itemplugin;
    MapPlugin mapplugin;
    GameData gamedata;
    World world;
    Entity testItem;
    int life;
    float itemX;
    float itemY;

    
    @BeforeEach
    public void setUp(){
        itemplugin = new ItemPlugin();
        mapplugin = new MapPlugin();
        gamedata = new GameData();
        world = new World();
        mapplugin.start(gamedata, world);
        testItem = null;
        life = 0;
        //Random numbers for x and y
        itemX = 1123123;
        itemY = 3123123;
    }
    @Test
    @DisplayName("Testing that an entity of Enemy is not present without creating it")
    public void ItemNotCreatedTest(){
        for(Entity item : world.getEntities()){
            if(item.getClass().getSimpleName().equals("Item")){
               testItem = item;
           }      
        }
       assertNull(testItem,"An enemy is already created");
    }
    
    @Test
    @DisplayName("Ensure a enemy is created")
    public void ItemCreatedTest() {
        itemplugin.start(gamedata, world);
        
        for(Entity item : world.getEntities()){ 
            if(item.getClass().getSimpleName().equals("Item")){
                testItem = item;
            }
        }
        assertNotNull(testItem, "An enemy was not created");
    }
    
}

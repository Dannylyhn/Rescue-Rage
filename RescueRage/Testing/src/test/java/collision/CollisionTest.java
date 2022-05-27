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
import rescuerage.common.data.entityparts.*;
import rescuerage.playersystem.PlayerControlSystem;
import rescuerage.playersystem.PlayerPlugin;
import rescuerage.item.ItemPlugin;
import rescuerage.map.MapPlugin;

/**
 *
 * @author danny
 */
public class CollisionTest {

    PlayerPlugin playerplugin;
    ItemPlugin itemplugin;
    MapPlugin mapplugin;
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
        itemplugin = new ItemPlugin();
        mapplugin = new MapPlugin();
        world = new World();
        gamedata = new GameData();
        collisionHandler = new CollisionHandler();
        players = new ArrayList<>();
        positionParts = new ArrayList<>();

    }
    
    
    @Test
    @DisplayName("Test: Collision detection between entities")
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
    
    @Test
    @DisplayName("Test: Collision detection between player and health inc item")
    public void PlayerHelthIncItemTest() {
        mapplugin.start(gamedata, world);
        playerplugin.start(gamedata, world);
        itemplugin.start(gamedata, world);
        Entity player = null;
        for(Entity e : world.getEntities()){
            if(e.getClass().getSimpleName().equals("Player")){
                player = e;
            }
        }
        LifePart lp = player.getPart(LifePart.class);
        PositionPart ppp = player.getPart(PositionPart.class);
        
        Entity item = createItemOfType("healthInc");
        PositionPart ipp = item.getPart(PositionPart.class);
        
        ppp.setPosition(100, 100);
        ipp.setPosition(100, 100);
        
        updateShape(player);
        updateShape(item);
        
        boolean collisionDetect = collisionHandler.isCollision(player, item);
        assertTrue(collisionDetect, "Player and item are not colliding");
        
        // Test that health inc item does not do anything when player has full health
        collisionHandler.itemCollider(item, player, world);
        int life = lp.getLife();
        assertEquals(5, life, "Player health is not 5");
        assertTrue(world.getEntities().contains(item), "Item was removed from the world");
        
        // Test that health inc item does heal the player for 1 and remove the item from the map
        lp.hit(2);
        life = lp.getLife();
        assertEquals(3, life, "Player health is not 3");
        collisionHandler.itemCollider(item, player, world);
        life = lp.getLife();
        assertEquals(4, life, "Player health is not 4");
        assertFalse(world.getEntities().contains(item), "Item was not removed from the world");
    }
    
    @Test
    @DisplayName("Test: Collision detection between player and max helth inc item")
    public void PlayerMaxHelthIncItemTest() {
        mapplugin.start(gamedata, world);
        playerplugin.start(gamedata, world);
        itemplugin.start(gamedata, world);
        Entity player = null;
        for(Entity e : world.getEntities()){
            if(e.getClass().getSimpleName().equals("Player")){
                player = e;
            }
        }
        LifePart lp = player.getPart(LifePart.class);
        PositionPart ppp = player.getPart(PositionPart.class);
        
        Entity item = createItemOfType("maxHealthInc");
        PositionPart ipp = item.getPart(PositionPart.class);
        
        ppp.setPosition(100, 100);
        ipp.setPosition(100, 100);
        
        updateShape(player);
        updateShape(item);
        
        boolean collisionDetect = collisionHandler.isCollision(player, item);
        assertTrue(collisionDetect, "Player and item are not colliding");
        
        // Test that max health inc item does give the player 1 more max health, and fully heal them and remove the item from the map
        lp.hit(2);
        int life = lp.getLife();
        assertEquals(3, life, "Player health is not 3");
        collisionHandler.itemCollider(item, player, world);
        life = lp.getLife();
        assertEquals(6, life, "Player health is not 6");
        assertFalse(world.getEntities().contains(item), "Item was not removed from the world");
    }
    
    private Entity createItemOfType(String type){
        Entity item = itemplugin.createItem();
        /*while(item==null){
            System.out.println("hmm");
            itemplugin.start(gamedata, world);
            for(Entity e : world.getEntities()){
                if(e.getClass().getSimpleName().equals("Item")){
                    item = e;
                }
            }
        }*/
        item.remove(ItemPart.class);
        item.add(new ItemPart(type,1,100));
        return item;
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

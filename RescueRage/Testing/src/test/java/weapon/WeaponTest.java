/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package weapon;

/**
 *
 * @author danny
 */
import static collision.CollisionTest.updateShape;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import rescuerage.collision.CollisionHandler;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.GunCooldownPart;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.data.entityparts.LoadoutPart;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.item.ItemPlugin;
import rescuerage.map.MapPlugin;
import rescuerage.playersystem.PlayerPlugin;
import rescuerage.weapon.Weapon;
import rescuerage.weapon.WeaponPlugin;
import rescuerage.weapon.WeaponControlSystem;


public class WeaponTest {
    PlayerPlugin playerplugin;
    World world;
    GameData gamedata;
    MapPlugin mapplugin;
    WeaponPlugin weaponplugin;
    GunPart gunpart;
    Entity weapon;
    LoadoutPart lp;
    Entity player;
    WeaponControlSystem wcs;
    PositionPart positionpart;
    
    
    ArrayList<Entity> weapons;
    int level;
    
    
    @BeforeEach 
    public void setUp(){
        playerplugin = new PlayerPlugin();
        mapplugin = new MapPlugin();
        weaponplugin = new WeaponPlugin();
        wcs = new WeaponControlSystem();
        world = new World();
        gamedata = new GameData();
        level = 0;
        gunpart = null;
        weapon = null;
        weapons = null;
        lp = null;
        player = null;
        positionpart = null;
        

    }
    
    
    @Test
    @DisplayName("Test: Weapon is created")
    public void WeaponCreatedTest() {
        weaponplugin.start(gamedata, world);
        
        for(Entity weapon : world.getEntities()){
        if(weapon.getClass().getSimpleName().equals("Weapon")){
            
            this.weapon = weapon;
            
           }      
        }

        assertNotNull(weapon, "Weapon does not exist");

    }
    
    @Test
    @DisplayName("Test: Weapon is not created")
    public void WeaponNotCreatedTest() {
   
        for(Entity weapon : world.getEntities()){
        if(weapon.getClass().getSimpleName().equals("Weapon")){
            
            this.weapon = weapon;
            
           }      
        }
        
        assertNull(weapon, "Weapon does not exist");
    }
    
    @Test
    @DisplayName("Test: Remove weapon from world")
    public void WeaponRemoveTest() {
        weaponplugin.start(gamedata, world);
        mapplugin.start(gamedata, world);
        
        for(Entity weapon : world.getEntities()){
        if(weapon.getClass().getSimpleName().equals("Weapon")){
            
            this.weapon = weapon;
            
           }      
        }
        assertTrue(world.getEntities().contains(weapon), "Weapon does not exist");
        weaponplugin.stop(gamedata, world);
        assertFalse(world.getEntities().contains(weapon), "Weapon does exist");

    }
    
    @Test
    @DisplayName("Test: Change weapon")
    public void WeaponChangeWeaponTest() {
        weapons = new ArrayList<>();
        weaponplugin.start(gamedata, world);
        playerplugin.start(gamedata, world);
        
        ArrayList<String> weaponsNames = new ArrayList<>();
        
        for(Entity player : world.getEntities()){
        if(player.getClass().getSimpleName().equals("Player")){
            
            this.player = player;  
            
           }      
        }
        
        //Creates a new weapon
        weaponplugin.createWeapon(0);
   
        for(Entity weapon : world.getEntities()){
        if(weapon.getClass().getSimpleName().equals("Weapon")){
  
            weapons.add(weapon);

            //For getting the names (Not used)
            gunpart = weapon.getPart(GunPart.class);
            weaponsNames.add(gunpart.getName());
            
           }      
        }
        
        lp = player.getPart(LoadoutPart.class);
        
        //Sets the current weapon to the first one
        lp.setCurrentWeapon(weapons.get(0));
        Entity currentWeapon = lp.getCurrentWeapon();
        
        
        //Sets the current weapon to a new one
        lp.setCurrentWeapon(weapons.get(1));
        
        assertNotSame(currentWeapon, lp.getCurrentWeapon());
        

    }
    
    @Test
    @DisplayName("Test: Create specific weapon (shotgun)")
    public void CreateSpecificWeaponTest() {
        weaponplugin.start(gamedata, world);
        playerplugin.start(gamedata, world);
        
        weaponplugin.createWeapon(0);
        String shotgun = "";
  
        for(Entity weapon : world.getEntities()){
        if(weapon.getClass().getSimpleName().equals("Weapon")){
            gunpart = weapon.getPart(GunPart.class);
            if(gunpart.getName().equals("Shotgun")){
                shotgun = gunpart.getName();
            }   
           }      
        }
        
        assertEquals("Shotgun", shotgun);

    }
    
    @Test
    @DisplayName("Test: Reload weapon")
    public void WeaponReloadTest() {
        weaponplugin.start(gamedata, world);
        playerplugin.start(gamedata, world);
       
        
        
        for(Entity weapon : world.getEntities(Weapon.class)){
        if(weapon.getClass().getSimpleName().equals("Weapon")){
            
            this.weapon = weapon;
            
           }      
        }
        
        positionpart = weapon.getPart(PositionPart.class);
        
        GunCooldownPart gunCD = weapon.getPart(GunCooldownPart.class);
        
        
        gunpart = weapon.getPart(GunPart.class);
     
        int ammo = gunpart.getMagazine();
        
        
        for(int i = 0; i<5; i++){
            gunpart.minusMagazine();
        }
        

        
        //Checks that magazine is decreased to 5
        assertEquals(5, gunpart.getMagazine(), "Magazine for pistol is not 5");  
        
        wcs.reload(gunpart);
       
        //Checks magazine is fully reloaded
        assertEquals(gunpart.getMagazineLength(), gunpart.getMagazine(), "Magazine is not full (5)"); 

    }
    
    
 
    
    
    
    
    

}
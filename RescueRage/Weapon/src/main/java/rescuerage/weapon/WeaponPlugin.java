/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.weapon;
import rescuerage.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.data.entityparts.PositionPart;

/**
 *
 * @author dan
 */
@ServiceProviders(value = {
        @ServiceProvider(service = IGamePluginService.class),})
public class WeaponPlugin implements IGamePluginService {
    
    private Entity weapon;

    @Override
    public void start(GameData gameData, World world) {
        int max = world.getHouseH()*world.getHouseW()-1;
        int min = 1;
        int range = max - min + 1;
        
        
        //Gunpart = Bulletspershot, ammo, spraypattern
        weapon = createWeapon(gameData, new GunPart(1,10000, new float[]{0}));
        world.setDefaultWeapon(weapon.getID());
        world.addEntity(weapon);
        
        weapon = createWeapon(gameData, new GunPart(3,10000, new float[]{-6,6,6}));
        world.addEntityInRoom(weapon, generateRandom(min,max,range));
        world.addEntity(weapon);

        
        weapon = createWeapon(gameData, new GunPart(6,10000, new float[]{-9,9,9,9,9,9}));
        world.addEntityInRoom(weapon, generateRandom(min,max,range));
        world.addEntity(weapon);

    }
    
    private Entity createWeapon(GameData gameData, GunPart gunPart)
    { 
        float x = 60;
        float y = 60;
        float radians = 3.1415f / 2;
       
        Entity weapon = new Weapon();
        weapon.setRadius(8);
        
        weapon.add(gunPart);
        weapon.add(new PositionPart(x,y,radians));
        
        return weapon;
    }

    @Override
    public void stop(GameData gameData, World world) {
    }
    
    public int generateRandom(int min, int max, int range)
    {
        int rand = (int)(Math.random()*range) + min;
        return rand;
    }
}

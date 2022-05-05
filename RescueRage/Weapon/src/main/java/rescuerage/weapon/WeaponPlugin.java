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
import rescuerage.common.data.entityparts.GunCooldownPart;
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
        //Gunpart = Bulletspershot, ammo, spraypattern
        weapon = createWeapon(gameData, new GunPart("Pistol",1,10000, 10, new float[]{0}));
        GunCooldownPart gcd = new GunCooldownPart(20,5);
        weapon.add(gcd);
        world.addEntity(weapon);
        world.setDefaultWeapon(weapon.getID());
        
        weapon = createWeapon(gameData, new GunPart("Shotgun",3,10000, 10, new float[]{-6,6,6}));
        weapon.add(gcd);
        world.addEntityInRoom(weapon, 0);
        world.addEntity(weapon);
        
        weapon = createWeapon(gameData, new GunPart("Boomerang",6,10000, 10, new float[]{-9,9,9,9,9,9}));
        weapon.add(gcd);
        world.addEntityInRoom(weapon, 0);
        world.addEntity(weapon);
    }
    
    private Entity createWeapon(GameData gameData, GunPart gunPart)
    { 
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
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
}

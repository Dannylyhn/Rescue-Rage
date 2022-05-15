/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.weapon;
import java.util.ArrayList;
import java.util.Random;
import org.openide.util.Exceptions;
import rescuerage.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.GunCooldownPart;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.data.entityparts.LoadoutPart;
import rescuerage.common.data.entityparts.PositionPart;

/**
 *
 * @author dan
 */
@ServiceProviders(value = {
        @ServiceProvider(service = IGamePluginService.class),})
public class WeaponPlugin implements IGamePluginService {
    
    private ArrayList<Entity> weapons = new ArrayList<>();
    Random random = new Random();
    private Entity weapon;
    private int level;
    private World world;
    private GameData gameData;
    private GunPart[] gunList = {
        new GunPart("Shotgun",3,10000, 10, new float[]{-6,6,6}),
        new GunPart("Boomerang",6,10000, 10, new float[]{-9,9,9,9,9,9})
    };

    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        this.gameData = gameData;
        this.level = 1;
        //Gunpart = Bulletspershot, ammo, spraypattern
        GunPart gp = new GunPart("Pistol",1,10000, 10, new float[]{0});
        gp.setEquipped(true);
        weapon = createBaseWeapon(gp);

        //GunCooldownPart gcd = new GunCooldownPart(20,5);
        //weapon.add(gcd);
        if(world.getPlayerID() != "")
        {
            world.setDefaultWeapon(weapon.getID());
            Entity player = world.getEntity(world.getPlayerID());
            LoadoutPart lp = player.getPart(LoadoutPart.class);
            lp.addWeapon(weapon);
            lp.setCurrentWeapon(weapon);
        }
        world.addEntity(weapon);
        /*
        //weapon = createWeapon(gameData, new GunPart("Shotgun",3,10000, 10, new float[]{-6,6,6}));
        weapon = createWeapon();
        //weapon.add(gcd);
        if(world.getLevel().size() > 0)
        {
            world.addEntityInRoom(weapon, 0);
        }
        world.addEntity(weapon);   
        System.out.println("size: " + world.getLevel().size());
        weapon = createWeapon();
        //weapon.add(gcd);
        if(world.getLevel().size() > 0)
        {
            world.addEntityInRoom(weapon, 0);
        }*/
        //world.addEntity(weapon);   
    }
    private Entity createBaseWeapon(GunPart gp){
        Entity weapon = new Weapon();
        /*weapon.setRadius(24);
        weapon.setSizeX(24);
        weapon.setSizeY(24);*/
        //int rand = random.nextInt(gunList.length);
        //GunPart gunPart = gunList[rand];
        weapon.add(gp);
        //weapon.add(new PositionPart(x,y,radians));
        weapon.add(new PositionPart(0,0,0));
        setShape(weapon);
        GunCooldownPart gcd = new GunCooldownPart(20,5);
        weapon.add(gcd);
        
        weapons.add(weapon);
        return weapon;
    }
    public void createWeaponsInLevel(){
        //Random random = new Random();
        /*for (int i = 0; i < world.getHouseRooms().size(); i++){
            int r = random.nextInt(5+level);
            //while(r<5+level){
            for(int j = 0; j < r+level; j++){
            //for(int j = 0; j < 1; j++){
                createWeaponInRoomIndex(i);
            }
        }*/
        createWeaponInRoomIndex(0,0);
        createWeaponInRoomIndex(0,1);
    }
    private void createWeaponInRoomIndex(int index, int i){
        world.addEntityInRoom(createWeapon(i), index);
    }
    
    private Entity createWeapon(int i)
    { 
        Entity weapon = new Weapon();
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;
        weapon.setRadius(24);
        weapon.setSizeX(24);
        weapon.setSizeY(24);
        int rand = random.nextInt(gunList.length);
        GunPart gunPart = null;
        switch(i){
            case 0:
                gunPart = new GunPart("Shotgun",3,10000, 10, new float[]{-6,6,6});
                break;
            case 1:
                gunPart = new GunPart("Boomerang",6,10000, 10, new float[]{-9,9,9,9,9,9});
                break;
        }
        //GunPart gunPart = gunList[rand];
        weapon.add(gunPart);
        //weapon.add(new PositionPart(x,y,radians));
        weapon.add(new PositionPart(0,0,0));
        setShape(weapon);
        GunCooldownPart gcd = new GunCooldownPart(20,5);
        weapon.add(gcd);
        weapons.add(weapon);
        world.addEntity(weapon);  
        return weapon;
    }
    private void setShape(Entity entity) {
        
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

    @Override
    public void stop(GameData gameData, World world) {
        //Remove all weapons from our maps. Set default weapon id to empty string
        for(int i = 0;i<weapons.size();i++){
            world.removeEntity(weapons.get(i));
        }
        world.setDefaultWeapon("");

        if(world.getPlayerID() != "")
        {
            Entity player = world.getEntity(world.getPlayerID());
            LoadoutPart lp = player.getPart(LoadoutPart.class);
            lp.getWeapons().clear();
        }
        
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.weapon;


import rescuerage.commonbullet.BulletSPI;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.services.IEntityProcessingService;
import rescuerage.common.data.GameKeys;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import rescuerage.common.data.entityparts.GunCooldownPart;
import static rescuerage.core.main.Sounds.shootSound;
import static rescuerage.core.main.Sounds.reloadSound;

/**
 *
 * @author dan
 */
@ServiceProviders(value = {
        @ServiceProvider(service = IEntityProcessingService.class)})
public class WeaponControlSystem implements IEntityProcessingService {
    private int level = 0;
    @Override
    public void process(GameData gameData, World world) {
        if(level!=world.level){
            //System.out.println("method call");
            Lookup.getDefault().lookup(WeaponPlugin.class).createWeaponsInLevel();
            level = world.level;
            //moveCount = world.tileSize * world.getEntities(Enemy.class).size();
        }
        
        
        
        for(Entity weapon : world.getEntities(Weapon.class))
        {
            
            PositionPart positionPart = weapon.getPart(PositionPart.class);
            GunPart gunPart = weapon.getPart(GunPart.class);
            GunCooldownPart gunCD = weapon.getPart(GunCooldownPart.class);
            
            gunCD.process(gameData, weapon);
            //Needs to be equipped and have ammo before it can shoot. 
            if(gunPart.equipped == true && gameData.getKeys().isDown(GameKeys.LEFTCLICK) && gunPart.getMagazine()!= 0)
            {
                if(gunCD.getCurrentShootingCD()<=0)
                {
                    shoot(weapon, gameData, world);
                    gunPart.minusMagazine();
                    gunCD.setCurrentShootingCD(gunCD.getShootingCD());
                    gunCD.setShot(true);
                }
            }
            
            if(gameData.getKeys().isDown(GameKeys.R) && gunPart.getAmmo()!=0 && gunPart.getMagazine()<10)
            {   
                    int reloadedAmount = gunPart.getMagazineLength()-gunPart.getMagazine();
                    gunPart.minusAmmo(reloadedAmount);
                    reloadSound();
                    gunPart.setMagazine(gunPart.getMagazineLength());
            }
            
            positionPart.process(gameData, weapon);
            
//            if(gunPart.pickedUp==false)
//            {
//                updateShape(weapon);       
//            }
            updateShape(weapon);       
        }
    }
    
    //The spray pattern thats called for each left click event
    private void shoot(Entity weapon, GameData gameData, World world)
    {
        //Game gameobj = new Game();
        GunPart gunPart = weapon.getPart(GunPart.class);
        PositionPart positionPart = weapon.getPart(PositionPart.class);

        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();
        float radius = weapon.getRadius();
        
        for(int i = 0 ; i < gunPart.bulletsPerShot ; i++)
        {
            radians = radians + gunPart.getSprayPattern()[i];
            shootSound();
            Entity bullet = Lookup.getDefault().lookup(BulletSPI.class).createBullet(x, y, radians, radius, gameData);      
            world.addEntity(bullet);
            world.getLevel().get(world.currentRoom).put(world.addEntity(bullet), bullet);
//            if(Lookup.getDefault().lookup(BulletSPI.class) != null)
//            {
//                Entity bullet = Lookup.getDefault().lookup(BulletSPI.class).createBullet(x, y, radians, radius, gameData);      
//                world.addEntity(bullet);
//                world.getLevel().get(world.currentRoom).put(world.addEntity(bullet), bullet);
//            }
        }
    }
    
    private void updateShape(Entity entity) {
        
        float[] shapex = new float[4];
        float[] shapey = new float[4];
        PositionPart positionPart = entity.getPart(PositionPart.class);
        float x = positionPart.getX();
        float y = positionPart.getY();
        float radians = positionPart.getRadians();

        shapex[0] = (float) (x + Math.cos(radians) * entity.getRadius());
        shapey[0] = (float) (y + Math.sin(radians) * entity.getRadius());

        shapex[1] = (float) (x + Math.cos(radians - 4 * 3.1415f / 5) * entity.getRadius());
        shapey[1] = (float) (y + Math.sin(radians - 4 * 3.1145f / 5) * entity.getRadius());

        shapex[2] = (float) (x + Math.cos(radians + 3.1415f) * entity.getRadius() * 0.5);
        shapey[2] = (float) (y + Math.sin(radians + 3.1415f) * entity.getRadius() * 0.5);

        shapex[3] = (float) (x + Math.cos(radians + 4 * 3.1415f / 5) * entity.getRadius());
        shapey[3] = (float) (y + Math.sin(radians + 4 * 3.1415f / 5) * entity.getRadius());

        entity.setShapeX(shapex);
        entity.setShapeY(shapey);
    }
}

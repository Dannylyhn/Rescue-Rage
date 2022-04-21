package rescuerage.playersystem;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.MovingPart;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import org.openide.util.lookup.ServiceProviders;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.data.entityparts.LoadoutPart;
import rescuerage.common.data.entityparts.PlayerMovingPart;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {

        // Add entity to the world
        player = createPlayerShip(gameData);
        
        // Hardcoding weapons for now until we add collision.
        LoadoutPart lp = player.getPart(LoadoutPart.class);
        for(String weaponID : world.getWeapons())
        {
            Entity weapon = world.getEntity(weaponID);
            lp.addWeapon(weapon);
        }
        
        // First weapon is current equipped weapon.
        Entity firstWeapon = world.getEntity(world.getWeapons()[0]);
        lp.setCurrentWeapon(firstWeapon);
        GunPart gunPart = firstWeapon.getPart(GunPart.class);
        gunPart.setEquipped(true);
        
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        float maxSpeed = 50;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 20;
        float y = gameData.getDisplayHeight() / 20;
        float radians = 3.1415f / 2;

        Entity playerShip = new Player();
        playerShip.setRadius(8);
        playerShip.add(new PlayerMovingPart(maxSpeed));
        playerShip.add(new PositionPart(x, y, radians));
        playerShip.add(new LifePart(1));
        playerShip.add(new LoadoutPart());
        
        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

}

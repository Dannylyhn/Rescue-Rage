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
import rescuerage.common.data.entityparts.InventoryPart;
import rescuerage.common.data.entityparts.LoadoutPart;
import rescuerage.common.data.entityparts.PlayerMovingPart;

@ServiceProviders(value = {
    @ServiceProvider(service = IGamePluginService.class),})
public class PlayerPlugin implements IGamePluginService {

    private Entity player;
    private World world;

    public PlayerPlugin() {
    }

    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        // Add entity to the world
        player = createPlayerShip(gameData);
        world.setPlayerID(player.getID());
        
        LoadoutPart lp = player.getPart(LoadoutPart.class);
        
        // Add default weapon
        if(!world.getDefaultWeapon().equals(""))
        {
            Entity defaultWeapon = world.getEntity(world.getDefaultWeapon());
            lp.addWeapon(defaultWeapon);

            //Set defualt weapon to the currently equipped weapon. 
            lp.setCurrentWeapon(defaultWeapon);
            GunPart gunPart = defaultWeapon.getPart(GunPart.class);
            gunPart.setEquipped(true);   
        }

        
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {

        float maxSpeed = 100;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 20;
        float y = gameData.getDisplayHeight() / 20;
        float radians = 3.1415f / 2;

        Entity playerShip = new Player();
        playerShip.setRadius(world.tileSize/2);
        playerShip.setSizeX(world.tileSize/2);
        playerShip.setSizeY(world.tileSize/2);
        
        playerShip.setRadius(8);
        playerShip.add(new PlayerMovingPart(maxSpeed));
        playerShip.add(new PositionPart(x, y, radians));
        LifePart lp = new LifePart(5);
        lp.setIsPlayer();
        playerShip.add(lp);
        playerShip.add(new LoadoutPart());
        playerShip.add(new InventoryPart());
        
        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        world.removeEntity(player);
    }

}

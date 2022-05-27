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
        player = createPlayer(gameData);
        world.setPlayerID(player.getID());
        
        LoadoutPart lp = player.getPart(LoadoutPart.class);
        
//         Add default weapon
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

    private Entity createPlayer(GameData gameData) {

        float maxSpeed = 100;
        float rotationSpeed = 5;
        float x = gameData.getDisplayWidth() / 2;
        float y = gameData.getDisplayHeight() / 2;
        float radians = 3.1415f / 2;

        Entity player = new Player();
        player.setRadius(world.tileSize/2);
        player.setSizeX(world.tileSize/2);
        player.setSizeY(world.tileSize/2);
        
        player.setRadius(world.tileSize/2);
        player.add(new PlayerMovingPart(maxSpeed));
        player.add(new PositionPart(x, y, radians));
        LifePart lp = new LifePart(5);
        lp.setIsPlayer();
        player.add(lp);
        player.add(new LoadoutPart());
        player.add(new InventoryPart());
        
        return player;
    }

    @Override
    public void stop(GameData gameData, World world) {
        LoadoutPart lp = player.getPart(LoadoutPart.class);
        Entity currentWeapon = lp.getCurrentWeapon();
        GunPart gunPart = currentWeapon.getPart(GunPart.class);
        gunPart.setEquipped(false);
        // Remove entities
        world.setPlayerID("");
        world.removeEntity(player);
    }

}

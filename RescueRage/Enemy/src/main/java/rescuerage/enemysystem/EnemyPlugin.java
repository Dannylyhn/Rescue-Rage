package rescuerage.enemysystem;

import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.MovingPart;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.services.IGamePluginService;
import java.util.Random;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IGamePluginService.class)
public class EnemyPlugin implements IGamePluginService {
   
    /*
    private Entity enemy;
    private Entity enemy2;
    */
    private ArrayList<Entity> enemies = new ArrayList<>();
 

    @Override
    public void start(GameData gameData, World world) {
        int amountOfEnemies = 50;
        // Add entities to the world
        
        for(int i  = 0; i<amountOfEnemies;i++){
            enemies.add(createEnemyShip(gameData));
        }
        
        
        /*
        enemy = createEnemyShip(gameData);
        enemy2 = createEnemyShip(gameData);
        */
        
      /*
            world.addEntity(enemy);
            world.addEntity(enemy2);
      */
      
      for(int i = 0;i<enemies.size();i++){
        world.addEntity(enemies.get(i));   
      }
     
    }

    private Entity createEnemyShip(GameData gameData) {

     
        float maxSpeed = 20;

        
        
        //Declares the position of the ship
        float x = new Random().nextFloat() * gameData.getDisplayWidth();
        float y = new Random().nextFloat() * gameData.getDisplayHeight();
        float radians = 3.1415f / 2;

        
        float[] colour = new float[4];
        colour[0] = 1.0f;
        colour[1] = 0.0f;
        colour[2] = 0.0f;
        colour[3] = 1.0f;

        Entity enemyShip = new Enemy();
        enemyShip.setRadius(8);
        enemyShip.add(new MovingPart(maxSpeed));
        enemyShip.add(new PositionPart(x, y, radians));
     

        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        //world.removeEntity(enemy);
        for(int i = 0;i<enemies.size();i++){
            world.removeEntity(enemies.get(i));
        }
    }
    

}

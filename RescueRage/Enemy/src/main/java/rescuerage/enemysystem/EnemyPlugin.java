package rescuerage.enemysystem;

//import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.EnemyMovingPart;
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
    
    private int houseH;
    private int houseW;
    private int roomH = 9; // must be uneven number, because the doors are being set at the center of the wall
    private int roomW = 11; // must be uneven number, because the doors are being set at the center of the wall
    private int tileSize = 16; // must be even number, because the top and right rows are depending on it
    private int WIDTH = roomW * tileSize;
    private int HEIGHT = roomH * tileSize;
    private int level;
    private World world;
    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        this.level = 0;
        createEnemiesInLevel();
        
        //int amountOfEnemies = 50;
        // Add entities to the world
        /*
        for(int i  = 0; i<amountOfEnemies;i++){
            enemies.add(createEnemyShip(gameData));
        }
        */
        
        /*
        enemy = createEnemyShip(gameData);
        enemy2 = createEnemyShip(gameData);
        */
        
      /*
            world.addEntity(enemy);
            world.addEntity(enemy2);
      */
      /*
      for(int i = 0;i<enemies.size();i++){
        world.addEntity(enemies.get(i));   
      }
     */
    }
    
    private void initLevel(){
        this.world = world;
        this.houseH = world.houseH;
        this.houseW = world.houseW + 1;
        this.roomH = world.roomH;
        this.roomW = world.roomW;
        this.tileSize = world.tileSize;
    }
    
    public void createEnemiesInLevel(){
        /*if (level != world.level){
            level = world.level;
        }*/
        //world.clearRoomMap();
        initLevel();
        //initLevel();
        // starting area
        
        //createHouse();
        
        //createBoxInRoomIndex(4);
        createEneymyInRoomIndex(3);
        createEneymyInRoomIndex(0);
        createEnemyInRoomBossArea();
        // end area
        
    }
    
    private void createEneymyInRoomIndex(int index){
        world.addEntityInRoom(createEnemy(), index);
    }
    private void createEnemyInRoomBossArea(){
        world.addEntityInBossArea(createEnemy());
    }
    private Entity createEnemy(){
        Entity enemy = new Enemy();
        /*
        enemy.setRadius(tileSize/2);
        enemy.setSizeX(tileSize/2);
        enemy.setSizeY(tileSize/2);
        */
        float maxSpeed = world.tileSize;
        enemy.setRadius(8);
        enemy.add(new EnemyMovingPart(maxSpeed));
        //System.out.println("x: " + x);
        //System.out.println("y: " + y);
        enemy.add(new PositionPart(0,0,0));
        enemy.add(new LifePart(3));
        
        world.addEntity(enemy);
        //addEntity(map, roomEntityMap);
        return enemy;
    }
    
    private Entity createEnemyShip(GameData gameData) {
    
        float maxSpeed = 20;

        
        
        //Declares the position of the ship
        //float x = new Random().nextFloat() * gameData.getDisplayWidth();
        //float y = new Random().nextFloat() * gameData.getDisplayHeight();
        //float radians = 3.1415f / 2;

        

        Entity enemyShip = new Enemy();
        enemyShip.setRadius(8);
        enemyShip.add(new EnemyMovingPart(maxSpeed));
        //enemyShip.add(new PositionPart(x, y, radians));
     

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

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
    private int roomH = 19; // must be uneven number, because the doors are being set at the center of the wall
    private int roomW = 23; // must be uneven number, because the doors are being set at the center of the wall
    private int tileSize = 48; // must be even number, because the top and right rows are depending on it
    private int WIDTH = roomW * tileSize;
    private int HEIGHT = roomH * tileSize;
    private int level;
    private World world;
    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        this.level = 1;
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
        Random random = new Random();
        for (int i = 0; i < world.getHouseRooms().size(); i++){
            int r = random.nextInt(5+level);
            //while(r<5+level){
            for(int j = 0; j < r+level; j++){
            //for(int j = 0; j < 1; j++){
                createEneymyInRoomIndex(i);
            }
        }
        //initLevel();
        // starting area
        
        //createHouse();
        
        //createBoxInRoomIndex(4);
        /*createEneymyInRoomIndex(3);
        createEneymyInRoomIndex(0);*/
        createEnemyInRoomBossArea();
        createEnemyInRoomBossArea();
        createEnemyInRoomBossArea();
        createEnemyInRoomBossArea();
        createEnemyInRoomBossArea();
        // end area
        
    }
    
    private void createEneymyInRoomIndex(int index){
        world.addEntityInRoom(createEnemy(index+2), index);
    }
    private void createEnemyInRoomBossArea(){
        world.addEntityInBossArea(createEnemy(1));
    }
    private Entity createEnemy(int roomNR){
        Entity enemy = new Enemy();
        
        enemy.setRadius(tileSize/2);
        enemy.setSizeX(tileSize/2);
        enemy.setSizeY(tileSize/2);
        
        float maxSpeed = world.tileSize;
        enemy.setRadius(tileSize/2);
        enemy.add(new EnemyMovingPart(maxSpeed, roomNR, world.getLevel().get(roomNR)));
        //System.out.println("x: " + x);
        //System.out.println("y: " + y);
        enemy.add(new PositionPart(0,0,0));
        enemy.add(new LifePart(3));
        setShape(enemy);
        
        world.addEntity(enemy);
        //addEntity(map, roomEntityMap);
        return enemy;
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
    /*
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
*/
    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        //world.removeEntity(enemy);
        for(int i = 0;i<enemies.size();i++){
            world.removeEntity(enemies.get(i));
        }
    }
    

}

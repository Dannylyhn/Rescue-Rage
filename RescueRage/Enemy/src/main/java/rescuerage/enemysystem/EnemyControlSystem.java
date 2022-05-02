package rescuerage.enemysystem;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.data.entityparts.EnemyMovingPart;
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.services.IEntityProcessingService;
import java.util.Random;
import org.openide.util.Lookup;
import org.openide.util.lookup.ServiceProvider;

@ServiceProvider(service = IEntityProcessingService.class)
public class EnemyControlSystem implements IEntityProcessingService {

    private int level = 1;
    //private int enemyCount = 0;
    //private int moveCount = 0;
    private boolean up = true;
    private float playerX = -1;
    private float playerY = -1;
    
    @Override
    public void process(GameData gameData, World world) {
        if(level!=world.level){
            Lookup.getDefault().lookup(EnemyPlugin.class).createEnemiesInLevel();
            level = world.level;
            //moveCount = world.tileSize * world.getEntities(Enemy.class).size();
        }
        
        playerX = world.getPlayerPositionPart().getX();
        playerY = world.getPlayerPositionPart().getY();
        
        /*
        if(enemyCount != world.getEntities(Enemy.class).size()){
            enemyCount = world.getEntities(Enemy.class).size();
            if(moveCount>world.tileSize)
                moveCount = moveCount - world.tileSize;
        }
        if(moveCount == 0){
            moveCount = world.tileSize * 2 * enemyCount;
            up = !up;
        }
        */
        
        //int playerTile = 0;
        int playerTile = (int)playerX/world.tileSize;
        playerTile = playerTile * ((int)playerY/world.tileSize);

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            EnemyMovingPart movingPart = enemy.getPart(EnemyMovingPart.class);
            
            movingPart.setPlayerTile(playerTile, world.currentRoom, (int)playerX, (int)playerY);

            if(movingPart.newTile == false){
                if(movingPart.path.size()>0){
                    String move = movingPart.path.get(0);
                    //System.out.println("path 0 : " + move);
                    if(move!=null){
                        switch (move) {
                            case "up":
                                movingPart.setUp(true);
                                break;
                            case "down":
                                movingPart.setDown(true);
                                break;
                            case "left":
                                movingPart.setLeft(true);
                                break;
                            case "right":
                                movingPart.setRight(true);
                                break;
                            case "upleft":
                                movingPart.setUpLeft(true);
                                break;
                            case "upright":
                                movingPart.setUpRight(true);
                                break;
                            case "downleft":
                                movingPart.setDownLeft(true);
                                break;
                            case "downright":
                                movingPart.setDownRight(true);
                                break;
                            default:
                                break;
                        }
                    }
                }
                /*if(up){
                    movingPart.setUp(true);
                    System.out.println("up");
                }
                else{
                    movingPart.setDownLeft(true);
                    System.out.println("!up");
                }*/
            }
            else{
                /*up = !up;*/
                if(movingPart.path.size()>0){
                    movingPart.path.remove(0);
                }
                movingPart.newTile = false;
            }
            //System.out.println("moveCount: " + moveCount);
            /*
            if (rng > 0 && rng < 1000) {
                movingPart.setUp(true);
                movingPart.setRight(false);
                movingPart.setLeft(false);           
                movingPart.setDown(false);
            }

            if (rng > 1000 && rng < 3000) {
                movingPart.setLeft(true);
                movingPart.setRight(false);
                movingPart.setUp(false);
                movingPart.setDown(false);
            }

            if (rng > 3000 && rng < 6000) {
                movingPart.setRight(true);
                movingPart.setLeft(false);
                movingPart.setUp(false);
                movingPart.setDown(false);
            }
            
            if (rng > 6000 && rng < 10000) {
                movingPart.setDown(true);
                movingPart.setRight(false);
                movingPart.setLeft(false);
                movingPart.setUp(false);
         
            }
            */
            
           

            movingPart.process(gameData, enemy);
            positionPart.process(gameData, enemy);
            
            
            PositionPart playerPart = world.getPlayerPositionPart();

            //Vector3 playerPos = new Vector3(playerPart.getX(), playerPart.getY(), 0);
            //radians = (float)Math.atan2(positionPart.y - playerPos.y, positionPart.x - playerPos.x);
            float degree = (float)Math.atan2( playerPart.getY() - positionPart.getY(), playerPart.getX() - positionPart.getX());
            positionPart.setRadians(degree);
            //positionPart.setRadians(radians);


            updateShape(enemy);

            movingPart.setRight(false);
            movingPart.setLeft(false);
            movingPart.setUp(false);
            movingPart.setDown(false);
            movingPart.setUpLeft(false);
            movingPart.setUpRight(false);
            movingPart.setDownLeft(false);
            movingPart.setDownRight(false);
            
            //System.out.println("Post move | x: " + positionPart.getX() + " | y: " + positionPart.getY() + "\n");
        }
    }

    private void updateShape(Entity entity) {
        /*
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
        */
        
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
}

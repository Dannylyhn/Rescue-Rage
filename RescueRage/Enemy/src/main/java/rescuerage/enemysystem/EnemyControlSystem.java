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
    @Override
    public void process(GameData gameData, World world) {
        if(level!=world.level){
            //MapPlugin.createLevel();
            //world.clearRoomMap();
            Lookup.getDefault().lookup(EnemyPlugin.class).createEnemiesInLevel();
            //Entity bullet = Lookup.getDefault().lookup(BulletSPI.class).createBullet(x, y, radians, radius, gameData);
            level = world.level;
        }

        for (Entity enemy : world.getEntities(Enemy.class)) {
            PositionPart positionPart = enemy.getPart(PositionPart.class);
            EnemyMovingPart movingPart = enemy.getPart(EnemyMovingPart.class);
            

            Random rand = new Random();

            int rng = rand.nextInt(10000);
            System.out.println(rng);
            
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

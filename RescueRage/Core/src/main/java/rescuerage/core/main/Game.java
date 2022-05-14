package rescuerage.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import java.util.ArrayList;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.services.IEntityProcessingService;
import rescuerage.common.services.IGamePluginService;
import rescuerage.common.services.IPostEntityProcessingService;
import rescuerage.core.managers.GameInputProcessor;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import org.openide.util.Lookup;
import org.openide.util.LookupEvent;
import org.openide.util.LookupListener;
import rescuerage.common.data.entityparts.GunPart;
import rescuerage.common.data.entityparts.InventoryPart;
import rescuerage.common.data.entityparts.ItemPart;
import rescuerage.common.data.entityparts.LifePart;
import rescuerage.common.data.entityparts.TilePart;
import rescuerage.common.data.entityparts.PositionPart;


public class Game implements ApplicationListener {

    private static OrthographicCamera cam;
    private ShapeRenderer sr;
    SpriteBatch batch;
    Sprite sprite;
    //SpriteBatch wSprite;
    //SpriteBatch fSprite;
    Texture playerSprite;
    Texture boxSprite;
    Texture wallSprite;
    Texture floorSprite;
    Texture enemySprite;
    Texture keySprite;
    Texture healthSprite;
    Texture chestSprite;
    Texture bulletSprite;
    BitmapFont font;
    private final Lookup lookup = Lookup.getDefault();
    private final GameData gameData = new GameData();
    private World world = World.getInstance();
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;
    private Entity player;
    private PositionPart positionPart = null;
    private float radians;
    private float shiftX = 0;
    private float shiftY = 0;

    @Override
    public void create() {
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        batch = new SpriteBatch();
        font = new BitmapFont();
        //cam.translate(gameData.getDisplayWidth(), gameData.getDisplayHeight());
        cam.update();
        
        
        //---------------------------------------------
         playerSprite = new Texture("assets/images/PlayerSprite.png");
         boxSprite = new Texture(Gdx.files.internal("assets/images/box.png"));
         wallSprite = new Texture(Gdx.files.internal("assets/images/wall.png"));
         floorSprite = new Texture(Gdx.files.internal("assets/images/floor.png"));
         enemySprite = new Texture(Gdx.files.internal("assets/images/enemySprite.png"));
         keySprite = new Texture(Gdx.files.internal("assets/images/key.png"));
         healthSprite = new Texture(Gdx.files.internal("assets/images/health.png"));
         chestSprite = new Texture(Gdx.files.internal("assets/images/chest.png"));
         bulletSprite = new Texture(Gdx.files.internal("assets/images/bullet2.png"));
         //new Texture(Gdx.files.internal("E:\\Mit drev\\software engineering\\Semester 4\\PRO\\PersonalTesting\\AsteroidsProTesting\\core\\assets\\player.png"));
        //For the pokemon guy
        // sprite = new Sprite(img, 64, 64);
        //For the soldier
         sprite = new Sprite(playerSprite);
         //wSprite = new SpriteBatch(wallSprite);
         batch = new SpriteBatch();
         
         //---------------------------------------------

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
        
        //We need the player entity 
        if(!world.getPlayerID().equals(""))
        {
            player = world.getEntity(world.getPlayerID());
            positionPart = player.getPart(PositionPart.class);
        }

    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        
        
      
        
        
       
        
        if(positionPart != null)
        {
            PositionPart playerPosPart = world.getPlayerPositionPart();
            //Sets camera position center to player
            cam.position.x = playerPosPart.getX();
            cam.position.y = playerPosPart.getY();
            cam.update();
            //System.out.println("CamX: " + cam.position.x + " CamY:" + cam.position.y);
            //System.out.println(cam.position);

            //Rotates player to the cursor
            Vector3 mousePos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
            Vector3 playerPos = new Vector3(playerPosPart.getX(), playerPosPart.getY(), 0);
            radians = (float)Math.atan2(mousePos.y - playerPos.y, mousePos.x - playerPos.x);
            playerPosPart.setRadians(radians);   
            
            shiftX = positionPart.getX()-(gameData.getDisplayWidth()/2);
            shiftY = positionPart.getY()-(gameData.getDisplayHeight()/2);
        }
        
        
        //Code down below is for drawing the sprite, setting the position and rotation
        if(!world.getPlayerID().equals(""))
        {
        
       
        float PlayerSpriteX = (gameData.getDisplayWidth()/2);
        float PlayerSpriteY = (gameData.getDisplayHeight()/2);
        sprite.setPosition(PlayerSpriteX-47, PlayerSpriteY-47);
       // sprite.setSize(60, 60);
        float xInput = Gdx.input.getX();
        float yInput = (Gdx.graphics.getHeight() - Gdx.input.getY());
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - PlayerSpriteY, xInput - PlayerSpriteX);
      

        if(angle < 0){
           angle += 360;
          }
        sprite.setRotation(angle);
        
        }else{
          //If player is not in, it should remove the picture
           playerSprite = new Texture("assets/images/Empty.png");
           sprite = new Sprite(playerSprite);
        }
        
        
        update();
        draw();
        batch.begin();
        sprite.draw(batch);
        batch.end();
        //postUpdate();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }
    private void postUpdate(){
        // Post Update
        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
            postEntityProcessorService.process(gameData, world);
        }
    }

    private void draw() {
        sr.setProjectionMatrix(cam.combined);
        //System.out.println("draw 1");
        //for(Map<String, Entity> entityMap : world.getRooms()){
        //System.out.println("rooms: " + world.getHouseRooms().size());
        
        /*
        for(Entity player : world.getEntities()){  
        if(player.getClass().getSimpleName().equals("Player")){
            
            
            
           }      
        }
        */
        
        /*
        //Code down below is for drawing the sprite, setting the position and rotation
        if(!world.getPlayerID().equals(""))
        {
        batch.begin();
        float PlayerSpriteX = (gameData.getDisplayWidth()/2);
        float PlayerSpriteY = (gameData.getDisplayHeight()/2);
        sprite.setPosition(PlayerSpriteX-47, PlayerSpriteY-47);
       // sprite.setSize(60, 60);
        float xInput = Gdx.input.getX();
        float yInput = (Gdx.graphics.getHeight() - Gdx.input.getY());
        float angle = MathUtils.radiansToDegrees * MathUtils.atan2(yInput - PlayerSpriteY, xInput - PlayerSpriteX);
      

        if(angle < 0){
           angle += 360;
          }
        sprite.setRotation(angle);
        
        }else{
          //If player is not in, it should remove the picture
           img = new Texture("assets/images/Empty.png");
           sprite = new Sprite(img);
        }
        sprite.draw(batch);
        batch.end();
        */
        
        //Following code has issues with dynamic load and unload
        int justOne = -1;
        if(world.currentRoom != 0)
        {
            for(Entity e : world.getLevel().get(world.currentRoom).values()){
                if(e.getClass().getSimpleName().equals("Map")){
                        TilePart tile = e.getPart(TilePart.class);
                        if(tile.getType().equals("roomInfo")){
                            if(tile.getState().equals("unexplored"))
                                justOne = world.currentRoom;
                        }
                    }
            }       
        }

        for(int ii = 0; ii < world.getLevel().size(); ii++){
            boolean skip = false;
            for (Entity entity : world.getLevel().get(ii).values()) {
                if(entity.getClass().getSimpleName().equals("Map")){
                    TilePart tile = entity.getPart(TilePart.class);
                    if(tile.getType().equals("roomInfo")){
                        if(tile.getState().equals("unexplored")){
                            skip = true;
                        }
                    }
                }
            }
            if(justOne!=-1){
                ii = justOne;
            }
            
            if(!skip){
                //for (Entity entity : world.getLevel().get(ii).values().g)
                ArrayList<Entity> sorted = new ArrayList<>();
                for (Entity entity : world.getLevel().get(ii).values()) {
                    if(entity.getClass().getSimpleName().equals("Map")){
                        TilePart tile = entity.getPart(TilePart.class);
                        if(tile.getType().equals("floor")){
                            sorted.add(0, entity);
                        }
                        else{
                            sorted.add(entity);
                        }
                    }
                    else{
                        sorted.add(entity);
                    }
                }
                //for (Entity entity : world.getLevel().get(ii).values()) {
                for (Entity entity : sorted) {
                    //System.out.println("ii: " + ii);
                    //System.out.println("draw 3");
                    PositionPart tilePos = entity.getPart(PositionPart.class);
                    if(entity.getClass().getSimpleName().equals("Map")){
                        //System.out.println("draw 4");
                        TilePart tile = entity.getPart(TilePart.class);
                        //PositionPart tilePos = entity.getPart(PositionPart.class);
                        if(tile.getType().equals("door")){
                            //System.out.println("draw door");
                            //System.out.println("Colliding with door");
                            if(tile.locked){
                                sr.setColor(1, 0, 0, 0);
                                batch.begin();
                                batch.draw(wallSprite, ((int)tilePos.getX()-24-shiftX), ((int)tilePos.getY()-24-shiftY));
                                batch.end();
                            }
                            else{
                                //System.out.println("draw wall");
                                sr.setColor(0, 1, 0, 0);
                                batch.begin();
                                batch.draw(floorSprite, ((int)tilePos.getX()-24-shiftX), ((int)tilePos.getY()-24-shiftY));
                                batch.end();
                            }
                        }
                        else if(tile.getType().equals("floor")){
                            sr.setColor(1, 1, 1, 1);
                            batch.begin();
                            batch.draw(floorSprite, ((int)tilePos.getX()-24-shiftX), ((int)tilePos.getY()-24-shiftY));
                            batch.end();
                        }
                        else if(tile.getType().equals("wall")){
                            sr.setColor(0, 0, 1, 0);
                            
                            batch.begin();
                            batch.draw(wallSprite, ((int)tilePos.getX()-24-shiftX), ((int)tilePos.getY()-24-shiftY));
                            batch.end();
                        }
                        else if(tile.getType().equals("box")){
                            sr.setColor(0, 0, 1, 0);
                            
                            batch.begin();
                            batch.draw(boxSprite, ((int)tilePos.getX()-24-shiftX), ((int)tilePos.getY()-24-shiftY));
                            batch.end();
                        }
                        else{
                            sr.setColor(0, 0, 1, 0);
                        }
                    }
                    else if(entity.getClass().getSimpleName().equals("Enemy")){
                        sr.setColor(1, 0, 0, 0);
                        batch.begin();
                        batch.draw(enemySprite, ((int)tilePos.getX()-24-shiftX), ((int)tilePos.getY()-24-shiftY));
                        batch.end();
                    }
                    else if(entity.getClass().getSimpleName().equals("Item")){
                        ItemPart ip = entity.getPart(ItemPart.class);
                        if(ip.getType().equals("chest")){
                            sr.setColor(1, 1, 0, 0);
                            
                            batch.begin();
                            batch.draw(chestSprite, ((int)tilePos.getX()-24-shiftX), ((int)tilePos.getY()-24-shiftY));
                            batch.end();
                        }
                        else if(ip.getType().equals("key")){
                            sr.setColor(1, 0, 1, 0);
                            batch.begin();
                            batch.draw(keySprite, ((int)tilePos.getX()-24-shiftX), ((int)tilePos.getY()-24-shiftY));
                            batch.end();
                        }
                        else if(ip.getType().equals("healthInc")){
                            sr.setColor(1, 0, 1, 0);
                            batch.begin();
                            batch.draw(healthSprite, ((int)tilePos.getX()-24-shiftX), ((int)tilePos.getY()-24-shiftY));
                            batch.end();
                        }
                        else
                            sr.setColor(0, 1, 1, 0);
                    }
                    else if(entity.getClass().getSimpleName().equals("Bullet")){
                        sr.setColor(1, 1, 1, 1);
                        batch.begin();
                        batch.draw(bulletSprite, ((int)tilePos.getX()-shiftX), ((int)tilePos.getY()-shiftY));
                        batch.end();
                        }
                    else{
                        sr.setColor(1, 1, 1, 1);
                    }

                    sr.begin(ShapeRenderer.ShapeType.Line);

                    float[] shapex = entity.getShapeX();
                    float[] shapey = entity.getShapeY();

                    for (int i = 0, j = shapex.length - 1;
                            i < shapex.length;
                            j = i++) {
                        //System.out.println("Entitny type: " + entity.getClass().getSimpleName() + " shapes xi yi xj yj: " + shapex[i] +" "+ shapey[i] +" "+ shapex[j] +" "+ shapey[j]);

                        //sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
                    }

                    sr.end();
                    if(justOne!=-1){
                        ii = world.getLevel().size();
                    }
                }
            }
        }
        
        for (Entity entity : world.getCollisionEntities()) {
            //Draw magazine and ammo
            batch.begin();

            String nameOfWeapon = "Current: ";
            String ammo = "Ammo: ";
            String magazine = "          |  Magazine: ";
            if(entity.getClass().getSimpleName().equals("Weapon")){
                GunPart gunPart = entity.getPart(GunPart.class);
                if(gunPart.isEquipped())
                {
                    ammo = ammo + gunPart.getAmmo();
                    int magazineAmount = gunPart.getMagazine();
                    font.draw(batch, magazine+String.valueOf(magazineAmount), 600, 100);
                    nameOfWeapon = nameOfWeapon + gunPart.getName();
                    font.draw(batch, nameOfWeapon, 550, 150);
                }
            }


            font.draw(batch, ammo, 550, 100);
            batch.end();
            sr.setColor(1, 1, 1, 1);
            if(!entity.getClass().getSimpleName().equals("Map") && !entity.getClass().getSimpleName().equals("Enemy") && !entity.getClass().getSimpleName().equals("Weapon") &&  !entity.getClass().getSimpleName().equals("Item")){
                /*TilePart tile = entity.getPart(TilePart.class);
                if(tile.getType().equals("door")){
                    //System.out.println("Colliding with door");
                    if(tile.locked){
                        sr.setColor(1, 0, 0, 0);
                    }
                    else{
                        sr.setColor(0, 1, 0, 0);
                    }
                }
                else{
                    sr.setColor(0, 0, 1, 0);
                }*/
                // from top sr = new ShapeRenderer();
                batch.begin();
                String s = "Level: ";
                int l = world.level;
                s = s + l + " | Life: ";
                if(entity.getClass().getSimpleName().equals("Player")){
                    LifePart lifepart = entity.getPart(LifePart.class);
                    int life = lifepart.getLife();
                    s = s + life;
                    s = s + " | Money: ";
                    InventoryPart ip = entity.getPart(InventoryPart.class);
                    s = s + ip.money;
                    s = s + " | Keys: ";
                    s = s + ip.keys;
                }
                font.draw(batch, s, 100, 100);
                batch.end();
                sr.setColor(1, 1, 1, 1);

                sr.begin(ShapeRenderer.ShapeType.Line);

                float[] shapex = entity.getShapeX();
                float[] shapey = entity.getShapeY();

                for (int i = 0, j = shapex.length - 1;
                        i < shapex.length;
                        j = i++) {

                    //sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
                }

                sr.end();
            }
        }
        //world.getCollisionEntities().
        //world.getPlayerID()
        
        /*
        Entity player = world.getEntity(world.getPlayerID());
        LifePart life = player.getPart(LifePart.class);
        int lifeAmount = life.getLife();
        batch.begin();
        font.draw(batch, ("Health: " + lifeAmount), 100, 40);
        batch.end();
        */
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }

    private Collection<? extends IEntityProcessingService> getEntityProcessingServices() {
        return lookup.lookupAll(IEntityProcessingService.class);
    }

    private Collection<? extends IPostEntityProcessingService> getPostEntityProcessingServices() {
        return lookup.lookupAll(IPostEntityProcessingService.class);
    }

    private final LookupListener lookupListener = new LookupListener() {
        @Override
        public void resultChanged(LookupEvent le) {

            Collection<? extends IGamePluginService> updated = result.allInstances();

            for (IGamePluginService us : updated) {
                // Newly installed modules
                if (!gamePlugins.contains(us)) {
                    us.start(gameData, world);
                    gamePlugins.add(us);
                }
            }

            // Stop and remove module
            for (IGamePluginService gs : gamePlugins) {
                if (!updated.contains(gs)) {
                    gs.stop(gameData, world);
                    gamePlugins.remove(gs);
                }
            }
        }
    };
}

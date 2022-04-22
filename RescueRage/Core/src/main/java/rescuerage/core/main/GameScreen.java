package rescuerage.core.main;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
import rescuerage.common.data.entityparts.PositionPart;
import rescuerage.common.data.entityparts.TilePart;

public class GameScreen extends Game implements Screen {

    public static OrthographicCamera cam;
    private ShapeRenderer sr;
    private final Lookup lookup = Lookup.getDefault();
    private final GameData gameData = new GameData();
    private World world = new World();
    
    private Entity player;
    private PositionPart positionPart;
    private float radians;
    private Game game;



    public GameScreen(Game game){
        this.game = game;
    }
    
    private List<IGamePluginService> gamePlugins = new CopyOnWriteArrayList<>();
    private Lookup.Result<IGamePluginService> result;


    @Override
    public void create() {
        gameData.setDisplayWidth(Gdx.graphics.getWidth());
        gameData.setDisplayHeight(Gdx.graphics.getHeight());

        cam = new OrthographicCamera(gameData.getDisplayWidth(), gameData.getDisplayHeight());
//        cam.translate(gameData.getDisplayWidth() / 2, gameData.getDisplayHeight() / 2);

        cam.update();

        sr = new ShapeRenderer();

        Gdx.input.setInputProcessor(new GameInputProcessor(gameData));
        

        result = lookup.lookupResult(IGamePluginService.class);
        result.addLookupListener(lookupListener);
        result.allItems();

        for (IGamePluginService plugin : result.allInstances()) {
            plugin.start(gameData, world);
            gamePlugins.add(plugin);
        }
        
        player = world.getEntity(world.getPlayerID());
        positionPart = player.getPart(PositionPart.class);
    }

    @Override
    public void render() {
        // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        
//        System.out.println("X: " + positionPart.getX() + " Y:" + positionPart.getY());
        cam.position.x = positionPart.getX();
        cam.position.y = positionPart.getY();
        cam.update();
        System.out.println("CamX: " + cam.position.x + " CamY:" + cam.position.y);
        System.out.println(cam.position);
        
        Vector3 mousePos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector3 playerPos = new Vector3(positionPart.getX(), positionPart.getY(), 0);
        radians = (float)Math.atan2(mousePos.y - playerPos.y, mousePos.x - playerPos.x);
        positionPart.setRadians(radians);
        
        update();
        draw();
        //postUpdate();
    }

    private void update() {
        // Update
        for (IEntityProcessingService entityProcessorService : getEntityProcessingServices()) {
            entityProcessorService.process(gameData, world);
        }

        // Post Update
//        for (IPostEntityProcessingService postEntityProcessorService : getPostEntityProcessingServices()) {
//            postEntityProcessorService.process(gameData, world);
//        }
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
        for(int ii = 0; ii < world.getRooms().size(); ii++){

            //System.out.println("draw 2");
            for (Entity entity : world.getRooms().get(ii).values()) {
                //System.out.println("draw 3");
                if(entity.getClass().getSimpleName().equals("Map")){
                    //System.out.println("draw 4");
                    TilePart tile = entity.getPart(TilePart.class);
                    if(tile.getType().equals("door")){
                        //System.out.println("draw door");
                        //System.out.println("Colliding with door");
                        if(tile.locked){
                            sr.setColor(1, 0, 0, 0);
                        }
                        else{
                            //System.out.println("draw wall");
                            sr.setColor(0, 1, 0, 0);
                        }
                    }
                    else{
                        sr.setColor(0, 0, 1, 0);
                    }
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

                    sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
                }

                sr.end();
            }
        }
        
        
        for (Entity entity : world.getCollisionEntities()) {
            if(entity.getClass().getSimpleName().equals("Map")){
                TilePart tile = entity.getPart(TilePart.class);
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
                }
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

                sr.line(shapex[i], shapey[i], shapex[j], shapey[j]);
            }

            sr.end();
        }
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

    @Override
    public void show() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void render(float f) {
          // clear screen to black
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameData.setDelta(Gdx.graphics.getDeltaTime());
        gameData.getKeys().update();
        
//        System.out.println("X: " + positionPart.getX() + " Y:" + positionPart.getY());
        cam.position.x = positionPart.getX();
        cam.position.y = positionPart.getY();
        cam.update();
        System.out.println("CamX: " + cam.position.x + " CamY:" + cam.position.y);
        System.out.println(cam.position);
        
        Vector3 mousePos = cam.unproject(new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0));
        Vector3 playerPos = new Vector3(positionPart.getX(), positionPart.getY(), 0);
        radians = (float)Math.atan2(mousePos.y - playerPos.y, mousePos.x - playerPos.x);
        positionPart.setRadians(radians);
        
        update();
        draw();
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
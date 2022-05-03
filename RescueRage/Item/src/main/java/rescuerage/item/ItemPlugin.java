/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rescuerage.item;

//import org.graalvm.compiler.serviceprovider.ServiceProvider;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import rescuerage.common.data.GameData;
import rescuerage.common.data.World;
import rescuerage.common.services.IGamePluginService;
import org.openide.util.lookup.ServiceProvider;
import rescuerage.common.data.Entity;
import rescuerage.common.data.entityparts.ItemPart;
import rescuerage.common.data.entityparts.PositionPart;

/**
 *
 * @author ander
 */
@ServiceProvider(service = IGamePluginService.class)
public class ItemPlugin implements IGamePluginService {
    
    private ArrayList<Entity> items = new ArrayList<>();
    private World world;

    @Override
    public void start(GameData gameData, World world) {
        this.world = world;
        createItemsInLevel();
    }
    public void createItemsInLevel(){
        Random random = new Random();
        for (int i = 0; i < world.getHouseRooms().size(); i++){
            int r = random.nextInt(10);
            if(r<3){
                createItemInRoomIndex(i);
            }
        }
        /*
        createItemInRoomIndex(0);
        createItemInRoomIndex(1);
        createItemInRoomIndex(2);
        */
    }
    private void createItemInRoomIndex(int index){
        world.addEntityInRoom(createItem(), index);
    }
    private Entity createItem(){
        Entity item = new Item();
        item.setRadius(world.tileSize/2);
        item.setSizeX(world.tileSize/2);
        item.setSizeY(world.tileSize/2);
        item.setRadius(8);
        item.add(new PositionPart(0,0,0));
        
        ArrayList<String> types = new ArrayList(Arrays.asList("healthInc", "key", "chest"));
        //ArrayList<String> types = new ArrayList(Arrays.asList("healthInc", "key", "chest", "healthInc", "key", "chest", "healthInc", "key", "chest", "healthInc", "key", "chest"));
        Random r = new Random();
        
        int rIndex = r.nextInt(types.size());
        
        item.add(new ItemPart(types.get(rIndex),1,100));
        
        world.addEntity(item);
        return item;
    }
    
    /*
    private Entity createTile(float x, float y, String type, java.util.Map<String, Entity> roomEntityMap){
        Entity map = new Map();
        
        map.setRadius(tileSize/2);
        map.setSizeX(tileSize/2);
        map.setSizeY(tileSize/2);
        //System.out.println("x: " + x);
        //System.out.println("y: " + y);
        map.add(new PositionPart(x,y,0));
        map.add(new TilePart(type));
        if(type.equals("box")){
            map.add(new LifePart(1));
        }
        world.addEntity(map);
        addEntity(map, roomEntityMap);
        return map;
    }
    */
    @Override
    public void stop(GameData gameData, World world) {
        // Remove entities
        //world.removeEntity(enemy);
        for(int i = 0;i<items.size();i++){
            world.removeEntity(items.get(i));
        }
    }
    
}

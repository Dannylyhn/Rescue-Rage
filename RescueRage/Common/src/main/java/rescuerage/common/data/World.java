package rescuerage.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rescuerage.common.data.entityparts.TilePart;

/**
 *
 * @author jcs
 */
public class World {
    //Player set id
    private String playerID;

    public String getPlayerID() {
        return playerID;
    }
    public void setPlayerID(String playerID) {
        this.playerID = playerID;
    }    

    //String with weapon needs to be changed with inventory    
    private String[] weapons = new String[3];
    public String[] getWeapons() {
        return weapons;
    }
    public void setWeapons(String[] weapons) {
        this.weapons = weapons;
    }
    
    public String weapon;

    public String getWeapon() {
        return weapon;
    }

    public void setWeapon(String weapon) {
        this.weapon = weapon;
    }
    
    public int level = 1;
    
    public int houseH = 3;
    public int houseW = 2;
    public int roomH = 9; // must be uneven number, because the doors are being set at the center of the wall
    public int roomW = 11; // must be uneven number, because the doors are being set at the center of the wall
    public int tileSize = 16; // must be even number, because the top and right rows are depending on it
    public int WIDTH = roomW * tileSize;
    public int HEIGHT = roomH * tileSize;
    
    
    private final ArrayList<Map<String, Entity>> roomMap = new ArrayList<>();
    
    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private final Map<String, Entity> collisionMap = new ConcurrentHashMap<>();

    public void nextLevel(){
        level = level + 1;
        houseW = houseW + 1;
        houseH = houseH + 1;
    }
    public void addRoom(Map<String, Entity> room){
        roomMap.add(room);
    }
    public ArrayList<Map<String, Entity>> getRooms() {
        return roomMap;
    }
    
    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        if(ignoreWalkableTiles(entity)){
            collisionMap.put(entity.getID(), entity);
        }
        return entity.getID();
    }
    private boolean ignoreWalkableTiles(Entity e1){
        if (e1.getClass().getSimpleName().equals("Map")) {
            TilePart tile1 = e1.getPart(TilePart.class);
            if(tile1.getType().equals("floor")){
                return false;
            }
            return true;
        }
        return true;
    }

    public void removeEntity(String entityID) {
        entityMap.remove(entityID);
        collisionMap.remove(entityID);
    }

    public void removeEntity(Entity entity) {
        entityMap.remove(entity.getID());
        collisionMap.remove(entity.getID());
    }

    public Collection<Entity> getEntities() {
        return entityMap.values();
    }
    public Collection<Entity> getCollisionEntities() {
        return collisionMap.values();
    }

    public <E extends Entity> List<Entity> getEntities(Class<E>... entityTypes) {
        List<Entity> r = new ArrayList<>();
        for (Entity e : getEntities()) {
            for (Class<E> entityType : entityTypes) {
                if (entityType.equals(e.getClass())) {
                    r.add(e);
                }
            }
        }
        return r;
    }

    public Entity getEntity(String ID) {
        return entityMap.get(ID);
    }
    public Entity getCollisionEntity(String ID) {
        return collisionMap.get(ID);
    }

}

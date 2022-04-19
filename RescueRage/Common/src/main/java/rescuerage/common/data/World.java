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
    
    
    
    private final Map<String, Entity> entityMap = new ConcurrentHashMap<>();
    private final Map<String, Entity> collisionMap = new ConcurrentHashMap<>();

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

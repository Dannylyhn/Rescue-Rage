package rescuerage.common.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import rescuerage.common.data.entityparts.PositionPart;
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
    
    public int level = 1;
    public int currentRoom = 0;
    
    public int houseH = 2;
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
        currentRoom = 0;
        for(Entity e : entityMap.values()){
            if (e.getClass().getSimpleName().equals("Player")) {
                PositionPart player = e.getPart(PositionPart.class);
                player.setX(player.getX()-((houseW+1)*roomW*tileSize));
            }
        }
        
        houseW = houseW + 1;
        houseH = houseH + 1;
        //roomMap.clear();
        level = level + 1;
        
    }
    public void addRoom(Map<String, Entity> room){
        roomMap.add(room);
    }
    public void addBossarea(Map<String, Entity> room){
        roomMap.add(1,room);
    }
    public ArrayList<Map<String, Entity>> getLevel() {
        return roomMap;
    }
    public Map<String, Entity> getCurrentRoom(){
        return roomMap.get(currentRoom);
    }
    public Map<String, Entity> getStartingArea(){
        return roomMap.get(0);
    }
    public Map<String, Entity> getBossArea(){
        return roomMap.get(1);
    }
    public ArrayList<Map<String, Entity>> getHouseRooms(){
        ArrayList<Map<String, Entity>> ret = new ArrayList<>(roomMap);
        if(ret.size()>2){
            ret.remove(0);
            ret.remove(0);
        }
        return ret;
    }
    public void lockBossRoom(){
        for(Entity e : getBossArea().values()){
            if(e.getClass().getSimpleName().equals("Map")){
                TilePart tp = e.getPart(TilePart.class);
                if(tp.getType().equals("door")){
                    tp.lock();
                }
            }
        }
    }
    public void unlockBossRoom(){
        for(Entity e : getBossArea().values()){
            if(e.getClass().getSimpleName().equals("Map")){
                TilePart tp = e.getPart(TilePart.class);
                if(tp.getType().equals("door")){
                    tp.unlock();
                }
            }
        }
    }
    public void lockDoors(){
        int counter = 0;
        for(Entity e : getLevel().get(currentRoom).values()){
            if(e.getClass().getSimpleName().equals("Map")){
                TilePart tp = e.getPart(TilePart.class);
                if(tp.getType().equals("door")){
                    tp.lock();
                }
                else if(tp.getType().equals("box")){
                    counter++;
                }
            }
        }
        //System.out.println("\n\n\n\n c: " + counter + "\n\n\n\n\n");
        if(counter==0){
            unlockDoors();
        }
    }
    public void unlockDoors(){
        if(currentRoom==1){
            nextLevel();
            return;
        }
        for(Entity e : getLevel().get(currentRoom).values()){
            if(e.getClass().getSimpleName().equals("Map")){
                TilePart tp = e.getPart(TilePart.class);
                if(tp.getType().equals("door")){
                    tp.unlock();
                }
                else if(tp.getType().equals("roomInfo")){
                    tp.setState("explored");
                }
            }
        }
        int counter = 0;
        for(Map<String, Entity> room : getHouseRooms()){
            for(Entity e : room.values()){
                TilePart tp = e.getPart(TilePart.class);
                if(tp.getType().equals("roomInfo")){
                    System.out.println("room check unlocking");
                    if(tp.getState().equals("unexplored")){
                        counter++;
                        System.out.println("counter: " + counter);
                    }
                }
            }
        }
        if(counter == 0){
            System.out.println("unlocking boss");
            unlockBossRoom();
        }
    }
    
    public String addEntity(Entity entity) {
        entityMap.put(entity.getID(), entity);
        if(ignoreWalkableTiles(entity)){
            collisionMap.put(entity.getID(), entity);
        }
        return entity.getID();
    }
    public void addEntityInRoom(Entity entity, int roomIndex){
        Map<String, Entity> room = getHouseRooms().get(roomIndex);
        PositionPart p = entity.getPart(PositionPart.class);
        int shiftY = 0;
        int shiftX = roomIndex+1;
        while(shiftX>houseW){
            shiftX = shiftX - houseW;
            shiftY = shiftY + 1;
        }
        //System.out.println("shiftX: " + shiftX + " | shiftY: " + shiftY);
        p.setPosition((shiftX*roomW*tileSize)+p.getX(), (shiftY*roomH*tileSize)+p.getY());
        //p.setPosition(p.getX(), p.getY());
        
        room.put(entity.getID(), entity);
    }
    public void addEntityInBossArea(Entity entity){
        Map<String, Entity> room = getBossArea();
        PositionPart p = entity.getPart(PositionPart.class);
        p.setPosition(((houseW+1)*roomW*tileSize)+p.getX(), p.getY());
        room.put(entity.getID(), entity);
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
        
        for(Map<String, Entity> entityMap : getLevel()){
            if(entityMap.containsValue(entity)){
                entityMap.remove(entity.getID());
            }
        }
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

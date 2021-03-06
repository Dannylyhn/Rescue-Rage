package rescuerage.common.data;

import rescuerage.common.data.entityparts.EntityPart;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class Entity implements Serializable {

    private final UUID ID = UUID.randomUUID();

    private float[] shapeX = new float[4];
    private float[] shapeY = new float[4];
    private float radius;
    private float sizeX;
    private float sizeY;
    private Map<Class, EntityPart> parts;

    public Entity() {
        parts = new ConcurrentHashMap<>();
    }

    public void add(EntityPart part) {
        parts.put(part.getClass(), part);
    }

    public void remove(Class partClass) {
        parts.remove(partClass);
    }

    public <E extends EntityPart> E getPart(Class partClass) {
        return (E) parts.get(partClass);
    }

    public void setRadius(float r) {
        this.radius = r;
    }

    public float getRadius() {
        return radius;
    }
    public void setSizeX(float r) {
        this.sizeX = r;
    }

    public float getSizeX() {
        return sizeX;
    }
    public void setSizeY(float r) {
        this.sizeY = r;
    }

    public float getSizeY() {
        return sizeY;
    }

    public String getID() {
        return ID.toString();
    }

    public float[] getShapeX() {
        return shapeX;
    }

    public void setShapeX(float[] shapeX) {
        this.shapeX = shapeX;
    }

    public float[] getShapeY() {
        return shapeY;
    }

    public void setShapeY(float[] shapeY) {
        this.shapeY = shapeY;
    }
    
    public boolean contains(float x, float y){
        boolean b = false;
        for(int i = 0, j = this.shapeX.length -1; i < this.shapeX.length; j = i++){
            if((this.shapeY[i] > y) != (this.shapeY[j] > y) && (x < (this.shapeX[j] - this.shapeX[i]) * (y - this.shapeY[i]) / (this.shapeY[j] - this.shapeY[i]) + this.shapeX[i])){
                b = !b;
            }
        }
        return b;
    }

}

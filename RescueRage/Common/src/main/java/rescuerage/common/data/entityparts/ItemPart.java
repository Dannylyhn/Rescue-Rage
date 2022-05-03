/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.common.data.entityparts;
import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;
/**
 *
 * @author ander
 */
public class ItemPart implements EntityPart {
    private String type;
    private int value;
    private int price;
    public boolean E;
    
    public ItemPart(String type, int value, int price) {
        this.type = type;
        this.value = value;
        this.price = price;
    }
    
    public String getType(){
        return type;
    }
    public int getValue(){
        return value;
    }
    public int getPrice(){
        return price;
    }
    public void open(){
        this.type = "healthInc";
        this.value = 1;
    }
    public void setE(boolean E) {
        this.E = E;
    }
    
    @Override
    public void process(GameData gameData, Entity entity) {
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rescuerage.common.data.entityparts;

import rescuerage.common.data.Entity;
import rescuerage.common.data.GameData;

/**
 *
 * @author ander
 */
public class InventoryPart implements EntityPart {
    public int money = 0;
    public int keys = 0;
    
    public void incMoney(int i){
        this.money = this.money+i;
    }
    public void decMoney(int i){
        this.money = this.money - i;
    }
    public void takeKey(){
        this.keys = this.keys + 1;
    }
    public void useKey(){
        this.keys = this.keys-1;
    }

    @Override
    public void process(GameData gameData, Entity entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}

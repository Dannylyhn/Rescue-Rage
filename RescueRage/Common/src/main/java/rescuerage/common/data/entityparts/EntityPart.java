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
public interface EntityPart {

    void process(GameData gameData, Entity entity);
}

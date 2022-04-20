/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rescuerage.commonloadout;
import rescuerage.common.data.World;
import rescuerage.common.data.Entity;

/**
 *
 * @author dan
 */
public interface LoadoutSPI {
    Entity createLoadout(World world);
}

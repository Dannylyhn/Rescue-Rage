/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package rescuerage.commonbullet;

import rescuerage.common.data.GameData;
import rescuerage.common.data.Entity;

/**
 *
 * @author dan
 */
public interface BulletSPI {
    Entity createBullet(float x, float y, float radians, float radius, GameData gamedata);
}

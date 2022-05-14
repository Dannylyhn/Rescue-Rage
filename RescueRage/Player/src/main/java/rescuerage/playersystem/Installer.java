/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/moduleInstall.java to edit this template
 */
package rescuerage.playersystem;

import org.openide.modules.ModuleInstall;
import rescuerage.common.data.World;


public class Installer extends ModuleInstall {

    @Override
    public void restored() {
        // TODO
    }
    
    @Override
    public void uninstalled()
    {
        World world = World.getInstance();
        world.setPlayerID("");
    }
    

}

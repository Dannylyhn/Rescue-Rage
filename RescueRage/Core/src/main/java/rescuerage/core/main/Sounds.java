/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.core.main;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

/**
 *
 * @author danny
 */
public class Sounds {
    
    //Very bad solutin for shooting
    public static void shootSound(){
        Sound shootingSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/shootingAlt.mp3"));
        shootingSound.play();
    }
    
    
    
    
}

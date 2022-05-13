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
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/shootingAlt.mp3"));
        sound.play();
    }
    
    public static void deathSound(){
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/zombieDeath.mp3"));
        sound.play(1f);
    }
    
    public static void reloadSound(){
        Sound sound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/Reload.mp3"));
        sound.play();
    }
    
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.core.main;

import com.badlogic.gdx.Game;

/**
 *
 * @author dannyly
 */
public class StartGame extends Game {
    private Game game;
    
    
    public StartGame(){
        this.game = game;
    }
    
    @Override
    public void render(){
        super.render();
    }
    
    @Override
    public void create() {
        
        this.setScreen(new MainMenuScreen(game));
    
    }
    
}

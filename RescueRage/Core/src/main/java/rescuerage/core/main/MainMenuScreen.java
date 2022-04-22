/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rescuerage.core.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 *
 * @author dannyly
 */
public class MainMenuScreen extends Game implements Screen{

    public SpriteBatch batch;
    public SpriteBatch batch2;
    public BitmapFont font;
    public BitmapFont font2;
    private Game game;
    

    public MainMenuScreen(Game game) {
        this.game = game;
    }

   
    @Override
    public void create() {
        batch = new SpriteBatch();
	font = new BitmapFont(); // use libGDX's default Arial font
        batch2 = new SpriteBatch();
        font2 = new BitmapFont();
        
    }

    @Override
    public void show() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        font.draw(batch, "Rescue Rage", 100, 100);
        font.setScale(1.2f);
    
        font2.draw(batch, "Press any button to begin", 100,70);
        font2.setScale(1);
        batch.end();
        
        if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
        
        
        
    } 
    
    //Method for screen
    @Override
     public void render(float f) {
          if (Gdx.input.isTouched()) {
            game.setScreen(new GameScreen(game));
            dispose();
        }
        
    }

    @Override
    public void hide() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
  
   
   
    
    
}

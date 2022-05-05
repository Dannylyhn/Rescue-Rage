package rescuerage.core.managers;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import rescuerage.common.data.GameData;
import rescuerage.common.data.GameKeys;

public class GameInputProcessor extends InputAdapter implements InputProcessor {

    private final GameData gameData;

    public GameInputProcessor(GameData gameData) {
        this.gameData = gameData;
    }
    
    @Override
    public boolean touchDown(int x, int y, int pointer, int button)
    {
        if(button == Input.Buttons.LEFT)
        {
            gameData.getKeys().setKey(GameKeys.LEFTCLICK, true);
            return true;
        }
        return false;
    }
    
    @Override
    public boolean touchUp(int x, int y, int pointer, int button)
    {
        if(button == Input.Buttons.LEFT)
        {
            gameData.getKeys().setKey(GameKeys.LEFTCLICK, false);
            return true;
        }
        return false;
    }
   
    public boolean keyDown(int k) {
        if (k == Keys.W) {
            gameData.getKeys().setKey(GameKeys.W, true);
        }
        if (k == Keys.A) {
            gameData.getKeys().setKey(GameKeys.A, true);
        }
        if (k == Keys.S) {
            gameData.getKeys().setKey(GameKeys.S, true);
        }
        if (k == Keys.D) {
            gameData.getKeys().setKey(GameKeys.D, true);
        }
        if (k == Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, true);
        }
        if (k == Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, true);
        }
        if (k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, true);
        }
        if (k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, true);
        }
        if (k == Keys.Q) {
            gameData.getKeys().setKey(GameKeys.Q, true);
        }
        if (k == Keys.E) {
            gameData.getKeys().setKey(GameKeys.E, true);
        }
        if (k == Keys.R) {
            gameData.getKeys().setKey(GameKeys.R, true);
        }
        return true;
    }

    public boolean keyUp(int k) {
        if (k == Keys.W) {
            gameData.getKeys().setKey(GameKeys.W, false);
        }
        if (k == Keys.A) {
            gameData.getKeys().setKey(GameKeys.A, false);
        }
        if (k == Keys.S) {
            gameData.getKeys().setKey(GameKeys.S, false);
        }
        if (k == Keys.D) {
            gameData.getKeys().setKey(GameKeys.D, false);
        }
        if (k == Keys.ENTER) {
            gameData.getKeys().setKey(GameKeys.ENTER, false);
        }
        if (k == Keys.ESCAPE) {
            gameData.getKeys().setKey(GameKeys.ESCAPE, false);
        }
        if (k == Keys.SPACE) {
            gameData.getKeys().setKey(GameKeys.SPACE, false);
        }
        if (k == Keys.SHIFT_LEFT || k == Keys.SHIFT_RIGHT) {
            gameData.getKeys().setKey(GameKeys.SHIFT, false);
        }
        if (k == Keys.Q) {
            gameData.getKeys().setKey(GameKeys.Q, false);
        }
        if (k == Keys.E) {
            gameData.getKeys().setKey(GameKeys.E, false);
        }
        if (k == Keys.R) {
            gameData.getKeys().setKey(GameKeys.R, false);
        }
        return true;
    }
}

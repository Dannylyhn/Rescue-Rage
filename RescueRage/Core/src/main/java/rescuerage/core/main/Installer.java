package rescuerage.core.main;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    private static StartGame g;
    private static GameScreen gm;

    @Override
    public void restored() {

        g = new StartGame();
       

        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "RescueRage";
        cfg.width = 800;
        cfg.height = 600;
        cfg.useGL30 = false;
        cfg.resizable = false;

        new LwjglApplication(g, cfg);
    }
}

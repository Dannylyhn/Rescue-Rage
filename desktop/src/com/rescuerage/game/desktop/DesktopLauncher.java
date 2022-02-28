package com.rescuerage.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rescuerage.game.RescueRageGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new RescueRageGame(), config);
		config.title = "Rescue Rage";
		config.width = 1024;
		config.width = 768;
	}
}

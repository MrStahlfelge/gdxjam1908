package de.golfgl.gdxjamgame.oneroom.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import de.golfgl.gdxjamgame.oneroom.GdxJamGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GdxJamGame.nativeGameWidth;
		config.height = GdxJamGame.nativeGameHeight;
		new LwjglApplication(new GdxJamGame(), config);
	}
}

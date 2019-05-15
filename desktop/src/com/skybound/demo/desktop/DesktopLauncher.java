package com.skybound.demo.desktop;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.skybound.demo.SkyboundDemoMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.fullscreen = true;
		config.disableAudio = true;
		config.title = "TESTTEST";
		config.addIcon("core\\assets\\TEMPICON.png", Files.FileType.Local);
		new LwjglApplication(new SkyboundDemoMain(), config);
	}
}

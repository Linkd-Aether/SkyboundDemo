package com.skybound.demo.desktop;

import java.util.Scanner;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.skybound.demo.SkyboundDemoMain;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
//		config.fullscreen = false;
//		
//		Scanner scan = new Scanner(System.in);
//		System.out.print("View in fullscreen? (must use alt + f4 to escape) ");
//		String ans = scan.nextLine();
//		if(ans == "yes" || ans == "Yes") {
//			System.out.println("AH");
			config.fullscreen = true;
//		}
//		scan.close();
		System.out.println(config.fullscreen);
		config.width = 640;
		config.height = 480;
		config.disableAudio = true;
		config.title = "Game Demo";
		config.addIcon("TEMPICON.png", Files.FileType.Internal);
		new LwjglApplication(new SkyboundDemoMain(), config);
	}
}

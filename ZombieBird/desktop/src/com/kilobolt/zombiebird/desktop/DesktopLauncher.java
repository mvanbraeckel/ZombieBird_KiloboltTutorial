/*
 * Mitchell Van Braeckel
 * 14/12/2016
 * ZombieBird Kilobolt Tutorial: Desktop Launcher
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.zombiebird.desktop;

// imports
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.kilobolt.zombiebird.ZBGame;
/**
 * 
 * @author Mitchell Van Braeckel
 */
public class DesktopLauncher {
    public static void main (String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

        // set title and size
        config.title = "Zombie Bird";
        config.width = 272; //used to be 480
        config.height = 408;//used to be 320

        new LwjglApplication(new ZBGame(), config);
    } // end main
} // end desktopLauncher class
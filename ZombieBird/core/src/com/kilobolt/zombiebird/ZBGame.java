/*
 * Mitchell Van Braeckel
 * 14/12/2016
 * ZombieBird Kilobolt Tutorial: Main Game
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.zombiebird;

// imports
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.kilobolt.screens.GameScreen;
import com.kilobolt.zbhelpers.AssetLoader;
/**
 * 
 * @author Mitchell Van Braeckel
 */
public class ZBGame extends Game {
    /**
     * called when the game is started
     */
     @Override
    public void create() {
        Gdx.app.log("ZBGame", "created");
        AssetLoader.load();             //loads assets
        setScreen(new GameScreen());
    }
    /**
     * called when the game is closed
     */
     @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
    }
} // end ZBGame class

/*
 * Mitchell Van Braeckel
 * 14/12/2016
 * ZombieBird Kilobolt Tutorial: GameScreen
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
        • has 2 helper classes:
            -> GameWorld    --> updating helper class
            -> GameRenderer --> rendering helper class
 */
package com.kilobolt.screens;

// imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.kilobolt.gameworld.GameRenderer;
import com.kilobolt.gameworld.GameWorld;
import com.kilobolt.zbhelpers.InputHandler;
/**
 *
 * @author Mitchell Van Braeckel
 */
public class GameScreen implements Screen{
        // ATTRIBUTES
    private GameWorld world;
    private GameRenderer renderer;
    private float runTime = 0;      //keeps track of how long the game has been running for
    
        // CONSTRUCTORS
    /**
     * Default Constructor - instantiates a new GameScreen w/ a GameWorld (to help updating) and a GameRenderer (to help render)
     */
    public GameScreen() {
        // calc screen dimensions
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 136;
        float gameHeight = screenHeight / (screenWidth / gameWidth);
        int midPointY = (int)(gameHeight / 2);  //calc midPointY
        
        //Gdx.app.log("GameScreen", "Attached");
        world = new GameWorld(midPointY);       //init world
        renderer = new GameRenderer(world, (int)gameHeight, midPointY);     //init renderer
        
        // tells libGDX to take our new InputHandler as its processor
        Gdx.input.setInputProcessor(new InputHandler(world));
    }
        // BEHAVIOURS
    /**
     * Game Looping Render method: update all game objects, and renders them
     * @param delta the number of seconds (usually a small fraction) that has passed since the last time that the render method was called
     */
     @Override
    public void render(float delta) {
        runTime += delta;       //used to render the animation properly
        // We are passing in delta to our update method so that we can perform frame-rate independent movement. 
        world.update(delta);    //GameWorld updates 
        renderer.render(runTime);      //GameRenderer renders
        
        /*// Sets a Color to Fill the Screen with (RGB = 10, 15, 230), Opacity of 1 (100%)
        Gdx.gl.glClearColor(10/255.0f, 15/255.0f, 230/255.0f, 1f);
        // Fills the screen with the selected color
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Convert Frame rate to String, print it
        //(1/delta) = how many times that the render method would be called in one second if that rate was sustained
        Gdx.app.log("GameScreen FPS", (1/delta) + "");*/
    }
    
     @Override
    public void resize(int width, int height) {
        //Gdx.app.log("GameScreen", "resizing");
    }
    
     @Override
    public void show() {
        //Gdx.app.log("GameScreen", "show called");
    }
    
     @Override
    public void hide() {
        //Gdx.app.log("GameScreen", "hide called");
    }
    
     @Override
    public void pause() {
        //Gdx.app.log("GameScreen", "pause called");
    }
    
     @Override
    public void resume() {
        //Gdx.app.log("GameScreen", "resume called");
    }

     @Override
    public void dispose() {
        // Leave blank
    }
    
} // end GameScreen class

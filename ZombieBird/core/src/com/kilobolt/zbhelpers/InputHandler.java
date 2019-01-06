/*
 * Mitchell Van Braeckel
 * 19/12/2016
 * ZombieBird Kilobolt Tutorial: InputHandler   --> helper class for reacting to input
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.zbhelpers;

// imports
import com.badlogic.gdx.InputProcessor;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameworld.GameWorld;
/**
 *
 * @author Mitchell Van Braeckel
 */
public class InputHandler implements InputProcessor{
        // ATTRIBUTES
    private GameWorld myWorld;
    private Bird myBird;
    
        // CONSTRUCTORS
    /*
     * Primary Constructor - instantiates a new InputHandler with a Bird
     * (asks for a reference to the Bird from GameWorld when InputHandler is created)
     * //@param bird the bird that will be changed based on input
     */
    /*public InputHandler(Bird bird) {
        myBird = bird;  //myBird now represents the GameWorld's bird
    }*/
    // Ask for a reference to the Bird when InputHandler is created
    public InputHandler(GameWorld myWorld) {
        // myBird now represents the gameWorld's bird.
        this.myWorld = myWorld;
        myBird = myWorld.getBird();
    }
        // BEHAVIOURS
     @Override
    public boolean keyDown(int keycode) {
        return false;
    }

     @Override
    public boolean keyUp(int keycode) {
        return false;
    }

     @Override
    public boolean keyTyped(char character) {
        return false;
    }

     @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        if (myWorld.isReady()) {
            myWorld.start();
        }
        
        myBird.onClick();
        
        if (myWorld.isGameOver() || myWorld.isHighScore()) {
            // Reset all variables, go to GameState.READY
            myWorld.restart();
        }
        return true;    //return true to say we handled the touch
    }

     @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

     @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

     @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

     @Override
    public boolean scrolled(int amount) {
        return false;
    }
    
} // end InputHandler class

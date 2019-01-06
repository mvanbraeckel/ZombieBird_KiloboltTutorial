/*
 * Mitchell Van Braeckel
 * 14/12/2016
 * ZombieBird Kilobolt Tutorial: GameWorld      --> UPDATING helper class for GameScreen
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.gameworld;

// imports
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.zbhelpers.AssetLoader;
/**
 *
 * @author Mitchell Van Braeckel
 */
public class GameWorld {
        // ATTRIBUTES
    //private Rectangle rect = new Rectangle(0, 0, 17, 12);
    private Bird bird;
    private ScrollHandler scroller;
    private Rectangle ground;
    private int score = 0;
    private int midPointY;
    
    private GameState currentState;
        // GAME STATES
    public enum GameState {
        READY, RUNNING, GAMEOVER, HIGHSCORE
    }
    
        // CONSTRUCTORS
    /**
     * Primary Constructor - instantiates a new GameWorld w/ a bird
     * @param midPointY the y-coordinate of the vertical middle of the screen
     */
    public GameWorld(int midPointY) {
        currentState = GameState.READY;
        this.midPointY = midPointY;
        bird = new Bird(33, midPointY -5, 17, 12);
        scroller = new ScrollHandler(this, midPointY + 66); //the grass should start 66 pixels below the midPointY
        ground = new Rectangle(0, midPointY + 66, 136, 11);
    }
        // BEHAVIOURS
    /**
     * Checks the current state of the game before determining which more specific update method to call
     * @param delta the number of seconds (usually a small fraction) that has passed since the last time that the render method (from GameWorld) was called
     */
    public void update(float delta) {
        switch (currentState) {
            case READY:
                updateReady(delta);
                break;

            case RUNNING:
                updateRunning(delta);
                break;
                
            default:
                break;
        }
    }
    /**
     * 
     * @param delta the number of seconds (usually a small fraction) that has passed since the last time that the render method (from GameWorld) was called
     */
    private void updateReady(float delta) {
        // Do nothing for now
    }
    /**
     * The looping method that updates all the game objects as a helper to the GameWorld
     * @param delta the number of seconds (usually a small fraction) that has passed since the last time that the render method (from GameWorld) was called
     */
    public void updateRunning(float delta) {
        // Add a delta cap so that if our game takes too long to update, we won't break our collision detection
        if (delta > .15f) {
            delta = .15f;
        }
        //Gdx.app.log("GameWorld", "update");
        bird.update(delta);
        scroller.update(delta);
        
        if(scroller.collides(bird) && bird.isAlive()) {
            // Clean up on game over
            scroller.stop();
            bird.die();
            AssetLoader.dead.play();
        }
        
        if (Intersector.overlaps(bird.getBoundingCircle(), ground)) {
            scroller.stop();
            bird.die();
            bird.decelerate();
            currentState = GameState.GAMEOVER;
        }
        
        // Check whether our current score is better than the previous high score: if true, update the high score
        if (currentState == GameState.GAMEOVER && score > AssetLoader.getHighScore()) {
            AssetLoader.setHighScore(score);
            currentState = GameState.HIGHSCORE;
        }
    }
    
    // ================================ getters ================================
    public Bird getBird() {
        return bird;
    }
    
    public ScrollHandler getScroller() {
        return scroller;
    }
    
    public int getScore() {
        return score;
    }
    // increment method
    public void addScore(int increment) {
        score += increment;
    }
    // ============================== game states ==============================
    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public void start() {
        currentState = GameState.RUNNING;
    }

    public void restart() {
        currentState = GameState.READY;
        score = 0;
        bird.onRestart(midPointY - 5);
        scroller.onRestart();
        currentState = GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }
    
    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }
    // =========================================================================
} // end GameWorld class

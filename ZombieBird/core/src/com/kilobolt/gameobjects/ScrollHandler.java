/*
 * Mitchell Van Braeckel
 * 22/12/2016
 * ZombieBird Kilobolt Tutorial: ScrollHandler  --> takes care of creating, updating, and handling reset for Scrollable objects
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
    - We need a constructor that receives a y position to know where we need to create our ground (where the grass and the bottom pipes will begin).
    - We need five instance variables: 2 for two Grass objects and 3 for three Pipe objects (for now, we will treat each column of pipes as one Pipe object).
    - We need getters for all of these.
    - We need an update method.
 */
package com.kilobolt.gameobjects;

// imports

import com.kilobolt.gameworld.GameWorld;
import com.kilobolt.zbhelpers.AssetLoader;


/**
 *
 * @author Mitchell Van Braeckel
 */
public class ScrollHandler {
        // ATTRIBUTES
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;
    
    // ScrollHandler will use the constants below to determine how fast we need to scroll
    // and also determine the size of the gap between each pair of pipes.
    public static final int SCROLL_SPEED = -59;
    public static final int PIPE_GAP = 49;
    
    private GameWorld gameWorld;
    
       // CONSTRUCTORS
    /**
     * Primary Constructor - receives a float that tells us where we need to create our Grass and Pipe objects
     * @param gameWorld
     * @param yPos
     */
    public ScrollHandler(GameWorld gameWorld, float yPos) {
        this.gameWorld = gameWorld;
        frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
        backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11, SCROLL_SPEED);

        pipe1 = new Pipe(210, 0, 22, 60, SCROLL_SPEED, yPos);
        pipe2 = new Pipe(pipe1.getTailX() + PIPE_GAP, 0, 22, 70, SCROLL_SPEED, yPos);
        pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED, yPos);
    }
        // BEHAVIOURS
    public void update(float delta) {
        // Update our objects
        frontGrass.update(delta);
        backGrass.update(delta);
        pipe1.update(delta);
        pipe2.update(delta);
        pipe3.update(delta);

        // Check if any of the pipes are scrolled left, and reset accordingly
        if (pipe1.isScrolledLeft()) {
            pipe1.reset(pipe3.getTailX() + PIPE_GAP);
        } else if (pipe2.isScrolledLeft()) {
            pipe2.reset(pipe1.getTailX() + PIPE_GAP);
        } else if (pipe3.isScrolledLeft()) {
            pipe3.reset(pipe2.getTailX() + PIPE_GAP);
        } // end if

        // Same with grass
        if (frontGrass.isScrolledLeft()) {
            frontGrass.reset(backGrass.getTailX());
        } else if (backGrass.isScrolledLeft()) {
            backGrass.reset(frontGrass.getTailX());
        } // end if
    }
    
    public void stop() {
        frontGrass.stop();
        backGrass.stop();
        pipe1.stop();
        pipe2.stop();
        pipe3.stop();
    }
    /**
     * 
     * @param bird
     * @return true if ANY pipe hits the bird
     */
    public boolean collides(Bird bird) {
        // checks if beak is past pipe midpoint to add score
        if (!pipe1.isScored() && pipe1.getX() + (pipe1.getWidth() / 2) < bird.getX() + bird.getWidth()) {
            addScore(1);
            pipe1.setScored(true);
            AssetLoader.coin.play();
        } else if (!pipe2.isScored() && pipe2.getX() + (pipe2.getWidth() / 2) < bird.getX() + bird.getWidth()) {
            addScore(1);
            pipe2.setScored(true);
            AssetLoader.coin.play();
        } else if (!pipe3.isScored() && pipe3.getX() + (pipe3.getWidth() / 2) < bird.getX() + bird.getWidth()) {
            addScore(1);
            pipe3.setScored(true);
            AssetLoader.coin.play();
        }
        
        return (pipe1.collides(bird) || pipe2.collides(bird) || pipe3.collides(bird));
    }
    
    private void addScore(int increment) {
        gameWorld.addScore(increment);
    }
    
    public void onRestart() {
        frontGrass.onRestart(0, SCROLL_SPEED);
        backGrass.onRestart(frontGrass.getTailX(), SCROLL_SPEED);
        pipe1.onRestart(210, SCROLL_SPEED);
        pipe2.onRestart(pipe1.getTailX() + PIPE_GAP, SCROLL_SPEED);
        pipe3.onRestart(pipe2.getTailX() + PIPE_GAP, SCROLL_SPEED);
    }
    // =========================================================================
    // The getters for our five instance variables
    public Grass getFrontGrass() {
        return frontGrass;
    }

    public Grass getBackGrass() {
        return backGrass;
    }

    public Pipe getPipe1() {
        return pipe1;
    }

    public Pipe getPipe2() {
        return pipe2;
    }

    public Pipe getPipe3() {
        return pipe3;
    }
    // =========================================================================
} // end ScrollHandler class

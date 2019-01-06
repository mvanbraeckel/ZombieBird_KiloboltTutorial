/*
 * Mitchell Van Braeckel
 * 21/12/2016
 * ZombieBird Kilobolt Tutorial: Pipe           --> defines properties of a Pipe as a Scrollable object
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.gameobjects;

// imports
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import java.util.Random;
/**
 *
 * @author Mitchell Van Braeckel
 */
public class Pipe extends Scrollable{
        // ATTRIBUTES
    private Random r;
    
    private Rectangle skullUp, skullDown, barUp, barDown;
    
    public static final int VERTICAL_GAP = 45;
    public static final int SKULL_WIDTH = 24;
    public static final int SKULL_HEIGHT = 11;
    private float groundY;
    
    private boolean isScored = false;
    
        // CONSTRUCTORS
    /**
     * Primary Constructor - instantiates a new Pipe
     * @param x the x-coordinate of the object
     * @param y the x-coordinate of the object
     * @param w the width of the object
     * @param h the height of the object
     * @param scrollSpeed the scrolling speed
     */
    public Pipe(float x, float y, int w, int h, float scrollSpeed, float groundY) {
        super(x, y, w, h, scrollSpeed);
        r = new Random();   //init Random object for rnd# gen
        skullUp = new Rectangle();
        skullDown = new Rectangle();
        barUp = new Rectangle();
        barDown = new Rectangle();
        this.groundY = groundY;
    }
        // BEHAVIOURS
    @Override
    public void update(float delta) {
        super.update(delta);

        // The set() method allows you to set the top left corner's x, y coordinates, along with the width and height of the rectangle
        barUp.set(position.x, position.y, width, height);
        barDown.set(position.x, position.y + height + VERTICAL_GAP, width, groundY - (position.y + height + VERTICAL_GAP));

            // Since skull width is 24 and bar width is only 22 pixels wide,
            // the skull must be shifted 1 pixel to the left
            // (so that the skull is centered with respect to its bar).
            // This shift is equivalent to: (SKULL_WIDTH - width) / 2
        skullUp.set(position.x - (SKULL_WIDTH - width) / 2, position.y + height - SKULL_HEIGHT, SKULL_WIDTH, SKULL_HEIGHT);
        skullDown.set(position.x - (SKULL_WIDTH - width) / 2, barDown.y, SKULL_WIDTH, SKULL_HEIGHT);
    }
    /**
     * Checks for collision: (1) if the bird passes pipe horizontally (2) hit-box intersect of bird and pipe
     * @param bird
     * @return 
     */
    public boolean collides(Bird bird) {
        if (position.x < bird.getX() + bird.getWidth()) {
            return (Intersector.overlaps(bird.getBoundingCircle(), barUp)
                    || Intersector.overlaps(bird.getBoundingCircle(), barDown)
                    || Intersector.overlaps(bird.getBoundingCircle(), skullUp)
                    || Intersector.overlaps(bird.getBoundingCircle(), skullDown));
        }
        return false;
    }
    /**
     * Reset: x-position & height of the pipe
     * @param newX the new x-position after it is reset
     */
     @Override
    public void reset(float newX) {
        super.reset(newX);
        height = r.nextInt(90) + 15;    //changes height to a random number
        isScored = false;
    }
    
    public void onRestart(float x, float scrollSpeed) {
        velocity.x = scrollSpeed;
        reset(x);
    }
    // ================================ getters ================================
    public Rectangle getSkullUp() {
        return skullUp;
    }

    public Rectangle getSkullDown() {
        return skullDown;
    }

    public Rectangle getBarUp() {
        return barUp;
    }

    public Rectangle getBarDown() {
        return barDown;
    }
    
    public boolean isScored() {
        return isScored;
    }
    // ================================ setters ================================
    public void setScored(boolean b) {
        isScored = b;
    }
    // =========================================================================
} // end Pipe class

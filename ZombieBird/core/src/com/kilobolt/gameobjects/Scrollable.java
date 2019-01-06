/*
 * Mitchell Van Braeckel
 * 21/12/2016
 * ZombieBird Kilobolt Tutorial: Scrollable     --> defines all the shared properties of the Pipe and the Grass objects
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.gameobjects;

// imports
import com.badlogic.gdx.math.Vector2;
/**
 *
 * @author Mitchell Van Braeckel
 */
public class Scrollable {
        // ATTRIBUTES
    protected Vector2 position, velocity;
    protected int width, height;
    protected boolean isScrolledLeft;
    
        // CONSTRUCTORS
    /**
     * Primary Constructor - instantiates a new Scrollable object
     * @param x the x-coordinate of the object
     * @param y the x-coordinate of the object
     * @param w the width of the object
     * @param h the height of the object
     * @param scrollSpeed the scrolling speed
     */
    public Scrollable(float x, float y, int w, int h, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.width = w;
        this.height = h;
        isScrolledLeft = false;
    }
        // BEHAVIOURS
    /**
     * The looping method that updates all the game objects
     * @param delta the number of seconds (usually a small fraction) that has passed since the last time that the render method (from GameWorld) was called
     */
    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));    //add the updated scaled velocity to the bird's position (gives us our new position)
        // Checks if the Scrollable object is no longer visible:
        if (position.x + width < 0) {
            isScrolledLeft = true;
        } // end if
    }
    /**
     * Reset: Should Override in subclass for more specific behavior.
     * @param newX the new x-position after it is reset
     */
    public void reset(float newX) {
        position.x = newX;
        isScrolledLeft = false;
    }
    
    public void stop() {
        velocity.x = 0;
    }
    // =========================================================================
    // Getters for instance variables
    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }

    public float getTailX() {
        return position.x + width;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
    // =========================================================================
} // end Scrollable class

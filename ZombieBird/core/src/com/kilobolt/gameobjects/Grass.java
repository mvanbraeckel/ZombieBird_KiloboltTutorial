/*
 * Mitchell Van Braeckel
 * 21/12/2016
 * ZombieBird Kilobolt Tutorial: Grass          --> defines properties of Grass as a Scrollable object
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.gameobjects;

// imports

/**
 *
 * @author Mitchell Van Braeckel
 */
public class Grass extends Scrollable {
        // ATTRIBUTES
    
        // CONSTRUCTORS
    /**
     * Primary Constructor - instantiates a new Grass
     * @param x the x-coordinate of the object
     * @param y the x-coordinate of the object
     * @param w the width of the object
     * @param h the height of the object
     * @param scrollSpeed the scrolling speed
     */
    public Grass(float x, float y, int w, int h, float scrollSpeed) {
        super(x, y, w, h, scrollSpeed);
    }
        // BEHAVIOURS
    public void onRestart(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
    }
} // end Grass class

/*
 * Mitchell Van Braeckel
 * 15/12/2016
 * ZombieBird Kilobolt Tutorial: Bird           --> AKA Flaps (the main character)
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.gameobjects;

// imports
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Vector2;
import com.kilobolt.zbhelpers.AssetLoader;
/**
 *
 * @author Mitchell Van Braeckel
 */
public class Bird {
        // ATTRIBUTES
    private Vector2 position, velocity, acceleration;
    
    private float rotation; //for handling bird rotation
    private int width, height;
    
    private Circle boundingCircle;
    private boolean isAlive;
    
        // CONSTRUCTORS
    /**
     * Primary Constructor - instantiates a new Bird w/ default values
     * @param x the x-coordinate of the Bird
     * @param y the y-coordinate of the Bird
     * @param w the width of the Bird (in pixels)
     * @param h the height of the Bird (in pixels)
     */
    public Bird(float x, float y, int w, int h) {
        width = w;
        height = h;
        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 460);
        boundingCircle = new Circle();
        isAlive = true;
    }
        // BEHAVIOURS
    /**
     * Updates the Bird's position using acceleration and velocity (called when GameWorld updates)
     * @param delta the number of seconds (usually a small fraction) that has passed since the last time that the render method (from GameWorld) was called
     */
    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));    //add our scaled acceleration vector to our velocity vector (gives us our new velocity)
        
        if(velocity.y > 200) {  //sets a velocity cap of 200
            velocity.y = 200;
        } // end if
        
        // CEILING CHECK 
        if (position.y < -13) {
            position.y = -13;
            velocity.y = 0;
        }
        position.add(velocity.cpy().scl(delta));        //add the updated scaled velocity to the bird's position (gives us our new position)
        /* NOTE: scaled means multiply the acceleration and velocity vectors by the delta,
                 which is the amount of time that has passed since the update method was previously called (this has a normalizing effect)
                 By scaling our Vectors with delta, we can achieve frame-rate independent movement
                 eg. If the update method took twice as long to execute, then we just move our character by 2x the original velocity, and so on*/
        boundingCircle.set(position.x + 9, position.y + 6, 6.5f);   //Set the circle's center to be (9, 6) with respect to the bird.
                                                                    //Set the circle's radius to be 6.5f;
        
        // Rotate counterclockwise (negative change in rotation)
        if (velocity.y < 0) {
            rotation -= 600 * delta;    //scales rotation by delta
            if (rotation < -20) {       //rotation cap
                rotation = -20;
            } // end if
        } // end if

        // Rotate clockwise (positive change in rotation)
        if (isFalling() || !isAlive) {
            rotation += 480 * delta;    //scales rotation by delta
            if (rotation > 90) {        //rotation cap
                rotation = 90;
            } // end if
        } // end if
    }
    /**
     * Used to decide when the bird should begin rotating downwards
     * @return true if the bird is falling
     */
    public boolean isFalling() {
        return velocity.y > 110;
    }
    /**
     * Used to determine when the bird should stop animating (streamline fall)
     * @return true if bird is streamlining
     */
    public boolean shouldntFlap() {
        return velocity.y > 70 || !isAlive;
    }
    /**
     * action method - called when the screen is clicked or touched
     */
    public void onClick() {
        if(isAlive) {
            AssetLoader.flap.play();    //plays flapping sound
            velocity.y = -140;  //sets velocity to upwards 140
        }
    }
    /**
     * mutator method - kills the bird and stops movement
     */
    public void die() {
        isAlive = false;
        velocity.y = 0;
    }
    /**
     * mutator method - zeroes acceleration when dead
     */
    public void decelerate() {
        // We want the bird to stop accelerating downwards once it is dead.
        acceleration.y = 0;
    }
    /**
     * Undo all the changes that were done during the course of the gameplay
     * @param y 
     */
    public void onRestart(int y) {
        rotation = 0;
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 460;
        isAlive = true;
    }
    // =========================================================================
    public boolean isAlive() {  //getter
        return isAlive;
    }
    // =========================================================================
    // getters for x-position, y-position, width, height, and rotation of the Bird
    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getRotation() {
        return rotation;
    }
    
    public Circle getBoundingCircle() {
        return boundingCircle;
    }
    // =========================================================================
} // end Bird class

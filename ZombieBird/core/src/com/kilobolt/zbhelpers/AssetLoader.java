/*
 * Mitchell Van Braeckel
 * 19/12/2016
 * ZombieBird Kilobolt Tutorial: AssetLoader    --> helper class for loading images, sounds, animations, etc
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.zbhelpers;

// imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
/**
 *
 * @author Mitchell Van Braeckel
 */
public class AssetLoader {
        // ATTRIBUTES
    public static Texture texture, logoTexture;
    public static TextureRegion logo, zbLogo, bg, grass, bird, birdDown, birdUp,
            skullUp, skullDown, bar, playButtonUp, playButtonDown;
    public static Animation birdAnimation;
    public static Sound dead, flap, coin;
    public static BitmapFont font, shadow;
    public static Preferences prefs;
    
        // CONSTRUCTORS (no constructors, only one copy is created)
        // BEHAVIOURS
    /**
     * called when the game starts up
     */
    public static void load() {
        logoTexture = new Texture(Gdx.files.internal("logo.png"));
        logoTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);

        logo = new TextureRegion(logoTexture, 0, 0, 512, 114);
        
        texture = new Texture(Gdx.files.internal("texture.png"));
        //each pixel will retain its shape rather than becoming blurry when art is stretched
        texture.setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
        
        playButtonUp = new TextureRegion(texture, 0, 83, 29, 16);
        playButtonDown = new TextureRegion(texture, 29, 83, 29, 16);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);

        zbLogo = new TextureRegion(texture, 0, 55, 135, 24);
        zbLogo.flip(false, true);
        
        // Since libGDX uses y-up coordinate system, we must flip all images because we are using a y-down system
        bg = new TextureRegion(texture, 0, 0, 136, 43);
        bg.flip(false, true);

        grass = new TextureRegion(texture, 0, 43, 143, 11);
        grass.flip(false, true);

        birdDown = new TextureRegion(texture, 136, 0, 17, 12);
        birdDown.flip(false, true);

        bird = new TextureRegion(texture, 153, 0, 17, 12);
        bird.flip(false, true);

        birdUp = new TextureRegion(texture, 170, 0, 17, 12);
        birdUp.flip(false, true);
        
        //create Bird Animation w/ 3 frames that changes frames every 0.06 seconds (down, middle, up, middle, down, ...etc)
        TextureRegion[] birds = {birdDown, bird, birdUp};           //creates an array of TextureRegions
        birdAnimation = new Animation(0.06f, (Object[])birds);                //creates a new Animation in which each frame is 0.06 seconds long, using the above array
        birdAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);//sets play mode to be ping pong, in which we will see a bounce

        skullUp = new TextureRegion(texture, 192, 0, 24, 14);
        skullDown = new TextureRegion(skullUp); // Create by flipping existing skullUp
        skullDown.flip(false, true);

        bar = new TextureRegion(texture, 136, 16, 22, 3);
        bar.flip(false, true);
        
        dead = Gdx.audio.newSound(Gdx.files.internal("dead.wav"));
        flap = Gdx.audio.newSound(Gdx.files.internal("flap.wav"));
        coin = Gdx.audio.newSound(Gdx.files.internal("coin.wav"));
        
        // load the files and change them to an appropriate size for us to use
        font = new BitmapFont(Gdx.files.internal("text.fnt"));
        //font.setScale(.25f, -.25f);
        font.getData().setScale(.25f, -.25f);
        shadow = new BitmapFont(Gdx.files.internal("shadow.fnt"));
        //shadow.setScale(.25f, -.25f);
        shadow.getData().setScale(.25f, -.25f);
        
        // Create (or retrieve existing) preferences file
        prefs = Gdx.app.getPreferences("ZombieBird");

        // Provide default high score of 0
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }
    }
    /**
     * called when the game is being closed
     */
    public static void dispose() {
        texture.dispose();  //We must dispose of the texture when we are finished.
        dead.dispose();
        flap.dispose();     //dispose sounds
        coin.dispose();
        font.dispose();
        shadow.dispose();
    }
    
    // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }
} // end AssetLoader class

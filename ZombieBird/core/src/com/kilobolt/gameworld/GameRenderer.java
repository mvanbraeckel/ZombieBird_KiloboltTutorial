/*
 * Mitchell Van Braeckel
 * 14/12/2016
 * ZombieBird Kilobolt Tutorial: GameRenderer   --> RENDERING helper class for GameScreen
 * --> essentially a clone/copy of the FlappyBird game
 * --> tutorial walkthrough provided by http://www.kilobolt.com/
 */
package com.kilobolt.gameworld;

// imports
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.kilobolt.gameobjects.Bird;
import com.kilobolt.gameobjects.Grass;
import com.kilobolt.gameobjects.Pipe;
import com.kilobolt.gameobjects.ScrollHandler;
import com.kilobolt.zbhelpers.AssetLoader;
/**
 *
 * @author Mitchell Van Braeckel
 */
public class GameRenderer {
        // ATTRIBUTES
    private GameWorld myWorld;  //GameRenderer needs to have a reference to the GameWorld that it will be drawing,
                                //whenever we want to refer to an object inside our GameWorld, we can retrieve it
    private OrthographicCamera cam;         //camera that views 3D as 2D
    private ShapeRenderer shapeRenderer;    //draws shapes and lines for us
    
    private SpriteBatch batcher;            //draws images for us using the indices provided (x, y, width and height, typically)
    private int midPointY;
    private int gameHeight;
    
    // game objects
    private Bird bird;
    private ScrollHandler scroller;
    private Grass frontGrass, backGrass;
    private Pipe pipe1, pipe2, pipe3;
    
    // game assets
    private TextureRegion bg, grass;
    private Animation birdAnimation; 
    private TextureRegion birdMid, birdDown, birdUp;
    private TextureRegion skullUp, skullDown, bar;
    
        // CONSTRUCTORS
    /**
     * Primary Constructor - instantiates a new GameRenderer (to render the game) as a helper to the GameWorld
     * @param world the GameWorld that the GameRenderer will render for
     * @param gameHeight 
     * @param midPointY
     */
    public GameRenderer(GameWorld world, int gameHeight, int midPointY) {
        myWorld = world;
        this.gameHeight = gameHeight;
        this.midPointY = midPointY;
        
        cam = new OrthographicCamera(); //init cam
        cam.setToOrtho(true, 136, gameHeight);  //true = we want an orthographic projection || width || height
                                                //since GameScreen res = 272,408 ; game scaled x2 when drawn
        batcher = new SpriteBatch();                    //init batcher
        batcher.setProjectionMatrix(cam.combined);      //attach it to cam
        shapeRenderer = new ShapeRenderer();            //init shapeRenderer
        shapeRenderer.setProjectionMatrix(cam.combined);//attach it to cam
        
        // Call helper methods to initialize instance variables
        initGameObjects();
        initAssets();
    }
        // helper constructors
    /**
     * initializes game objects
     */
    private void initGameObjects() {
        bird = myWorld.getBird();
        scroller = myWorld.getScroller();
        frontGrass = scroller.getFrontGrass();
        backGrass = scroller.getBackGrass();
        pipe1 = scroller.getPipe1();
        pipe2 = scroller.getPipe2();
        pipe3 = scroller.getPipe3();
    }
    /**
     * initializes game assets
     */
    private void initAssets() {
        bg = AssetLoader.bg;
        grass = AssetLoader.grass;
        birdAnimation = AssetLoader.birdAnimation;
        birdMid = AssetLoader.bird;
        birdDown = AssetLoader.birdDown;
        birdUp = AssetLoader.birdUp;
        skullUp = AssetLoader.skullUp;
        skullDown = AssetLoader.skullDown;
        bar = AssetLoader.bar;
    }

        // BEHAVIOURS
    /**
     * The looping method that renders the game
     * @param runTime uses this value (and the previously determined frame duration) to determine which TextureRegion to display
     */
    public void render(float runTime) {
        // Fill the entire screen with black, to prevent potential flickering
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
            // Begin ShapeRenderer
        shapeRenderer.begin(ShapeType.Filled);
        
        // Draw Background color
        shapeRenderer.setColor(55 / 255.0f, 80 / 255.0f, 100 / 255.0f, 1);
        shapeRenderer.rect(0, 0, 136, midPointY + 66);
        // Draw Grass
        shapeRenderer.setColor(111 / 255.0f, 186 / 255.0f, 45 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 66, 136, 11);
        // Draw Dirt
        shapeRenderer.setColor(147 / 255.0f, 80 / 255.0f, 27 / 255.0f, 1);
        shapeRenderer.rect(0, midPointY + 77, 136, 52);
        
        shapeRenderer.end();    // End ShapeRenderer
        
            // Begin SpriteBatch
        batcher.begin();
        
        batcher.disableBlending();  //Disable transparency (good for performance when drawing images that do not require transparency)
        batcher.draw(bg, 0, midPointY +23, 136, 43);
        
        drawGrass();    // 1. Draw Grass
        drawPipes();    // 2. Draw Pipes
        
        batcher.enableBlending();   //Enable transparency (bird needs it)(so do Skulls)
        
        drawSkulls();   // 3. Draw Skulls (requires transparency)
        
            // Draws bird at its coordinates
        // Pass in the runTime variable to get the current frame & Checks if the bird is flapping or streamlined
        if (bird.shouldntFlap()) {
            batcher.draw(birdMid, bird.getX(), bird.getY(), bird.getWidth() / 2.0f, bird.getHeight() / 2.0f,
                    bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        } else {
            batcher.draw((TextureRegion)birdAnimation.getKeyFrame(runTime), bird.getX(), bird.getY(), bird.getWidth() / 2.0f,
                    bird.getHeight() / 2.0f, bird.getWidth(), bird.getHeight(), 1, 1, bird.getRotation());
        } // end if
        
        // TEMPORARY CODE! We will fix this section later:

        if (myWorld.isReady()) {
            // Draw shadow first
            AssetLoader.shadow.draw(batcher, "Touch me", (136 / 2) - (42), 76);
            // Draw text
            AssetLoader.font .draw(batcher, "Touch me", (136 / 2) - (42 - 1), 75);
        } else {
            if (myWorld.isGameOver() || myWorld.isHighScore()) {
                if (myWorld.isGameOver()) {
                    AssetLoader.shadow.draw(batcher, "Game Over", 25, 56);
                    AssetLoader.font.draw(batcher, "Game Over", 24, 55);

                    AssetLoader.shadow.draw(batcher, "High Score:", 23, 106);
                    AssetLoader.font.draw(batcher, "High Score:", 22, 105);

                    String highScore = AssetLoader.getHighScore() + "";

                    // Draw shadow first
                    AssetLoader.shadow.draw(batcher, highScore, (136 / 2) - (3 * highScore.length()), 128);
                    // Draw text
                    AssetLoader.font.draw(batcher, highScore, (136 / 2) - (3 * highScore.length() - 1), 127);
                } else {
                    AssetLoader.shadow.draw(batcher, "High Score!", 19, 56);
                    AssetLoader.font.draw(batcher, "High Score!", 18, 55);
                }

                AssetLoader.shadow.draw(batcher, "Try again?", 23, 76);
                AssetLoader.font.draw(batcher, "Try again?", 24, 75);

                // Convert integer into String
                String score = myWorld.getScore() + "";

                // Draw shadow first
                AssetLoader.shadow.draw(batcher, score, (136 / 2) - (3 * score.length()), 12);
                // Draw text
                AssetLoader.font.draw(batcher, score, (136 / 2) - (3 * score.length() - 1), 11);
            }

            // Convert integer into String
            String score = myWorld.getScore() + "";

            // Draw shadow first
            AssetLoader.shadow.draw(batcher, "" + myWorld.getScore(), (136 / 2) - (3 * score.length()), 12);
            // Draw text
            AssetLoader.font.draw(batcher, "" + myWorld.getScore(), (136 / 2) - (3 * score.length() - 1), 11);
        }

        batcher.end();  // End SpriteBatch
        
            /*/ 2. We draw the Filled rectangle
        shapeRenderer.begin(ShapeType.Filled);                          // Tells shapeRenderer to begin drawing filled shapes
        shapeRenderer.setColor(87/255.0f, 109/255.0f, 120/255.0f, 1);   // Chooses RGB Color of 87, 109, 120 at full opacity
        shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y,    // Draws the rectangle from myWorld (Using ShapeType.Filled)
                myWorld.getRect().width, myWorld.getRect().height);
        shapeRenderer.end();    // Tells the shapeRenderer to finish rendering  // We MUST do this every time

            // 3. We draw the rectangle's outline
        shapeRenderer.begin(ShapeType.Line);                            // Tells shapeRenderer to draw an outline of the following shapes
        shapeRenderer.setColor(255/255.0f, 109/255.0f, 120/255.0f, 1);  // Chooses RGB Color of 255, 109, 120 at full opacity
        shapeRenderer.rect(myWorld.getRect().x, myWorld.getRect().y,    // Draws the rectangle from myWorld (Using ShapeType.Line)
                myWorld.getRect().width, myWorld.getRect().height);
        shapeRenderer.end();    // Tells the shapeRenderer to finish rendering  // We MUST do this every time*/
            
            /*/ Draws the Bird's hitbox (as a red circle)
        shapeRenderer.begin(ShapeType.Filled);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.circle(bird.getBoundingCircle().x, bird.getBoundingCircle().y, bird.getBoundingCircle().radius);
        
        // Excuse the mess below. Temporary code for testing bounding rectangles.
            
        // Bar up for pipes 1 2 and 3
        shapeRenderer.rect(pipe1.getBarUp().x, pipe1.getBarUp().y,
                pipe1.getBarUp().width, pipe1.getBarUp().height);
        shapeRenderer.rect(pipe2.getBarUp().x, pipe2.getBarUp().y,
                pipe2.getBarUp().width, pipe2.getBarUp().height);
        shapeRenderer.rect(pipe3.getBarUp().x, pipe3.getBarUp().y,
                pipe3.getBarUp().width, pipe3.getBarUp().height);

        // Bar down for pipes 1 2 and 3
        shapeRenderer.rect(pipe1.getBarDown().x, pipe1.getBarDown().y,
                pipe1.getBarDown().width, pipe1.getBarDown().height);
        shapeRenderer.rect(pipe2.getBarDown().x, pipe2.getBarDown().y,
                pipe2.getBarDown().width, pipe2.getBarDown().height);
        shapeRenderer.rect(pipe3.getBarDown().x, pipe3.getBarDown().y,
                pipe3.getBarDown().width, pipe3.getBarDown().height);

        // Skull up for Pipes 1 2 and 3
        shapeRenderer.rect(pipe1.getSkullUp().x, pipe1.getSkullUp().y,
                pipe1.getSkullUp().width, pipe1.getSkullUp().height);
        shapeRenderer.rect(pipe2.getSkullUp().x, pipe2.getSkullUp().y,
                pipe2.getSkullUp().width, pipe2.getSkullUp().height);
        shapeRenderer.rect(pipe3.getSkullUp().x, pipe3.getSkullUp().y,
                pipe3.getSkullUp().width, pipe3.getSkullUp().height);

        // Skull down for Pipes 1 2 and 3
        shapeRenderer.rect(pipe1.getSkullDown().x, pipe1.getSkullDown().y,
                pipe1.getSkullDown().width, pipe1.getSkullDown().height);
        shapeRenderer.rect(pipe2.getSkullDown().x, pipe2.getSkullDown().y,
                pipe2.getSkullDown().width, pipe2.getSkullDown().height);
        shapeRenderer.rect(pipe3.getSkullDown().x, pipe3.getSkullDown().y,
                pipe3.getSkullDown().width, pipe3.getSkullDown().height);

        shapeRenderer.end();*/
    }
    
    // ================================= draw ==================================
    /**
     * Draws the Grass
     */
    private void drawGrass() {
        batcher.draw(grass, frontGrass.getX(), frontGrass.getY(), frontGrass.getWidth(), frontGrass.getHeight());
        batcher.draw(grass, backGrass.getX(), backGrass.getY(), backGrass.getWidth(), backGrass.getHeight());
    }
    /**
     * Draws the Skulls
     */
    private void drawSkulls() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.

        batcher.draw(skullUp, pipe1.getX() - 1, pipe1.getY() + pipe1.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe1.getX() - 1, pipe1.getY() + pipe1.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe2.getX() - 1, pipe2.getY() + pipe2.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe2.getX() - 1, pipe2.getY() + pipe2.getHeight() + 45, 24, 14);

        batcher.draw(skullUp, pipe3.getX() - 1, pipe3.getY() + pipe3.getHeight() - 14, 24, 14);
        batcher.draw(skullDown, pipe3.getX() - 1, pipe3.getY() + pipe3.getHeight() + 45, 24, 14);
    }
    /**
     * Draws the Pipes
     */
    private void drawPipes() {
        // Temporary code! Sorry about the mess :)
        // We will fix this when we finish the Pipe class.
        
        batcher.draw(bar, pipe1.getX(), pipe1.getY(), pipe1.getWidth(), pipe1.getHeight());
        batcher.draw(bar, pipe1.getX(), pipe1.getY() + pipe1.getHeight() + 45, pipe1.getWidth(), midPointY + 66 - (pipe1.getHeight() + 45));

        batcher.draw(bar, pipe2.getX(), pipe2.getY(), pipe2.getWidth(), pipe2.getHeight());
        batcher.draw(bar, pipe2.getX(), pipe2.getY() + pipe2.getHeight() + 45, pipe2.getWidth(), midPointY + 66 - (pipe2.getHeight() + 45));

        batcher.draw(bar, pipe3.getX(), pipe3.getY(), pipe3.getWidth(), pipe3.getHeight());
        batcher.draw(bar, pipe3.getX(), pipe3.getY() + pipe3.getHeight() + 45, pipe3.getWidth(), midPointY + 66 - (pipe3.getHeight() + 45));
    }
    // =========================================================================
} // end GameRenderer class

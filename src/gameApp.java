import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;

/***
 * Step 0 for keyboard control - Import
 */


//*******************************************************************************
// Class Definition Section

public class gameApp implements Runnable, KeyListener {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window

    public boolean gameOver = false;
    final int WIDTH = 1100;
    final int HEIGHT = 600;

    //Declare the variables needed for the graphics
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;

    public BufferStrategy bufferStrategy;
    public Image backgroundPic;
    public Image marioPic;
    public Image mushroomPic;
    public Image goombaPic;
    public Image firemarioPic;
    public Image smallmarioPic;
    public Image billPic;
    public Image gameoverPic;
    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    public Mario marioFigure;
    public Mario mushroomFigure;
    public Mario goombaFigure;
    public Bill billFigure;
    public Bill[] bulletBill;
    public SoundFile thememusic;
    public SoundFile powerdown;
    public SoundFile powerup;
    public SoundFile die;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        gameApp ex = new gameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }

    public gameApp() {
        setUpGraphics();
        canvas.addKeyListener(this);

        marioPic = Toolkit.getDefaultToolkit().getImage("mario8bit.png");
        marioFigure = new Mario("mario", 200, 450);
        marioFigure.lives = 2;
        marioFigure.dx = 4;
        marioFigure.dy = -4;


        mushroomPic = Toolkit.getDefaultToolkit().getImage("mushroom8bit.png");
        mushroomFigure = new Mario("mushroom", 600, 480);
        // mushroomFigure.dy = 0;
        mushroomFigure.width = 40;
        mushroomFigure.height = 40;

        goombaPic = Toolkit.getDefaultToolkit().getImage("goomba8bit.png");
        goombaFigure = new Mario ("goomba", 50, 480);
        goombaFigure.dy = 0;
        goombaFigure.width = 40;
        goombaFigure.height = 40;

        billPic = Toolkit.getDefaultToolkit().getImage("bulletbill.png");
        billFigure = new Bill (500, 300, -3, 0, billPic);
        billFigure.width = 40;
        billFigure.height = 40;
        billFigure.dx = -3;
        bulletBill = new Bill[5];
        // construct bulletBill array
        for (int x = 0; x < 5; x++) {
            bulletBill[x] = new Bill((int) (Math.random() * 600 + 400), (int) (Math.random() * 500), -3, 0, billPic);
        }

        smallmarioPic = Toolkit.getDefaultToolkit().getImage("smallmario8bit.png");

        firemarioPic = Toolkit.getDefaultToolkit().getImage("firemario8bit.png");

        gameoverPic = Toolkit.getDefaultToolkit().getImage("gameover.png");

        backgroundPic = Toolkit.getDefaultToolkit().getImage("mariobackgroundgif.gif");

        thememusic = new SoundFile("mariotheme.wav");

        powerup = new SoundFile("powerup.wav");

        powerdown = new SoundFile("powerdown.wav");

        die = new SoundFile("die.wav");

        thememusic.loop();
    }

    public void run() {

        //for the moment we will loop things forever.
        while (true) {
            moveThings();  //move all the game objects
            crash();
            mimimizeMario();
            render();  // paint the graphics
            pause(20); // sleep for 10 ms
        }
    }

    public void moveThings() {
        //calls the move( ) code in the objects
        mushroomFigure.mushroomBounce();
        marioFigure.moveOnOwn();
        goombaFigure.bounce();
        billFigure.wrap();
        for (int x = 0; x < 5; x++) {
            if(bulletBill[x] != null){
                bulletBill[x].wrap();

            }
        }
    }

    public void crash(){
        if (mushroomFigure.rec.intersects(marioFigure.rec) && !marioFigure.isCrashing){

            marioFigure.isCrashing = true;
            thememusic.pause();
            powerup.play();

            if (marioFigure.lives == 1) {
                marioPic = Toolkit.getDefaultToolkit().getImage("mariobackgroundgif.gif");
                marioFigure.width = 100;
                marioFigure.height = 80;
            }
            if (marioFigure.lives == 2) {
                marioPic = Toolkit.getDefaultToolkit().getImage("firemario8bit.png");
            }
            if (marioFigure.lives < 3) {
                marioFigure.lives = marioFigure.lives + 1;
                System.out.println(marioFigure.lives);
            }

        }
        // System.out.println(marioFigure.isCrashing);
        if (!marioFigure.rec.intersects(mushroomFigure.rec)) {
            marioFigure.isCrashing = false;
            // System.out.println("not touching mushroom");
        }
        if (!gameOver) {
            thememusic.resume();
        }
    }
    public void mimimizeMario(){

        if (billFigure.rec.intersects(marioFigure.rec) && !marioFigure.minimizingBill) {
            marioFigure.minimizingBill = true;
            thememusic.pause();
            marioFigure.lives = marioFigure.lives - 1;
            System.out.println(marioFigure);
        }
        else if (goombaFigure.rec.intersects(marioFigure.rec) && !marioFigure.isMinimizing) {
            marioFigure.isMinimizing = true;
            thememusic.pause();
            powerdown.play();
            marioFigure.lives = marioFigure.lives - 1;
        }

        for (int x = 0; x < 5; x++) {
            if(bulletBill[x] != null){
                if (bulletBill[x].rec.intersects(marioFigure.rec) && !marioFigure.minimizingBill) {
                    marioFigure.minimizingBill = true;
                    thememusic.pause();
                    marioFigure.lives = marioFigure.lives - 1;
                }
            }
        }

            if (marioFigure.lives == 1) {
                marioPic = Toolkit.getDefaultToolkit().getImage("smallmario8bit.png");
//                marioFigure.lives = marioFigure.lives - 1;
                marioFigure.width = 40;
                marioFigure.height = 40;
                thememusic.resume();
            }
            if (marioFigure.lives == 2) {
                marioPic = Toolkit.getDefaultToolkit().getImage("mario8bit.png");
//                marioFigure.lives = marioFigure.lives - 1;
                thememusic.resume();
            }
            if (marioFigure.lives == 0) {
                gameOver = true;
                thememusic.stop();
                die.play();
                marioFigure.stop();
                goombaFigure.stop();
                mushroomFigure.stop();
                billFigure.stop();
            }

        if (!marioFigure.rec.intersects(goombaFigure.rec)) {
            marioFigure.isMinimizing = false;
        }
        if (!marioFigure.rec.intersects(billFigure.rec)) {
            marioFigure.minimizingBill = false;
        } // (make another boolean (isMinimizing) for bill
    }



    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
    public void keyPressed(KeyEvent event) {
        //This method will do something whenever any key is pressed down.
        //Put if(  statements here
        char key = event.getKeyChar();     //gets the character of the key pressed
        int keyCode = event.getKeyCode();  //gets the keyCode (an integer) of the key pressed
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 39) {// right arrow
            marioFigure.right = true;
        }
        if (keyCode == 83) {// s (move down)
            marioFigure.down = true;
        }
        if (keyCode == 32 && marioFigure.jumps < 3) {// space bar
            marioFigure.dy = -15;
            marioFigure.jumps++;
            System.out.println("jump #: " + marioFigure.jumps);
        }
        if (keyCode == 37) {// left arrow
            marioFigure.left = true;
        }
    }//keyPressed()

    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        //This method will do something when a key is released
        if (keyCode == 39) {
            marioFigure.right = false;
        }
        if (keyCode == 83) {
            marioFigure.down = false;
        }
        if (keyCode == 32 && marioFigure.jumps < 2) {
            marioFigure.dy = -20;
            marioFigure.jumps++;
        }
        if (keyCode == 37) {
            marioFigure.left = false;
        }

    }//keyReleased()
    public void keyTyped(KeyEvent event) {
        // handles a press of a character key (any key that can be printed but not keys like SHIFT)
        // we won't be using this method, but it still needs to be in your program
    }//keyTyped()

    //Graphics setup method
    private void setUpGraphics() {
        frame = new JFrame("Application Template");   //Create the program window or frame.  Names it.

        panel = (JPanel) frame.getContentPane();  //sets up a JPanel which is what goes in the frame
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));  //sizes the JPanel
        panel.setLayout(null);   //set the layout

        // creates a canvas which is a blank rectangular area of the screen onto which the application can draw
        // and trap input events (Mouse and Keyboard events)
        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);  // adds the canvas to the panel.

        // frame operations
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  //makes the frame close and exit nicely
        frame.pack();  //adjusts the frame and its contents so the sizes are at their default or larger
        frame.setResizable(false);   //makes it so the frame cannot be resized
        frame.setVisible(true);      //IMPORTANT!!!  if the frame is not set to visible it will not appear on the screen!

        // sets up things so the screen displays images nicely.
        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");
    }

    //Paints things on the screen using bufferStrategy
    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (!gameOver) {
            g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);

            // if mushroomFigure.isAlive(){
            g.drawImage(mushroomPic, mushroomFigure.xpos, mushroomFigure.ypos, mushroomFigure.width, mushroomFigure.height, null);
            // g.drawRect(mushroomFigure.rec.x, mushroomFigure.rec.y, mushroomFigure.rec.width, mushroomFigure.rec.width);
        // }
            //draw the image of the astronaut

            g.drawImage(marioPic, marioFigure.xpos, marioFigure.ypos, marioFigure.width, marioFigure.height, null);
            // g.drawRect(marioFigure.rec.x, marioFigure.rec.y, marioFigure.rec.width,marioFigure.rec.height);

            g.drawImage(goombaPic, goombaFigure.xpos, goombaFigure.ypos, goombaFigure.width, goombaFigure.height, null);
            // g.drawRect(goombaFigure.rec.x, goombaFigure.rec.y, goombaFigure.rec.width, goombaFigure.rec.height);

            g.drawImage(billPic, billFigure.xpos, billFigure.ypos, billFigure.width, billFigure.height, null);
            g.drawRect(billFigure.rec.x, billFigure.rec.y, billFigure.rec.width, billFigure.rec.height);
            for (int x = 0; x < 4; x++) {
                if (bulletBill[x] != null && bulletBill[x].isAlive) {
                    g.drawImage(bulletBill[x].pic, bulletBill[x].xpos, bulletBill[x].ypos, bulletBill[x].width, bulletBill[x].height, null);
                    g.drawRect(bulletBill[x].rec.x, bulletBill[x].rec.y, bulletBill[x].rec.width, bulletBill[x].rec.height);
                }
            }
        }

        else {
            g.drawImage(gameoverPic,0,0, WIDTH, HEIGHT, null);
        }
        g.dispose();
        bufferStrategy.show();
    }

}



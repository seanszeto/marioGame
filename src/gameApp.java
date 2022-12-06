import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.tools.Tool;


//*******************************************************************************
// Class Definition Section

public class gameApp implements Runnable {

    //Variable Definition Section
    //Declare the variables used in the program
    //You can set their initial values too

    //Sets the width and height of the program window
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
    public Image gameoverPic;

    //Declare the objects used in the program
    //These are things that are made up of more than one variable type
    public Mario marioFigure;
    public Mario mushroomFigure;
    public Mario goombaFigure;

    // Main method definition
    // This is the code that runs first and automatically
    public static void main(String[] args) {
        gameApp ex = new gameApp();   //creates a new instance of the game
        new Thread(ex).start();                 //creates a threads & starts up the code in the run( ) method
    }

public gameApp() {
    setUpGraphics();

    marioPic = Toolkit.getDefaultToolkit().getImage("mario8bit.png");
    marioFigure = new Mario("mario", 10, 100);
    marioFigure.lives = 2;

    mushroomPic = Toolkit.getDefaultToolkit().getImage("mushroom8bit.png");
    mushroomFigure = new Mario("mushroom", 50, 480);
    // mushroomFigure.dy = 0;
    mushroomFigure.width = 40;
    mushroomFigure.height = 40;

    goombaPic = Toolkit.getDefaultToolkit().getImage("goomba8bit.png");
    goombaFigure = new Mario ("goomba", 50, 480);
    goombaFigure.dy = 0;
    goombaFigure.width = 40;
    goombaFigure.height = 40;

    smallmarioPic = Toolkit.getDefaultToolkit().getImage("smallmario8bit.png");
    // smallmarioFigure = new smallMario()

    firemarioPic = Toolkit.getDefaultToolkit().getImage("firemario8bit.png");

    gameoverPic = Toolkit.getDefaultToolkit().getImage("gameover.png");

    backgroundPic = Toolkit.getDefaultToolkit().getImage("marioBackground.png");
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
        marioFigure.bounce();
        goombaFigure.bounce();
    }

    public void crash(){
        if (mushroomFigure.rec.intersects(marioFigure.rec) && !marioFigure.isCrashing){

            marioFigure.isCrashing = true;
            // marioFigure.width = marioFigure.width*2;
            // marioFigure.height = marioFigure.height*2;
            marioPic = Toolkit.getDefaultToolkit().getImage("firemario8bit.png");
            // mushroomFigure.bounce();
            // mushroomFigure.changeDirection();
            marioFigure.width = 100;
            marioFigure.height = 80;
            marioFigure.lives = marioFigure.lives + 1;
            System.out.println(marioFigure.lives);
        }
        if (marioFigure.rec.intersects(mushroomFigure.rec)) {
            marioFigure.isCrashing = false;
        }

    }


    public void mimimizeMario(){
        if (goombaFigure.rec.intersects(marioFigure.rec) && marioFigure.isCrashing == false){
            marioFigure.isCrashing = true;
            marioPic = Toolkit.getDefaultToolkit().getImage("smallmario8bit.png");
            marioFigure.width = 30;
            marioFigure.height = 30;
            marioFigure.lives = marioFigure.lives - 1;
            System.out.println(marioFigure.lives);
        }
        if (marioFigure.rec.intersects(goombaFigure.rec)) {
            marioFigure.isCrashing = false;
        }
        if (marioFigure.lives == 0) {
            backgroundPic = Toolkit.getDefaultToolkit().getImage("gameover.png");

        }
    }



    //Pauses or sleeps the computer for the amount specified in milliseconds
    public void pause(int time ) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

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

        g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);

        if (mushroomFigure.isAlive){
            g.drawImage(mushroomPic, mushroomFigure.xpos, mushroomFigure.ypos, mushroomFigure.width, mushroomFigure.height, null);
            // g.drawRect(mushroomFigure.rec.x, mushroomFigure.rec.y, mushroomFigure.rec.width, mushroomFigure.rec.width);
        }
        //draw the image of the astronaut

        g.drawImage(marioPic, marioFigure.xpos, marioFigure.ypos, marioFigure.width, marioFigure.height, null);
        // g.drawRect(marioFigure.rec.x, marioFigure.rec.y, marioFigure.rec.width,marioFigure.rec.height);

        g.drawImage(goombaPic, goombaFigure.xpos, goombaFigure.ypos, goombaFigure.width, goombaFigure.height, null);
        // g.drawRect(goombaFigure.rec.x, goombaFigure.rec.y, goombaFigure.rec.width, goombaFigure.rec.height);

        g.dispose();
        bufferStrategy.show();
    }

}



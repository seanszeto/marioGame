import java.awt.*;

public class smallMario {
    public String name;               //name of the hero
    public int xpos;                  //the x position
    public int ypos;                  //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;                 //the width of the hero image
    public int height;                //the height of the hero image
    public boolean isAlive;           //a boolean to denote if the hero is alive or dead
    public boolean isCrashing = false;
    public Rectangle rec;

    public smallMario (int pXpos, int pYpos){
        xpos = pXpos;
        xpos = (int)(Math.random()*400+100);
        ypos = pYpos;
        ypos = (int)(Math.random()*500+50);
        dx = 3;
        dy = -3;
        width = 40;
        height = 30;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);
    } // end mario constructor
}

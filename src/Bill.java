import java.awt.*;

public class Bill {
    public String name;               //name of the hero
    public int xpos;                  //the x position
    public int ypos;                  //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;                 //the width of the hero image
    public int height;                //the height of the hero image
    public Rectangle rec;

    public Bill (String pName, int pXpos, int pYpos) {
        name = pName;
        xpos = pXpos;
        ypos = pYpos;
        dx = 0;
        dy = 0;
        width = 50;
        height = 30;
        rec = new Rectangle(xpos, ypos, width, height);
    }

    public void move() { // move
        xpos = xpos + dx;
        ypos = ypos + dy;
    } // end move

    public void stop(){
        dx = 0;
        dy = 0;
    }

    public void wrap() {
        xpos = xpos + dx;

        if (xpos >= 1100) {// left wall
            xpos = 0;
            rec = new Rectangle(xpos,ypos,width,height);
        }
        if (xpos <= 0) {// right wall
            xpos = 1100;
            rec = new Rectangle(xpos,ypos,width,height);
        }
////        if (ypos <= -height && dy < 0) {//top wall
////            ypos = 600;
////        }
////        if (ypos >= 600 && dy > 0) {
////            ypos = -height;
//        }
        rec = new Rectangle(xpos,ypos,width,height);
    }



}

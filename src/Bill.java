import java.awt.*;

public class Bill {
    public int xpos;                  //the x position
    public int ypos;                  //the y position
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public int width;                 //the width of the hero image
    public int height;                //the height of the hero image
    public boolean isAlive;
    public Image pic;
    public Rectangle rec;

    public Bill (int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {
        xpos = pXpos;
        ypos = pYpos;
        width = 40;
        height = 40;
        dx = dxParameter;
        dy = dyParameter;
        isAlive = true;
        pic = picParameter;
        rec = new Rectangle(xpos, ypos, width, height);
    }

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

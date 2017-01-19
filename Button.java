package flap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Button implements Updateable, Renderable {
    private int x, y;
    private BufferedImage buttonPic;
    //private boolean pressed = false;
    private int xmin, xmax, ymin, ymax;
    //Point p = new Point();
    Point p1 = new Point();
    Point p2 = new Point();
    
    public Button(String s, int xin, int yin){
        x = xin;
        y = yin;
        
        try {
                buttonPic = Sprite.getSprite(s);
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        /* USE THIS IF PIC IS DRAWN FROM CENTER
        xmin = xin - (buttonPic.getWidth() / 2);
        xmax = xin + (buttonPic.getWidth() / 2);
        ymin = yin - (buttonPic.getHeight() / 2);
        ymax = yin + (buttonPic.getHeight() / 2);
        */
        //USE THIS IF PIC IS DRAWN FROM TOP RIGHT CORNER
        xmin = xin;
        xmax = xin + buttonPic.getWidth();
        ymin = yin;
        ymax = yin + buttonPic.getHeight();
        p1.x = xmin;
        p1.y = ymin;
        p2.x = xmax;
        p2.y = ymax;
        
        
    }
    
    public Point getP1(){
        return p1;
    }
    public Point getP2(){
        return p2;
    }
    public void setP1(Point p){
        p1 = p;
    }
    public void setP2(Point p){
        p2 = p;
    }
    
    public boolean isClicked(Point p){
        return (p.getX() > xmin && p.getY() > ymin && p.getX() < xmax && p.getY() < ymax);
    }
    
    /*
    public boolean isClicked(){
        boolean pressed = false;
        if(p.getX() > xmin && p.getX() < xmax && 
                    p.getY() > ymin && p.getY() < ymax){
                pressed = true;
        }
        p.x = 0;
        p.y = 0;
        return pressed;
    }
    */

    @Override
    public void update(Input input) {
        /*
        if(input.isMouseClicked()){
            p = input.getMouseCoords();   
        }
        */
    }

    @Override
    public void render(Graphics2D g, float interpolation) {
        g.setColor(Color.blue);
        g.drawImage(buttonPic, x, y, null);
    }
}

package flap;

import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;

public class Input implements KeyListener, MouseListener{
    
    private boolean spacePressed = false;
    private boolean spaceReleased = true;
    private boolean mouseClicked = false;
    private Point p = new Point();
    
    public boolean isSpacePressed() {
        boolean s = spacePressed;
        spacePressed = false;
        return s;
    }
    
    public boolean isMouseClicked(){
        boolean s = mouseClicked;
        mouseClicked = false;
        return s;
    }
    
    public Point getMouseCoords(){
        return p;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE && spaceReleased){
            spacePressed = true;
            spaceReleased = false;
            System.out.println("Space Pressed");
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_SPACE){
            spaceReleased = true;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        mouseClicked = true;
        p.x = e.getX();
        p.y = e.getY();
        //System.out.println(mouseClicked);
        System.out.println(p.x + " , " + p.y);
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
    
}

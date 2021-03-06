package flap;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.PI;
import java.util.Random;

public class Pipes implements Updateable, Renderable {
    
    private int pipeWidth = 100;
    private int pipeHorizontalSpacing = 210;
    private int pipeVerticalSpacing = 180; //change this to make game easier or harder
    /*
    PIPE SPACEING
    0 = Easy    = 220
    1 = Medium  = 180
    2 = Hard    = 150
    */
    
    private float xVel = -5.0f;
    private float x1, x2, x3;
    private float y1, y2, y3;
    
    // the pipe closest to the bird
    private int currentPipe;
    // an array to hold the pipes' coordinates
    private float[][] pipeCoordinates = new float[3][2];
    
    private Random rand;
    private BufferedImage pipePic;
    private BufferedImage bottomPipe;
    private BufferedImage topPipe;
    int pipePicWidth;
    int pipePicHeight;
    
    
    
    public Pipes(int difficultyInt){
        rand = new Random();
        switch(difficultyInt){
            case 0:
                pipeVerticalSpacing = 220;
                break;
            case 1:
                pipeVerticalSpacing = 180;
                break;
            case 2:
                pipeVerticalSpacing = 150;
                break;
        }
        try {
            pipePic = Sprite.getSprite("Pipe.png");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        
        //Scale the image
        AffineTransform scaleTransform = new AffineTransform();
        scaleTransform.scale(3.125, 3.125);
        AffineTransformOp scaleOp = 
                new AffineTransformOp(scaleTransform, AffineTransformOp.TYPE_BILINEAR);
        bottomPipe = scaleOp.filter(pipePic, bottomPipe);
        pipePicWidth = bottomPipe.getWidth();
        pipePicHeight = bottomPipe.getHeight();
        
        //Flip the PipePic
        AffineTransform flip = new AffineTransform();
        flip.rotate(Math.PI, pipePicWidth/2, pipePicHeight/2);
        AffineTransformOp op = new AffineTransformOp(flip, AffineTransformOp.TYPE_BILINEAR);
        topPipe = op.filter(bottomPipe, null);
        
        resetPipes();
    }
    
    public void resetPipes(){
        currentPipe = 0;
        
        x1 = Game.WIDTH * 2;
        x2 = x1 + pipeWidth + pipeHorizontalSpacing;
        x3 = x2 + pipeWidth + pipeHorizontalSpacing;
        
        y1 = getRandomY();
        y2 = getRandomY();
        y3 = getRandomY();
    }
    
    private int getRandomY() {
        return rand.nextInt((int)(Game.HEIGHT * 0.4f) + (Game.HEIGHT / 10));
    }
    
    @Override
    public void update(Input input) {
        x1 += xVel;
        x2 += xVel;
        x3 += xVel;
        
        if(x1 + pipeWidth < 0){
            x1 = Game.WIDTH;
            y1 = getRandomY();
            currentPipe = 1;
        }
        if(x2 + pipeWidth < 0){
            x2 = Game.WIDTH;
            y2 = getRandomY();
            currentPipe = 2;
        }
        if(x3 + pipeWidth < 0){
            x3 = Game.WIDTH;
            y3 = getRandomY();
            currentPipe = 0;
        }
        
        //update the pipe coordinates
        switch(currentPipe){
            case 0:
                pipeCoordinates[0][0] = x1;
                pipeCoordinates[0][1] = y1;
                break;
            case 1:
                pipeCoordinates[1][0] = x2;
                pipeCoordinates[1][1] = y2;
                break;
            case 2:
                pipeCoordinates[2][0] = x3;
                pipeCoordinates[2][1] = y3;
                break;
        }
        
    }
    
    @Override
    public void render(Graphics2D g, float interpolation) {
        g.setColor(Color.RED);
        
        //Pipe1
        g.drawImage(topPipe, (int)(x1 + (xVel * interpolation)), 0, pipeWidth, (int) y1, null);
        g.drawImage(bottomPipe, (int)(x1 + (xVel * interpolation)), (int)(y1 + pipeVerticalSpacing), pipeWidth, Game.HEIGHT, null);
        
        //Pipe2
        g.drawImage(topPipe, (int)(x2 + (xVel * interpolation)), 0, pipeWidth, (int) y2, null);
        g.drawImage(bottomPipe, (int)(x2 + (xVel * interpolation)), (int)(y2 + pipeVerticalSpacing), pipeWidth, Game.HEIGHT, null);
        
        //Pipe3
        g.drawImage(topPipe, (int)(x3 + (xVel * interpolation)), 0, pipeWidth, (int) y3, null);
        g.drawImage(bottomPipe, (int)(x3 + (xVel * interpolation)), (int)(y3 + pipeVerticalSpacing), pipeWidth, Game.HEIGHT, null);
        
        /* RECTANGLES FOR PIPES
        //Pipe1
        g.fillRect((int)(x1 + (xVel * interpolation)), 0, pipeWidth, (int) y1);
        g.fillRect((int)(x1 + (xVel * interpolation)), (int)(y1 + pipeVerticalSpacing), pipeWidth, Game.HEIGHT);
        
        //Pipe2
        g.fillRect((int)(x2 + (xVel * interpolation)), 0, pipeWidth, (int) y2);
        g.fillRect((int)(x2 + (xVel * interpolation)), (int)(y2 + pipeVerticalSpacing), pipeWidth, Game.HEIGHT);
        
        //Pipe3
        g.fillRect((int)(x3 + (xVel * interpolation)), 0, pipeWidth, (int) y3);
        g.fillRect((int)(x3 + (xVel * interpolation)), (int)(y3 + pipeVerticalSpacing), pipeWidth, Game.HEIGHT);
        */
    }
    
    public float[] getCurrentPipe() {
        return pipeCoordinates[currentPipe];
    }
    
    public int getCurrentPipeID(){
        return currentPipe;
    }
    
    public int getPipeHorizontalSpacing() {
        return pipeHorizontalSpacing;
    }
    
    public int getPipeVerticalSpacing() {
        return pipeVerticalSpacing;
    }
    
    public int getPipeWidth(){
        return pipeWidth;
    }
    
    }

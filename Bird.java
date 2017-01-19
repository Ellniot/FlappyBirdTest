package flap;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import javax.imageio.ImageIO;


public class Bird implements Updateable, Renderable {
    
    private int x, y;
    private float yVel;
    private float baseYVel = -6.0f;
    private float gravity = 0.25f;
    
    private Pipes pipes;
    private int scoredPipe = 0;
    private int score = 0, highscore = 0;
    
    private Font gameFont = new Font("Arial", Font.BOLD, 30);
    
    private BufferedImage flapUp;
    private BufferedImage flapDown;
    
    private boolean isBirdDead = true;
    
    public Bird(Pipes pipes){
        resetBird();
        this.pipes = pipes;
        //this.pipes refers to global variable of pipes
        //pipes refers to local variable pipes [i.e. Bird(Pipes pipes)]
        
        try {
            flapUp = Sprite.getSprite("marioUp.png");
            flapDown = Sprite.getSprite("marioDown.png");
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        
    }
    
    public boolean isDead(){
        return !isBirdDead;
    }
    
    public void resetBird() {
        isBirdDead = false;
        x = 100;
        y = 100;
        yVel = baseYVel = -6.0f;
        score = 0;
    }
    
    private void flap() {
        yVel = baseYVel;
    }
    
    //getHighScore()
    
    @Override
    public void update(Input input) {
        y += yVel;
        yVel += gravity;
        
        if(y < 0){
            y = 0;
            yVel = 0;
        }
        
        if(input.isSpacePressed()){
            flap();
        }
        
        float[] pipeCoords = pipes.getCurrentPipe();
        float pipeX = pipeCoords[0];
        float pipeY = pipeCoords[1];
        
        //collision detection
        if((x >= pipeX && x <= pipeX + pipes.getPipeWidth() && (y <= pipeY || y >= pipeY + pipes.getPipeVerticalSpacing())) || y >= Game.HEIGHT){
            pipes.resetPipes();
            isBirdDead = true;
            //resetBird();
            //score = 0;
            
        }
        else{
            int currentPipeID = pipes.getCurrentPipeID();
            score = (scoredPipe != currentPipeID) ? score + 1 : score;
            //       specific pipe != current P then increment score
            scoredPipe = currentPipeID;
        }
    }
    
    @Override
    public void render(Graphics2D g, float interpolation) {
        g.setColor(Color.blue);
        
        g.drawImage(yVel <= 0 ? flapUp : flapDown, (int) x, (int) (y + (yVel * interpolation)), null);
        
        g.setFont(gameFont);
        g.drawString("Score : "+ score, 20, 50);
    }
}

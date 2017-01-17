package flap;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JFrame;
import java.awt.image.BufferStrategy;

public class Game
{
    
    public final static int WIDTH = 800, HEIGHT = 600;
    private String gameName = "Flappy Bird";
    
    private Canvas game = new Canvas();
    
    private Input input;
    
    private ArrayList<Updateable> updateables = new ArrayList<>();
    private ArrayList<Renderable> renderables = new ArrayList<>();
    
    public void addUpdateable(Updateable u){
        updateables.add(u);
    }
    public void removeUpdateable(Updateable u){
        updateables.remove(u);
    }
    
    public void addRenderable(Renderable u){
        renderables.add(u);
    }
    public void removeRenderable(Renderable u){
        renderables.remove(u);
    }
    
    public void start(){
        //init window
        Dimension gameSize = new Dimension(Game.WIDTH, Game.HEIGHT);
        JFrame gameWindow = new JFrame(gameName);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(gameSize);
        gameWindow.setResizable(false);
        gameWindow.setVisible(true);
        game.setSize(gameSize);
        game.setMaximumSize(gameSize);
        game.setMinimumSize(gameSize);
        game.setPreferredSize(gameSize);
        gameWindow.add(game);
        gameWindow.setLocationRelativeTo(null);
        
        //Init input
        input = new Input();
        game.addKeyListener(input);
        
        //game loop
        final int TICKS_PER_SECOND = 60; //max fps
        final int TIME_PER_TICK = 1000 / TICKS_PER_SECOND;
        final int MAX_FRAMESKIPS = 5; //Value can be changed to fix lag
        
        long nextGameTick = System.currentTimeMillis();
        int loops;
        float interpolation;
        
        long timeAtLastFPSCheck = 0;
        int ticks = 0;
        
        boolean running = true;
        while(running){
            //Updating
            loops = 0;
            
            while(System.currentTimeMillis() > nextGameTick && loops < MAX_FRAMESKIPS ){
                update();
                ticks++;
                
                nextGameTick += TIME_PER_TICK;
                loops++;
            }
            
            //Rendering
            interpolation = (float) (System.currentTimeMillis() + TIME_PER_TICK - nextGameTick)
                    / (float) TIME_PER_TICK;
            render(interpolation);
            
            //FPS Check
            if(System.currentTimeMillis() - timeAtLastFPSCheck >= 100){
                System.out.println("FPS: "+ ticks);
                gameWindow.setTitle(gameName + " - FPS: " + ticks);
                ticks = 0;
                timeAtLastFPSCheck = System.currentTimeMillis();
            }
        }
        //Game end
    }
    
    private void update() {
        for(Updateable u : updateables) {
            u.update(input);
        }
    }
    
    private void render(float interpolation) {
        BufferStrategy b = game.getBufferStrategy();
        if(b == null){
            game.createBufferStrategy(2);
            return;
        }
        
        Graphics2D g = (Graphics2D) b.getDrawGraphics();
        g.clearRect(0, 0, game.getWidth(), game.getHeight());
        
        for(Renderable r : renderables) {
            r.render(g, interpolation);
        }
        g.dispose();
        b.show();
    }
}

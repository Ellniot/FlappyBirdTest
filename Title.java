package flap;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Title {
    public final static int WIDTH = 800, HEIGHT = 600;
    private String gameName = "Flappy Bird";
    private Canvas title = new Canvas();
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
        Dimension gameSize = new Dimension(Title.WIDTH, Title.HEIGHT);
        
        JFrame gameWindow = new JFrame(gameName);
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.setSize(gameSize);
        gameWindow.setResizable(false);
        gameWindow.setVisible(true);
        title.setSize(gameSize);
        title.setMaximumSize(gameSize);
        title.setMinimumSize(gameSize);
        title.setPreferredSize(gameSize);
        gameWindow.add(title);
        gameWindow.setLocationRelativeTo(null);
        boolean showingTitle = true;
        
        //Init input
        input = new Input();
        title.addMouseListener(input);
        title.addKeyListener(input);
        Point inputP = new Point();
        
        //Add the Title Image
        Button logo = new Button("title.png", 200, 50);
        renderables.add(logo);
        
        //Add the buttons
        ////button is drawn with top left corner @ coords////
        Button play = new Button("Play.png", 313, 200);
        Button settings = new Button("Settings.png", 245, 350);
        renderables.add(play);
        renderables.add(settings);
        updateables.add(play);
        updateables.add(settings);
        
        int difficultyInt = 1;
        
        //title loop
        while(showingTitle){
            update();
            render(0);
            if (input.isMouseClicked()){
                inputP = input.getMouseCoords();
            }
            
            
            if(settings.isClicked(inputP)){
                Settings s = new Settings();
                difficultyInt = s.start(gameWindow, title, difficultyInt);
            }
            
            else if(play.isClicked(inputP)){
                
                    Game g = new Game();
                    //Init game objects
                    Pipes p = new Pipes(difficultyInt);
                    Bird b = new Bird(p);
        
                    //Add updateables and renderables
                    g.addRenderable(p);
                    g.addUpdateable(p);
        
                    g.addRenderable(b);
                    g.addUpdateable(b);
        
                    //Start
                    g.start(gameWindow, title, b);
            }
            clearP(inputP);
        }
        
        
    }
    
    private void clearP(Point i){
        i.x = 0;
        i.y = 0;
    }
    
    private void update() {
        for(Updateable u : updateables) {
            u.update(input);
        }
    }
    
    private void render(float interpolation) {
        BufferStrategy b = title.getBufferStrategy();
        if(b == null){
            title.createBufferStrategy(2);
            return;
        }
        
        Graphics2D g = (Graphics2D) b.getDrawGraphics();
        g.clearRect(0, 0, title.getWidth(), title.getHeight());
        
        for(Renderable r : renderables) {
            r.render(g, interpolation);
        }
        g.dispose();
        b.show();
    }
}

    


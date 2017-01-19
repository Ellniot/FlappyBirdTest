package flap;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import javax.swing.JFrame;

public class Settings {
    
    public final static int WIDTH = 800, HEIGHT = 600;
    private Input input;
    private Canvas settingsCanvas;
    private int difficultyInt = 1;
    
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
    
    public int start(JFrame wind, Canvas settCanv, int currentDiff){
        settingsCanvas = settCanv;
        boolean running = true;
        input = new Input();
        settingsCanvas.addMouseListener(input);
        Point p = new Point();
        difficultyInt = currentDiff;
        
        //AddButtons
        Button diffE = new Button("DifficultyE.png", 212, 50); //0
        Button diffM = new Button("DifficultyM.png", 183, 50); //1
        Button diffH = new Button("DifficultyH.png", 210, 50); //2
        Button back = new Button("Back.png", 338, 450);
        switch(difficultyInt){
            case 0:
                updateables.add(diffE);
                renderables.add(diffE);
                break;
            case 1:
                updateables.add(diffM);
                renderables.add(diffM);
                break;
            case 2:
                updateables.add(diffH);
                renderables.add(diffH);
                break;
        }
        //updateables.add(diffM);
        //renderables.add(diffM);
        updateables.add(back);
        renderables.add(back);
        
        while(running){
            
            
            if(input.isMouseClicked()){
                p = input.getMouseCoords();
            }
            
            switch(difficultyInt){
                case 0:
                    if(diffE.isClicked(p)){
                        updateables.remove(diffE);
                        renderables.remove(diffE);
                        updateables.add(diffM);
                        renderables.add(diffM);
                        difficultyInt = 1;
                        clearP(p);
                    }
                    break;
                case 1:
                    if(diffM.isClicked(p)){
                        updateables.remove(diffM);
                        renderables.remove(diffM);
                        updateables.add(diffH);
                        renderables.add(diffH);
                        difficultyInt = 2;
                        clearP(p);
                    }
                    break;
                case 2:
                    if(diffH.isClicked(p)){
                        updateables.remove(diffH);
                        renderables.remove(diffH);
                        updateables.add(diffE);
                        renderables.add(diffE);
                        difficultyInt = 0;
                        clearP(p);
                    }
                    break;
            }
            if(back.isClicked(p)){
                //System.out.println("Settings: returning: " + difficultyInt);
                return difficultyInt;
            }
            update();
            render(0);
        }
        return 1;
        
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
        BufferStrategy b = settingsCanvas.getBufferStrategy();
        if(b == null){
            settingsCanvas.createBufferStrategy(2);
            return;
        }
        
        Graphics2D g = (Graphics2D) b.getDrawGraphics();
        g.clearRect(0, 0, settingsCanvas.getWidth(), settingsCanvas.getHeight());
        
        for(Renderable r : renderables) {
            r.render(g, interpolation);
        }
        g.dispose();
        b.show();
    }
}

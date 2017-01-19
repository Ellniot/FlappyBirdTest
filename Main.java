package flap;

public class Main {
    
    /*
    private static void play(){
        Game g = new Game();
        
        //Init game objects
        Pipes p = new Pipes();
        Bird b = new Bird(p);
        
        //Add updateables and renderables
        g.addRenderable(p);
        g.addUpdateable(p);
        
        g.addRenderable(b);
        g.addUpdateable(b);
        
        //Start
        g.start();
    }
    */
    
    public static void main(String[] args) {
        Title t = new Title();
        t.start();  
    }
    
}

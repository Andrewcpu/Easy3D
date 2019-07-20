import constructs.Camera;
import constructs.Point3D;
import draw.Canvas;
import draw.Renderer;
import world.World;

import javax.swing.*;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main extends JFrame {
    public static void main(String[] args) {
        new Main();
    }

    public Main(){
        setTitle("RENDER");
        World world = new World();
        Camera camera = new Camera(new Point3D(2,49,-6)); // both the camera
        Renderer renderer = new Renderer();                         // and the renderer have singletons, unsure why im storing them here. guess we'll find out.
        Canvas canvas = new Canvas(world);
        setBounds(0,0,Canvas.DIMENSION,Canvas.DIMENSION);
        canvas.setBounds(getBounds());
        add(canvas);
        addKeyListener(canvas);
        addMouseListener(canvas);
        addMouseMotionListener(canvas);
        setVisible(true);
        Executors.newSingleThreadScheduledExecutor().scheduleAtFixedRate(()->{
            canvas.repaint();
            world.tick();
        }, 20, 20, TimeUnit.MILLISECONDS);
    }
}

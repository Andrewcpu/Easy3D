package draw;

import constructs.Camera;
import constructs.Point3D;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JComponent implements MouseListener, MouseMotionListener, KeyListener {
    private World world;

    private static Canvas instance = null;

    public static int DIMENSION = 750;


    private List<Integer> keys = new ArrayList<>();


    public static Canvas getInstance() {
        return instance;
    }

    public Canvas(World world){
        instance = this;
        this.world = world;
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        if(Renderer.getInstance().selectedShape != null)
            Renderer.getInstance().selectedShape.destroy();

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


    @Override
    public void mouseDragged(MouseEvent e) {

    }



    private Point lastPoint = new Point(Canvas.DIMENSION / 2, Canvas.DIMENSION / 2);
    @Override
    public void mouseMoved(MouseEvent e) {

        int dx = lastPoint.x - e.getPoint().x;
        int dy = lastPoint.y - e.getPoint().y;

        Camera.getInstance().setRotationHorizontal(Camera.getInstance().getRotationHorizontal() - Math.toRadians(dx));
        Camera.getInstance().setRotationVertical(Camera.getInstance().getRotationVertical() + Math.toRadians(dy));

        lastPoint = e.getPoint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(keys.contains(e.getKeyCode())) return;
        keys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(!keys.contains(e.getKeyCode())) return;
        keys.remove((Integer)e.getKeyCode());
    }

    private void keyboardTicks(){
        if(keys.contains(KeyEvent.VK_W)){
            Camera.getInstance().forward();
        }
        if(keys.contains(KeyEvent.VK_A)){
            Camera.getInstance().left();
        }
        if(keys.contains(KeyEvent.VK_D)){
            Camera.getInstance().right();
        }
        if(keys.contains(KeyEvent.VK_S)){
            Camera.getInstance().backward();
        }
        if(keys.contains(KeyEvent.VK_SHIFT)){
            Camera.getInstance().down();
        }
        if(keys.contains(KeyEvent.VK_SPACE)){
            Camera.getInstance().up();
        }
        if(keys.contains(KeyEvent.VK_R)){
            Camera.getInstance().setRotationVertical(0);
            Camera.getInstance().setRotationHorizontal(0);
            Camera.getInstance().setLocation( Point3D.ORIGIN.clone());
        }
        if(keys.contains(KeyEvent.VK_G)){
            World.getInstance().generate();
        }
        if(keys.contains(KeyEvent.VK_H)){
            World.getInstance().refreshVisibility();
        }
        double speed = 0.05;
        if(keys.contains(KeyEvent.VK_UP)){
            World.getInstance().worldStateZ+=speed;
        }
        if(keys.contains(KeyEvent.VK_DOWN)){
            World.getInstance().worldStateZ-=speed;
        }
        if(keys.contains(KeyEvent.VK_LEFT)){
            World.getInstance().worldStateX-=speed;
        }
        if(keys.contains(KeyEvent.VK_RIGHT)){
            World.getInstance().worldStateX+=speed;
        }
        if(keys.contains(KeyEvent.VK_O)){
            World.getInstance().worldStateY+=speed;
        }
        if(keys.contains(KeyEvent.VK_P)){
            World.getInstance().worldStateY-=speed;
        }

    }

    private Font font = new Font("Calibri", Font.PLAIN, 16);

    @Override
    public void paint(Graphics g){
        g.setFont(font);
        keyboardTicks();
        Renderer.getInstance().render(g);
    }
}

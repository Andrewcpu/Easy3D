package draw;

import constructs.Camera;
import constructs.KeyboardManager;
import constructs.Point3D;
import world.World;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class Canvas extends JComponent implements MouseListener, MouseMotionListener, KeyListener {
    private World world;

    private static Canvas instance = null;

    public static int DIMENSION = 750;




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
        KeyboardManager.getInstance().keyPress(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        KeyboardManager.getInstance().keyRelease(e.getKeyCode());

    }


    private void keyboardTicks(){
        double groundSpeed = 0.05;
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_W)){
            Camera.getInstance().forward();
//            World.getInstance().forward();
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_A)){
            Camera.getInstance().left();



        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_D)){
            Camera.getInstance().right();



        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_S)){
            Camera.getInstance().backward();



        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_SHIFT)){
            Camera.getInstance().down();
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_SPACE)){
            Camera.getInstance().up();
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_R)){
            Camera.getInstance().setRotationVertical(0);
            Camera.getInstance().setRotationHorizontal(0);
            Camera.getInstance().setLocation( new Point3D(0,0,-5));
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_G)){
            World.getInstance().generate();
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_H)){
            World.getInstance().refreshVisibility();
        }
        double speed = 0.05;
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_UP)){
            World.getInstance().worldStateZ+=speed;
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_DOWN)){
            World.getInstance().worldStateZ-=speed;
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_LEFT)){
            World.getInstance().worldStateX-=speed;
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_RIGHT)){
            World.getInstance().worldStateX+=speed;
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_O)){
            World.getInstance().worldStateY+=speed;
        }
        if(KeyboardManager.getInstance().isPressing(KeyEvent.VK_P)){
            World.getInstance().worldStateY-=speed;
        }

    }

    private Font font = new Font("Calibri", Font.PLAIN, 16);

    @Override
    public void paint(Graphics gr){
        BufferedImage img = new BufferedImage(DIMENSION, DIMENSION, BufferedImage.TYPE_INT_ARGB);
        Graphics g = img.getGraphics();

        Graphics2D graphics2D = (Graphics2D) g;
        graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        g.setFont(font);
        keyboardTicks();
        Renderer.getInstance().render(g);
        gr.drawImage(img, 0, 0 , getWidth(), getHeight(), null);
    }
}

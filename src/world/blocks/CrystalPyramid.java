package world.blocks;

import constructs.Point3D;
import world.Pyramid;
import world.World;

import java.awt.*;

public class CrystalPyramid extends Pyramid {

    private Color originalColor = null;
    public CrystalPyramid(Point3D location, int width) {
        super(location, width);
        World.getInstance().registerCrystalPyramid(this);
    }

    @Override
    public void setColor(Color color){
        originalColor = color;
        super.setColor(color);
    }

    @Override
    public void destroy(){
        World.getInstance().getCrystalPyramids().remove(this);
    }

    private int life = 0;
    @Override
    public void tick(){

        double d = Math.abs( Math.cos(Math.toRadians(life)) * 20);

        super.setColor(new Color((int)(originalColor.getRed() + d), (int)(originalColor.getGreen()), (int)(originalColor.getBlue() + d)));
        reinit();
        life++;
        super.tick();
    }
}

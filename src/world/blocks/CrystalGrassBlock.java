package world.blocks;

import constructs.Point3D;
import world.World;

import java.awt.*;

public class CrystalGrassBlock extends CrystalBlock {
    private double closestCrystal = 1000;

    private Color originalColor = null;


    public CrystalGrassBlock(Point3D location, double size) {
        super(location, size);
        findClosestCrystal();
    }

    @Override
    public void setColor(Color color){
        originalColor = color;
        super.setColor(color);
    }

    public void findClosestCrystal(){
        CrystalPyramid closest = null;
        for(CrystalPyramid crystalPyramid : World.getInstance().getCrystalPyramids()){
            if(closest == null)
            {
                closest = crystalPyramid;
                continue;
            }
            if(getLocation().distance(crystalPyramid.getLocation()) < getLocation().distance(closest.getLocation()))
            {
                closest = crystalPyramid;
            }
        }
        if(closest == null){
            closestCrystal = 500; // just a really big number, means nothing
            return;
        }
        closestCrystal = closest.getLocation().distance(this.getLocation());


        double d = (150 - closestCrystal) / 150.0;
        d *= 150;
        if(d > 150.0){
            d = 150.0;
        }
        else if(d < 0){
            d = 0;
        }
        updateColor(d);


    }

    public void updateColor(double d){
        if(originalColor == null) return;
        Color c =new Color((int)d,originalColor.getGreen(),(int)d);
        if(getColor().getRGB() == c.getRGB())
            return;
        super.setColor(c);
        reinit();
    }

    private int life = 0;
    @Override
    public void tick(){
        life++;
        findClosestCrystal();
        super.tick();
    }

}

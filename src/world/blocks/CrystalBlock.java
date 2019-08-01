package world.blocks;

import constructs.Point3D;
import world.World;
import world.shapes.Cube;
import java.awt.*;

public class CrystalBlock extends Cube {

    public double closestCrystal = 1000;


    public CrystalBlock(Point3D location, double size) {
        super(location, size);
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
        closestCrystal = closest.getLocation().distance(getLocation());
    }


    @Override
    public void update(){
        findClosestCrystal();
    }

    @Override
    public void tick(){

    }
}

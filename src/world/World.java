package world;

import constructs.Camera;
import constructs.OpenSimplexNoise;
import constructs.Point3D;
import world.blocks.CrystalBlock;
import world.blocks.CrystalGrassBlock;
import world.blocks.CrystalPyramid;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class World {

    public Color skyColor = new Color(18, 164, 255);

    private static World instance = null;

    public int size = 25;

    public Point3D gravity = new Point3D(0,16,0);

    public static World getInstance(){
        return instance;
    }

    private List<Shape> cubes = new CopyOnWriteArrayList<>();


    private List<CrystalPyramid> crystalPyramids = new ArrayList<>();

    public World(){
        instance = this;
        generate();
    }

    public void registerCrystalPyramid(CrystalPyramid crystalPyramid){
        crystalPyramids.add(crystalPyramid);
    }

    public List<CrystalPyramid> getCrystalPyramids() {
        return crystalPyramids;
    }

    public void setCrystalPyramids(List<CrystalPyramid> crystalPyramids) {
        this.crystalPyramids = crystalPyramids;
    }

    public void generate(){
        cubes.clear();
        crystalPyramids.clear();
        int mapSize = 10;

        for(int x = -mapSize; x<mapSize; x++){
            for(int z = -mapSize; z<mapSize; z++){
                double aY = new OpenSimplexNoise().eval(x/5.0,z/5.0) * World.getInstance().size;
                System.out.println(aY);
                CrystalGrassBlock crystalGrassBlock = new CrystalGrassBlock(new Point3D(x * World.getInstance().size + 1, aY , z * World.getInstance().size + 1), World.getInstance().size);

                int col = (int)(Math.random() * 100);

                crystalGrassBlock.setColor(new Color(col,col * 2,col));
                crystalGrassBlock.reinit();
                cubes.add(crystalGrassBlock);

                if(Math.random() < 0.02){
                    double height = (Math.random() * 6) + 3;
                    for(int y = 1; y<=height; y++){
                        Shape c;
                        Point3D location = new Point3D(crystalGrassBlock.getLocation().getX(), crystalGrassBlock.getLocation().getY() + (y * size) + aY, crystalGrassBlock.getLocation().getZ());
                        if(y + 1 < height)
                            c = new CrystalBlock(location, size);
                        else
                            c = new CrystalPyramid(location, size);

                        c.setColor(new Color(175 + (int)(Math.random() * 50), 32 + (int)(Math.random() * 50), 140 + (int)(Math.random() * 50)));
                        c.reinit();
                        cubes.add(c);
                    }
                }
            }
        }

        for(Shape cube : cubes){
            cube.update();
        }
    }

    private double[] cartesian(double theta, double r){
        double x = r * Math.cos( theta );
        double y = r * Math.sin(theta );
        return new double[]{x,y};
    }

    public void tick(){
        for(Shape shape : cubes){
            shape.tick();
        }
        Camera.getInstance().tick();
        refreshVisibility();
    }

    public List<Shape> getCubes() {
        return cubes;
    }


    public void refreshVisibility(){
        for(Shape shape : getCubes()){
            shape.update();
        }
    }

    public Shape getShapeAt(Point3D point3D){
        List<Shape> shapes = cubes.stream().filter(c -> c.contains(point3D)).collect(Collectors.toList());
        if(shapes.size() == 0 ) return  null;
        return shapes.get(0);
    }

    public Shape getShapeAt(double x, double y, double z){
        return getShapeAt(new Point3D(x,y,z));
    }

    public Shape getHighestShapeAt(double x, double z){
        Shape highest = null;

        for(Shape shape : cubes.stream().filter(shape -> shape.contains(new Point3D(x, shape.getLocation().getY() + 1, z))).collect(Collectors.toList())){

            if(highest == null || shape.getLocation().getY() > highest.getLocation().getY()){
                    highest = shape;
            }
        }
        return highest;
    }


    public void generateIsland(Point3D location, double size){
        for(double x = location.getX() - (size / 2); x<location.getX() + (size / 2); x+=World.getInstance().size){
            for(double y = location.getY() - (size / 2); y<location.getY(); y+=World.getInstance().size){
                for(double z = location.getZ() - (size / 2); z < location.getZ() + (size / 2); z+=World.getInstance().size){
                    Point3D p = new Point3D(x,y,z);
                    if(p.distance(location) < size / 2){
                        CrystalGrassBlock grassBlock = new CrystalGrassBlock(p, this.size);
                        int col = (int)(Math.random() * 100);
                        grassBlock.setColor(new Color(col,  col, col));
                        grassBlock.reinit();
                        cubes.add(grassBlock);
                    }
                }
            }
        }
        refreshVisibility();
    }




}

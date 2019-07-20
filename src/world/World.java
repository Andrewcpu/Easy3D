package world;

import constructs.Camera;
import constructs.ImprovedNoise;
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
        int mapSize = 6;

        for(int x = -mapSize; x<mapSize; x++){
            for(int z = -mapSize; z<mapSize; z++){
                CrystalGrassBlock crystalGrassBlock = new CrystalGrassBlock(new Point3D(x * World.getInstance().size + 1, 0, z * World.getInstance().size + 1), World.getInstance().size);

                int col = (int)(Math.random() * 100);

                crystalGrassBlock.setColor(new Color(col,col * 2,col));
                crystalGrassBlock.reinit();
                cubes.add(crystalGrassBlock);

                if(Math.random() < 0.02){
                    double height = (Math.random() * 6) + 3;
                    for(int y = 1; y<=height; y++){
                        Shape c;
                        Point3D location = new Point3D(crystalGrassBlock.getLocation().getX(), crystalGrassBlock.getLocation().getY() + (y * size), crystalGrassBlock.getLocation().getZ());
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

    public void regenerate(){
        int mapSize = 10;
        for(int x = -size * mapSize; x<size * mapSize + 1; x+=size) {
            for (int z = 1; z < size * 100 + 1; z += size){
                if ((Math.abs(x / size) == mapSize / 2) && z / size % 5 == 0)
                {
                    for (int y = 0; y <= size * 5; y += size) {
                        Shape cube;
                        if(y / size == 5)
                            cube = new Pyramid(new Point3D(x,y,z),size);
                        else
                            cube = new Cube(new Point3D(x, y, z), size);

                        int f = (int)(Math.random() * 150);

                        cube.setColor(new Color(f,f,f));
                        if(cube instanceof Pyramid){
                            cube.setColor(new Color(150 + (int)(Math.random() * 105), (int)(Math.random() * 50), (int)(Math.random() * 50)));
                        }
                        cube.reinit();
                        cubes.add(cube);
                    }
                }

                Cube cube = new Cube(new Point3D(x, 0, z), size);
                int f = (int)(Math.random() * 50);
                cube.setColor(new Color(f,(int)(255.0 * ImprovedNoise.noise(x,0,z)),f));
                cube.reinit();
                cubes.add(cube);
            }
        }

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

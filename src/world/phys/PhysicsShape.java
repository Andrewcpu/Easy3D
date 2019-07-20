package world.phys;

import constructs.Point3D;
import world.Shape;
import world.World;

public class PhysicsShape extends Shape {

    private double density;
    private double speed = 1;

    private Point3D location;

    private double width = 0, height = 0;

    public PhysicsShape(int SIDES, int POINTS_PER_SIDE, int AMOUNT_OF_NODES, double density,Point3D location) {
        super(SIDES, POINTS_PER_SIDE, AMOUNT_OF_NODES);
        this.density = density;
        this.location = location;
    }

    public double getTerminalVelocity() {
        double m = getVolume() * getDensity(); // mass
        double g = World.getInstance().gravity.getY(); // gravity acceleration (16??)
        double p = 1.225; // fluid density (air)
        double A = getWidth() * getWidth(); // projected area of object
        double C = 2; // drag

        return ((2 * m * g) / (p * A * C));
    }


    public double getVolume(){
        return 0;
    }

    public double getDensity(){
        return density;
    }

    public void setDensity(double density){
        this.density = density;
    }

    public void setWidth(double width){
        this.width = width;
    }

    public void setHeight(double height){
        this.height = height;
    }




    public double getWidth(){
        return width;
    }
    public double getHeight(){
        return height;
    }


    private boolean falling;


    @Override
    public void tick() {
        if (World.getInstance().getShapeAt(location.getX(), location.getY() - speed, location.getZ()) == null || (speed < 0 && World.getInstance().getShapeAt(location.getX(),location.getY()+getHeight()-speed,location.getZ())==null)) {
            location.subtract(0, speed, 0);
            reinit();
            speed++;
            if (speed > getTerminalVelocity()) {
                speed = getTerminalVelocity();
            }
            falling = true;
        }
        else {
            if(falling) {
                speed = -(speed * 0.25);
                if(Math.abs(speed) < 0.25)
                {
                    speed = 1;
                    falling = false;
                }
            }
            else
                speed = 1;

        }
    }


}

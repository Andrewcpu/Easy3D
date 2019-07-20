package constructs;

import draw.Renderer;
import world.Shape;
import world.World;

public class Camera {
    private static Camera instance = null;
    public static Camera getInstance(){
        return instance;
    }


    public static int FOV = 90;
    public static double FOCAL_LENGTH = 3.0/5.0; //human eye

    private Point3D location;
    private double rotationVertical = 0; // radians
    private double rotationHorizontal = 0; // radians
    private double speed = 10; //px per tick

    public Camera(Point3D location) {
        instance = this;
        this.location = location;
    }

    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public double getRotationVertical() {
        return rotationVertical;
    }

    public void setRotationVertical(double rotationVertical) {
        if (rotationVertical > -1.5 && rotationVertical < 1.5) {
            this.rotationVertical = rotationVertical;
        }

    }

    public double getRotationHorizontal() {
        return rotationHorizontal;
    }

    public void setRotationHorizontal(double rotationHorizontal) {
        this.rotationHorizontal = rotationHorizontal;
        normalize();
    }


    private void move(double x, double y, double z){
        Shape s = World.getInstance().getShapeAt(location.clone().add(x,y - 40 ,z));
        if(s == null)
            this.location.add(x,y,z);

    }



    public void normalize(){
        while(getRotationHorizontal() > Math.toRadians(360)){
            rotationHorizontal -= Math.toRadians(360);
        }
        while(getRotationVertical() > Math.toRadians(360)){
            rotationVertical -= Math.toRadians(360);
        }


    }
    public void backward(){
        normalize();
        double x = Math.sin(getRotationHorizontal()) * speed;
        double y = Math.sin(getRotationVertical()) * speed;
        double z = Math.cos(getRotationHorizontal()) * speed;
        move(-x,-y,-z);
    }
    public void forward(){
        normalize();
        double x = Math.sin(getRotationHorizontal() ) * speed;
        double y = Math.sin(getRotationVertical()) * speed;
        double z = Math.cos(getRotationHorizontal() ) * speed;
        move(x,y,z);

    }

    public void right(){
        normalize();
        double x = Math.sin(getRotationHorizontal() + Math.toRadians(90) )* speed;
        double y = Math.sin(getRotationVertical()) * speed;
        double z = Math.cos(getRotationHorizontal() + Math.toRadians(90)) * speed;
        move(x,-y,z);
    }

    public void left(){
        normalize();
        double x = Math.sin(getRotationHorizontal() + Math.toRadians(90) )* speed;
        double y = Math.sin(getRotationVertical()) * speed;
        double z = Math.cos(getRotationHorizontal() + Math.toRadians(90)) * speed;
        move(-x,y,-z);
    }


    public void up(){
        move(0,speed,0);
    }
    public void down(){
        move(0,-speed,0);
    }


    public void tick(){
        normalize();
    }

}

package constructs;

import draw.Canvas;
import draw.Renderer;

import java.awt.*;

public class Point3D {

    public static final Point3D ORIGIN = new Point3D(0,0,0);


    private double x, y, z;




    public Point3D(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Point3D add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Point3D subtract(double x, double y, double z) {
        return add(-x, -y, -z);
    }

    public Point3D subtract(Point3D point3D) {
        return subtract(point3D.getX(), point3D.getY(), point3D.getZ());
    }

    public Point3D add(Point3D point3D) {
        return add(point3D.getX(), point3D.getY(), point3D.getZ());
    }

    public Point3D divide(double m) {
        this.x /= m;
        this.y /= m;
        this.z /= m;
        return this;
    }

    public Point3D multiply(double m) {
        return divide(1.0 / m);
    }


    public double distance(double x, double y, double z) {
        return Math.sqrt(Math.pow(getX() - x, 2) + Math.pow(getY() - y, 2) + Math.pow(getZ() - z, 2));
    }


    public double distance(Point3D p) {
        return distance(p.getX(), p.getY(), p.getZ());
    }

    public Point3D clone() {
        return new Point3D(getX(), getY(), getZ());
    }

    public double[] to2DCoordinate() {
        Point p = getScreenPoint();
        return new double[]{p.getX(),p.getY()};
    }


    public Point3D rotateAroundY3D(double t) {
        double x = getX();
        double z = getZ();
        double nX = x * Math.cos(t) - z * Math.sin(t);
        double nZ = z * Math.cos(t) + x * Math.sin(t);
        this.x = nX;
        this.z = nZ;
        return this;
    }


    public Point3D rotateAroundX3D(double theta) {
        double sinTheta = Math.sin(theta);
        double cosTheta = Math.cos(theta);
        double y = getY();
        double z = getZ();
        double nY = y * cosTheta - z * sinTheta;
        double nZ = z * cosTheta + y * sinTheta;
        this.y = nY;
        this.z = nZ;
        return this;
    }


    public double dotProduct(double x, double y, double z) {
        return getX() * x + getY() * y + getZ() * z;
    }



    private Point getScreenPoint()
    {
        double focalLength = Camera.FOCAL_LENGTH;
        double[] aPoints = getPointOnPlane();
        aPoints[0] *= focalLength;
        aPoints[1] *= -focalLength;
        aPoints[0] += Canvas.DIMENSION / 2;
        aPoints[1] += Canvas.DIMENSION / 2;

        return new Point((int)aPoints[0], (int)aPoints[1]);
    }
    public Point3D performVisionConversion() {
        Point3D base = clone().subtract(Camera.getInstance().getLocation());
        base.rotateAroundY3D(Camera.getInstance().getRotationHorizontal() );
        base.rotateAroundX3D(Camera.getInstance().getRotationVertical() );
        return base;
    }


    public boolean isVisible() {

        double fov = Camera.FOV + 10;

        double length = distance(Point3D.ORIGIN);
        if (length > Renderer.RENDER_DISTANCE) return false;
        double inverseLength = 1.0 / length;
        Point3D direction = new Point3D(getX() * inverseLength, getY() * inverseLength, getZ() * inverseLength);
        Point3D norm2 = new Point3D(0, 0, 1);
        double dot = norm2.dotProduct(direction.getX(), direction.getY(), direction.getZ());
        return dot >= Math.cos(Math.toRadians(fov / 2.0));
    }


    private double[] getPointOnPlane(){

        Point3D point3D = this;

        double x = point3D.getX();
        double y = point3D.getY();
        double z = point3D.getZ();


        double planeZ = 10.0;

        Point3D vector = new Point3D(x,y,z);

        double mod = planeZ / vector.getZ();

        vector = new Point3D(vector.getX() * mod, vector.getY() * mod, planeZ);

        double d = (getDimensions());

        //d(x) = dimensions

        double modifier = (double) Canvas.DIMENSION / (d / 2.0);

        return  new double[]{vector.getX() * modifier,vector.getY() * modifier};
    }
    private double getDimensions(){
        return 10.0 * Math.tan(Math.toRadians(Camera.FOV / 2.0)) * 2;
    }

    @Override
    public String toString(){
        return (int)getX() + ", " + (int)getY() + ", " + (int)getZ();
    }

}

package world;

import constructs.Camera;
import constructs.Point3D;

import java.awt.*;

public class Face implements Comparable<Face>{
    private Point3D[] points;
    private FaceDirection direction;

    private Color color;

    private Shape parent = null;

    private boolean visible = true;

    public Face(int points, FaceDirection direction, Color color) {
        this.direction = direction;
        this.points = new Point3D[points];
        this.color = color;
    }

    public Shape getParent() {
        return parent;
    }

    public void setParent(Shape parent) {
        this.parent = parent;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point3D[] getPoints() {
        return points;
    }

    public void setPoints(Point3D[] points) {
        this.points = points;
    }

    public FaceDirection getDirection() {
        return direction;
    }

    public void setDirection(FaceDirection direction) {
        this.direction = direction;
    }

    public Point3D getAveragePoint(){
        double x = 0;
        double y = 0;
        double z = 0;
        for(Point3D point3D : points){
            x += point3D.getX();
            y += point3D.getY();
            z += point3D.getZ();
        }

        x /= points.length;
        y /= points.length;
        z /= points.length;

        return new Point3D(x,y,z);
    }


    @Override
    public int compareTo(Face o) {
        int d = Double.compare(getAveragePoint().distance(Camera.getInstance().getLocation()), o.getAveragePoint().distance(Camera.getInstance().getLocation()));
        return d;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}

class FaceBuilder{
    private int points;
    private FaceDirection faceDirection;
    private Point3D[] nodes;
    private Color color = null;
    private Shape shape = null;
    public FaceBuilder() {
    }


    public FaceBuilder setAmountOfNodes(int points){
        this.points = points;
        return this;
    }

    public FaceBuilder setFaceDirection(FaceDirection direction){
        this.faceDirection = direction;
        return this;
    }

    public FaceBuilder setNodes(Point3D[] nodes){
        this.nodes = nodes;
        return this;
    }


    public FaceBuilder setColor(Color color) {
        this.color = color;
        return this;
    }

    public FaceBuilder setParent(Shape shape){
        this.shape = shape;
        return this;
    }

    public Face build(){
        Face face = new Face(points, faceDirection, color);
        face.setPoints(nodes);
        face.setParent(this.shape);
        return face;
    }

}

enum FaceDirection{
    NORTH,SOUTH,EAST,WEST,UP,DOWN;
    public static Point3D slope(FaceDirection dir){
        switch (dir){
            case NORTH:
                return new Point3D(0,0,1);
            case SOUTH:
                return new Point3D(0,0,-1);
            case EAST:
                return new Point3D(1,0,0);
            case WEST:
                return new Point3D(-1,0,0);
            case UP:
                return new Point3D(0,1,0);
            case DOWN:
                return new Point3D(0,-1,0);
        }
        return new Point3D(0,0,0);
    }
}
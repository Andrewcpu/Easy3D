package world;

import constructs.Hitbox;
import constructs.Point3D;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public abstract class Shape {

    private int SIDES;
    private int POINTS_PER_SIDE;
    private int AMOUNT_OF_NODES;

    private int[][] faceConstructions; // [amount of faces][amount of points in each face]

    private Face[] faces;

    private Point3D[] nodes;

    private Color color;

    private Hitbox hitbox;




    public Shape(int SIDES, int POINTS_PER_SIDE, int AMOUNT_OF_NODES) {
        this.SIDES = SIDES;
        this.POINTS_PER_SIDE = POINTS_PER_SIDE;
        this.AMOUNT_OF_NODES = AMOUNT_OF_NODES;
        init();
    }


    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getSIDES() {
        return SIDES;
    }

    public void setSIDES(int SIDES) {
        this.SIDES = SIDES;
    }

    public int getPOINTS_PER_SIDE() {
        return POINTS_PER_SIDE;
    }

    public void setPOINTS_PER_SIDE(int POINTS_PER_SIDE) {
        this.POINTS_PER_SIDE = POINTS_PER_SIDE;
    }

    public int getAMOUNT_OF_NODES() {
        return AMOUNT_OF_NODES;
    }

    public void setAMOUNT_OF_NODES(int AMOUNT_OF_NODES) {
        this.AMOUNT_OF_NODES = AMOUNT_OF_NODES;
    }

    public int[][] getFaceConstructions() {
        return faceConstructions;
    }

    public void setFaceConstructions(int[][] faceConstructions) {
        this.faceConstructions = faceConstructions;
    }

    public Face[] getFaces() {
        return faces;
    }

    public void setFaces(Face[] faces) {
        this.faces = faces;
    }

    public Point3D[] getNodes() {
        return nodes;
    }

    public void setNodes(Point3D[] nodes) {
        this.nodes = nodes;
    }

    public Hitbox getHitbox() {
        return hitbox;
    }

    public void setHitbox(Hitbox hitbox) {
        this.hitbox = hitbox;
    }

    private void init(){
        this.faceConstructions = new int[SIDES][POINTS_PER_SIDE];
        this.faces = new Face[SIDES];
        this.nodes = new Point3D[AMOUNT_OF_NODES];
    }

    public void init(int[][] faceConstructions, Face[] faces, Point3D[] nodes){
        this.faceConstructions = faceConstructions;
        this.faces = faces;
        this.nodes = nodes;
    }


    //should be overridden by shape class
    public void reinit(){

    }

    public void destroy(){
        World.getInstance().getCubes().remove(this);
    }

    public boolean contains(Point3D point3D){
        return false;
    }

    public void tick(){

    }


    public Point3D getLocation(){
        return null;
    }



    public void update(){

    }

}

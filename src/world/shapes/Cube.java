package world.shapes;

import constructs.Hitbox;
import constructs.Point3D;
import world.FaceDirection;
import world.Shape;
import world.World;

import java.awt.*;


public class Cube extends world.Shape {
    private Point3D location;
    private double width;

    private Color color = Color.BLUE;



    private Point3D[] nodes = new Point3D[8];



    private static int FRONT_TOP_LEFT = 0;
    private static int FRONT_TOP_RIGHT = 1;
    private static int FRONT_BOTTOM_LEFT = 2;
    private static int FRONT_BOTTOM_RIGHT = 3;

    private static int BACK_TOP_LEFT = 4;
    private static int BACK_TOP_RIGHT = 5;
    private static int BACK_BOTTOM_LEFT = 6;
    private static int BACK_BOTTOM_RIGHT = 7;

    private static int FACE_NORTH = 0;
    private static int FACE_SOUTH = 1;
    private static int FACE_EAST = 2;
    private static int FACE_WEST = 3;
    private static int FACE_UP = 4;
    private static int FACE_DOWN = 5;


    private static final int SIDES = 6;
    private static final int POINTS_PER_SIDE = 4;

    private int[][] faceConstructions = new int[SIDES][POINTS_PER_SIDE]; // [amount of faces][amount of points in each face]

    private Face[] faces = new Face[SIDES];
    
    private double rotationAroundY = 0; // in radians
    private double rotationAroundX = 0; // in radians
    private double rotationAroundZ = 0; // (guess what) in radians



    public Cube(Point3D location, double size) {
        super(SIDES, POINTS_PER_SIDE, 8);
        this.location = location;
        this.width = size;
        init();
        update();
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public void reinit(){
        faces = new Face[SIDES];
        faceConstructions = new int[SIDES][POINTS_PER_SIDE];
        nodes = new Point3D[8];
        init();
        update();
    }
    
    private void configureNodes(){
        nodes[FRONT_TOP_LEFT] = Point3D.ORIGIN.clone().add(0,0,0).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(),location.getY(),location.getZ());
        nodes[FRONT_TOP_RIGHT] = Point3D.ORIGIN.clone().add(width, 0, 0).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(),location.getY(),location.getZ());

        nodes[FRONT_BOTTOM_LEFT] = Point3D.ORIGIN.clone().add(0,width,0).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(),location.getY(),location.getZ());
        nodes[FRONT_BOTTOM_RIGHT] = Point3D.ORIGIN.clone().add(width,width,0).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(),location.getY(),location.getZ());


        nodes[BACK_TOP_LEFT] = Point3D.ORIGIN.clone().add(0,0,width).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(),location.getY(),location.getZ());
        nodes[BACK_TOP_RIGHT] = Point3D.ORIGIN.clone().add(width, 0, width).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(),location.getY(),location.getZ());

        nodes[BACK_BOTTOM_LEFT] = Point3D.ORIGIN.clone().add(0,width,width).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(),location.getY(),location.getZ());
        nodes[BACK_BOTTOM_RIGHT] = Point3D.ORIGIN.clone().add(width,width,width).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(),location.getY(),location.getZ());


        setNodes(nodes);
    }

    private void init(){

        
        configureNodes();
        
        faceConstructions[FACE_NORTH] = new int[]{BACK_TOP_LEFT,BACK_TOP_RIGHT,BACK_BOTTOM_RIGHT,BACK_BOTTOM_LEFT}; // back
        faceConstructions[FACE_SOUTH] = new int[]{FRONT_TOP_LEFT,FRONT_TOP_RIGHT,FRONT_BOTTOM_RIGHT,FRONT_BOTTOM_LEFT}; //front
        
        faceConstructions[FACE_EAST] = new int[]{FRONT_TOP_RIGHT,FRONT_BOTTOM_RIGHT,BACK_BOTTOM_RIGHT,BACK_TOP_RIGHT}; // right
        faceConstructions[FACE_WEST] = new int[]{FRONT_TOP_LEFT,FRONT_BOTTOM_LEFT,BACK_BOTTOM_LEFT,BACK_TOP_LEFT}; //left
        
        faceConstructions[FACE_UP] = new int[]{FRONT_TOP_LEFT,FRONT_TOP_RIGHT,BACK_TOP_RIGHT,BACK_TOP_LEFT}; // top
        faceConstructions[FACE_DOWN] = new int[]{FRONT_BOTTOM_LEFT,FRONT_BOTTOM_RIGHT,BACK_BOTTOM_RIGHT,BACK_BOTTOM_LEFT}; // bottom
    
        
        
        
        Point3D[] northNodes = new Point3D[]{nodes[faceConstructions[FACE_NORTH][0]],nodes[faceConstructions[FACE_NORTH][1]],nodes[faceConstructions[FACE_NORTH][2]],nodes[faceConstructions[FACE_NORTH][3]]};
        Point3D[] southNodes = new Point3D[]{nodes[faceConstructions[FACE_SOUTH][0]],nodes[faceConstructions[FACE_SOUTH][1]],nodes[faceConstructions[FACE_SOUTH][2]],nodes[faceConstructions[FACE_SOUTH][3]]};
        Point3D[] eastNodes = new Point3D[]{nodes[faceConstructions[FACE_EAST][0]],nodes[faceConstructions[FACE_EAST][1]],nodes[faceConstructions[FACE_EAST][2]],nodes[faceConstructions[FACE_EAST][3]]};
        Point3D[] westNodes = new Point3D[]{nodes[faceConstructions[FACE_WEST][0]],nodes[faceConstructions[FACE_WEST][1]],nodes[faceConstructions[FACE_WEST][2]],nodes[faceConstructions[FACE_WEST][3]]};
        Point3D[] upNodes = new Point3D[]{nodes[faceConstructions[FACE_UP][0]],nodes[faceConstructions[FACE_UP][1]],nodes[faceConstructions[FACE_UP][2]],nodes[faceConstructions[FACE_UP][3]]};
        Point3D[] downNodes = new Point3D[]{nodes[faceConstructions[FACE_DOWN][0]],nodes[faceConstructions[FACE_DOWN][1]],nodes[faceConstructions[FACE_DOWN][2]],nodes[faceConstructions[FACE_DOWN][3]]};
        
        
        
        faces[FACE_NORTH] = new FaceBuilder().setParent(this).setColor(color).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.NORTH).setNodes(northNodes).build();
        faces[FACE_SOUTH] = new FaceBuilder().setParent(this).setColor(color).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.SOUTH).setNodes(southNodes).build();
        faces[FACE_EAST] = new FaceBuilder().setParent(this).setColor(color).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.EAST).setNodes(eastNodes).build();
        faces[FACE_WEST] = new FaceBuilder().setParent(this).setColor(color).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.WEST).setNodes(westNodes).build();
        faces[FACE_UP] = new FaceBuilder().setParent(this).setColor(color).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.UP).setNodes(upNodes).build();
        faces[FACE_DOWN] = new FaceBuilder().setParent(this).setColor(color).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.DOWN).setNodes(downNodes).build();



        setHitbox(new Hitbox(nodes[BACK_TOP_LEFT], nodes[FRONT_BOTTOM_RIGHT]));
        configureFaceVisibility();

        super.init(faceConstructions,faces,nodes);


    }

    public void configureFaceVisibility(){
        if(getFaces() == null) return;
        boolean allSidesVisible = true;
        for(Face face : getFaces()){
            Point3D slope = face.getDirection().slope(face.getDirection()).multiply(2);
            world.Shape shape = World.getInstance().getShapeAt(face.getAveragePoint().add(slope));
            face.setVisible(shape == null || allSidesVisible);
        }
    }

    public Face[] getFaces() {
        return faces;
    }

    @Override
    public void destroy(){
        for(Face face : getFaces()){
            Point3D slope = face.getDirection().slope(face.getDirection()).multiply(2);
            Shape shape = World.getInstance().getShapeAt(face.getAveragePoint().add(slope));
            if(shape != null)
                shape.update();
        }
        super.destroy();
    }

    public void rotate(double x, double y){
        this.rotationAroundX = x;
        this.rotationAroundY = y;
        reinit();
    }

    public double getRotationAroundY() {
        return rotationAroundY;
    }

    public void setRotationAroundY(double rotationAroundY) {
        this.rotationAroundY = rotationAroundY;
    }

    public double getRotationAroundX() {
        return rotationAroundX;
    }

    public void setRotationAroundX(double rotationAroundX) {
        this.rotationAroundX = rotationAroundX;
    }

    public boolean contains(Point3D point3D){
        return getHitbox().within(point3D);
    }

    @Override
    public void update(){
        //configureFaceVisibility();
    }

    @Override
    public void tick(){
        update();
        super.tick();
    }
}

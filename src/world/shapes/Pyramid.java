package world.shapes;

import constructs.Hitbox;
import constructs.Point3D;
import world.FaceDirection;
import world.phys.PhysicsShape;

public class Pyramid extends PhysicsShape {

    //static variables that are useful, kinda a per shape class sort of thing...
    private static final int FACES = 5;
    private static final int AMOUNT_OF_NODES = 5;
    private static final int POINTS_PER_SIDE = 4;

    //node indicies ??? i dont know how to spell it and quite frankly i dont care.
    private static final int BACK_LEFT = 0;
    private static final int BACK_RIGHT = 1;
    private static final int FRONT_RIGHT = 2;
    private static final int FRONT_LEFT = 3;
    private static final int TOP = 4;


    //face labelling
    private static final int FACE_BACK = 0;
    private static final int FACE_FRONT = 1;
    private static final int FACE_LEFT = 2;
    private static final int FACE_RIGHT = 3;
    private static final int FACE_BOTTOM = 4;


    //instance variables
    private Point3D location;
    private int width;
    public double rotationAroundX = 0; // guess what
    public double rotationAroundY = 0; // radians. because no one uses degrees apparently



    public Pyramid(Point3D location, int width) {
        super(FACES, POINTS_PER_SIDE, AMOUNT_OF_NODES,2.0,location);



        this.location = location;
        this.width = width;

        __construct();

        setDensity(getDensity());
        setWidth(getWidth());
        setHeight(getHeight());
    }

    private void __construct() {
        configureNodes();
        configureFaceConstruction();
        configureFaces();
        setHitbox(new Hitbox(getNodes()[FRONT_LEFT], getNodes()[BACK_RIGHT].clone().add(0, getNodes()[TOP].getY() - getNodes()[FRONT_LEFT].getY(), 0)));
    }

    private void configureNodes() {
        Point3D[] nodes = new Point3D[5];
        nodes[FRONT_LEFT] = Point3D.ORIGIN.clone().add(0, 0, 0).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(), location.getY(), location.getZ());
        nodes[FRONT_RIGHT] = Point3D.ORIGIN.clone().add(width, 0, 0).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(), location.getY(), location.getZ());
        nodes[BACK_LEFT] = Point3D.ORIGIN.clone().add(0, 0, width).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(), location.getY(), location.getZ());
        nodes[BACK_RIGHT] = Point3D.ORIGIN.clone().add(width, 0, width).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(), location.getY(), location.getZ());
        nodes[TOP] = Point3D.ORIGIN.clone().add(width / 2, width, width / 2).rotateAroundX3D(rotationAroundX).rotateAroundY3D(rotationAroundY).add(location.getX(), location.getY(), location.getZ());
        setNodes(nodes);
    }

    private void configureFaceConstruction() {

        int[][] faceConstructions = new int[FACES][POINTS_PER_SIDE];

        faceConstructions[FACE_FRONT] = new int[]{FRONT_LEFT, FRONT_RIGHT, TOP, FRONT_LEFT}; // front
        faceConstructions[FACE_BACK] = new int[]{BACK_LEFT, BACK_RIGHT, TOP, BACK_LEFT}; //back

        faceConstructions[FACE_LEFT] = new int[]{FRONT_LEFT, BACK_LEFT, TOP, FRONT_LEFT}; // left
        faceConstructions[FACE_RIGHT] = new int[]{FRONT_RIGHT, BACK_RIGHT, TOP, FRONT_RIGHT}; // right

        faceConstructions[FACE_BOTTOM] = new int[]{FRONT_RIGHT, FRONT_LEFT, BACK_LEFT, BACK_RIGHT}; // top
        setFaceConstructions(faceConstructions);
    }


    private Point3D[] getNodeForDirection(FaceDirection faceDirection) {
        Point3D[] points = new Point3D[POINTS_PER_SIDE];
        switch (faceDirection) {
            case NORTH:
                points = new Point3D[]{getNodes()[BACK_LEFT], getNodes()[BACK_RIGHT], getNodes()[TOP], getNodes()[BACK_LEFT]};
                break;
            case SOUTH:
                points = new Point3D[]{getNodes()[FRONT_LEFT], getNodes()[FRONT_RIGHT], getNodes()[TOP], getNodes()[FRONT_LEFT]};
                break;
            case EAST:
                points = new Point3D[]{getNodes()[BACK_RIGHT], getNodes()[FRONT_RIGHT], getNodes()[TOP], getNodes()[BACK_RIGHT]};

                break;
            case WEST:
                points = new Point3D[]{getNodes()[BACK_LEFT], getNodes()[FRONT_LEFT], getNodes()[TOP], getNodes()[BACK_LEFT]};
                break;
            case DOWN:
                points = new Point3D[]{getNodes()[BACK_RIGHT], getNodes()[BACK_LEFT], getNodes()[FRONT_LEFT], getNodes()[FRONT_RIGHT]};

                break;

        }
        return points;
    }

    private void configureFaces() {
        Face[] faces = new Face[FACES];
        faces[FACE_FRONT] = new FaceBuilder().setParent(this).setColor(getColor()).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.SOUTH).setNodes(getNodeForDirection(FaceDirection.SOUTH)).build();
        faces[FACE_BACK] = new FaceBuilder().setParent(this).setColor(getColor()).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.NORTH).setNodes(getNodeForDirection(FaceDirection.NORTH)).build();
        faces[FACE_LEFT] = new FaceBuilder().setParent(this).setColor(getColor()).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.WEST).setNodes(getNodeForDirection(FaceDirection.WEST)).build();
        faces[FACE_RIGHT] = new FaceBuilder().setParent(this).setColor(getColor()).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.EAST).setNodes(getNodeForDirection(FaceDirection.EAST)).build();
        faces[FACE_BOTTOM] = new FaceBuilder().setParent(this).setColor(getColor()).setAmountOfNodes(POINTS_PER_SIDE).setFaceDirection(FaceDirection.DOWN).setNodes(getNodeForDirection(FaceDirection.DOWN)).build();
        setFaces(faces);
        updateVisibility();
    }
    /*
    This method tells the faces that are covered by another solid shape to not render
     */
    public void updateVisibility(){
        for(Face face : getFaces()){

        }
    }

    public void reinit() {
        __construct();
    }


    public boolean contains(Point3D point3D) {
        setHitbox(new Hitbox(getNodes()[FRONT_LEFT], getNodes()[BACK_RIGHT].clone().add(0, getNodes()[TOP].getY() - getNodes()[FRONT_LEFT].getY(), 0)));
        return getHitbox().within(point3D);
    }


    public double getWidth() {
        return Math.max(getNodes()[FRONT_LEFT].getX(), getNodes()[FRONT_RIGHT].getX()) - Math.min(getNodes()[FRONT_LEFT].getX(), getNodes()[FRONT_RIGHT].getX());
    }

    public double getHeight() {
        return getNodes()[TOP].getY() - getNodes()[FRONT_LEFT].getY();

    }

    @Override
    public double getVolume(){
        return getWidth() * getWidth() * getHeight() / 3.0;
    }


    @Override
    public Point3D getLocation() {
        return location;
    }

    @Override
    public void tick(){
        super.tick();
    }
}

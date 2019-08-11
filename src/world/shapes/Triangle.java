package world.shapes;

import constructs.Point3D;
import world.FaceDirection;
import world.Shape;
import world.World;

import java.awt.*;

public class Triangle extends Shape {
    private int A = 0;
    private int B = 1;
    private int C = 2;

    private int FACE = 0;

    public Triangle(Point3D aP, Point3D bP, Point3D cP) {
        super(1, 3, 3);
        setColor(new Color(255,255,255,50));

        Point3D[] nodes = new Point3D[3];
        nodes[A] = aP;
        nodes[B] = bP;
        nodes[C] = cP;
        setNodes(nodes);

        int[][] faceConstruction = new int[1][getPOINTS_PER_SIDE()];
        faceConstruction[FACE] = new int[]{A,B,C};
        setFaceConstructions(faceConstruction);

        Face[] faces = new Face[getSIDES()];
        faces[FACE] = new FaceBuilder().setParent(this).setColor(getColor()).setAmountOfNodes(getPOINTS_PER_SIDE()).setFaceDirection(FaceDirection.UP).setNodes(nodes).build();
        setFaces(faces);
    }
    @Override
    public Point3D getLocation() {
        return getNodes()[A];
    }

    @Override
    public void reinit(){

        int[][] faceConstruction = new int[1][getPOINTS_PER_SIDE()];
        faceConstruction[FACE] = new int[]{A,B,C};
        setFaceConstructions(faceConstruction);

        Face[] faces = new Face[getSIDES()];
        faces[FACE] = new FaceBuilder().setParent(this).setColor(getColor()).setAmountOfNodes(getPOINTS_PER_SIDE()).setFaceDirection(FaceDirection.UP).setNodes(getNodes()).build();
        setFaces(faces);
    }

    @Override
    public void update(){
        getNodes()[A].setY(World.getInstance().eval(getNodes()[A]));
        getNodes()[B].setY(World.getInstance().eval(getNodes()[B]));
        getNodes()[C].setY(World.getInstance().eval(getNodes()[C]));
    }

}

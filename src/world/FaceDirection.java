package world;


import constructs.Point3D;

public enum FaceDirection{
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
                return new Point3D(0,-1,0);
            case DOWN:
                return new Point3D(0,1,0);
        }
        return new Point3D(0,0,0);
    }
}
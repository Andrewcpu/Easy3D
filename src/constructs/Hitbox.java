package constructs;

public class Hitbox {
    private Point3D a;
    private Point3D b;

    public Hitbox(Point3D a, Point3D b) {
        this.a = a;
        this.b = b;
    }

    public boolean within(Point3D c){
        double minX = Math.min(a.getX(),b.getX());
        double minY = Math.min(a.getY(),b.getY());
        double minZ = Math.min(a.getZ(),b.getZ());

        double maxX = Math.max(a.getX(),b.getX());
        double maxY = Math.max(a.getY(),b.getY());
        double maxZ = Math.max(a.getZ(),b.getZ());

        if(c.getX() >= minX && c.getX() < maxX){
            if(c.getY() >= minY && c.getY() < maxY){
                if(c.getZ() >= minZ && c.getZ() < maxZ){
                    return true;
                }
            }
        }
        return false;
    }

    public Point3D getMiddle(){
        double x = 0, y = 0, z = 0;
        x += a.getX() + b.getX();
        y += a.getY() + b.getY();
        z += a.getZ() + b.getZ();
        return new Point3D(x,y,z);
    }

    public Point3D getA() {
        return a;
    }

    public void setA(Point3D a) {
        this.a = a;
    }

    public Point3D getB() {
        return b;
    }

    public void setB(Point3D b) {
        this.b = b;
    }
}

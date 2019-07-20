package world.entities;

import constructs.Camera;
import constructs.Point3D;
import world.World;

public class Player extends Entity{
    private Camera camera;
    public Player(Point3D location, World world) {
        super(location, world);
        camera = Camera.getInstance();
    }
}

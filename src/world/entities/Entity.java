package world.entities;

import constructs.Point3D;
import world.World;

public class Entity {
    private Point3D location;
    private World world;

    public Entity(Point3D location, World world) {
        this.location = location;
        this.world = world;
    }

    public Point3D getLocation() {
        return location;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void teleport(Point3D location){
        setLocation(location);
    }

    public void tick(){

    }
}

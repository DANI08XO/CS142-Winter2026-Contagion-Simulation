// Terrain.java

public abstract class Terrain extends WorldObject {

    // can ants move onto this tile
    public boolean isTraversable() {
        return false;
    }

    // Can a worker dig this tile into a Tunnel?
    public boolean isDiggable() {
        return false;
    }

    public boolean isSolid() {
        return false;
    }

    @Override
    public boolean isCarryable() {
        return false;
    }

    @Override
    public boolean isEdible() {
        return false; // terrain is never edible
    }
}
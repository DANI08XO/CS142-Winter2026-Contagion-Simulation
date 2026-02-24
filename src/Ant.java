// Ant.java
import java.util.Random;

public abstract class Ant {
    protected static final int DEFAULT_MAX_ENERGY = 50;

    private final WorldGrid world;
    private final Random rng;

    private int currentEnergy;
    private final int maxEnergy;

    private int x;
    private int y;

    private WorldObject heldItem;
    private boolean alive = true;

    protected Ant(WorldGrid world, Random rng, Point pos, int maxEnergy) {
        if (world == null) throw new IllegalArgumentException("world");
        if (rng == null) throw new IllegalArgumentException("rng");
        if (pos == null) throw new IllegalArgumentException("pos");
        if (maxEnergy <= 0) throw new IllegalArgumentException("maxEnergy must be > 0");

        this.world = world;
        this.rng = rng;
        this.x = pos.x;
        this.y = pos.y;
        this.maxEnergy = maxEnergy;
        this.currentEnergy = maxEnergy;
    }

    protected final WorldGrid world() { return world; }
    protected final Random rng() { return rng; }

    public final int getX() { return x; }
    public final int getY() { return y; }
    public final Point getPoint() { return new Point(x, y); }

    public final boolean isAlive() { return alive; }
    public final int getEnergy() { return currentEnergy; }
    public char getSymbol() { return 'A'; }
    public WorldObject getHeldItem(){ return heldItem; }

    protected final void changeEnergy(int delta) {
        currentEnergy += delta;
        if (currentEnergy <= 0) kill();
    }

    public final void kill() {
        if (!alive) return;
        alive = false;

        // drop held item on death if possible
        if (heldItem != null) {
            world.placeObject(getPoint(), heldItem);
            heldItem = null;
        }
    }

    // returns true if the ant moved
    public boolean move(Direction dir) {
        if (dir == null) return false;

        if (world.canMoveTo(getPoint().add(dir))) {
            x += dir.dx;
            y += dir.dy;
            changeEnergy(-1);
            return true;
        }
        return false;
    }

    // picks up an object from the ant's current tile.
    // returns true if pickup succeeded.
    public boolean pickupObject() {
        if (heldItem != null) return false;

        Point here = getPoint();
        WorldObject obj = world.getObjectAt(here);
        if (obj == null || !obj.isCarryable()) return false;
        heldItem = world.removeObject(here);
        return true;
    }

    public boolean setHeldItem(WorldObject obj){
        if (obj == null|| heldItem != null) return false;
        heldItem = obj;
        return true;
    }

    // consumes held item if edible.
    public boolean consume() {
        if (heldItem == null || !heldItem.isEdible()) return false;

        currentEnergy = maxEnergy;
        heldItem = null;
        return true;
    }

    // potential logic for seeking out a point
    public Direction pathFind(Point target) {
        return Direction.CENTER;
    }
}
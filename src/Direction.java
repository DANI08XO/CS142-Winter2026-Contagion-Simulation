// Direction.java

public enum Direction {
    NORTH(0, -1),
    EAST(1, 0),
    SOUTH(0, 1),
    WEST(-1, 0),
    CENTER(0, 0);

    public final int dx;
    public final int dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }

    // only movement directions (no CENTER)
    public static Direction[] allDirections() {
        return new Direction[]{NORTH, EAST, SOUTH, WEST};
    }

    // random direction
    public static Direction random(java.util.Random rng) {
        return allDirections()[rng.nextInt(allDirections().length)];
    }
}
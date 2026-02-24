// WorldGrid.java

public class WorldGrid {

    private final int width;
    private final int height;

    private final Terrain[][] terrain;
    private final WorldObject[][] objects;
    private final Pheromones pheromones;

    public WorldGrid(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid grid size");
        }

        this.width = width;
        this.height = height;
        terrain = new Terrain[height][width];
        objects = new WorldObject[height][width];
        pheromones = new Pheromones(width, height);

        // Default: fill with Dirt
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                terrain[y][x] = new Dirt();
            }
        }
        // objects is already null filled
        // pheromones is already null filled
    }

    public int getWidth() { return width; }
    public int getHeight() { return height; }

    public Terrain getTerrainAt(Point p) {
        if (!inBounds(p)) return null;
        return terrain[p.y][p.x];
    }

    public WorldObject getObjectAt(Point p) {
        if (!inBounds(p)) return null;
        return objects[p.y][p.x];
    }

    public double getPheromone(PheromoneType type, Point p) {
        return pheromones.get(type, p);
    }

    // placement/removal
    public void setTerrain(Point p, Terrain t) {
        if (!inBounds(p) || t == null) return;
        terrain[p.y][p.x] = t;
    }

    public boolean placeObject(Point p, WorldObject obj) {
        if (!inBounds(p) || obj == null) return false;

        if (!canMoveTo(p)) return false; // only place on valid tiles
        if (objects[p.y][p.x] != null) return false;

        objects[p.y][p.x] = obj;
        return true;
    }

    public WorldObject removeObject(Point p) {
        if (!inBounds(p)) return null;

        WorldObject obj = objects[p.y][p.x];
        objects[p.y][p.x] = null;
        return obj;
    }

    public WorldObject removeTerrain(Point p) {
        if (!inBounds(p)) return null;

        WorldObject obj = terrain[p.y][p.x];
        terrain[p.y][p.x] = new Tunnel();
        return obj;
    }

    // movement rules
    public boolean canMoveTo(Point p) {
        if (!inBounds(p)) return false;

        Terrain t = terrain[p.y][p.x];

        // tunnel is always walkable
        if (t instanceof Tunnel) return true;

        // air is only walkable if supported from below
        if (t instanceof Air) {
            Point below = new Point(p.x, p.y + 1);
            if (!inBounds(below)) return false;

            Terrain belowTerrain = terrain[below.y][below.x];
            return (belowTerrain != null) && (belowTerrain.isSolid());
        }

        return false;
    }

    // digging
    public boolean dig(Point p) {
        if (!inBounds(p)) return false;

        Terrain t = terrain[p.y][p.x];
        if (t != null && t.isDiggable()) {
            terrain[p.y][p.x] = new Tunnel();
            return true;
        }
        return false;
    }

    // pheromones
    public void addPheromone(PheromoneType type, Point p, double amount) {
        pheromones.add(type, p, amount);
    }

    public void decayPheromones() {
        pheromones.decay(.95); // 5% loss each tick
    }

    // helper
    public boolean inBounds(Point p) {
        return  p != null &&
                p.x >= 0  && p.x < width &&
                p.y >= 0  && p.y < height;
    }
}
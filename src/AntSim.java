// AntSim.java
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AntSim {

    private final WorldGrid world;
    private final Random rng;
    private final List<Ant> ants;

    private static final int DEFAULT_W = 50;
    private static final int DEFAULT_H = 50;

    public AntSim() {
        this(DEFAULT_W, DEFAULT_H, new Random());
    }

    public AntSim(int width, int height) {
        this(width, height, new Random());
    }

    public AntSim(int width, int height, Random rng) {
        if (rng == null) throw new IllegalArgumentException("rng");

        this.world = new WorldGrid(width, height);
        this.rng = rng;
        this.ants = new ArrayList<>();

        setupWorld();
        setupAnts();
    }

    public WorldGrid getWorld() {
        return world;
    }

    public List<Ant> getAnts() {
        return ants;
    }

    // simulation tick
    public void step() {
        //TODO:
        // Example behavior for now:
        // - Queen occasionally spawns
        // - All ants move randomly (excluding CENTER) as a placeholder

        // Spawn from queen
        for (Ant a : new ArrayList<>(ants)) {
            if (!a.isAlive()) continue;

            if (a instanceof QueenAnt q) {
                // spawn occasionally
                if (rng.nextInt(20) == 0) {
                    Ant child = q.spawnAnt();
                    if (child != null) ants.add(child);
                }
            }
        }

        // remove dead ants
        ants.removeIf(a -> !a.isAlive());

        // Move ants - no dead ants in ants
        for (Ant a : ants) {
            // lower chance of queen moving
            if (a instanceof QueenAnt && rng.nextInt(20) < 19) continue;
            Direction[] dirs = Direction.allDirections();
            Direction d = dirs[rng.nextInt(dirs.length)];
            a.move(d);
        }

        // decay pheromones
        world.decayPheromones();
    }

    // setup helpers
    private void setupWorld() {
        int width = world.getWidth();
        int height = world.getHeight();

        // carve a rectangular room of tunnel so ants can move
        for (int y=height/16*7; y<=height/16*9; ++y) {
            for (int x=width/16*7; x<=width/16*9; ++x) {
                world.setTerrain(new Point(x, y), new Tunnel());
            }
        }

        // carve a rectangular room of air for the surface world
        for (int y=0; y<=5; ++y) {
            for (int x=0; x<=width; ++x) {
                world.setTerrain(new Point(x, y), new Air());
            }
        }

        // Add a couple rocks as obstacles
        for (int r=0; r<25; ++r){
            Point rand = new Point(rng.nextInt(width), rng.nextInt(height));
            if (world.getTerrainAt(rand) instanceof Dirt){
                world.setTerrain(rand, new Rock());
            } else { --r; } // try again
        }
    }

    private void setupAnts() {
        Point center = new Point(world.getWidth()/2, world.getHeight()/2);

        // Ensure the queen starts on a valid tile
        if (!world.canMoveTo(center)) {
            // find any tunnel tile
            center = findAnyTunnel();
        }

        QueenAnt queen = QueenAnt.spawn(world, rng, center);
        ants.add(queen);

        // Starter ants next to queen if possible
        for (Direction d : Direction.allDirections()) {
            Point p = center.add(d);
            if (!world.canMoveTo(p)) continue;

            // Alternate worker/guard
            if (ants.size() % 2 == 0) ants.add(WorkerAnt.spawn(world, rng, p, center));
            else ants.add(GuardAnt.spawn(world, rng, p, center));

            if (ants.size() >= 3) break;
        }
    }

    private Point findAnyTunnel() {
        for (int y=0; y<world.getHeight(); ++y) {
            for (int x=0; x<world.getWidth(); ++x) {
                Point p = new Point(x, y);
                if (world.canMoveTo(p)) return p;
            }
        }
        return new Point(0, 0);
    }

    public static void main(String[] args) {
        AntSim sim = new AntSim();
        new AntSimGUI(sim);
    }
}
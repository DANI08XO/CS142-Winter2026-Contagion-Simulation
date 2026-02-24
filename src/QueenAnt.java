// QueenAnt.java

import java.util.Random;

public class QueenAnt extends ColonyAnt {
    public static final int DEFAULT_ENERGY = 200;

    private QueenAnt(WorldGrid world, Random rng, Point pos, int maxEnergy, Point home) {
        super(world, rng, pos, maxEnergy, home);
    }

    // queens home is its spawn point
    public static QueenAnt spawn(WorldGrid world, Random rng, Point pos) {
        return new QueenAnt(world, rng, pos, DEFAULT_ENERGY, pos);
    }

    @Override
    public char getSymbol() {
        return 'Q';
    }

    // Returns a newly created ant, or null if no adjacent spawn space exists.
    public Ant spawnAnt() {
        if (getEnergy() < 50) return null; // avoids killing the queen ant to quickly
        int type = rng().nextInt(2);
        Point home = getHome();

        for (Direction d : Direction.allDirections()) {
            Point spawn = getPoint().add(d);
            if (!world().canMoveTo(spawn)) continue;

            if (type == 0) {
                changeEnergy(-5);
                return GuardAnt.spawn(world(), rng(), spawn, home);
            } else {
                changeEnergy(-5);
                return WorkerAnt.spawn(world(), rng(), spawn, home);
            }
        }

        return null;
    }
}
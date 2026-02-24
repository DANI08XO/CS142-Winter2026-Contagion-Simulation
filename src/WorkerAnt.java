// WorkerAnt.java

import java.util.Random;

public class WorkerAnt extends ColonyAnt {
    public static final int DEFAULT_ENERGY = 100;

    private WorkerAnt(WorldGrid world, Random rng, Point pos, int maxEnergy, Point home) {
        super(world, rng, pos, maxEnergy, home);
    }

    public static WorkerAnt spawn(WorldGrid world, Random rng, Point pos, Point home) {
        return new WorkerAnt(world, rng, pos, DEFAULT_ENERGY, home);
    }

    public static WorkerAnt withEnergy(WorldGrid world, Random rng, Point pos, int energy, Point home) {
        return new WorkerAnt(world, rng, pos, energy, home);
    }

    @Override
    public char getSymbol() {
        return 'W';
    }

    public boolean dig(Direction dir){
        if (dir == null || getHeldItem() != null) return false;
        Point target = getPoint().add(dir);
        if (world().getTerrainAt(target) instanceof Dirt){
            return setHeldItem(world().removeTerrain(target));
        }
        return false;
    }

    //TODO: add worker behavior later (searchForFood, build, etc.)
}
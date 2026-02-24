// ColonyAnt.java

import java.util.Random;

public class ColonyAnt extends Ant {
    private final Point home;
    private Point foodStore;

    public ColonyAnt(WorldGrid world, Random rng, Point pos, int maxEnergy, Point home){
        super(world, rng, pos, maxEnergy);
        if (home == null) throw new IllegalArgumentException("home");
        this.home = home;
    }

    public void setFoodStore(Point pos){
        foodStore = pos;
    }
}
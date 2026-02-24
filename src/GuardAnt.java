// GuardAnt.java

import java.util.Random;

public class GuardAnt extends ColonyAnt {
    public static final int DEFAULT_ENERGY = 100;

    private boolean isAttacking;
    private boolean isGuarding;

    private GuardAnt(WorldGrid world, Random rng, Point pos, int maxEnergy, Point home) {
        super(world, rng, pos, maxEnergy, home);
    }

    public static GuardAnt spawn(WorldGrid world, Random rng, Point pos, Point home) {
        return new GuardAnt(world, rng, pos, DEFAULT_ENERGY, home);
    }

    public static GuardAnt withEnergy(WorldGrid world, Random rng, Point pos, int energy, Point home) {
        return new GuardAnt(world, rng, pos, energy, home);
    }

    @Override
    public char getSymbol() {
        return 'G';
    }

    public boolean isGuarding() { return isGuarding; }
    public boolean isAttacking() { return isAttacking; }

    public Direction findEntrance() {
        isGuarding = true;
        isAttacking = false;
        return returnHome();
    }

    public boolean attack(Ant other) {
        if (other == null || !isAlive() || !other.isAlive()) return false;

        // attacker check
        boolean attackerKill;
        if (!(other instanceof GuardAnt)) {
            attackerKill = rng().nextInt(4) < 3; // 3/4 victory chance
        } else {
            attackerKill = rng().nextBoolean(); // 1/2 victory chance
        }

        if (attackerKill) {
            other.kill();
            return true;
        }

        // retaliation
        boolean defenderKill = rng().nextBoolean(); // 1/2 victory chance
        if (defenderKill) this.kill();
        return false;
    }
}
// Dirt.java

public class Dirt extends Terrain {

    @Override
    public boolean isDiggable() {
        return true;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean isTraversable() {
        return false;
    }

    @Override
    public boolean isCarryable() {
        return true; // ants need to move dirt in order to make space
    }

    @Override
    public char getSymbol() {
        return '#';
    }
}
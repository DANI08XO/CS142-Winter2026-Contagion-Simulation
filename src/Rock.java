// Rock.java

public class Rock extends Terrain {

    @Override
    public boolean isDiggable() {
        return false;
    }

    @Override
    public boolean isSolid() {
        return true;
    }

    @Override
    public boolean isTraversable() {
        return false; // permanent obstacle
    }

    @Override
    public char getSymbol() {
        return 'R';
    }
}
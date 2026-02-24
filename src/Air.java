// Air.java

public class Air extends Terrain {

    @Override
    public boolean isDiggable() {
        return false;
    }

    @Override
    public boolean isTraversable() {
        return false;
    }

    @Override
    public char getSymbol() {
        return ' ';
    }
}
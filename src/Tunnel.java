// Tunnel.java

public class Tunnel extends Terrain {

    @Override
    public boolean isDiggable() {
        return false; // already open
    }

    @Override
    public boolean isTraversable() {
        return true;  // ants can walk on tunnel floors and walls
    }

    @Override
    public char getSymbol() {
        return '.';
    }
}
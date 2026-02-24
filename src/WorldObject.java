// WorldObject.java

public abstract class WorldObject {

    // can an ant pick this up and carry it?
    public boolean isCarryable() {
        return false;
    }

    // can an ant consume this for energy?
    public boolean isEdible() {
        return false;
    }

    // generic symbol system for GUI
    public char getSymbol() {
        return '?';
    }
}
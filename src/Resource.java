// Resource.java

public abstract class Resource extends WorldObject {

    private final String type;

    public Resource(String type) {
        if (type == null || type.isEmpty()) throw new IllegalArgumentException("type");
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean isCarryable() {
        return true;
    }

    @Override
    public boolean isEdible() {
        return false;
    }

    @Override
    public char getSymbol() {
        return 'S';
    }
}
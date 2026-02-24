// PheromoneType.java

public enum PheromoneType {
    FOOD(0),
    HOME(1),
    DANGER(2),
    BUILD(3);

    public final int type;

    PheromoneType(int type){
        this.type = type;
    }
}
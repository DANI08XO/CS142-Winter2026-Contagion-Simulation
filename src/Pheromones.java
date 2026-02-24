// Pheromones.java

public class Pheromones {
    private final int width;
    private final int height;

    // [type][y][x]
    private final double[][][] grid;

    public Pheromones(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new double[PheromoneType.values().length][width][height];
    }

    public double get(PheromoneType type, Point p) {
        return grid[type.type][p.x][p.y];
    }

    public void add(PheromoneType type, Point p, double amount) {
        grid[type.type][p.x][p.y] += amount;
    }

    public void set(PheromoneType type, Point p, double value) {
        grid[type.type][p.x][p.y] = value;
    }

    public void decay(double rate) {
        for (int t=0; t<PheromoneType.values().length; ++t) {
            for (int x=0; x<width; ++x) {
                for (int y=0; y<height; ++y) {
                    grid[t][x][y] *= rate;
                }
            }
        }
    }
}
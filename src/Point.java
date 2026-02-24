// Point.java

public final class Point {
    public final int x;
    public final int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point add(Direction dir) {
        return new Point(x + dir.dx, y + dir.dy);
    }

    @Override // override default behavior
    public boolean equals(Object o) {
        if (!(o instanceof Point p)) return false;
        return (x == p.x) && (y == p.y);
    }

    @Override
    public String toString() {
        return "("+x+", "+y+")";
    }
}
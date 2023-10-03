package grauly.roborumblegui.data;

public class Vector2 {
    private int x;
    private int y;

    public Vector2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public static Vector2 fromDirAndLength(RoboDirection direction, int length) {
        var vector = RoboDirection.getVector(direction);
        vector.multiply(length);
        return vector;
    }

    public void multiply(int scalar) {
        x *= scalar;
        y *= scalar;
    }

    public void add(Vector2 other) {
        x += other.getX();
        y += other.getY();
    }

    public boolean isInBoundsRect(int rangeBot, int rangeTop) {
        return x >= rangeBot && x <= rangeTop && y >= rangeBot && y <= rangeTop;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Vector2 clone() {
        return new Vector2(x,y);
    }
}

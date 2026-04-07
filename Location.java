import java.util.Objects;

public class Location {
    private int x;
    private int y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
	return this.x;
    }
    public int getY() {
	return this.y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Location)) return false;
        Location obj = (Location) o;

        return ((obj.x == this.x) && (obj.y == this.y));
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
    @Override
    public String toString() {
        return "(" + this.x + "," + this.y + ")";
    }
}

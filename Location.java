package model;

import java.util.Objects;

public class Location{
    private int x, y;

    public Location(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX(){ return x; }
    public int getY(){ return y; }

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof Location)) return false;
        Location l = (Location) o;
        return x == l.x && y == l.y;
    }

    @Override
    public int hashCode(){
        return Objects.hash(x, y);
    }
}


import java.util.HashMap;
import java.util.Map;

public class Piece {

    private Map<Direction, Piece> voisins;
    private Element occupant;
    private Location location;

    public Piece() {
        this.voisins = new HashMap<>();
        this.occupant = null;
    }

    public Piece(Element e) {
        this.voisins = new HashMap<>();
        this.occupant = e;
    }

    public void setVoisin(Direction dir, Piece voisine) {
        this.voisins.put(dir, voisine);
    }

    public Piece getVoisin(Direction dir) {
        return this.voisins.get(dir);
    }

    public void setOccupant(Element obj) {
        this.occupant = obj;
    }

    //changement : occupant peut être null (case vide) , NullPointerException sinon
    public boolean estLibre() {
        return occupant == null || !occupant.isSolide();
    }

    public Element getOccupant() {
        return occupant;
    }

    public void setLocation(Location l) { this.location = l; }

    public Location getLocation() { return this.location; }
}
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Plateau {

    private Map<Location, Piece> grille;
    private Location playerPosition;

    public Plateau() {
        this.grille = new HashMap<>();
    }

    public void ajouterPiece(Location loc, Piece piece) {
        this.grille.put(loc, piece);
        piece.setLocation(loc);
    }

    public Piece getPieceAt(Location loc) {
        return this.grille.get(loc);
    }

    //changement : retournait getPieceAt(playerPosition) soit une Piece
    public Location getPlayerPosition() {
        return this.playerPosition;
    }

    public void setPlayerPosition(Location l) {
        this.playerPosition = l;
    }

    public Location getLocationOf(Piece piece) {
        if (piece == null) throw new NullPointerException("piece is null");
        return piece.getLocation();
    }

    public Map<Location, Piece> getGrille() {
        return Collections.unmodifiableMap(grille);
    }
}
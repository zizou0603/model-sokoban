public class WorldContext {
    Plateau plateau;
    Piece containerPiece; // the Piece in the parent that holds the MiniMonde

    public WorldContext(Plateau plateau, Piece containerPiece) {
        this.plateau = plateau;
        this.containerPiece = containerPiece;
    }
}
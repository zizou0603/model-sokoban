public class CibleBox implements Element {

    @Override
    public boolean isSolide() {
        return false;
    }

    @Override
    public boolean isPoussable() {
        return false;
    }

    @Override
    public String getNom() {
        return "CibleBox";
    }

    @Override
    public Piece getPieceEntree(Direction dirEntree) {
        return null;
    }
}
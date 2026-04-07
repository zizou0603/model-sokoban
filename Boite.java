
public class Boite implements Element {

    private boolean fixe = false;

    public void setFixe(boolean fixe) {
        this.fixe = fixe;
    }

    public boolean isFixe() {
        return fixe;
    }

    @Override
    public boolean isSolide(){
        return true;
    }

    @Override
    public boolean isPoussable() {
        return !fixe;
    }

    @Override
    public String getNom() {
        return fixe ? "BoiteFixe" : "Boîte";
    }

    @Override
    public Piece getPieceEntree(Direction dirEntree) {
        return null;
    }
}

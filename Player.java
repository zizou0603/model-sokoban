package model;

public class Player extends Element{

    public Player(Piece p){
        super(p);
        p.setOccupant(this);
    }

    public boolean move(Direction d){
        Move m = tryMove(this, d);

        if (m == null) return false;

        m.apply();
        return true;
    }

    private Move tryMove(Element e, Direction d){

    Piece current = e.getPiece();
    Piece next = current.getVoisin(d);

    if (next == null) return null;

    if (next.estLibre()){
        return new Move(d, e, current, next);
    }

    if (next.getOccupant() instanceof Box){
        Box b = (Box) next.getOccupant();

        if (b.getInnerWorld() != null){
            Plateau inner = b.getInnerWorld();

            Piece entry = inner.getPieces().iterator().next();

            return new Move(d, e, current, entry);
        }

        Move sub = tryMove(b, d);
        if (sub == null) return null;

        Move m = new Move(d, e, current, next);
        m.setNext(sub);
        return m;
    }

    Plateau p = current.getPlateauParent();
    Piece container = p.getPieceConteneur();

    if (container != null) {
        Piece outside = container.getVoisin(d);

        if (outside != null && outside.estLibre()){
            return new Move(d, e, current, outside);
        }
    }

    return null;
}
}

package model; 

public class Box extends Element{

    private Plateau innerWorld;

    public Box(Piece p, Plateau inner){
        super(p);
        this.innerWorld = inner;
        p.setOccupant(this);

        if (inner != null){
            inner.setPieceConteneur(p);
        }
    }

    public Plateau getInnerWorld(){
        return innerWorld;
    }
}


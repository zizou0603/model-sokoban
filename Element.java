package model;

public abstract class Element{

    protected Piece piece;

    public Element(Piece p){
        this.piece = p;
    }

    public Piece getPiece(){
        return piece;
    }

    public void setPiece(Piece p){
        this.piece = p;
    }
}



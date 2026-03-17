package model;

import java.util.*;

public class Plateau{

    private int id;
    private Map<Location, Piece> grille = new HashMap<>();
    private Piece pieceConteneur;

    public Plateau(int id){
        this.id = id;
    }

    public void ajouterPiece(Piece p){
        grille.put(p.getLocation(), p);
    }

    public Piece getPieceAt(Location loc){
        return grille.get(loc);
    }

    public Collection<Piece> getPieces(){
        return grille.values();
    }

    public void setPieceConteneur(Piece p){
        this.pieceConteneur = p;
    }

    public Piece getPieceConteneur(){
        return pieceConteneur;
    }
}

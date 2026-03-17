package model;

import java.util.*;

public class Piece{

    private Location location;
    private Plateau plateauParent;
    private TypeFixe type;
    private Element occupant;
    private Map<Direction, Piece> voisins = new HashMap<>();

    public Piece(Location loc, Plateau p, TypeFixe type){
        this.location = loc;
        this.plateauParent = p;
        this.type = type;
    }

    public boolean estLibre(){
        return type != TypeFixe.MUR && occupant == null;
    }

    public void setVoisin(Direction d, Piece p, Direction oppose){
        voisins.put(d, p);
        p.voisins.put(oppose, this);
    }

    public Piece getVoisin(Direction d){
        return voisins.get(d);
    }

    public Location getLocation(){ return location; }

    public Element getOccupant(){ return occupant; }

    public void setOccupant(Element e){
        this.occupant = e;
    }

    public Plateau getPlateauParent(){ return plateauParent; }

    public TypeFixe getType(){ return type; }
}

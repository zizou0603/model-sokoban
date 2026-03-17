package model;

import java.util.*;

public class Move {

    private Direction direction;
    private Piece from;
    private Piece to;
    private Element element;
    private Move next;

    public Move(Direction d, Element e, Piece from, Piece to) {
        this.direction = d;
        this.element = e;
        this.from = from;
        this.to = to;
    }

    public void setNext(Move m) {
        this.next = m;
    }

    public void apply() {
    
    	System.out.println("Move: " + element.getClass().getSimpleName());
    	
        from.setOccupant(null);
        to.setOccupant(element);
        element.setPiece(to);

        if (next != null) next.apply();
    }

    public void rollback() {
        if (next != null) next.rollback();

        to.setOccupant(null);
        from.setOccupant(element);
        element.setPiece(from);
    }

}

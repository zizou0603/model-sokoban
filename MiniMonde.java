import java.util.HashMap;
import java.util.Map;

public class MiniMonde implements Element {
 	private Plateau mondeInterieur;
	private Map<Direction, Piece> entryPoints;
	private Plateau mondeParent;
 	
	public MiniMonde(Plateau mondeInterieur,Plateau mondeParent) {
 		this.mondeInterieur = mondeInterieur;
		this.mondeParent= mondeParent;
		this.entryPoints = new HashMap<Direction,Piece>();
 	}
 	public Plateau getMondeInterieur() { 
		return mondeInterieur;
       	}
 	@Override 
	public boolean isSolide() { 
		return true; 
	}
 	@Override 
	public boolean isPoussable() {
	       	return true; 
	}
 	@Override 
	public String getNom() { 
		return "Mini-Monde";
       	}

	public Piece getPieceEntree(Direction from) {
    	return entryPoints.get(from.opposite());
}

public Plateau getMondeParent(){
	return mondeParent;
}

public void setEntryPoint(Direction d, Piece p) {
    this.entryPoints.put(d, p);
}
}

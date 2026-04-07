public class Joueur implements Element {

	@Override 
	public boolean isSolide() { 
		return true; 
	}
 	@Override 
	public boolean isPoussable() { 
		return false; 
	}
 	@Override 
	public String getNom() { 
		return "Joueur"; 
	}
 	@Override 
	public Piece getPieceEntree(Direction dirEntree) { 
		return null; 
	}

}

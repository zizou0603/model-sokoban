 public enum Direction {
    HAUT, BAS, GAUCHE, DROITE;

    public Direction opposite() {
        switch (this) {
            case HAUT: return BAS;
            case BAS: return HAUT;
            case GAUCHE: return DROITE;
            case DROITE: return GAUCHE;
            default: throw new IllegalStateException("Unexpected value: " + this);
        }
    }
}




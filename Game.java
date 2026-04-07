import java.util.Stack;

public class Game {

    private Joueur player;
    private Plateau map;
    private Stack<Move> moves;
    private Stack<WorldContext> worldStack;

    public Game(Joueur player, Plateau map) {
        this.player = player;
        this.map = map;
        this.moves = new Stack<Move>();
        this.worldStack = new Stack<>();
    }

    public Plateau getMap() { return this.map; }

    // Game Controller: Link this to Solve button
    public void solve() {
        Cible c = map.getCible();
        Solver s = new Solver(this.map, this.player, c);
        Stack<Move> pathToVictory = s.resoudre();
        while (!pathToVictory.isEmpty()) {
            Move m = pathToVictory.pop();
            if (!apply(m)) throw new IllegalMoveException("Move can't be applied");
            moves.push(m);
        }
    }

    // Game Controller: Link this to Hint button
    public Move hint() {
        Cible c = map.getCible();
        Solver s = new Solver(this.map, this.player, c);
        Stack<Move> pathToVictory = s.resoudre();
        Stack<Move> reversed = new Stack<>();
        while (!pathToVictory.isEmpty()) {
            reversed.push(pathToVictory.pop());
        }
        return reversed.pop();
    }

    // Game Controller: Link this to choose lvl button
    public Plateau loadWorldFromFile(String filename) {
        if (filename == null) throw new NullPointerException("file unrecognized");
        LireNiveau reader = new LireNiveau();
        try {
            Plateau p = reader.lire(filename);
            this.map = p;
            return p;
        } catch (PlateNotLoadedException e) {
            throw new PlateNotLoadedException("Failed to load the plate");
        }
    }

    // Game Controller: link this to save lvl button
    public void saveWorldInFile(String filename) {
        if (filename == null) throw new NullPointerException("File unrecognized");
        EcritNiveau writer = new EcritNiveau();
        try {
            writer.ecrire(filename, this.map);
        } catch (PlateNotSavedException e) {
            throw new PlateNotSavedException("Failed to save the Plate");
        }
    }

    // Game Controller: Link this to Event:PIECE_PRESSED
    public void moveToPiece(Piece p) {
        if (p == null) throw new NullPointerException("Piece provided is null");

        Location depart = map.getPlayerPosition();
        Location arrive = map.getLocationOf(p);
        java.util.List<Direction> path = Chemins.trouverChemin(depart, arrive, map);

        if (path == null) throw new IllegalMoveException("No path found");

        for (Direction d : path) {
            Move m = new Move(d, null);
            if (!apply(m)) throw new IllegalMoveException("Path Failed");
            this.moves.push(m);
        }
    }

    // Game Logic: test if move is legal and apply it
    public boolean apply(Move m) {
        if (m == null) throw new NullPointerException("Move is null");
        if (!isLegalMove(m)) return false;
        applyMove(m);
        return true;
    }

    private boolean isLegalMove(Move m) {
        Direction d = m.getDirection();

        // --- WORLD LEAVE (checked first, independent of next tile) ---
        if (!worldStack.isEmpty()) {
            WorldContext ctx = worldStack.peek();
            Piece outside = ctx.containerPiece.getVoisin(d);
            if (outside != null && outside.estLibre()) {
                m.setType(TypeMouvement.WORLDLEAVE);
                return true;
            }
        }

        Location currentLoc = map.getPlayerPosition();
        Piece current = map.getPieceAt(currentLoc);
        Piece next = current.getVoisin(d);

        if (next == null) return false;

        Element occupant = next.getOccupant();

        // --- SIMPLE ---
        if (next.estLibre()) {
            m.setType(TypeMouvement.SIMPLE);
            return true;
        }

        // --- POUSSABLE (Box OR MiniMonde) ---
        if (occupant != null && occupant.isPoussable()) {
            Piece after = next.getVoisin(d);

            if (after != null && after.estLibre()) {
                m.setType(TypeMouvement.BOXMOVED);
                return true;
            }

            // --- WORLD ENTRY (only if MiniMonde can't be pushed) ---
            if (occupant instanceof MiniMonde) {
                Piece entry = ((MiniMonde) occupant).getPieceEntree(d);
                if (entry != null && entry.estLibre()) {
                    m.setType(TypeMouvement.WORLDENTRY);
                    return true;
                }
            }
        }

        return false;
    }

    private void applyMove(Move m) {
        Direction d = m.getDirection();

        Location currentLoc = map.getPlayerPosition();
        Piece current = map.getPieceAt(currentLoc);
        Piece next = current.getVoisin(d);

        Element playerEl = current.getOccupant();

        switch (m.getType()) {

            case SIMPLE:
                next.setOccupant(playerEl);
                current.setOccupant(null);
                map.setPlayerPosition(next.getLocation());
                break;

            case BOXMOVED:
                Element pushed = next.getOccupant();
                Piece after = next.getVoisin(d);

                if (after.getOccupant() instanceof CibleBox) {
                    if (pushed instanceof Boite) {
                        ((Boite) pushed).setFixe(true);
                    }
                }
                after.setOccupant(pushed);
                next.setOccupant(playerEl);
                current.setOccupant(null);
                map.setPlayerPosition(next.getLocation());
                break;

            case WORLDENTRY:
                MiniMonde monde = (MiniMonde) next.getOccupant();
                Piece entry = monde.getPieceEntree(d);

                current.setOccupant(null);
                entry.setOccupant(playerEl);

                worldStack.push(new WorldContext(map, next));
                map = monde.getMondeInterieur();
                map.setPlayerPosition(entry.getLocation());
                break;

            case WORLDLEAVE:
                if (worldStack.isEmpty())
                    throw new IllegalStateException("Can't leave: already in root world");

                WorldContext ctx = worldStack.pop();
                Piece outside = ctx.containerPiece.getVoisin(d);

                if (outside == null || !outside.estLibre())
                    throw new IllegalMoveException("Exit is blocked");

                current.setOccupant(null);
                outside.setOccupant(playerEl);

                map = ctx.plateau;
                map.setPlayerPosition(outside.getLocation());
                break;
        }
    }

    public boolean rollback() {
        if (moves.isEmpty()) return false;

        Move m = moves.pop();
        Direction d = m.getDirection();
        Direction opposite = d.opposite();

        Location currentLoc = map.getPlayerPosition();
        Piece current = map.getPieceAt(currentLoc);
        Element playerEl = current.getOccupant();

        switch (m.getType()) {

            case SIMPLE:
                Piece prev = current.getVoisin(opposite);
                prev.setOccupant(playerEl);
                current.setOccupant(null);
                map.setPlayerPosition(prev.getLocation());
                break;

            case BOXMOVED:
                Piece boxNow    = current.getVoisin(d);       // où est la boite maintenant
                Piece boxOrigin = current;                     // où était la boite avant
                Piece playerOrigin = current.getVoisin(opposite); // d'où venait le joueur

                Element box = boxNow.getOccupant();

                // Annuler le setFixe si la boite avait atterri sur une CibleBox
                if (box instanceof Boite) {
                    ((Boite) box).setFixe(false);
                }

                boxOrigin.setOccupant(box);     // boite retourne à sa place
                boxNow.setOccupant(null);        // case de la boite vidée
                playerOrigin.setOccupant(playerEl); // joueur retourne à sa place
                current.setOccupant(null);
                map.setPlayerPosition(playerOrigin.getLocation());
                break;

            case WORLDENTRY:
                if (worldStack.isEmpty())
                    throw new IllegalStateException("worldStack is empty on WORLDENTRY rollback");

                WorldContext ctx = worldStack.pop();
                Piece reentry = ctx.containerPiece.getVoisin(opposite);

                current.setOccupant(null);
                reentry.setOccupant(playerEl);

                map = ctx.plateau;
                map.setPlayerPosition(reentry.getLocation());
                break;

            case WORLDLEAVE:
                Piece containerPiece = current.getVoisin(opposite);
                MiniMonde monde = (MiniMonde) containerPiece.getOccupant();
                Piece exitPiece = monde.getPieceEntree(d);

                current.setOccupant(null);
                exitPiece.setOccupant(playerEl);

                worldStack.push(new WorldContext(map, containerPiece));
                map = monde.getMondeInterieur();
                map.setPlayerPosition(exitPiece.getLocation());
                break;
        }

        return true;
    }

    // isVictory() : Solver (responsable ResAuto)
    public boolean isVictory() {
        return Solver.isVictory(map);
    }
}
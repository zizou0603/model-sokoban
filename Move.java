
import java.util.*;

public class Move {

    private Direction direction;
    private TypeMouvement type;

    public Move(Direction d,TypeMouvement type) {
        this.direction = d;
        this.type = type;
    }

    public Direction getDirection(){
        return this.direction;
    }
    public TypeMouvement getType(){
        return type;
    }
    public void setType(TypeMouvement t){
        this.type= t;
    }

}
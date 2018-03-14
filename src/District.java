import java.util.ArrayList;
import java.util.List;

/**
 * The district class represent a district area on the
 * London map
 */
public class District {

    private int x;
    private int y;
    private int numberOfBnbs;
    private String name;
    private List<AirbnbListing> bnbs;


    public District(String name, int x, int y){
        this.name = name;
        this.x = x;
        this.y = y;
        numberOfBnbs = 0;
        bnbs = new ArrayList<>();
    }

    public void addBnb(AirbnbListing bnb){
        bnbs.add(bnb);
        numberOfBnbs++;
    }

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

}

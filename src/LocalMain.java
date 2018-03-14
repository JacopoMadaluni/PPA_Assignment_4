import java.util.ArrayList;
import java.util.List;

public class LocalMain {

    public static void main(String[] args){
        GuiTestFrame gui = new GuiTestFrame();
        try{
            Thread.sleep(1000);
        }catch(Exception e) {
            System.out.println(e);
        }
        gui.drawIconTest();

        AirbnbDataLoader dataLoader = new AirbnbDataLoader();
        ArrayList<AirbnbListing> bnbs = dataLoader.load();

        ArrayList<String> districts = listAllDistricts(bnbs);

        for (String s : districts){
            System.out.println(s);
        }

    }

    /**
     * Testing method that returns a list with all the possible districts where a
     * airbnb can be placed.
     * @param bnbs The list of all AirBnbs loaded.
     * @return
     */
    public static ArrayList<String> listAllDistricts(List<AirbnbListing> bnbs){
        ArrayList<String> districts = new ArrayList<>();
        for (AirbnbListing a : bnbs){
            if (!districts.contains(a.getNeighbourhood())){
                districts.add(a.getNeighbourhood());
            }
        }
        System.out.println("Districts loaded!");
        return districts;
    }
}

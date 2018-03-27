import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;


/**
 * This class draws a statistics panel with the average price per
 * room type.
 * @author Jacopo Madaluni 1737569
 */
public class AverageRoomtypePrice extends BarChart{

    private List<AirbnbListing> bnbToShow;
    private HashMap<String, Integer> rooms;

    public AverageRoomtypePrice(String title, List<AirbnbListing> listings, int lowPrice, int highPrice){
        super(title, listings, lowPrice, highPrice);


    }

    @Override
    protected String getYLabel() {
        return "Average Price Per Room Type";
    }

    @Override
    protected String getXLabel() {
        return "Room type";
    }

    /**
     * Return the text to be displayed at the bottom of the central panel
     * @return the text to be displayed at the bottom of the central panel
     */
    @Override
    public String getBottomText() {
        return( getTitle() + "");
    }

    /**
     * Create the dataset for the data visualisation
     * @return the dataset for the data visualisation
     */
    @Override
    protected CategoryDataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice ) {
        rooms = new HashMap<>();
        bnbToShow = getListingsInRange(listings);
        computeAverages();
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        // add values to dataset
        try {
            for(Map.Entry<String, Integer> entry : rooms.entrySet()){
                String room = entry.getKey();
                Integer average = entry.getValue();
                dataset.addValue(average,room, "" );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    private List<String> getRoomTypes(){
        List<String> roomTypes = new ArrayList<>();
        for (AirbnbListing bnb: bnbToShow){
            String roomType = bnb.getRoom_type();
            if (!roomTypes.contains(roomType)){
                roomTypes.add(roomType);
            }
        }
        return roomTypes;
    }

    private void computeAverages(){
        List<String> roomTypes = getRoomTypes();
        for (String roomType : roomTypes){
            int divisor = 0;
            int sum = 0;
            int average;
            for (AirbnbListing bnb : bnbToShow){
                if (bnb.getRoom_type().equals(roomType)){
                    sum = sum + bnb.getPrice();
                    divisor++;
                }
            }
            if (divisor != 0){
                average = sum/divisor;
                rooms.put(roomType, average);
            }

        }
    }

}

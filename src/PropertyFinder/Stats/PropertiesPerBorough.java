package PropertyFinder.Stats;

import PropertyFinder.AirbnbListing;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import java.util.*;

/**
 * This class initialises and creates the dataset needed for a barchart in order to display
 * the average number of properties in the 5 boroughs with the highest value
 * @author Danilo Del Busso
 * @version 28.03.2018
 */
public class PropertiesPerBorough extends BarChart{
    private static final int  numberOfBoroughs = 5; //the number of boroughs shown on the chart
    private HashMap<String, Double> listingsPerBorough;
    /**
     * This Panel shows the number of properties per borough
     * @param title The title of the panel
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public PropertiesPerBorough(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
    }

    @Override
    protected Dataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice) {
        listingsPerBorough = new HashMap<>();
        setDistricts();
        
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
        
        //name of group of bar graphs
        final String averageValue = "Top "+numberOfBoroughs+" Boroughs";

        // add values to dataset
        try {

            for(int i = 0; i<numberOfBoroughs; i++){
                String borough = getBoroughByPosition(i+1, listingsPerBorough);
               dataset.addValue(listingsPerBorough.get(borough) , borough , averageValue );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    /**
     * Sets up the collection of district names and average number of reviews
    */
    private void setDistricts(){
        for (String district: getAllNeighbourhoods()){
            listingsPerBorough.put(district,getListingsByBorough(listings,district));
        }
    }

    /**
     * Return the number of listings in a borough
     * @return the number of listings in a borough
     */
    private double getListingsByBorough(List<AirbnbListing> listings, String borough) {
       double sum = 0;
        for(AirbnbListing listing: getListingsInRange(listings)){
            if(listing.getNeighbourhood().equals(borough)){
                sum++;
            }
        }

        return sum;
    }
    @Override
    protected String getXLabel() {
        return "Borough";
    }

    @Override
    protected String getYLabel() {
        return "Number of Properties";
    }

    @Override
    public String getBottomText() {
        return null;
    }
}

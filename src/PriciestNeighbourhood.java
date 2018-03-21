import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.text.DecimalFormat;
import java.util.*;

/**
 * This panel shows the priciest neighbourhoods and their values
 * @author Danilo Del Busso
 * @version 21.03.2018
 */
public class PriciestNeighbourhood extends BarChart {
    private HashMap<String, Double> neighbourhoodsPrices;
    //the number of neighbourhoods shown on scren at any time
    private static final int neighbourhoodsToShow = 10;

    /**
     * Show priciest 10 neighbourhoods in the dataset and in subsections of it
     * divided by price range
     * @param title  the title of the panel
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public PriciestNeighbourhood(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
        createBottomBox();
    }

    /**
     * Create the dataset for the data visualisation
     * @return the dataset for the data visualisation
     */
    @Override
    protected CategoryDataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice ) {
        neighbourhoodsPrices = new HashMap<>();
        initialiseNeighbourhoods(lowPrice, highPrice);
        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

        //name of group of bar graphs
        final String total = "All Neighbourhoods";

        // add values to dataset
        try {
           for(int i = neighbourhoodsToShow; i>0 ;i--){
               dataset.addValue(getPriceNeighboorhood(i),getNameNeighboorhood(i), total );
           }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    /**
     * Return the price of the neighbourhood at the given position, ordered by neighbourhood price
     * @param position the position of neighbourhood as priciest on the map (e.g. 1 is priciest)
     * @return the price of the neighbourhood at the given position, ordered by neighbourhood price
     */
    private double getPriceNeighboorhood(int position) {
        ArrayList<Double> valuesList = new ArrayList<>(neighbourhoodsPrices.values());
        Collections.sort(valuesList);
        Collections.reverse(valuesList);
        return valuesList.get(position-1);
    }

    /**
     * Return the name of the neighbourhood at the given position, ordered by neighbourhood price
     * @param position the position of neighbourhood as priciest on the map (e.g. 1 is priciest)
     * @return the name of the neighbourhood at the given position, ordered by neighbourhood price
     */
    private String getNameNeighboorhood(int position) {
          ArrayList<Double> valuesList = new ArrayList<>(neighbourhoodsPrices.values());
          Collections.sort(valuesList);
          Collections.reverse(valuesList);
          Iterator it = neighbourhoodsPrices.entrySet().iterator();
          if(position > 0 && position<valuesList.size()){
               while(it.hasNext()){
                   HashMap.Entry pair = (HashMap.Entry)it.next();
                   if(pair.getValue() == valuesList.get(position-1)){
                       return (String) pair.getKey();
                   }
               }
          }
        return "";
    }

    /**
     * Return the value of the neighbourhood in the given price range
     * @param listings the dataset used for calculation
     * @param lowPrice the lower bound of price range used for searching
     * @param highPrice the upper bound of price range used for searching
     * @return the value of the neighbourhood in the given price range.
     */
    private Double getNeighbourhoodValue(List<AirbnbListing> listings, int lowPrice, int highPrice, String neighbourhood) {
        double price = 0;
        double n = 0;
        for(AirbnbListing listing: listings){
           if(listing.getPrice()<= highPrice && listing.getPrice()>=lowPrice && listing.getNeighbourhood().equals(neighbourhood)){
                   price += listing.getPrice() / listing.getMinimumNights();
                   n++;
           }
        }
        //if n is 0 return 0
        return n>0? price/n : 0;
    }

    /**
     * Initialise and construct the hashmap containing all boroughs and their respective
     * max price
     */
    private void initialiseNeighbourhoods(int lowPrice, int highPrice){
        neighbourhoodsPrices.put(
                "Kingston upon Thames",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Kingston upon Thames"));
        neighbourhoodsPrices.put("Croydon",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Croydon"));
        neighbourhoodsPrices.put("Bromley",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Bromley"));
        neighbourhoodsPrices.put("Hounslow",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Hounslow"));
        neighbourhoodsPrices.put("Ealing",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Ealing"));
        neighbourhoodsPrices.put("Havering",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Havering"));
        neighbourhoodsPrices.put("Hillington",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Hillington"));
        neighbourhoodsPrices.put("Harrow",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Harrow"));
        neighbourhoodsPrices.put("Brent",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Brent"));
        neighbourhoodsPrices.put("Barnet",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Barnet"));
        neighbourhoodsPrices.put("Enfield",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Enfield"));
        neighbourhoodsPrices.put("Waltham Forest",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Waltham Forest"));
        neighbourhoodsPrices.put("Redbridge",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Redbridge"));
        neighbourhoodsPrices.put("Sutton",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Sutton"));
        neighbourhoodsPrices.put("Lambeth",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Lambeth"));
        neighbourhoodsPrices.put("Southwark",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Southwark"));
        neighbourhoodsPrices.put("Lewisham",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Lewisham"));
        neighbourhoodsPrices.put("Greenwich",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Greenwich"));
        neighbourhoodsPrices.put("Bexley",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Bexley"));
        neighbourhoodsPrices.put("Richmond upon Thames",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Richmond upon Thames"));
        neighbourhoodsPrices.put("Merton",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Merton"));
        neighbourhoodsPrices.put("Wandsworth",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Wandsworth"));
        neighbourhoodsPrices.put("Hammersmith and Fulham",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Hammersmith and Fulham"));
        neighbourhoodsPrices.put("Kensington and Chelsea",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Kensington and Chelsea"));
        neighbourhoodsPrices.put("City of London",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "City of London"));
        neighbourhoodsPrices.put("Westminster",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Westminster"));
        neighbourhoodsPrices.put("Camden",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Camden"));
        neighbourhoodsPrices.put("Tower Hamlets",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Tower Hamlets"));
        neighbourhoodsPrices.put("Islington",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Islington"));
        neighbourhoodsPrices.put("Hackney",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Hackney"));
        neighbourhoodsPrices.put("Haringey",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Haringey"));
        neighbourhoodsPrices.put("Newham",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Newham"));
        neighbourhoodsPrices.put("Barking and Dagenham",
                getNeighbourhoodValue(listings, lowPrice, highPrice, "Barking and Dagenham"));
    }



    /**
     * Return the text to be displayed at the bottom of the central panel
     * @return the text to be displayed at the bottom of the central panel
     */
    @Override
    public String getBottomText() {
        return( getTitle() + " between " + lowPrice+"£ and "+ highPrice+"£ is:\n" +
                "► "+getNameNeighboorhood(1).toUpperCase() + " with an average value of \n"+
                Double.parseDouble(new DecimalFormat("#####.##").format(getPriceNeighboorhood(1)))+"£ per property per night");
    }
}

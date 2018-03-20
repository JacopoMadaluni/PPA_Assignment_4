import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.ArrayList;
import java.util.List;

public class AvgReviewScore extends CentralPanel {
    int lowPrice;
    int highPrice;

    /**
     * This Panel shows the average review score of available properties
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public AvgReviewScore(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Review Range by Listing Price",
                "Number of Reviews",
                createDataset(),
                PlotOrientation.VERTICAL,
                true, true, false);

        ChartPanel chartPanel = new ChartPanel( barChart );
        add( chartPanel );

    }

    /**
     * Create the dataset for the data visualisation
     * @return the dataset for the data visualisation
     */
    private CategoryDataset createDataset( ) {

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );


        final String all = "Total Average";

        //name of group of bar graphs
        final String averageValue = "All listings";

        // add values to dataset
        dataset.addValue(calculateAvgReviewScore(listings, lowPrice,highPrice/4), lowBound, averageValue );
        dataset.addValue(calculateAvgReviewScore(listings, highPrice/4,highPrice/4*2), mediumLowBound, averageValue );
        dataset.addValue(calculateAvgReviewScore(listings, highPrice/4*2,highPrice/4*3), mediumHighBound, averageValue );
        dataset.addValue(calculateAvgReviewScore(listings, highPrice/4*3,highPrice/4), highBound, averageValue );
        dataset.addValue( calculateAvgReviewScore(listings, lowPrice,highPrice), all , averageValue );

        return dataset;
    }

    /**
     * Return the average review score of the listings given as parameter
     * @param listings the dataset for the calculation
     * @return the average review score of the listings given as parameter
     */
    private double calculateAvgReviewScore(List<AirbnbListing> listings, int lowBound ,int highBound) {
        double sum = 0;
        double avg = 0;
        for(AirbnbListing listing: listings){
            if(listing.getPrice()<=highBound && listing.getPrice()>=lowBound){
                //  TODO
                //can only be done after requirement conflict has been resolve (no data in dataset)
            }

        }
        return avg;
    }


    /**
     * Add all listings that are in the price range to the
     * listingsInRange arrayList
     * @param listings the entirety of all listings in the dataset
     */
    private ArrayList<AirbnbListing> getListingsInRange(List<AirbnbListing> listings) {
        ArrayList<AirbnbListing> listingsInRange = new ArrayList<>();
        for(AirbnbListing listing: listings){
            if(listing.getPrice() <= highPrice && listing.getPrice()>= lowPrice){
                listingsInRange.add(listing);
            }
        }
        return listingsInRange;
    }
}

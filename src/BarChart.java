import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;

public class BarChart extends CentralPanel {


    /**
     * This Panel shows the average review score of available properties
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public BarChart(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
    }

    @Override
    protected JFreeChart getChart(String title) {

        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Review Range by Listing Price",
                "Number of Reviews",
                createDataset(listings, lowPrice, highPrice),
                PlotOrientation.VERTICAL,
                true, true, false);
        return barChart;
    }
    /**
     * Create the dataset for the data visualisation
     * @return the dataset for the data visualisation
     */
    protected CategoryDataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice ) {

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset( );


        final String all = "Total Average";

        //name of group of bar graphs
        final String averageValue = "All listings";

        // add values to dataset
        dataset.addValue(getAvgFromData(listings, lowPrice,highPrice/4, "review_score"), lowBound, averageValue );
        dataset.addValue(getAvgFromData(listings, highPrice/4,highPrice/4*2,"review_score"), mediumLowBound, averageValue );
        dataset.addValue(getAvgFromData(listings, highPrice/4*2,highPrice/4*3,"review_score"), mediumHighBound, averageValue );
        dataset.addValue(getAvgFromData(listings, highPrice/4*3,highPrice/4,"review_score"), highBound, averageValue );
        dataset.addValue( getAvgFromData(listings, lowPrice,highPrice,"review_score"), all , averageValue );

        return dataset;
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

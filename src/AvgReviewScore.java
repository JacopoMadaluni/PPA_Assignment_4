import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.List;

public class AvgReviewScore extends BarChart{
    /**
     * This Panel shows the average review score of available properties
     *
     * @param title
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public AvgReviewScore(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
    }

    /**
     * Create the dataset for the data visualisation
     * @return the dataset for the data visualisation
     */
    @Override
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
        dataset.addValue(getAvgFromData(listings, lowPrice,highPrice,"review_score"), all , averageValue );

        return dataset;
    }

    @Override
    public String getBottomText() {
        return( getTitle() + " between " + lowPrice+"£ and "+ highPrice+"£: "+getAvgFromData(listings, lowPrice, highPrice, "reviewScore"));
    }

}

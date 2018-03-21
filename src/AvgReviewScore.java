import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.text.DecimalFormat;
import java.util.List;

/**
 * This class initialises and creates the dataset needed for a barchart in order to display
 * the average review score of all listings in the dataset
 * @author Danilo Del Busso
 * @version 21.03.2018
 */
public class AvgReviewScore extends BarChart{
    /**
     * This Panel shows the average review score of available properties
     *
     * @param title the title of the panel to be displayed at the top
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public AvgReviewScore(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
        createBottomBox();
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
        try {
            dataset.addValue(getAvgFromData(listings, lowPrice,highPrice/4, "review_score"), lowBound, averageValue );
            dataset.addValue(getAvgFromData(listings, highPrice/4,highPrice/4*2,"review_score"), mediumLowBound, averageValue );
            dataset.addValue(getAvgFromData(listings, highPrice/4*2,highPrice/4*3,"review_score"), mediumHighBound, averageValue );
            dataset.addValue(getAvgFromData(listings, highPrice/4*3,highPrice/4,"review_score"), highBound, averageValue );
            dataset.addValue(getAvgFromData(listings, lowPrice,highPrice,"review_score"), all , averageValue );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataset;
    }

    /**
     * Return the text to be displayed at the bottom of the central panel
     * @return the text to be displayed at the bottom of the central panel
     */
    @Override
    public String getBottomText() {
        try {
            return( getTitle() + " between " + lowPrice+"£ and "+ highPrice+"£: "
                    +Double.parseDouble(new DecimalFormat("#####.##").format(
                            getAvgFromData(listings, lowPrice, highPrice, "review_score"))));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

}

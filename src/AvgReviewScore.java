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

    @Override
    protected String getYLabel() {
        return "Number Of Reviews";
    }

    @Override
    protected String getXLabel() {
        return "Review Range By Listing Price";
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

        Double[] bounds = calculateExponentialBounds(4, lowPrice, highPrice);
        double lowStats = bounds[0];
        double lowMidStats= bounds[1];
        double highMidStats = bounds[2];
        double highStats = bounds[3];

        // add values to dataset
        try {
            dataset.addValue(getAvgFromData(listings, lowPrice,lowStats, "review_score"), getLowBound(), averageValue );
            dataset.addValue(getAvgFromData(listings, lowStats,lowMidStats,"review_score"), getMediumLowBound(), averageValue );
            dataset.addValue(getAvgFromData(listings, lowMidStats,highMidStats,"review_score"), getMediumHighBound(), averageValue );
            dataset.addValue(getAvgFromData(listings, highMidStats,highStats,"review_score"), getHighBound(), averageValue );
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

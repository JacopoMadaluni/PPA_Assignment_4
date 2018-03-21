import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import java.util.ArrayList;
import java.util.List;

/**
 * This class initialises and creates the dataset needed for a piechart in order to display
 * the total number of listings it is given as well as the number of listings
 * in price subsections (e.g. the number of properties of price between 0£ - 500£)
 * @author Danilo Del Busso
 * @version 21.03.2018
 */
public class TotListings extends PieChart {
    /**
     * This panel shows in a graph the total number of available properties from a given dataset
     *
     * @param title     Title of the panel.
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public TotListings(String title, ArrayList<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
        createBottomBox();
    }

    /**
     * Create dataset for piechart
     * @return dataset for piechart
     * @param listings the initial dataset
     */
    @Override
    protected PieDataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice) {

        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
        //low category
        dataset.setValue(lowBound, new Double(
                getTotFromData(listings, lowPrice, highPrice/4)
        ));

        //mediumlow category
        dataset.setValue(mediumLowBound, new Double(
                getTotFromData(listings, highPrice/4, highPrice/4*2)
        ));
        //mediumhigh category
        dataset.setValue(mediumHighBound, new Double(
                getTotFromData(listings, highPrice/4*2, highPrice/4*3)
        ));
        //high category
        dataset.setValue(highBound, new Double(
                getTotFromData(listings, highPrice/4*3, highPrice)
        ));
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
            return (getTitle() + " between " + lowPrice+"£ and "+ highPrice+"£: "+getTotFromData(listings, lowPrice, highPrice));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}

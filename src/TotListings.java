import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import java.util.ArrayList;
import java.util.List;

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
    }

    /**
     * Create dataset for piechart
     * @return dataset for piechart
     * @param listings the initial dataset
     */
    @Override
    protected PieDataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice) {

        DefaultPieDataset dataset = new DefaultPieDataset();
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

        return dataset;
    }

    @Override
    public String getBottomText() {
        return (getTitle() + " between " + lowPrice+"£ and "+ highPrice+"£: "+getTotFromData(listings, lowPrice, highPrice));
    }
}

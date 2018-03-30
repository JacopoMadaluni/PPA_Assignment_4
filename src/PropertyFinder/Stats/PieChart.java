package PropertyFinder.Stats;

import java.util.ArrayList;
import PropertyFinder.AirbnbListing;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;

/**
 * A type of central panel that displays a piechart with the given data.
 * Needs subclass with dataset in order to diplay information.
 * @author Danilo Del Busso
 * @version 25.03.2018
 */
public abstract class PieChart extends ChartCentralPanel {

    /**
     * This panel shows a Piechart constructed from a given dataset retrieved from
     * its subclass.
     *
     * @param title     Title of the panel.
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public PieChart(String title, ArrayList<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
    }

    /**
     * Return the chart that will be displayed
     * @param title the title of the chart
     * @return the chart that will be displayed
     */
    @Override
    protected JFreeChart getChart(String title) {
        initialiseExponentialBounds();
        return   ChartFactory.createPieChart(
                title,   // chart title
                (PieDataset) createDataset(listings, lowPrice, highPrice),          // data
                true,             // include legend
                true,
                false);
    }

}

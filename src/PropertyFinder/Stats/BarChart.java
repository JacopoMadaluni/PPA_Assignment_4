package PropertyFinder.Stats;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import java.util.List;
import PropertyFinder.*;

/**
 * This class lays the foundations for the creation of a bar chart. It creates the
 * chart and initialises the bounds in which its elements are subdivided.
 * @author Danilo Del Busso
 * @version 25.03.2018
 */
public abstract class BarChart extends ChartCentralPanel {


    /**
     * This Panel shows the average review score of available properties
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public BarChart(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
    }

    /**
     * Return the chart that will be displayed
     * @param title the title of the chart
     * @return the chart that will be displayed
     */
    @Override
    protected JFreeChart getChart(String title) {
        initialiseLinearBounds();
       return ChartFactory.createBarChart(
                title,
                getXLabel(),
                getYLabel(),
                (CategoryDataset) createDataset(listings, lowPrice, highPrice),
                PlotOrientation.VERTICAL,
                true, true, false);
    }

    /**
     * Return the label of the ordinatae
     * @return the label of the ordinatae
     */
    protected abstract String getYLabel();

    /**
     * Return the label of the abscissae
     * @return the label of the abscissae
     */
    protected abstract String getXLabel();
}

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import java.util.ArrayList;
import java.util.List;

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

        JFreeChart barChart = ChartFactory.createBarChart(
                title,
                "Review Range by Listing Price",
                "Number of Reviews",
                (CategoryDataset) createDataset(listings, lowPrice, highPrice),
                PlotOrientation.VERTICAL,
                true, true, false);
        return barChart;
    }
}

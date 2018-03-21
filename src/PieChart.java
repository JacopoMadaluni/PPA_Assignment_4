import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.PieDataset;

public abstract class PieChart extends ChartCentralPanel {

    /**
     * This panel shows in a graph the total number of available properties from a given dataset
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
     * Return the chart that shows the data
     * @param title the title of the chart
     * @return the chart that shows the data
     */
    @Override
    protected JFreeChart getChart(String title) {
        JFreeChart pie =   ChartFactory.createPieChart(
                title,   // chart title
                (PieDataset) createDataset(listings, lowPrice, highPrice),          // data
                true,             // include legend
                true,
                false);
        return pie;
    }



}

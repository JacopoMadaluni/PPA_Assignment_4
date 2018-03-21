import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import javax.swing.*;

public class PieChart extends CentralPanel {

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

    /**
     * Return the chart that shows the data
     * @param title the title of the chart
     * @return the chart that shows the data
     */
    @Override
    protected JFreeChart getChart(String title) {
        JFreeChart pie =   ChartFactory.createPieChart(
                title,   // chart title
                createDataset(listings, lowPrice, highPrice),          // data
                true,             // include legend
                true,
                false);
        return pie;
    }


}

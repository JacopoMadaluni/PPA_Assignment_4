import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import javax.swing.*;

public class TotNumberStats extends CentralPanel {
    private ArrayList<AirbnbListing> filteredListings;
    /**
     * This panel shows in a graph the total number of available properties from a given dataset
     *
     * @param title     Title of the panel.
     * @param filteredListings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public TotNumberStats(String title, ArrayList<AirbnbListing> filteredListings, int lowPrice, int highPrice) {
        super(title, filteredListings, lowPrice, highPrice);
        setLayout(new BorderLayout());
        this.filteredListings = filteredListings;

        //create the chart
        createChart(title);

        //Set Text description at the bottom
        JTextArea  total = new JTextArea ();
        total.setEditable(false);
        total.setText(title + " between " + lowPrice+"£ and "+ highPrice+"£: "+numberOfPropertiesFromData(filteredListings, lowPrice, highPrice));
        total.setFont(new Font("Arial", Font.BOLD, 25));
        add(total, BorderLayout.SOUTH);

    }

    /**
     * Initialise and create the chart
     */
    public void createChart(String title){
        ChartPanel chartPanel = new ChartPanel(getPieChart(title));
        add(chartPanel, BorderLayout.CENTER);
    
    }

    /**
     * Create dataset for piechart
     * @return dataset for piechart
     * @param filteredListings the initial dataset
     */
    private PieDataset createDataset(List<AirbnbListing> filteredListings, int lowPrice, int highPrice) {

        DefaultPieDataset dataset = new DefaultPieDataset();
        //low category
        dataset.setValue(lowBound, new Double(
                numberOfPropertiesFromData(filteredListings, lowPrice, highPrice/4)
        ));
        //mediumlow category
        dataset.setValue(mediumLowBound, new Double(
                numberOfPropertiesFromData(filteredListings, highPrice/4, highPrice/4*2)
        ));
        //mediumhigh category
        dataset.setValue(mediumHighBound, new Double(
                numberOfPropertiesFromData(filteredListings, highPrice/4*2, highPrice/4*3)
        ));
        //high category
        dataset.setValue(highBound, new Double(
                numberOfPropertiesFromData(filteredListings, highPrice/4*3, highPrice)
        ));

        return dataset;
    }

    /**
     * Calculate number of listings in given price range
     * @param filteredfilteredListings
     * @param lowPrice
     * @param highPrice
     * @return
     */
    private int numberOfPropertiesFromData(List<AirbnbListing> filteredfilteredListings, int lowPrice, int highPrice) {
        int n = 0;
        for(AirbnbListing listing : filteredfilteredListings){
            if(listing.getPrice()<= highPrice && listing.getPrice()>=lowPrice){
                n++;
            }
        }
        return  n;
    }

    /**
     * Return the chart that shows the data
     * @param title the title of the chart
     * @return the chart that shows the data
     */
    public JFreeChart getPieChart(String title) {
        JFreeChart pie =   ChartFactory.createPieChart(
                title,   // chart title
                createDataset(filteredListings, lowPrice, highPrice),          // data
                true,             // include legend
                true,
                false);
        return pie;
    }
}

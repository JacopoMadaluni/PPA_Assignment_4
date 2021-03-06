package PropertyFinder.Stats;

import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import javax.swing.*;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.List;
import PropertyFinder.*;

/**
 * A special panel to be inserted in a StatsSubPanel.
 * It contains a statistic result calculated from the dataset it's given.
 * @author Danilo Del Busso
 * @version 25.03.2018
 */
public abstract class ChartCentralPanel extends AppPanel {
    private String lowBound;
    private String mediumLowBound;
    private String mediumHighBound;
    private String highBound;

    /**
     *  Create a new Central Panel to be displayed in the StatsSubPanel.
     *  Initialise range bounds, set layout and append a JTextArea as well as creating
     *  the chart that will be displayed.
     * @param title     Title of the panel.
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public ChartCentralPanel(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
        setLayout(new BorderLayout());
        createChart(title);
        setVisible(true);
    }

    /**
     * Creates box at the bottom of the panel showing some information
     */
    protected void createBottomBox(){
        JTextArea total = new JTextArea ();
        total.setEditable(false);
        //retrieve text from subclass
        total.setText(getBottomText());
        total.setFont(new Font("Arial", Font.BOLD, 15));
        add(total, BorderLayout.SOUTH);
    }

    /**
     * Initialise bound values for graphs. Divide them in 4 categories using
     * natural exponential function
     */
    protected void initialiseExponentialBounds(){
        Double[] bounds = calculateExponentialBounds(4, lowPrice, highPrice);
        double lowStats = bounds[0];
        double lowMidStats= bounds[1];
        double highMidStats =bounds[2];
        double highStats = bounds[3];


       lowStats = Double.parseDouble(new DecimalFormat("#####.##").format(lowStats));
       lowMidStats= Double.parseDouble(new DecimalFormat("#####.##").format(lowMidStats));
       highMidStats = Double.parseDouble(new DecimalFormat("#####.##").format(highMidStats));
       highStats = Double.parseDouble(new DecimalFormat("#####.##").format(highStats));


       lowBound=    lowPrice + "£ to "+ (lowStats) +"£";
       mediumLowBound=   (lowStats) +"£ to " +(lowMidStats)+"£";
       mediumHighBound=  (lowMidStats)+"£ to " +(highMidStats)+"£";
       highBound=    (highMidStats)+"£ to " +highStats +"£";
    }

    /**
     * Initialise bound values for graphs. Divide them in 4 categories using
     * natural exponential function
     */
    protected void initialiseLinearBounds(){
        lowBound=    lowPrice + "£ to "+ (highPrice/4) +"£";
        mediumLowBound=   (highPrice/4) +"£ to " +(highPrice/4*2)+"£";
        mediumHighBound=  (highPrice/4*2)+"£ to " +(highPrice/4*3)+"£";
        highBound=    (highPrice/4*3)+"£ to " +highPrice +"£";
    }

    /**
     * Calculate total number of listings in given price range from a given
     * dataset of listings
     * @param listings the dataset used for calculation
     * @param lowPrice the lower bound of the price range
     * @param highPrice the upper bound of the price range
     * @return Return the total number of listings in given price range from a given
     * dataset of listings
     */
    protected Double getTotFromData(List<AirbnbListing> listings, double lowPrice, double highPrice){
        Double sum = 0.0;
        for(AirbnbListing listing : listings){
            if(listing.getPrice() <= highPrice && listing.getPrice() >= lowPrice){
                sum++;
            }
        }
        return sum;
    }

    /**
     * Calculate the average value of a column values from the listings.
     * @param listings the dataset used for calculation
     * @param lowPrice the lower bound of the price range
     * @param highPrice the upper bound of the price range
     * @param column the column name used as filter for calculation. It has to be a column containing numerical values
     * @return the average value of a column values from the listings.
     */
    protected Double getAvgFromData(List<AirbnbListing> listings, double lowPrice, double highPrice, String column) throws Exception {
        int sum = 0;
        double avg = 0;
        int numberOfProps = 0;
        for(AirbnbListing listing: listings){
            if(listing.getPrice()<= highPrice && listing.getPrice() >= lowPrice){
                numberOfProps++;
                switch (column) {
                    case "price":
                        sum += listing.getPrice();
                        break;
                    case "minimum_nights":
                        sum += listing.getMinimumNights();
                        break;
                    case "number_of_reviews":
                        sum += listing.getNumberOfReviews();
                        break;
                    case "review_per_month":
                        sum += listing.getReviewsPerMonth();
                        break;
                    case "availability_365":
                        sum += listing.getAvailability365();
                        break;
                    default:
                        throw new Exception("The column does not contain values that can return an average. Not numerical.");
                }
            }
        }
        if(numberOfProps > 0){
            avg = sum/numberOfProps;
        }
        return avg;

    }

    /**
     * Use natural exponential function
     * {y= [(π/2 + e)^x] / [Σ_{i=1}^𝛿 (π/2 + e)^x )]}
     * to calculate percentages and values of
     * bounds given upper and lower bound of price
     * @param divisions the parts the price has to be divided in
     * @return an array containing the bounds needed
     */
    protected Double[] calculateExponentialBounds(int divisions, int lowPrice, int highPrice) {
        Double[] bounds = new Double[divisions];

        if(highPrice-lowPrice>=500){
            double divisor = 0;
            for(int i = 0; i<divisions; i++) {
                divisor += Math.pow(
                        Math.exp(1.0)+Math.PI/2,
                        i+1
                );
            }
            for(int j = 0; j<divisions; j++){

                 double bound = (Math.pow(
                        Math.exp(1.0)+Math.PI/2,
                        j+1)) / divisor;
                if(j!=0) {
                    bound += bounds[j-1];

                }
                bounds[j] = bound;
            }

            int delta = highPrice - lowPrice;

            for(int i = 0; i<bounds.length; i++){
                bounds[i] = lowPrice + bounds[i]*delta;
            }
        }
        else{
            for(int i = 0; i<divisions; i++){
               bounds[i] = highPrice * (0.25 * (i+1));
            }
        }
        return bounds;
    }

    /**
     * Return an arraylist containing all the neighbourhoods
     * @return an arraylist containing all the neighbourhoods
     */
    protected ArrayList<String> getAllNeighbourhoods(){
        ArrayList<String> nbr = new ArrayList<>();
        for(AirbnbListing listing:listings){
            if(!nbr.contains(listing.getNeighbourhood())){
                nbr.add(listing.getNeighbourhood());
            }
        }
        return nbr;
    }

    /**
     * Returns the borough by its key value in the given hasmap
     * @param i the position (e.g. 1 is the element with the highest number of listings)
     * @return the borough by its key value in listingsPerBorough
     */
    protected String getBoroughByPosition(int i, HashMap<String, Double> hashMap) {
        ArrayList<Double> values = new ArrayList<>(hashMap.values());
        Collections.sort(values);
        Collections.reverse(values);
        for (Map.Entry e: hashMap.entrySet()){
            if (e.getValue() == values.get(i-1))
                return (String)e.getKey();
        }
        return "";
    }

    /**
     * Initialise and create the chart
     */
    private void createChart(String title){
        ChartPanel chartPanel = new ChartPanel(getChart(title));
        add(chartPanel, BorderLayout.CENTER);
    }

    /**
     * Return the chart that will be displayed
     * @param title the title of the chart
     * @return the chart that will be displayed
     */
    protected abstract JFreeChart getChart(String title);

    /**
     * Create dataset for the chart
     * @return dataset for the chart
     * @param listings the initial data
     */
    protected abstract Dataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice);

    /**
     * Return the text to be displayed at the bottom of the central panel
     * @return the text to be displayed at the bottom of the central panel
     */
    public abstract String getBottomText();

    /**
     * Return the lowest bound of price range subdivisions
     * @return the lowest bound of price range subdivisions
     */
    protected String getLowBound() {
        return lowBound;
    }
    /**
     * Return the medium-low bound of price range subdivisions
     * @return the medium-low lowest bound of price range subdivisions
     */
    protected String getMediumLowBound() {
        return mediumLowBound;
    }
    /**
     * Return the medium-high bound of price range subdivisions
     * @return the medium-high bound of price range subdivisions
     */
    protected String getMediumHighBound() {
        return mediumHighBound;
    }
    /**
     * Return the highest bound of price range subdivisions
     * @return the highest bound of price range subdivisions
     */
    protected String getHighBound() {
        return highBound;
    }
}

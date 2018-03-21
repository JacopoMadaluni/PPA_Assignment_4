import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.Dataset;
import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A special panel to be inserted in a StatsSubPanel.
 * It contains a statistic result calculated from the dataset it's given.
 * @author Danilo Del Busso
 * @version 21.03.2018
 */
public abstract class ChartCentralPanel extends AppPanel {
    protected String lowBound;
    protected String mediumLowBound;
    protected String mediumHighBound;
    protected String highBound;

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
        initialiseRangeBounds();
        createChart(title);

        JTextArea total = new JTextArea ();
        total.setEditable(false);
        //retrieve text from subclass
        total.setText(getBottomText());
        total.setFont(new Font("Arial", Font.BOLD, 20));
        add(total, BorderLayout.SOUTH);

        setVisible(true);
    }

    /**
     * Initialise bound values for graphs. Divide them in 4 categories of
     * equal size
     */
    protected void initialiseRangeBounds(){
       lowBound=    lowPrice + "£ to "+ highPrice/4 +"£";
       mediumLowBound=   highPrice/4+"£ to " +(highPrice/4 *2)+"£";
       mediumHighBound=  highPrice/4*2+"£ to " +(highPrice/4 *3)+"£";
       highBound=    highPrice/4 *3+"£ to " +highPrice+"£";
    }

    /**
     * Calculate total number of listings in given price range from a given
     * dataset of listings
     * @param listings the dataset used for calculation
     * @param lowPrice the lower bound of the price range
     * @param highPrice the upper bound of the price range
     * @return
     */
    protected int getTotFromData(List<AirbnbListing> listings, int lowPrice, int highPrice) throws Exception {
        int sum = 0;
        for(AirbnbListing listing : listings){
            if(listing.getPrice() <= highPrice && listing.getPrice() >= lowPrice){
                sum++;
            }
        }
        return  sum;
    }

    /**
     * Calculate the max value of a column from the listings.
     * @param listings the dataset used for calculation
     * @param lowPrice the lower bound of the price range
     * @param highPrice the upper bound of the price range
     * @param column the column name used as filter for calculation. It has to be a column containing numerical values
     * @return the max value of a column from the listings.
     */
    protected double getMaxFromData(List<AirbnbListing> listings, int lowPrice, int highPrice, String column) throws Exception {
        double currentValue = 0;
        for(AirbnbListing listing : listings){
            if(listing.getPrice()<= highPrice && listing.getPrice() >= lowPrice){
                if(column.equals("review_score")){
                    //TODO ask kolling pls
                }
                else if(column.equals("price")){
                    if(listing.getPrice()>currentValue){
                        currentValue = listing.getPrice();
                    }
                }
                else if(column.equals("minimum_nights")){
                    if(listing.getMinimumNights()>currentValue){
                        currentValue = listing.getMinimumNights();
                    }
                }
                }
                else if(column.equals("number_of_review")){
                    if(listing.getNumberOfReviews()>currentValue){
                        currentValue = listing.getNumberOfReviews();
                    }
                }
                else if(column.equals("review_per_month")){
                    if(listing.getReviewsPerMonth()>currentValue){
                        currentValue = listing.getReviewsPerMonth();
                    }
                }
                else if(column.equals("availability_365")){
                    if(listing.getAvailability365()>currentValue){
                        currentValue = listing.getAvailability365();
                    }
                }
                else{
                    throw new Exception("The column does not contain values that can return a max. Not numerical.");
                }
        }
        return currentValue;
    }

    /**
     * Calculate the average value of a column values from the listings.
     * @param listings the dataset used for calculation
     * @param lowPrice the lower bound of the price range
     * @param highPrice the upper bound of the price range
     * @param column the column name used as filter for calculation. It has to be a column containing numerical values
     * @return the average value of a column values from the listings.
     */
    protected double getAvgFromData(List<AirbnbListing> listings, int lowPrice, int highPrice, String column) throws Exception {
        int sum = 0;
        double avg = 0;
        for(AirbnbListing listing: listings){
            if(listing.getPrice()<= highPrice && listing.getPrice() >= lowPrice){
                if(column.equals("review_score")){
                    //TODO ask kolling pls
                }
                else if(column.equals("price")){
                    sum+=listing.getPrice();
                }
                else if(column.equals("minimum_nights")){
                    sum+=listing.getMinimumNights();
                }
                else if(column.equals("number_of_review")){
                    sum+=listing.getNumberOfReviews();
                }
                else if(column.equals("review_per_month")){
                    sum+=listing.getReviewsPerMonth();
                }
                else if(column.equals("availability_365")){
                    sum+=listing.getAvailability365();
                }
                else{
                    throw new Exception("The column does not contain values that can return an average. Not numerical.");
                }
            }
        }
        if(listings.size() > 0){
            avg = sum/listings.size();
        }
        return avg;

    };

    /**
     * Initialise and create the chart
     */
    protected void createChart(String title){
        ChartPanel chartPanel = new ChartPanel(getChart(title));
        add(chartPanel, BorderLayout.CENTER);
    }


    protected abstract JFreeChart getChart(String title);

    protected abstract Dataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice);

    public abstract String getBottomText();
}

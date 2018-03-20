import java.util.List;

public class CentralPanel extends AppPanel {
    protected String lowBound;
    protected String mediumLowBound;
    protected String mediumHighBound;
    protected String highBound;



    /**
     * Create a new panel to be displayed in the main window.
     * The list of all the available properties is created only once in the MainWindow class to
     * ensure all the panels use the same set of data.
     * lowPrice and highPrice determine the price range of the properties the user wants to
     * see statistics about.
     *
     * @param title     Title of the panel.
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public CentralPanel(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super(title, listings, lowPrice, highPrice);
        initialiseRangeBounds();
        setVisible(true);
    }

    /**
     * Initialise bound values
     */
    public void initialiseRangeBounds(){
       lowBound=    lowPrice + "£ to "+ highPrice/4 +"£";
       mediumLowBound=   highPrice/4+"£ to " +(highPrice/4 *2)+"£";
       mediumHighBound=  highPrice/4*2+"£ to " +(highPrice/4 *3)+"£";
       highBound=    highPrice/4 *3+"£ to " +highPrice+"£";
    }
}

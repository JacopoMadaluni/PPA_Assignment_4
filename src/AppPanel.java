import javax.swing.*;
import java.util.List;

/**
 * This abstract class is used in the main window to scroll between panels.
 * Each panel (welcome, map, statistics,...) need to extend this class.
 *
 * @author Luka Kralj
 * @version 14 March 2018
 *
 * TODO: Plan is to add a bubble with the title when a user hovers over the buttons to switch the panels.
 *
 */
public abstract class AppPanel extends JPanel{
    private String title;
    protected List<AirbnbListing> listings;
    protected int lowPrice;
    protected int highPrice;

    /**
     * Create a new panel to be displayed in the main window.
     * The list of all the available properties is created only once in the MainWindow class to
     * ensure all the panels use the same set of data.
     * lowPrice and highPrice determine the price range of the properties the user wants to
     * see statistics about.
     * @param title Title of the panel.
     * @param listings List of all results from the CSV file.
     * @param lowPrice Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public AppPanel(String title, List<AirbnbListing> listings, int lowPrice, int highPrice) {
        this.title = title;
        this.listings = listings;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
    }

    /**
     *
     * @return The title of the panel.
     */
    public String getTitle() {
        return title;
    }
}

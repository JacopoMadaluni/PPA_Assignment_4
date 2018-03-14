import javax.swing.*;
import java.util.ArrayList;

/**
 * This abstract class is used in the main window to scroll between panels.
 * Each panel (welcome, map, statistics,...) need to extend this class.
 *
 * TODO: Plan is to add a bubble with the title when a user hovers over the buttons to switch the panels.
 *
 */
public abstract class AppPanel extends JPanel{
    private String title;
    private ArrayList<AirbnbListing> listings;
    private int lowPrice;
    private int highPrice;

    /**
     * Create a new panel to be displayed in the main window.
     * @param title Title of the panel.
     */
    public AppPanel(String title, ArrayList<AirbnbListing> listings, int lowPrice, int highPrice) {
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

    public ArrayList<AirbnbListing> getListings() {
        return listings;
    }
}

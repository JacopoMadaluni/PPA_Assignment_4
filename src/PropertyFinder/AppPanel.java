package PropertyFinder;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * This abstract class is used in the main window to scroll between panels.
 * Each panel (welcome, map, statistics,...) need to extend this class.
 *
 * @author Luka Kralj
 * @version 14 March 2018
 */
public abstract class AppPanel extends JPanel{
    private String title;
    protected List<AirbnbListing> listings;
    protected int lowPrice;
    protected int highPrice;

    /**
     * Create a new panel to be displayed in the main window.
     * The list of all the available properties is created only once in the PropertyFinder.MainWindow class to
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
     * Return all listings that are in the price range
     * @param listings the entirety of all listings in the dataset
     */
    protected ArrayList<AirbnbListing> getListingsInRange(List<AirbnbListing> listings) {
        ArrayList<AirbnbListing> listingsInRange = new ArrayList<>();
        for(AirbnbListing listing: listings){
            if(listing.getPrice() <= highPrice && listing.getPrice()>= lowPrice){
                listingsInRange.add(listing);
            }
        }
        return listingsInRange;
    }

    /**
     * @return The title of the panel.
     */
    public String getTitle() {
        return title;
    }
}
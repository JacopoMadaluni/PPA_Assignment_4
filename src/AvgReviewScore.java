import java.util.ArrayList;
import java.util.List;

public class AvgReviewScore extends AppPanel {
    ArrayList<AirbnbListing> listingsInRange;
    int lowPrice;
    int highPrice;

    /**
     * This Panel shows the average review score of available properties
     * @param listings  List of all results from the CSV file.
     * @param lowPrice  Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public AvgReviewScore( List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super("Average Review Score", listings, lowPrice, highPrice);
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        listingsInRange = new ArrayList<>();
        getListingsInRange(listings);

    }


    /**
     * Add all listings that are in the price range to the
     * listingsInRange arrayList
     * @param listings the entirety of all listings in the dataset
     */
    private void getListingsInRange(List<AirbnbListing> listings) {
        for(AirbnbListing listing: listings){
            if(listing.getPrice() <= highPrice && listing.getPrice()>= lowPrice){
                listingsInRange.add(listing);
            }
        }


    }
}

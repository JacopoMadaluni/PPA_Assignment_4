import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ReviewsPerDistrict extends BarChart {

    //Selects te number of neighborhoods to display
    private static final int topDistricts = 5;
    private HashMap<String,Double> reviewsPerDistrict;
    public ReviewsPerDistrict(String title, ArrayList<AirbnbListing> listings, int lowPrice, int highPrice){
        super(title,listings,lowPrice,highPrice);
    }

    @Override
    public String getTitle() {
        return "Average number of Reviews per District";
    }

    public String getXLabel(){
        return "District";
    }

    @Override
    public String getBottomText() {
        return null;
    }

    @Override
    protected Dataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice) {
        reviewsPerDistrict = new HashMap<>();
        setDistricts(lowPrice,highPrice);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try{
            dataset.addValue(getAvgFromData(listings,lowPrice,highPrice,"number_of_review"),highBound,"All listings");
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getYLabel() {
        return "Average number of reviews";
    }

    private void setDistricts(int lowPrice, int highPrice){
        reviewsPerDistrict.put(
                "Kingston upon Thames",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Kingston upon Thames"));
        reviewsPerDistrict.put("Croydon",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Croydon"));
        reviewsPerDistrict.put("Bromley",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Bromley"));
        reviewsPerDistrict.put("Hounslow",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Hounslow"));
        reviewsPerDistrict.put("Ealing",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Ealing"));
        reviewsPerDistrict.put("Havering",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Havering"));
        reviewsPerDistrict.put("Hillington",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Hillington"));
        reviewsPerDistrict.put("Harrow",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Harrow"));
        reviewsPerDistrict.put("Brent",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Brent"));
        reviewsPerDistrict.put("Barnet",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Barnet"));
        reviewsPerDistrict.put("Enfield",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Enfield"));
        reviewsPerDistrict.put("Waltham Forest",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Waltham Forest"));
        reviewsPerDistrict.put("Redbridge",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Redbridge"));
        reviewsPerDistrict.put("Sutton",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Sutton"));
        reviewsPerDistrict.put("Lambeth",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Lambeth"));
        reviewsPerDistrict.put("Southwark",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Southwark"));
        reviewsPerDistrict.put("Lewisham",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Lewisham"));
        reviewsPerDistrict.put("Greenwich",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Greenwich"));
        reviewsPerDistrict.put("Bexley",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Bexley"));
        reviewsPerDistrict.put("Richmond upon Thames",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Richmond upon Thames"));
        reviewsPerDistrict.put("Merton",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Merton"));
        reviewsPerDistrict.put("Wandsworth",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Wandsworth"));
        reviewsPerDistrict.put("Hammersmith and Fulham",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Hammersmith and Fulham"));
        reviewsPerDistrict.put("Kensington and Chelsea",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Kensington and Chelsea"));
        reviewsPerDistrict.put("City of London",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "City of London"));
        reviewsPerDistrict.put("Westminster",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Westminster"));
        reviewsPerDistrict.put("Camden",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Camden"));
        reviewsPerDistrict.put("Tower Hamlets",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Tower Hamlets"));
        reviewsPerDistrict.put("Islington",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Islington"));
        reviewsPerDistrict.put("Hackney",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Hackney"));
        reviewsPerDistrict.put("Haringey",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Haringey"));
        reviewsPerDistrict.put("Newham",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Newham"));
        reviewsPerDistrict.put("Barking and Dagenham",
                 getAvgReviewsPerDistrict(listings, lowPrice, highPrice, "Barking and Dagenham"));
    }

    private double getAvgReviewsPerDistrict(List<AirbnbListing> listings, int lowPrice, int highPrice, String districtName){
        int sum = 0;
        int avg;
        int total = 0;

        for (AirbnbListing listing:listings){
            if (listing.getNeighbourhood().equals(districtName)&&listing.getPrice()<= highPrice && listing.getPrice() >= lowPrice){
                total++;
                sum += listing.getNumberOfReviews();
            }
        }
        avg = sum/total;
        return avg;
    }
}

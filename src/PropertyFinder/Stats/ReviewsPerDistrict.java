package PropertyFinder.Stats;

import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import PropertyFinder.*;

/**
 * This class creates a bar chart representing the
 * average number of reviews per district. It extends the
 * BarChart class to create the graph.
 * @author Alvaro Rausell
 * @version 22.03.2018*/
public class ReviewsPerDistrict extends BarChart {

    //Selects te number of neighborhoods to display
    private static final int topDistricts = 5;
    //Collection of all districts with their average number of reviews
    private HashMap<String,Double> reviewsPerDistrict;

    /**
     * Creates the ReviewsPerDistrict object by calling the constructor
     * from BarChart.
     * @param title Title of the graph
     * @param listings Collection of Airbnb's from which to take the data
     * @param lowPrice Minimum price that the properties may cost
     * @param highPrice Maximum prices the the properties may cost*/
    public ReviewsPerDistrict(String title, ArrayList<AirbnbListing> listings, int lowPrice, int highPrice){
        super(title,listings,lowPrice,highPrice);
    }


    public String getXLabel(){
        return "Borough";
    }

    @Override
    public String getBottomText() {
        return null;
    }

    /**
     * Creates the set of data for the graph.
     * @param listings List of Airbnb's
     * @param lowPrice Lowest price bound
     * @param highPrice Highest price bound
     * @return Dataset for the graph*/
    @Override
    protected Dataset createDataset(List<AirbnbListing> listings, int lowPrice, int highPrice) {
        reviewsPerDistrict = new HashMap<>();
        setDistricts(lowPrice,highPrice);
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        try{
            for (int i = topDistricts; i>0;i--)
            dataset.addValue(getReviewAvg(i),getBoroughByPosition(i, reviewsPerDistrict),"");
        }catch (Exception e){
            e.printStackTrace();
        }
        return dataset;
    }

    /**
     * Gets the average number of reviews given an index.
     * This index represents the order by number of reviews.
     * @param i position in the number of reviews scale
     * @return Average number of reviews at the given position*/
    private double getReviewAvg(int i){
        ArrayList<Double> values = new ArrayList<>(reviewsPerDistrict.values());
        Collections.sort(values);
        Collections.reverse(values);
        return values.get(i-1);
    }


    @Override
    public String getYLabel() {
        return "Average Number of Reviews";
    }

    /**
     * Sets up the collection of district names and average number of reviews
     * @param lowPrice Lower price bound
     * @param highPrice Upper price bound*/
    private void setDistricts(int lowPrice, int highPrice){
        for (String district: getAllNeighbourhoods()){
            reviewsPerDistrict.put(district,getAvgReviewsPerDistrict(listings,lowPrice,highPrice,district));
        }
    }

    /**
     * Gets the average number of reviews for a given district
     * @param listings List of Airbnb's
     * @param lowPrice Lower price bound
     * @param highPrice Upper price bound
     * @param districtName Name of the district
     * @return Average number of reviews of the given district
     */
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
        avg = total !=0? sum/total: 0;
        return avg;
    }
}

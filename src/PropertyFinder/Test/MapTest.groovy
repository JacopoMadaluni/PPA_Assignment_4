package PropertyFinder.Test

import org.junit.Assert

import javax.swing.ImageIcon

import static org.junit.Assert.*;
import PropertyFinder.AirbnbListing
import PropertyFinder.Map.District
import PropertyFinder.Map.Map;
import com.opencsv.CSVReader
import org.junit.After
import org.junit.Before
import org.junit.Test


/**
 * Test class for the Map and the District system.
 * Classes being tested: Map, District.
 * @author Jacopo Madaluni K1763985
 * @version 29-03-2018
 */
class MapTest{


    private List<AirbnbListing> bnbs;
    AirbnbListing bnb1 = new AirbnbListing("15896822","Double room in newly refurbished flat",
                                            "69018624","Dafina","Kingston upon Thames", 51.9,
                                            Double.parseDouble("-0.3"),
                                            "Private room",23,7,
                                            1,
                                            "03/12/2016",0.32,1,
                                            61);
    @Before
    void setUp() {
        bnbs = loadTests();
    }

    @After
    void tearDown() {
    }

    /**
     * Tests all the cases for District and Map.
     * If this test passes then all possible tests have been passed.
     */
    @Test
    void testAll(){
        testMap();
        testDistrict();
    }

    /**
     * This test executes all the test cases for the Map class.
     */
    @Test
    void testMap(){
        testLoad();
        testMapAddEmptyDistrict();
    }

    /**
     * This test executes all the test cases for the District class.
     */
    @Test
    void testDistrict(){
        testAdd();
        testIcon();
        testSorting();
        testIconScaling();
    }

    /**
     * Test for the loading system & price fetching of the properties.
     */
    @Test
    void testLoad(){
        District districtTest = new District("Lambeth", 0, 0);
        for (AirbnbListing a : bnbs){
            if (a.getNeighbourhood().equals(districtTest.getName())){
                districtTest.addBnb(a);
            }
        }
        assert (districtTest.getNumberOfBnbs() == 2);

        // There should be no property that matches this price range.
        Map map1 = new Map("map1", bnbs, 40000, 50000);
        assertTrue(map1.getShownBnbs().size() == 0);

        Map map2 = new Map("map2", bnbs, 0, 100000);
        //all bnbs should be shown in this price range.
        assertTrue(map2.getShownBnbs().size() == bnbs.size());
    }

    /**
     * Test the case where the input list of properties is empty.
     * No District should be shown in this case.
     */
    @Test
    void testMapAddEmptyDistrict(){
        List<AirbnbListing> bnbs = new ArrayList<>();
        Map map1 = new Map("testMap", bnbs, 0, 0);
        assert (map1.getShownDistricts().size() == 0);
    }

    /**
     * Test the adding system in the District.
     */
   @Test
   void testAdd(){
        District test1 = new District("test1", 0, 0);
        assert (test1.getNumberOfBnbs() == 0);
        test1.addBnb(bnb1);
        assert (test1.getNumberOfBnbs() == 1);
    }

    /**
     * Test icon initialization.
     * Icon should never be null.
     */
    @Test
    void testIcon(){
        District test1 = new District("test1", 0, 0);
        assertNotNull(test1.getIcon());
    }

    /**
     * Test the correct sorting of the districts.
     */
    @Test
    void testSorting(){
        District test1 = new District("test1", 0,0);
        District test2 = new District("test2", 0,0);
        District test3 = new District("test3", 0,0);

        //test1 has all bnbs
        for (AirbnbListing bnb : bnbs){
            test1.addBnb(bnb);
        }
        //test2 has a half of bnbs
        for (int i = 0 ; i < bnbs.size() ; i += 2){
            test2.addBnb(bnbs.get(i));
        }
        //test3 has none.

        test1.putInList();
        test2.putInList();
        test3.putInList();

        int indexTest1;
        int indexTest2;
        int indexTest3;
        List<District> sortedList = District.getSortedList();
        for (int i = 0 ; i <  sortedList.size() ; i++){
            District currentDistrict = sortedList.get(i);
            if (currentDistrict.equals(test1)){
                indexTest1 = i;
            }else if (currentDistrict.equals(test2)){
                indexTest2 = i;
            }else if (currentDistrict.equals(test3)){
                indexTest3 = i;
            }
        }

        assertTrue(indexTest1 > indexTest2);
        assertTrue(indexTest1 > indexTest3);

    }

    /**
     * Test both scaling systems.
     * District with more properties should have a bigger (or equal) icon.
     */
    @Test
    void testIconScaling(){
        District test1 = new District("test1", 0,0);
        District test2 = new District("test2", 0,0);
        District test3 = new District("test3", 0,0);

        //test1 has all bnbs
        for (AirbnbListing bnb : bnbs){
            test1.addBnb(bnb);
        }
        //test2 has a half of bnbs
        for (int i = 0 ; i < bnbs.size() ; i += 2){
            test2.addBnb(bnbs.get(i));
        }

        //test3 has a third.
        for (int i = 0 ;i < bnbs.size() ; i += 3){
            test3.addBnb(bnbs.get(i));
        }

        District.reset();
        test1.setCorrectIcon();
        test2.setCorrectIcon();
        test3.setCorrectIcon();

        int icon1W = test1.getIcon().getIconWidth();
        int icon2W = test1.getIcon().getIconWidth();
        int icon3W = test1.getIcon().getIconWidth();

        assertTrue(icon1W >= icon2W);
        assertTrue(icon2W >= icon3W);

        District.setResMode();

        test1.setCorrectIcon();
        test2.setCorrectIcon();
        test3.setCorrectIcon();

        icon1W = test1.getIcon().getIconWidth();
        icon2W = test1.getIcon().getIconWidth();
        icon3W = test1.getIcon().getIconWidth();

        assertTrue(icon1W >= icon2W);
        assertTrue(icon2W >= icon3W);
    }


    private List<AirbnbListing> loadTests(){
        ArrayList<AirbnbListing> listings = new ArrayList<>();
        try{
            URL url = new File("resources/datasets/test_cases.csv").toURI().toURL();
            CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
            String [] line;
            //skip the first row (column headers)
            reader.readNext();
            while ((line = reader.readNext()) != null) {
                String id = line[0];
                String name = line[1];
                String host_id = line[2];
                String host_name = line[3];
                String neighbourhood = line[4];
                double latitude = Double.parseDouble(line[5]);
                double longitude = Double.parseDouble(line[6]);
                String room_type = line[7];
                int price = Integer.parseInt(line[8]);
                int minimumNights = Integer.parseInt(line[9]);
                int numberOfReviews = Integer.parseInt(line[10]);
                String lastReview = line[11];
                double reviewsPerMonth;
                if (line[12].isEmpty()){
                    reviewsPerMonth = 0.0;
                }else{
                    reviewsPerMonth = Double.parseDouble(line[12]);
                }
                int calculatedHostListingsCount = Integer.parseInt(line[13]);
                int availability365 = Integer.parseInt(line[14]);

                AirbnbListing listing = new AirbnbListing(id, name, host_id,
                        host_name, neighbourhood, latitude, longitude, room_type,
                        price, minimumNights, numberOfReviews, lastReview,
                        reviewsPerMonth, calculatedHostListingsCount, availability365
                );
                listings.add(listing);
            }
        } catch(IOException | URISyntaxException e){
            System.out.println("Failure! Something went wrong");
            e.printStackTrace();
        }
        System.out.println("Success! Number of loaded records: " + listings.size());
        return listings;
    }

}

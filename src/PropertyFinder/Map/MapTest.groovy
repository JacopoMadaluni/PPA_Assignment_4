package PropertyFinder.Map

import PropertyFinder.AirbnbDataLoader
import PropertyFinder.AirbnbListing
import com.opencsv.CSVReader

class MapTest extends GroovyTestCase{

    private List<AirbnbListing> bnbs;
    AirbnbListing bnb1 = new AirbnbListing(15896822,"Double room in newly refurbished flat",
                                            69018624,"Dafina,Kingston upon Thames",51.41003566,-0.306322953,
                                            "Private room",23,7,1,"03/12/2016",0.32,1,61);

    void testMap(){
        bnbs = loadTests();
        District districtTest = new District("Lambeth", 0, 0);
        for (AirbnbListing a : bnbs){
            if (a.getNeighbourhood().equals(districtTest.getName())){
                districtTest.add(a);
            }
        }
        assert (districtTest.getNumberOfBnbs() == 2);



    }

    void setUp() {
        super.setUp()
        testMap();
    }

    void tearDown() {

    }

    void testDistrict(){
        testAdd();
    }

    private void testAdd(){
        District test1 = new District("test1", 0, 0);

        assert (test1.getNumberOfBnbs() == 0);
        test1.add(bnb1);
        assert (test1.getNumberOfBnbs() == 1);
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
                double reviewsPerMonth = Double.parseDouble(line[12]);
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

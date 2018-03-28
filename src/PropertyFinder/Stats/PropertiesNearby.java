package PropertyFinder.Stats;

import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.ArrayList;
import PropertyFinder.*;
import java.util.List;

/**
 * This class represents one of the statistics panels. It allows a user to enter an address (preferably
 * theirs) and it displays the number of properties near that address.
 *
 * @author Luka Kralj
 * @version 26 March 2018
 */
public class PropertiesNearby extends AppPanel {
    private JTextField input;
    private JTextField range;
    private JLabel display;
    private List<AirbnbListing> sortedList;

    /**
     * Create a new panel which displays the number of properties in the given range from the given address.
     *
     * @param listings List of all results from the CSV file.
     * @param lowPrice Lower price boundary of the properties the user wants to see.
     * @param highPrice Upper price boundary of the properties the user wants to see.
     */
    public PropertiesNearby(List<AirbnbListing> listings, int lowPrice, int highPrice) {
        super("Properties near me", listings, lowPrice, highPrice);
        sortedList = getSortedList();
        initialisePanel();
    }

    /**
     *
     * @return List of only the properties whose prices are in the chosen range.
     */
    private List<AirbnbListing> getSortedList() {
        ArrayList<AirbnbListing> list = new ArrayList<>();
        for (AirbnbListing listing : listings) {
            if (listing.getPrice() <= highPrice && listing.getPrice() >= lowPrice) {
                list.add(listing);
            }
        }
        return list;
    }

    /**
     * Initialises the panel with all the components.
     *
     * TODO: Format layouts.
     */
    private void initialisePanel() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 10, 10, 10));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);

        JLabel title = new JLabel();
        title.setText("<html><h1>" + getTitle() + "</h1></html>");
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        titlePanel.setOpaque(false);
        titlePanel.add(title);
        top.add(title, BorderLayout.NORTH);


        JPanel central = new JPanel(new BorderLayout());
        central.setOpaque(false);
        central.setOpaque(false);

        JPanel inputs = new JPanel();
        inputs.setOpaque(false);
        inputs.setLayout(new BoxLayout(inputs, BoxLayout.PAGE_AXIS));
        JPanel addressPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addressPanel.setOpaque(false);
        JLabel address = new JLabel("Enter your address here:");
        addressPanel.add(address);
        inputs.add(addressPanel);

        JPanel inputPanel = new JPanel();
        inputPanel.setOpaque(false);
        inputPanel.setLayout(new BoxLayout(inputPanel, BoxLayout.LINE_AXIS));
        input = new JTextField();
        input.setEditable(true);
        inputPanel.add(input);
        inputs.add(inputPanel);

        inputs.add(Box.createRigidArea(new Dimension(inputs.getWidth(), 10)));

        JPanel rangePanel = new JPanel();
        rangePanel.setOpaque(false);
        rangePanel.setLayout(new BoxLayout(rangePanel, BoxLayout.LINE_AXIS));
        rangePanel.add(new JLabel("Enter range (in miles): "));

        JPanel rng = new JPanel();
        rng.setOpaque(false);
        rng.setLayout(new BoxLayout(rng, BoxLayout.LINE_AXIS));
        range = new JTextField();
        range.setEditable(true);
        rng.add(range);
        rangePanel.add(rng);
        inputs.add(rangePanel);

        inputs.add(Box.createRigidArea(new Dimension(inputs.getWidth(), 10)));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        searchPanel.setOpaque(false);
        JButton search = new JButton("<html><b>Search</b></html>");
        search.addActionListener(e -> searchClicked());
        searchPanel.add(search);
        inputs.add(searchPanel);

        central.add(inputs, BorderLayout.NORTH);


        display = new JLabel("No.");
        central.add(display, BorderLayout.CENTER);
        top.add(central, BorderLayout.CENTER);

        add(top, BorderLayout.NORTH);



    }

    /**
     * Invoked when the search button is clicked.
     */
    private void searchClicked() {
        String address = input.getText().trim();
        String distance = range.getText().trim();
        double rangeInMiles;
        if (address.isEmpty()) {
            showWarning("Enter you address first!");
            return;
        }
        else if (distance.isEmpty()) {
            showWarning("Please specify a valid range (in miles)!");
            return;
        }
        else {
            try {
                rangeInMiles = Double.parseDouble(distance);
            }
            catch (NumberFormatException e) {
                showWarning("Range must be a number!");
                return;
            }
        }
        String response = searchAddress(address);
        displayLocation(response, rangeInMiles);
    }

    /**
     * Pops up a warning message if the input is invalid.
     *
     * @param message Message to show.
     */
    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, new JLabel(message), "Invalid input", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Executes a query to an online Google Maps API to search for info about the given address.
     *
     * @param address User's input.
     * @return Response in format of Json string.
     */
    private String searchAddress(String address) {
        try {
            URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address="
                    + URIUtil.encodeQuery(address));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }
            String toReturn = getJsonString(conn.getInputStream());

            conn.disconnect();
            return toReturn;
        }
        catch (MalformedURLException e) {
            System.out.println("---------------");
            System.out.println("Malformed URL Exception in method searchAddress:");
            System.out.println(e.getLocalizedMessage());
        }
        catch (URIException e) {
            System.out.println("---------------");
            System.out.println("URI Exception in method searchAddress");
            System.out.println(e.getLocalizedMessage());
        }
        catch (IOException e) {
            System.out.println("---------------");
            System.out.println("IO Exception in method searchAddress");
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }

    /**
     * Create a json string from the input stream that is received as response from the online API.
     *
     * @param stream Response from the API.
     * @return Json string as string.
     *
     * TODO: Delete the print statement.
     */
    private String getJsonString(InputStream stream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String output = "";
        String jsonString = "";

        try {
            while ((output = br.readLine()) != null) {
                System.out.println(output);
                jsonString += output.trim();
            }
        }
        catch (IOException e) {
            System.out.println("---------------");
            System.out.println("IO Exception in method getJsonString");
            System.out.println(e.getLocalizedMessage());
        }

        return jsonString;
    }

    /**
     * Gets latitude and longitude for the address that user entered and displays the correct info.
     *
     * TODO: Add message according to the "status" of the response.
     *
     * @param jsonString Response from the API as Json string.
     * @param rangeInMiles Distance from address which the user wants statistics for.
     */
    private void displayLocation(String jsonString, double rangeInMiles) {
        double lat = 0;
        double lng = 0;
        boolean locationFound;
        try {
            lat = getLocationComponent(jsonString, "lat");
            lng = getLocationComponent(jsonString, "lng");
            locationFound = true;
        }
        catch (JSONException e) {
            e.getMessage();
            locationFound = false;
        }

        if (locationFound) {
            setDisplay(lat, lng, rangeInMiles);
        }
        else {
            // Some error occurred when parsing the data.
            // Sometimes resolved when clicking the search button again.
            display.setText("<html><p style=\"text-align: center;\">" +
                    "<font size=\"3\" color=\"gray\"><i>" +
                    "Sorry, an error occurred while obtaining your location.<br>Please, try again.</i></font>" +
                    "</p></html>");
        }


    }

    /**
     * Reads json string and returns the right location component, e.g. latitude or longitude.
     *
     * @param jsonString Response from the API as Json string.
     * @param key Location component, either "lat" or "lng", for latitude or longitude, respectively.
     * @return The value of the selected component.
     * @throws JSONException The exception is thrown if the results array is empty, or any other key is missing.
     */
    private double getLocationComponent(String jsonString, String key) throws JSONException {
        JSONObject object = new JSONObject(jsonString);
        return (double) object.getJSONArray("results").getJSONObject(0).getJSONObject("geometry")
                .getJSONObject("location").get(key);
    }

    /**
     * Displays the number of properties that are within the specified range
     * from the address that the user entered.
     *
     * @param lat Latitude of the address.
     * @param lng Longitude of the address.
     * @param rangeInMiles Distance in miles.
     */
    private void setDisplay(double lat, double lng, double rangeInMiles) {
        int counter = 0;
        for (AirbnbListing listing : sortedList) {
            double distance = calculateDistance(lat, lng, listing.getLatitude(), listing.getLongitude());
            if (distance < rangeInMiles) {
                counter++;
            }
        }
        display.setText("There are " + counter + " properties near you.");
    }

    /**
     * Calculates the distance between the two locations.
     *
     * @param lat1 Latitude of the first location.
     * @param lng1 Longitude of the first location.
     * @param lat2 Latitude of the second location.
     * @param lng2 Longitude of the second location.
     * @return
     */
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        lat1 = Math.toRadians(lat1);
        lng1 = Math.toRadians(lng1);
        lat2 = Math.toRadians(lat2);
        lng2 = Math.toRadians(lng2);
        // spherical law of cosines
        double theCos = Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng1 - lng2);
        double arcLength = Math.acos(theCos);
        return arcLength * 3963.1676;  // Earth radius in miles.
    }


    /**
     * Only for testing.
     * TODO: delete
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        AirbnbDataLoader loader = new AirbnbDataLoader();
        frame.getContentPane().add(new PropertiesNearby(loader.load(), 0, 5000));
        frame.pack();
        frame.setVisible(true);
    }
}

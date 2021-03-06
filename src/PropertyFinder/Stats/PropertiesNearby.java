package PropertyFinder.Stats;

import PropertyFinder.Map.BnbTable;
import PropertyFinder.Map.District;
import com.sun.istack.internal.Nullable;
import org.apache.commons.httpclient.URIException;
import org.apache.commons.httpclient.util.URIUtil;
import org.json.JSONException;
import org.json.JSONObject;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
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
    private static final int QUERY_COUNT_LIMIT = 10; // Maximum number of queries.

    private JTextField input; // Address
    private JTextField range; // Range in miles
    private JLabel displayInfo; // Number of properties or error message
    private JButton showResults; // Opens new table with properties, if set
    private List<AirbnbListing> sortedList; // Only listings within a valid price range.
    private int queryCounter; // Prevents infinite loop while querying the API.

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
        queryCounter = 0;
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
        JButton search = new JButton("Search");
        search.addActionListener(e -> searchClicked());
        searchPanel.add(search);
        inputs.add(searchPanel);

        central.add(inputs, BorderLayout.NORTH);
        top.add(central, BorderLayout.CENTER);
        add(top, BorderLayout.NORTH);

        JPanel results = new JPanel(new BorderLayout());
        results.setOpaque(false);

        JPanel topInfo = new JPanel(new BorderLayout());
        topInfo.setOpaque(false);
        JPanel infoLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        infoLabel.setOpaque(false);
        displayInfo = new JLabel();
        displayInfo.setText("<html><p style=\"text-align: center;\">" +
                "<font size=\"3\" color=\"gray\"><i>" +
                "Please enter an your address <br>and preferred range above.</i></font>" +
                "</p></html>");
        infoLabel.add(displayInfo);
        topInfo.add(infoLabel, BorderLayout.CENTER);
        results.add(topInfo, BorderLayout.NORTH);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(Box.createRigidArea(new Dimension(panel.getWidth(), 10)));

        JPanel button = new JPanel(new FlowLayout(FlowLayout.CENTER));
        button.setOpaque(false);
        showResults = new JButton("Show results");
        showResults.setVisible(false);
        showResults.setEnabled(false);
        button.add(showResults);
        panel.add(button);
        panel.add(Box.createRigidArea(new Dimension(panel.getWidth(), 20)));

        results.add(panel, BorderLayout.CENTER);

        add(results, BorderLayout.CENTER);
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
        // If inputs are correct we can continue.
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
     * @return Response in format of Json string. null if there was an error (for example no internet connection...)
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
     */
    private String getJsonString(InputStream stream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(stream));
        String output = "";
        StringBuilder jsonString = new StringBuilder();

        try {
            while ((output = br.readLine()) != null) {
                jsonString.append(output.trim());
            }
        }
        catch (IOException e) {
            System.out.println("---------------");
            System.out.println("IO Exception in method getJsonString");
            System.out.println(e.getLocalizedMessage());
        }

        return jsonString.toString();
    }

    /**
     * Gets latitude and longitude for the address that user entered and displays the correct info.
     *
     * @param jsonString Response from the API as Json string.
     * @param rangeInMiles Distance from address which the user wants statistics for.
     */
    private void displayLocation(@Nullable String jsonString, double rangeInMiles) {
        String status;
        JSONObject object;
        if (jsonString == null) {
            status = "Error";
        }
        else {
            object = new JSONObject(jsonString);
            status = (String) object.get("status");
        }

        if (status.equals("OK")) {
            try {
                object = new JSONObject(jsonString);
                JSONObject location = object.getJSONArray("results").getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                double lat = (double) location.get("lat");
                double lng = (double) location.get("lng");

                String address = (String) object.getJSONArray("results").getJSONObject(0).get("formatted_address");
                setDisplayInfo(lat, lng, rangeInMiles, address);
            }
            catch (JSONException e) {
                displayInfo.setText("<html><p style=\"text-align: center;\">" +
                        "<font size=\"3\" color=\"gray\"><i>" +
                        "Sorry, an error occurred while obtaining your location.<br>Please, try again.</i></font>" +
                        "</p></html>");
            }
            queryCounter = 0;
        }
        else if (status.equals("OVER_QUERY_LIMIT") && queryCounter <= QUERY_COUNT_LIMIT) {
            // This error is very often fixed by executing the query again.
            // Limit is set to prevent endless looping.
            queryCounter++;
            searchClicked();
        }
        else if (status.equals("ZERO_RESULTS")) {
            displayInfo.setText("<html><p style=\"text-align: center;\">" +
                    "<font size=\"3\" color=\"gray\"><i>" +
                    "Sorry, we couldn't find that address.<br>Please, try again.</i></font>" +
                    "</p></html>");
            queryCounter = 0;
        }
        else {
            // Some other error occurred.
            displayInfo.setText("<html><p style=\"text-align: center;\">" +
                    "<font size=\"3\" color=\"gray\"><i>" +
                    "Sorry, an error occurred while obtaining your location.<br>Please, try again.</i></font>" +
                    "</p></html>");
            queryCounter = 0;
        }

    }

    /**
     * Displays the number of properties that are within the specified range
     * from the address that the user entered.
     * Updates the button that shows all the properties in the given range.
     * If there are no properties the button is disabled.
     *
     * @param lat Latitude of the address.
     * @param lng Longitude of the address.
     * @param rangeInMiles Distance in miles.
     */
    private void setDisplayInfo(double lat, double lng, double rangeInMiles, String formattedAddres) {
        ArrayList<AirbnbListing> bnbs = new ArrayList<>();
        for (AirbnbListing listing : sortedList) {
            double distance = calculateDistance(lat, lng, listing.getLatitude(), listing.getLongitude());
            if (distance < rangeInMiles) {
                bnbs.add(listing);
            }
        }

        String[] address = formattedAddres.split(",");
        StringBuilder addressToDisplay = new StringBuilder();
        if (address.length < 3) {
            addressToDisplay.append(formattedAddres);
        }
        else {
            addressToDisplay.append(address[0]);
            addressToDisplay.append(", ");
            addressToDisplay.append(address[1]);
            addressToDisplay.append("<br>");
            for (int i = 2; i < address.length; i++) {
                addressToDisplay.append(address[i]);
                addressToDisplay.append("<br>");
            }
        }
        displayInfo.setText("<html>Showing results for: <br>" +
                "<i>" + addressToDisplay + "</i><br>" +
                "There are " + bnbs.size() + " properties near you." +
                "</html>");

        if (bnbs.size() > 0) {
            showResults.setVisible(true);
            showResults.setEnabled(true);
            for (ActionListener al : showResults.getActionListeners()){
                showResults.removeActionListener(al);
            }
            showResults.addActionListener(e -> {
                District district = new District("my range", 0, 0);
                for (AirbnbListing bnb : bnbs) {
                    district.addBnb(bnb);
                }
                new BnbTable(district);
            });
        }
        else {
            showResults.setVisible(false);
            showResults.setEnabled(false);
            for (ActionListener al : showResults.getActionListeners()){
                showResults.removeActionListener(al);
            }
        }
    }

    /**
     * Calculates the distance between the two locations.
     *
     * @param lat1 Latitude of the first location.
     * @param lng1 Longitude of the first location.
     * @param lat2 Latitude of the second location.
     * @param lng2 Longitude of the second location.
     * @return Distance in miles.
     */
    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        lat1 = Math.toRadians(lat1);
        lng1 = Math.toRadians(lng1);
        lat2 = Math.toRadians(lat2);
        lng2 = Math.toRadians(lng2);
        // Spherical law of cosines.
        double theCos = Math.sin(lat1) * Math.sin(lat2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.cos(lng1 - lng2);
        double arcLength = Math.acos(theCos);
        return arcLength * 3963.1676;  // Earth radius in miles.
    }
}

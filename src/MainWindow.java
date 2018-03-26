import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the main window of the application.
 *
 * @author Luka Kralj
 * @version 14 March 2018
 *
 * TODO: Test behaviour with actual panels.
 */
public class MainWindow {
    private JFrame frame;
    private AppPanel currentPanel;

    private JButton leftButton; // Scroll left.
    private JButton rightButton; // Scroll right.

    // List of all the panels through which the user can scroll.
    private List<AppPanel> panels;
    // Keep track of the index of the currently displayed panel.
    private int currentPanelIndex;
    // List of all the properties.
    private List<AirbnbListing> listings;

    // List of price range boundaries the user can choose from.
    private Integer[] prices;
    private JComboBox<Integer> lowPrice; // Lower price boundary.
    private JComboBox<Integer> highPrice; // Upper price boundary.

    // Current size and location of the main window.
    // For the main window to maintain its location on the screen when the user moves it there.
    private Dimension currentSize;
    private Point currentLocation;

    /**
     * Initialise the main window of the application.
     * Load all available properties from the CSV file. All panels will use the list
     * created here to ensure consistency amongst the data displayed.
     */
    public MainWindow() {
        panels = new ArrayList<>();
        currentPanelIndex = 0;
        AirbnbDataLoader loader = new AirbnbDataLoader();
        listings = loader.load();
        prices = createLists();

        frame = new JFrame("London properties - AirBnB");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout()); // Hold all three main components together (top, bottom and central panel that can change).

        JPanel top = createTop();
        pane.add(top, BorderLayout.NORTH);

        panels.add(new WelcomePanel("Welcome", -1, -1));
        panels.add(new Map("never displayed", new ArrayList<>(), 0, 0));
        currentPanel = panels.get(1);
        pane.add(currentPanel, BorderLayout.CENTER);

        JPanel bottom = createBottom();
        pane.add(bottom, BorderLayout.SOUTH);

        frame.pack();
        currentSize = frame.getSize();

        pane.remove(currentPanel);
        currentPanel = panels.get(0);
        panels.remove(1);
        pane.add(currentPanel, BorderLayout.CENTER);
        frame.repaint();

        frame.pack();
        frame.setSize(currentSize);
        currentLocation = getCentralLocation();
        frame.setLocation(currentLocation);
        frame.setVisible(true);
    }

    /**
     * Creates list from which the user chooses the price range.
     * The values are determined by the price of the most expensive property;
     * values increase by 10% of the maximum value.
     *
     * @return List of values to create drop-down lists with.
     */
    private Integer[] createLists() {
        int maxPrice = 0;
        for (AirbnbListing listing : listings) {
            if (listing.getPrice() > maxPrice) {
                maxPrice = listing.getPrice();
            }
        }
        if (maxPrice < 10) {
            // Very unlikely but is needed to prevent errors or duplication of the calculated values in the list.
            return new Integer[]{0, 5, 10};
        }
        Integer[] list = new Integer[11];
        list[0] = 0;
        list[1] = (maxPrice/10);      // 10% of max price
        list[2] = (maxPrice/10) * 2;  // 20%
        list[3] = (maxPrice/10) * 3;  // 30%
        list[4] = (maxPrice/10) * 4;  // 40%
        list[5] = (maxPrice/10) * 5;  // ...
        list[6] = (maxPrice/10) * 6;
        list[7] = (maxPrice/10) * 7;
        list[8] = (maxPrice/10) * 8;
        list[9] = (maxPrice/10) * 9;
        list[10] = maxPrice + 100; // To make sure that all the listings will be included.

        return list;
    }

    /**
     * Creates two drop down lists to pick the price from.
     * @return Top panel to be added to the top of the main screen.
     */
    private JPanel createTop() {
        JPanel top = new JPanel(new BorderLayout());

        JPanel lists = new JPanel(new FlowLayout());
        lowPrice = new JComboBox<>(prices);
        lowPrice.setPreferredSize(new Dimension(70, 30));
        lowPrice.setOpaque(true);
        lowPrice.setBackground(Color.WHITE);
        lowPrice.setFocusable(false);
        lowPrice.addActionListener(e -> lowPriceClicked());
        lowPrice.setSelectedIndex(-1);
        //lowPrice.setBorder(new LineBorder(new Color(62, 196, 248), 1, true)); // TODO: useful if the whole JComboBox has round corners and different arrow (define separate class?)
        highPrice = new JComboBox<>(prices);
        highPrice.setPreferredSize(new Dimension(70, 30));
        highPrice.setOpaque(true);
        highPrice.setBackground(Color.WHITE);
        highPrice.setFocusable(false);
        highPrice.addActionListener(e -> highPriceClicked());
        highPrice.setSelectedIndex(-1);
        //highPrice.setBorder(new LineBorder(new Color(62, 196, 248), 1, true));

        lists.add(new JLabel(" From: "));
        lists.add(lowPrice);
        lists.add(new JLabel(" To: "));
        lists.add(highPrice);

        top.add(lists, BorderLayout.EAST);
        top.add(new JSeparator(), BorderLayout.SOUTH);
        return top;
    }

    /**
     * Creates buttons to scroll through the panels in the main window.
     * @return Bottom panel to be added to the bottom of the main screen.
     */
    private JPanel createBottom() {
        JPanel bottom = new JPanel(new BorderLayout());

        bottom.add(new JSeparator(), BorderLayout.NORTH);

        JPanel left = new JPanel();
        left.setLayout(new FlowLayout());
        leftButton = new JButton("<");
        leftButton.addActionListener(e -> {
            currentPanelIndex--;
            currentLocation = frame.getLocation();
            currentSize = frame.getSize();
            updateCurrentPanel();
        });
        leftButton.setEnabled(false);
        leftButton.setPreferredSize(new Dimension(60, 20));
        left.add(leftButton);

        JPanel right = new JPanel();
        right.setLayout(new FlowLayout());
        rightButton = new JButton(">");
        rightButton.addActionListener(e -> {
            currentPanelIndex++;
            currentLocation = frame.getLocation();
            currentSize = frame.getSize();
            updateCurrentPanel();
        });
        rightButton.setEnabled(false);
        rightButton.setPreferredSize(new Dimension(60, 20));
        right.add(rightButton);

        bottom.add(left, BorderLayout.WEST);
        bottom.add(right, BorderLayout.EAST);
        return bottom;
    }

    /**
     * Updates the buttons to move left and right. If there is no panels on either
     * side the appropriate button is disabled.
     * If button is enabled there will be a tip text message showing the title of the next panel in that direction.
     */
    private void updateButtons() {
        if (panels.size() == 1) { // There is only one panel available.
            leftButton.setEnabled(false);
            leftButton.setToolTipText(null);
            rightButton.setEnabled(false);
            rightButton.setToolTipText(null);
        }
        else if (currentPanelIndex > 0 && currentPanelIndex < panels.size()-1) { // One of second to second-last panel is displayed.
            leftButton.setEnabled(true);
            leftButton.setToolTipText(getToolTipText(currentPanelIndex - 1));
            rightButton.setEnabled(true);
            rightButton.setToolTipText(getToolTipText(currentPanelIndex + 1));
        }
        else if (currentPanelIndex == 0) { // First panel is displayed.
            leftButton.setEnabled(false);
            leftButton.setToolTipText(null);
            rightButton.setEnabled(true);
            rightButton.setToolTipText(getToolTipText(currentPanelIndex + 1));
        }
        else { // Last panel is displayed.
            leftButton.setEnabled(true);
            leftButton.setToolTipText(getToolTipText(currentPanelIndex - 1));
            rightButton.setEnabled(false);
            rightButton.setToolTipText(null);
        }
    }

    /**
     * Generates the tip text for the left and right buttons according to the index of the next panel in
     * that direction.
     *
     * @param index Index of the next panel in certain direction.
     * @return Edited tip text that includes appropriate panel's title.
     */
    private String getToolTipText(int index) {
        return "<html><p style=\"background-color:white;\"><font size=\"5\" color=\"black\"><strong>" +
                "<i><font size=\"3\">Go to: </font></i>" + panels.get(index).getTitle() +
                "</strong></font></p></html>";
    }

    /**
     * Displays a new panel according to the current panel index and update buttons accordingly.
     */
    private void updateCurrentPanel() {
        frame.getContentPane().remove(currentPanel);
        currentPanel = panels.get(currentPanelIndex);
        frame.getContentPane().add(currentPanel, BorderLayout.CENTER);
        frame.repaint();
        frame.pack();
        frame.setLocation(currentLocation);
        frame.setSize(currentSize);
        updateButtons();
    }

    /**
     * This method is called when the user selects the lower price boundary in the appropriate
     * drop-down list. The method then appropriately updates the other drop-down list to only
     * include the valid prices.
     */
    private void lowPriceClicked() {
        if (lowPrice.getSelectedItem() == null) {
            return;
        }
        // Get the chosen item.
        Integer chosenLow = (Integer)lowPrice.getSelectedItem();
        // Create new list of prices.
        ArrayList<Integer> availablePrices = new ArrayList<>();
        for (int i = 0; i < prices.length; i++) {
            if (prices[i] > chosenLow) {
                availablePrices.add(prices[i]);
            }
        }

        Integer[] newPrices = new Integer[availablePrices.size()];
        newPrices = availablePrices.toArray(newPrices);
        // Set new list of prices to the second combo box.
        DefaultComboBoxModel model = new DefaultComboBoxModel(newPrices);
        highPrice.setModel(model);
        highPrice.setSelectedIndex(-1);
    }

    /**
     * This method is called when the user selects the higher price boundary in the appropriate
     * drop-down list. The method executed the loading of data and creates the main app panels
     * with appropriate parameters.
     */
    private void highPriceClicked() {
        currentSize = frame.getSize();
        currentLocation = frame.getLocation();

        if (lowPrice.getSelectedItem() == null || highPrice.getSelectedItem() == null) {
            return;
        }
        int chosenLow = (Integer)lowPrice.getSelectedItem();
        int chosenHigh = (Integer)highPrice.getSelectedItem();
        if (chosenLow == chosenHigh) { // The prices cannot be the same.
            return;
        }

        panels.clear();
        panels.add(new WelcomePanel("Welcome", chosenLow, chosenHigh));
        panels.add(new Map("Map", listings, chosenLow, chosenHigh));
        try {
            panels.add(new StatsPanel((ArrayList<AirbnbListing>)listings, chosenLow, chosenHigh, 4));
        } catch (Exception e) {
            System.out.println("Stats exception.");
        }
        updateCurrentPanel();
    }

    /**
     * Determines the new position of the main window on screen according to the frame and screen size
     * so that the window is displayed in the middle of the screen.
     *
     * @return Point of where the top left corner of the window will be.
     */
    private Point getCentralLocation() {
        int height = frame.getHeight();
        int width = frame.getWidth();
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;

        int x = screenWidth/2 - width/2;
        int y = screenHeight/2 - height/2;

        return new Point(x, y);
    }
}

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

    // List of price range boundaries the user can choose from.
    private Integer[] prices;
    private JComboBox<Integer> lowPrice; // Lower price boundary.
    private JComboBox<Integer> highPrice; // Upper price boundary.

    /**
     * Initialise the main window of the application.
     */
    public MainWindow() {
        panels = new ArrayList<>();
        currentPanelIndex = 0;
        prices = new Integer[]{0, 20, 50, 100, 200, 500, 1000, 5000, 8000}; // TODO: Decide on which prices to include. These values are just for testing purpcses.

        frame = new JFrame("London properties - AirBnB");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();
        pane.setLayout(new BorderLayout()); // Hold all three main components together (top, bottom and central panel that can change).

        JPanel top = createTop();
        pane.add(top, BorderLayout.NORTH);

        // Create the welcome panel and display it.
        panels.add(new WelcomePanel());
        currentPanel = panels.get(0);
        pane.add(currentPanel, BorderLayout.CENTER);

        JPanel bottom = createBottom();
        pane.add(bottom, BorderLayout.SOUTH);

        frame.setLocationRelativeTo(null);
        frame.setMinimumSize(new Dimension(500, 200));
        frame.pack();
        frame.setVisible(true);
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
        //lowPrice.setBorder(new LineBorder(new Color(62, 196, 248), 1, true)); // TODO: useful if the whole JComboBox has round corners and different arrow (define separate class?)
        highPrice = new JComboBox<>(prices);
        highPrice.setPreferredSize(new Dimension(70, 30));
        highPrice.setOpaque(true);
        highPrice.setBackground(Color.WHITE);
        highPrice.setFocusable(false);
        highPrice.addActionListener(e -> highPriceClicked());
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
     */
    private void updateButtons() {
        if (panels.size() == 1) { // There is only one panel available.
            leftButton.setEnabled(false);
            rightButton.setEnabled(false);
        }
        else if (currentPanelIndex > 0 && currentPanelIndex < panels.size()-1) { // One of second to second-last panel is displayed.
            leftButton.setEnabled(true);
            rightButton.setEnabled(true);
        }
        else if (currentPanelIndex == 0) { // First panel is displayed.
            leftButton.setEnabled(false);
            rightButton.setEnabled(true);
        }
        else { // Last panel is displayed.
            leftButton.setEnabled(true);
            rightButton.setEnabled(false);
        }
    }

    /**
     * Displays a new panel according to the current panel index and update buttons accordingly.
     */
    private void updateCurrentPanel() {
        frame.getContentPane().remove(currentPanel);
        currentPanel = panels.get(currentPanelIndex);
        frame.getContentPane().add(currentPanel, BorderLayout.CENTER);
        frame.getContentPane().repaint();
        // frame.revalidate(); Try with actual panels what is the difference.
        frame.pack();
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
    }

    /**
     * This method is called when the user selects the higher price boundary in the appropriate
     * drop-down list. The method executed the loading of data and creates the main app panels
     * with appropriate parameters.
     */
    private void highPriceClicked() {
        if (lowPrice.getSelectedItem() == null || highPrice.getSelectedItem() == null) {
            return;
        }
        int chosenLow = (Integer)lowPrice.getSelectedItem();
        int chosenHigh = (Integer)highPrice.getSelectedItem();
        if (chosenLow == chosenHigh) { // The prices cannot be the same.
            return;
        }
        AirbnbDataLoader loader = new AirbnbDataLoader();
        List<AirbnbListing> listings = loader.load();; // List of all the available properties.

        // TODO: Create panels and add them to the list accordingly.

        updateCurrentPanel();
    }

}

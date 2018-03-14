import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents the main window of the application.
 *
 * TODO: Test behaviour with actual panels.
 */
public class MainWindow {
    JFrame frame;
    private AppPanel currentPanel;

    private ArrayList<AirbnbListing> listings;

    private JButton leftButton;
    private JButton rightButton;

    // List of all the panels through which the user can scroll.
    private List<AppPanel> panels;
    // Keep track of the index of the currently displayed panel.
    private int currentPanelIndex;

    /**
     * Initialise the main window of the application.
     */
    public MainWindow() {
        panels = new ArrayList<>();
        currentPanelIndex = 0;

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
        frame.setMinimumSize(new Dimension(300, 200));
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates two drop down lists to pick the price from.
     * @return Top panel to be added to the top of the main screen.
     */
    private JPanel createTop() {
        JPanel top = new JPanel(new BorderLayout());

        Integer[] prices = {0, 50, 100, 1000, 5000}; // TODO: Decide on which prices to include. These values are just for testing purpcses.

        JPanel lists = new JPanel(new FlowLayout());

        JButton testTrigger = new JButton("Ok");
        testTrigger.addActionListener(e -> {
            updateCurrentPanel();
            testTrigger.setEnabled(false);
        });
        lists.add(testTrigger);

        JComboBox<Integer> lowPrice = new JComboBox<>(prices);
        lowPrice.setPreferredSize(new Dimension(70, 30));
        JComboBox<Integer> highPrice = new JComboBox<>(prices);
        highPrice.setPreferredSize(new Dimension(70, 30));
        lists.add(lowPrice);
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
        leftButton = new JButton("<<");
        leftButton.addActionListener(e -> {
            currentPanelIndex--;
            updateCurrentPanel();
        });
        leftButton.setEnabled(false);
        leftButton.setPreferredSize(new Dimension(60, 20));
        left.add(leftButton);

        JPanel right = new JPanel();
        right.setLayout(new FlowLayout());
        rightButton = new JButton(">>");
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
        if (currentPanelIndex > 0 && currentPanelIndex < panels.size()-1) { // One of second to second-last panel is displayed.
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
}

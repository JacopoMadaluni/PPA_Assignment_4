import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Represents the main window of the application.
 */
public class MainWindow {
    JFrame frame;
    private AppPanel currentPanel;
    private AppPanel welcome;
    private AppPanel map;
    private AppPanel stats;

    private ArrayList<AirbnbListing> listings;

    private JButton leftButton;
    private JButton rightButton;

    /**
     * Initialise the main window of the application.
     */
    public MainWindow() {
        frame = new JFrame("Main window");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        Container pane = frame.getContentPane();

        JPanel all = new JPanel(new BorderLayout());

        JPanel top = createTop();
        all.add(top, BorderLayout.NORTH);

        // Create the welcome panel and display it.
        welcome = new WelcomePanel();
        currentPanel = welcome;
        all.add(currentPanel);


        JPanel bottom = createBottom();
        all.add(bottom, BorderLayout.SOUTH);

        pane.add(all);
        frame.setLocationRelativeTo(null);
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
        leftButton.setPreferredSize(new Dimension(60, 20));
        left.add(leftButton);

        JPanel right = new JPanel();
        right.setLayout(new FlowLayout());
        rightButton = new JButton(">>");
        rightButton.setPreferredSize(new Dimension(60, 20));
        right.add(rightButton);

        bottom.add(left, BorderLayout.WEST);
        bottom.add(right, BorderLayout.EAST);
        return bottom;
    }
}

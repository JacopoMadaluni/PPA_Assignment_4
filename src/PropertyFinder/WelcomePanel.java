package PropertyFinder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Represents an initial panel that is already shown at the start of the application.
 * If the price range is not selected, it prompts the user to choose it. If the price range is selected
 * it will display it.
 *
 * @author Luka Kralj
 * @version 27 March 2018
 */
public class WelcomePanel extends AppPanel {
    private static final Image background = Toolkit.getDefaultToolkit().createImage("resources/icons/bg1.jpeg");

    /**
     * Create a welcome panel.
     *
     * @param title Titel of the welcome panel.
     * @param lowPrice Lower price boundary that the user has chosen.
     *                 <strong> Set to -1 if no price has been chosen.</strong>
     *                 (Usually the beginning of the program.)
     * @param highPrice Upper price boundary that the user has chosen.
     *                 <strong> Set to -1 if no price has been chosen.</strong>
     *                 (Usually the beginning of the program.)
     */
    public WelcomePanel(String title, int lowPrice, int highPrice) {
        super(title, null, lowPrice, highPrice);

        setOpaque(false);
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 25, 10, 25));

        JPanel top = new JPanel(new FlowLayout(FlowLayout.CENTER));
        top.setOpaque(false);
        top.add(createWelcomeLabel());
        add(top, BorderLayout.NORTH);

        add(createCentralPanel(lowPrice, highPrice), BorderLayout.CENTER);
        add(createTips(), BorderLayout.SOUTH);
    }

    /**
     * Create a panel with some welcome text.
     *
     * @return Panel with some welcome text.
     */
    private JPanel createWelcomeLabel() {
        String welcomeText ="<html>" +
                "<font size=\"6\" color=\"black\">" +
                "<p style=\"text-align: center;\">" +
                "This is a London Airbnb property finder that helps you locate and compare<br>" +
                "the available rooms which are just about right for your budget!</p>" +
                "<p style=\"text-align: center;\"><br></p>" +
                "</font>" +
                "</html>";

        JLabel text = new JLabel(welcomeText);
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textPanel.setOpaque(false);
        textPanel.add(text);

        JLabel ribbon = new JLabel(new ImageIcon("resources/icons/welcome.png"));
        JPanel ribbonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        ribbonPanel.setOpaque(false);
        ribbonPanel.add(ribbon);

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));

        panel.add(ribbonPanel);
        panel.add(textPanel);
        return panel;
    }

    /**
     * Create a label with short tips/instructions for using the application.
     *
     * @return Label with the tips text.
     */
    private JLabel createTips() {
        String tipsText = "<html>" +
                "<font size=\"4\" color=\"black\">" +
                "<p style=\"text-align: left;\"><font size=\"5\">Useful tips:</font></p>" +
                "<ul>" +
                "<li style=\"text-align: left;\">Choose the price range in the top right corner.</li>" +
                "<li style=\"text-align: left;\">When you select the wanted price range the map and various statistics will <br>" +
                "become available for you.</li>" +
                "<li style=\"text-align: left;\">Add properties to your list of favourites and compare them!</li>" +
                "<li style=\"text-align: left;\">Use buttons bellow to navigate around the app.</li>" +
                "<li style=\"text-align: left;\">Hover your mouse over the buttons bellow to see what the next panel is.</li>" +
                "</ul>" +
                "</font>" +
                "</html>";

        return new JLabel(tipsText);
    }

    /**
     * This method creates a panel that informs the user of his currently selected price range.
     *
     * @param lowPrice Lower price boundary that the user has selected. <strong>-1 if no range is selected.</strong>
     * @param highPrice Upper price boundary that the user has selected. <strong>-1 if no range is selected.</strong>
     * @return Panel with info about the range.
     */
    private JPanel createPriceRangePanel(int lowPrice, int highPrice) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel.setOpaque(false);
        String range = "<font size=\"3\" color=\"gray\"><i>Select the price range first.</i></font>";
        if (!(lowPrice == -1 || highPrice == -1)) {
            range = "<font size=\"6\">" + lowPrice + "&pound; - " + highPrice + "&pound;</font>";
        }
        String text = "<html>" +
                "<font size=\"4\" color=\"black\">" +
                "<p style=\"text-align: center;\">" +
                "<br>Your currently selected price range:<br><br>" +
                range +
                "</p>" +
                "</font>" +
                "</html>";
        panel.add(new JLabel(text));
        panel.setPreferredSize(new Dimension(this.getWidth(),100));
        return panel;
    }

    /**
     * Creates the central panel of the welcome window with an AirBnB logo and the selected price range.
     *
     * @param lowPrice Lower price boundary that the user has selected. <strong>-1 if no range is selected.</strong>
     * @param highPrice Upper price boundary that the user has selected. <strong>-1 if no range is selected.</strong>
     * @return Panel with icon and price range info.
     */
    private JPanel createCentralPanel(int lowPrice, int highPrice) {
        JPanel all = new JPanel();
        all.setOpaque(false);
        all.setLayout(new BoxLayout(all, BoxLayout.PAGE_AXIS));

        JPanel top = new JPanel(new BorderLayout());
        top.setOpaque(false);
        ImageIcon icon = new ImageIcon("resources/icons/circle-logo.png");
        JLabel image = new JLabel(icon);
        top.add(image, BorderLayout.CENTER);
        all.add(top);

        JPanel bottom = new JPanel(new BorderLayout());
        bottom.setOpaque(false);
        bottom.add(createPriceRangePanel(lowPrice, highPrice), BorderLayout.CENTER);
        all.add(bottom);

        return all;
    }

    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(background, 0, 0, this.getWidth(), this.getHeight(), null);
        super.paintComponent(g);

    }
}

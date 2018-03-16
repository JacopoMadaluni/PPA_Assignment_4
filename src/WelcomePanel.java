import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * Represents an initial panel that is already shown at the start of the application.
 * If the price range is not selected, it prompts the user to choose it. If the price range is selected
 * it will display it.
 *
 * @author Luka Kralj
 * @version 16 March 2018
 */
public class WelcomePanel extends AppPanel{

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

        setOpaque(true);
        setBackground(new Color(214, 219, 223));
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(10, 25, 10, 25));

        add(createWelcomeLabel(), BorderLayout.NORTH);
        add(createPriceRangePanel(lowPrice, highPrice), BorderLayout.CENTER);
        add(createTips(), BorderLayout.SOUTH);
    }

    /**
     * Create a label with short welcome text.
     *
     * @return Lable with some welcome text.
     */
    private JLabel createWelcomeLabel() {
        String welcomeText ="<html>" +
                "<h2 style=\"text-align: center;\"><strong>WELCOME!</strong></h2>" +
                "<font size=\"4\" color=\"black\">" +
                "<p style=\"text-align: center;\">This is a London AirBnB property finder that helps you locate the available rooms that are just about right for your budget.</p>" +
                "<p style=\"text-align: center;\"><br></p>" +
                "</font>" +
                "</html>";

        return new JLabel(welcomeText);
    }

    /**
     * Create a label with short tips/instructions for using the application.
     *
     * @return Label with the tips text.
     */
    private JLabel createTips() {
        String tipsText = "<html>" +
                "<font size=\"4\" color=\"black\">" +
                "<p style=\"text-align: left;\">Useful tips:</p>" +
                "<ul>" +
                "<li style=\"text-align: left;\">Choose the price range in the top right corner.</li>" +
                "<li style=\"text-align: left;\">When you select the wanted price range the map and various statistics will <br>" +
                "become available for you.</li>" +
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
                "Your currently selected price range:<br><br>" +
                range +
                "</p>" +
                "</font>" +
                "</html>";
        panel.add(new JLabel(text));
        panel.setPreferredSize(new Dimension(this.getWidth(),100));
        return panel;
    }
}

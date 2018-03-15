import javax.swing.*;
import java.awt.*;

/**
 * Represents an initial panel that is already shown at the start of the application.
 *
 * TODO: This class is not completed yet. It is created in order for the main window to run.
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
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));


        add(createWelcomeLabel());
        add(createTips());
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
}

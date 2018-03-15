import java.awt.*;

/**
 * Represents an initial panel that is already shown at the start of the application.
 *
 * TODO: This class is not completed yet. It is created in order for the main window to run.
 */
public class WelcomePanel extends AppPanel{

    public WelcomePanel() {
        super("Welcome", null, 0, 0);

        add(new Label("Test welcome window"));
    }
}

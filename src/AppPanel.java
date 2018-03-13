import javax.swing.*;

/**
 * This abstract class is used in the main window to scroll between panels.
 * Each panel (welcome, map, statistics,...) need to extend this class.
 *
 * TODO: Plan is to add a bubble with the title when a user hovers over the buttons to switch the panels.
 *
 */
public abstract class AppPanel extends JPanel{
    String title;

    /**
     * Create a new panel to be displayed in the main window.
     * @param title Title of the panel.
     */
    public AppPanel(String title) {
        this.title = title;
    }

    /**
     *
     * @return The title of the panel.
     */
    public String getTitle() {
        return title;
    }
}

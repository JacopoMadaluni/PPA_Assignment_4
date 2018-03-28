package PropertyFinder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This is a special panel contained in the grid layout of
 * the main StatsPanel, and it has the function of creating
 * and assigning functionality to the two side buttons as well
 * as holding the central statistic panel.
 * @author Danilo Del Busso, Luka Kralj
 * @version 14.03.2018
 */
public class StatsSubPanel extends JPanel {
   private JButton previousButton;
    private JButton nextButton;
    private JPanel centralPanel;

    /**
     * Constructor for PropertyFinder.StatsSubPanel, it creates the two side buttons
     * present in every SubPanel that allow for switching between central panels
     * and paints an initial central panel via the StatsPanel in which it
     * is contained.
     *
     * @param mainStatsPanel the StatsPanel that contains this subpanel
     */
    public StatsSubPanel(StatsPanel mainStatsPanel) {

        setLayout(new BorderLayout());
        StatsPanel mainStatsPanel1 = mainStatsPanel;
        //buttons
        previousButton = new JButton("<");
        nextButton = new JButton(">");

        //pick initial card to be displayed
        centralPanel = new JPanel();
        centralPanel = mainStatsPanel.getFreeNextCentralPanel(null);
        add(centralPanel, BorderLayout.CENTER);

        //set positions of elements
        add(previousButton, BorderLayout.WEST);
        add(nextButton, BorderLayout.EAST);

        //add buttons functionalities
        //previous
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                AppPanel newPanel = mainStatsPanel.getFreePreviousCentralPanel((AppPanel)centralPanel);
                updateCentralPanel(newPanel);
            }
        });

        //next
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AppPanel newPanel = mainStatsPanel.getFreeNextCentralPanel((AppPanel)centralPanel);
                updateCentralPanel(newPanel);
            }
        });

        setVisible(true);
    }

    /**
     * Update the central panel to be the specified one and repaint the whole
     * SubPanel
     * @param newPanel the new panel to be placed
     */
    private void updateCentralPanel(AppPanel newPanel) {
        if(newPanel != null){
            remove(centralPanel);
            centralPanel = newPanel;
            add(centralPanel, BorderLayout.CENTER);
            validate();
            repaint();
        }
    }

    /**
     * Return The current central panel.
     * @return The current central panel.
     */
    public AppPanel getCentralPanel() {
        if(centralPanel!= null){
             return (AppPanel) centralPanel;
            }

        return null;
    }

}

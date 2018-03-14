import com.sun.org.glassfish.external.statistics.Stats;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


/**
 * This is a sub-panel interface of panels contained in the grid layout of
 * the main StatsPanel, and it has the function of forcing
 * the creation of side buttons.
 * @author Danilo Del Busso
 * @version 14.03.2018
 */
public class StatsSubPanel extends JPanel {
    JButton previousButton;
    JButton nextButton;
    JPanel centralPanel;
    private StatsPanel mainStatsPanel;

    /**
     * Constructor for StatsSubPanel, it creates the two side buttons
     * present in every subpanel that allow for switching between statistics panels
     *
     * @param mainStatsPanel the StatsPanel that contains this subpanel
     */
    public StatsSubPanel(StatsPanel mainStatsPanel) {

        setLayout(new BorderLayout());
        this.mainStatsPanel = mainStatsPanel;
        //buttons
        previousButton = new JButton("<");
        nextButton = new JButton(">");

        centralPanel = new JPanel();
        centralPanel.setLayout(new CardLayout());

        //initialise all cards
        ArrayList<StatsCentralPanel> centralPanels = mainStatsPanel.getCentralPanels();
        for(StatsCentralPanel cp : centralPanels){
            centralPanel.add(cp, cp.getName());
        }

        //pick initial card to be displayed
        CardLayout cardLayout = (CardLayout) centralPanel.getLayout();
        cardLayout.show(centralPanel, mainStatsPanel.getFreeCentralPanelName());


        //add to SubPanel
        add(centralPanel, BorderLayout.CENTER);

        //set positions of elements
        add(previousButton, BorderLayout.WEST);
        add(nextButton, BorderLayout.EAST);


        //add button functionalities
        previousButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("PREVIOUS");
            }
        });


        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("NEXT");
            }
        });

        setVisible(true);
    }

    /**
     * Return the name of the card that is currently visible
     * on this subpanel
     * @return
     */
    public String getCurrentCardName(){
        for (Component comp : centralPanel.getComponents() ) {
            if (comp.isVisible() == true) {
                centralPanel = (JPanel)comp;
                System.out.println(centralPanel.getName() );
            }
        }
        return null;
    }


}

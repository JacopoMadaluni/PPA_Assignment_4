import com.sun.deploy.panel.JavaPanel;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


/** The statistics panel is based on CardLayout and allows for different panels which show different
    * statistics about the application and its data.
    * @author Danilo Del Busso
    * @version 14.03.2018
    */
public class StatsPanel extends AppPanel {

    /*Create the "sections". These panels are currently non existent
       * we are expecting one specialised panel to be created for each
       * needed section
       */
    private static ArrayList<StatsCentralPanel> centralPanels = new ArrayList<>();
    private static ArrayList<StatsCentralPanel> centralPanelsInUse = new ArrayList<>();
    private ArrayList<StatsSubPanel> statsSubPanels;
    //there needs be 4 additional statistics panels that
        // are not created for possible coupling reasons

    /**
     * Create a new statistics panel to be displayed in the main window.
     * @param listings
     * @param lowPrice
     * @param highPrice
     */
    public StatsPanel(ArrayList<AirbnbListing> listings, int lowPrice, int highPrice) {
        super("Statistics", listings, lowPrice, highPrice);

        //set the layout to be GridLayout, 2 rows and 2 columns
        setSize(200,200);
        setLayout(new GridLayout(2,2,10,10));

            //initialise all the statistics panels (8 in this case)
        centralPanels = new ArrayList<StatsCentralPanel>();
        statsSubPanels = new ArrayList<StatsSubPanel>();

        initialiseCentralPanels();
        initialiseSubPanels();

        setVisible(true);
    }



    /**
     * Create Instances of the StatsSubPanles and add
     * them to the statisticsPanels so they can be displayed
     */
    private void initialiseSubPanels() {


        StatsSubPanel sb1 = new StatsSubPanel(this);
        statsSubPanels.add(sb1);
        StatsSubPanel sb2 = new StatsSubPanel(this);
        statsSubPanels.add(sb2);
        StatsSubPanel sb3 = new StatsSubPanel(this);
        statsSubPanels.add(sb3);
        StatsSubPanel sb4 = new StatsSubPanel(this);

        statsSubPanels.add(sb4);

        for(StatsSubPanel subPanel:statsSubPanels){
            add(subPanel);
        }

    }

    public ArrayList<StatsCentralPanel> getCentralPanels() {
        return centralPanels;
    }

    public  void initialiseCentralPanels() {

        CentralPanel1 c1 = new CentralPanel1();
        CentralPanel2 c2 = new CentralPanel2();
        CentralPanel3 c3 = new CentralPanel3();
        CentralPanel4 c4 = new CentralPanel4();
        CentralPanel5 c5 = new CentralPanel5();


        centralPanels.add(c1);
        centralPanels.add(c2);
        centralPanels.add(c3);
        centralPanels.add(c4);
        centralPanels.add(c5);


    }

    public ArrayList<StatsSubPanel> getStatsSubPanels() {
        return statsSubPanels;
    }


    /**
     * Return the name of a card that is not currently displayed by any subpanel
     * @return the name of a card that is not currently displayed by any subpanel
     */
    public String getFreeCentralPanelName(){
        String name = null;

        for(StatsCentralPanel centralPanel : centralPanels){
            boolean found = false;
           if(! (statsSubPanels.size()<5)){
            for(StatsSubPanel subPanel: statsSubPanels){
                System.out.println(subPanel.getCurrentCardName());
                if(centralPanel.getName().equals(subPanel.getCurrentCardName())){
                   found = true;
                }
            }
           }
            //if it not found in current cards in the sub panels, it is not used
            if(!found){
                return centralPanel.getName();
            }

        }
        return name;
    };

}

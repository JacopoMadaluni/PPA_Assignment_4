import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**A Stats Panel has a number of subpanels in GridLayout that
    * show individual central panels with statistics about the listings in
    * the main application.
    * @author Danilo Del Busso, Luka Kralj
    * @version 14.03.2018
    */
public class StatsPanel extends AppPanel {

    /*Create the "sections". These panels are currently non existent
       * we are expecting one specialised panel to be created for each
       * needed section
       */
    private ArrayList<AirbnbListing> listings;
    private int lowPrice;
    private int highPrice;
    private ArrayList<AppPanel> centralPanels;
    private ArrayList<StatsSubPanel> statsSubPanels;

    /**
     * Initialise variables, create subpanels and give them initial central panels
     * to show initial statistics.
     * @param statsPanels the central panels which show the stats
     * @param listings the list of all listings in the application
     * @param lowPrice the lower bound for price range search
     * @param highPrice the upper bound for price range search
     * @throws Exception
     */
    public StatsPanel(List<AppPanel> statsPanels, ArrayList<AirbnbListing> listings, int lowPrice, int highPrice, int numberOfSubPanels) throws Exception {
        super("Statistics", listings, lowPrice, highPrice);

        this.listings = listings;
        this.lowPrice = lowPrice;
        this.highPrice = highPrice;
        //set the layout to be GridLayout, 2 rows and 2 columns with a spacing of 10 pixels between each subpanel
        setLayout(new GridLayout(2,2,10,10));
        //initialise data structures containing panels shown
        centralPanels = new ArrayList<AppPanel>();
        statsSubPanels = new ArrayList<StatsSubPanel>();
        //initialise and add visual elements
        initialiseCentralPanels(statsPanels);
        initialiseSubPanels(numberOfSubPanels); //"containers" for central panels
        setVisible(true);
    }

    /**
     * Inserts the panels that contain different statistics in the StatsPanel
     */
    public void initialiseCentralPanels(List<AppPanel> centralPanelsToAdd){
        for(AppPanel centralPanelToAdd: centralPanelsToAdd){
            centralPanels.add(centralPanelToAdd);
        }
    }

    /**
     * Create Instances of the StatsSubPanels and add
     * them to the statisticsPanels so they can be displayed
     * @param numberOfPanels the number of "containers" (StatsSubPanel) that are shown at any time on screen
     */
    private void initialiseSubPanels(int numberOfPanels) throws Exception {
        //check if the number of central panels is bigger than the expected number of sub panels that have
        // to contain them (pigeon hole principle)
        if(numberOfPanels > centralPanels.size()){
            throw new Exception("There are too few central panels and/or too many subpanels");
        }
        for(int i = 0; i<numberOfPanels;i++){
            statsSubPanels.add(new StatsSubPanel(this));
        }
        //add statssubpanels to this statspanel
        for(StatsSubPanel subPanel : statsSubPanels){
            add(subPanel);
        }

    }

    /**
     * Return the next central stats panel that is not visible on screen.
     * If all central panels are visible return null.
     * @param currentPanel The panel currently shown that we are substituting. If null there's no panel.If null there's no panel.
     * @return The next central stats panel that is not visible on screen.
     */
    public AppPanel getFreeNextCentralPanel(AppPanel currentPanel) {
        if(currentPanel != null){
            int indexOfCurrentPanel = centralPanels.indexOf(currentPanel);
            for(int i = 0; i < centralPanels.size(); i++){
                if(indexOfCurrentPanel == centralPanels.size()-1){
                    indexOfCurrentPanel = -1;
                }
                indexOfCurrentPanel++;
                AppPanel newPanel = centralPanels.get(indexOfCurrentPanel);
                if (!centralPanelIsVisible(newPanel)){
                    return newPanel;
                }
            }
        }
        //if currentpanel is null, we assume there's no panel and we return one random panel that is not currently shown
        else{
            return returnNewCentralPanel();
        }
        return null;
    }

    /**
     * Return the previous central stats panel that is not visible on screen.
     * If all central panels are visible return null.
     * @param currentPanel The panel currently shown that we are substituting. If null there's no panel.
     * @return The previous central stats panel that is not visible on screen
     */
    public AppPanel getFreePreviousCentralPanel(AppPanel currentPanel) {

        //
        if(currentPanel != null){
            int indexOfCurrentPanel = centralPanels.indexOf(currentPanel);
            for(int i = 0; i < centralPanels.size(); i++){
                if(indexOfCurrentPanel == 0){
                    indexOfCurrentPanel = centralPanels.size();
                }
                indexOfCurrentPanel--;
                AppPanel newPanel = centralPanels.get(indexOfCurrentPanel);
                if (!centralPanelIsVisible(newPanel)){
                    return newPanel;
                }
            }
        }
        //if currentpanel is null, we assume there's no panel and we return one random panel that is not currently shown
        else{
            return returnNewCentralPanel();
        }
        return null;
    }

    /**
     * Returns the first central panel in the centralPanels arraylist that is not
     * currently visible on screen.
     * @return The first central panel in the centralPanels arraylist that is not
     * currently visible on screen.
     */
    private AppPanel returnNewCentralPanel() {
        if(statsSubPanels.size() == 0){
            //if no subpanel has been created then there's no central panel that is currently visible
            return centralPanels.get(0);
        }
        for(AppPanel panel : centralPanels){
            if(!centralPanelIsVisible(panel)){
                return panel;
            }
        }
        return null;
    }

    /**
     * Return true if the centralPanel is currently visible on screen.
     * @param panelToCheck the central panel we're looking for on screen
     * @return true if the centralPanel is currently visible on screen.
     */
    private boolean centralPanelIsVisible(AppPanel panelToCheck) {
        if(!statsSubPanels.isEmpty()){
            for(StatsSubPanel subPanel: statsSubPanels){
                String panelToCheckName = panelToCheck.getTitle();
                AppPanel subPanelCentralPanel = (AppPanel) subPanel.getCentralPanel();
                String subPanelCentralPanelName = subPanelCentralPanel.getTitle();
                //if it is found as centralPanel of a subPanel then it is visible
                if(panelToCheckName.equals(subPanelCentralPanelName)){
                    return true;
                }
            }
        }
        //if it is not found as centralPanel of a subPanel then it not is visible
        return false;

    }
}
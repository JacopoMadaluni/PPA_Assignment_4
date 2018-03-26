package PropertyFinder.Map;

import PropertyFinder.AirbnbListing;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;
import javax.swing.*;
import java.awt.*;

/**
 * PropertyFinder.Map.SinglePropertyView Class. This will display
 * a window showing all the information related to
 * a specific Airbnb as well as an embedded
 * Google Maps application.
 * @author Alvaro Rausell
 * @version 20/03/2018
 */
public class SinglePropertyView {
    private AirbnbListing bnb;
    private BnbTable table;

    /**
     * Creates a Single Property View
     * @param bnb Airbnb to display information of
     * @param table Table of properties to open when this window is dismissed
     */
    public SinglePropertyView(AirbnbListing bnb,BnbTable table){
        this.bnb = bnb;
        this.table = table;
    }

    /**
     * Shows a window with all the Airbnb information as well
     * as the Google Maps implementation.
     */
    public void showProperty(){
        JFrame frame = new JFrame(bnb.getName());
        frame.setContentPane(getContent());
        frame.pack();
        frame.setSize(1000,1000);
        frame.setVisible(true);
    }

    /**
     * Returns the content of the information view.
     */
    public Container getContent(){
        JFrame frame = new JFrame(bnb.getName());
        frame.setResizable(false);

        JFXPanel mapPanel = new JFXPanel();
        frame.add(mapPanel,BorderLayout.PAGE_START);

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setLayout(new GridLayout(4,2,2,5));
        contentPanel.setBackground(new Color(255,255,255));
        JPanel southPanel = new JPanel(new BorderLayout());
        frame.add(southPanel, BorderLayout.CENTER);
        //Starts Google Maps
        Platform.runLater(()->{
            WebView webView = new WebView();
            mapPanel.setScene(new Scene(webView));
            webView.getEngine().load("https://www.google.com/maps/search/?api=1&query="+bnb.getLatitude()+","+bnb.getLongitude());
        });

        //GROUP 1
        contentPanel.add(makeLabel("  "+bnb.getName()+" (ID: "+bnb.getId()+")"));
        contentPanel.add(makeLabel("Price: £"+bnb.getPrice())); //TODO Price per what?
        //GROUP 2
        contentPanel.add(makeLabel("  Property type: "+bnb.getRoom_type()));
        contentPanel.add(makeLabel("Neighborhood: "+bnb.getNeighbourhood()));
        //GROUP 3
        contentPanel.add(makeLabel("  Minimum stay: "+bnb.getMinimumNights()));
        contentPanel.add(makeLabel("Yearly availability: "+bnb.getAvailability365()+" days"));
        //GROUP 4
        contentPanel.add(bnb.getReviewsPerMonth() != -1?makeLabel("  Number of reviews: "+bnb.getNumberOfReviews()+" ("+bnb.getReviewsPerMonth()+" reviews/month)"):makeLabel("  No reviews"));
        contentPanel.add(makeLabel("Host name: "+bnb.getHost_name()+" (ID: "+bnb.getHost_id()+")"));

        southPanel.add(contentPanel);

        JButton back = new JButton("Back");
        back.addActionListener((e)->{
            frame.setVisible(false);
            table.displayBnbList();
        });
        frame.add(back,BorderLayout.AFTER_LAST_LINE);
        return frame.getContentPane();
    }

    /**
     * Makes a JLabel with predefined settings
     * @param text text to display
     * @return JLabel object
     */
    private JLabel makeLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Helvetica",Font.PLAIN,17));
        return label;
    }
}

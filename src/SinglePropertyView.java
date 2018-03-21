import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class SinglePropertyView {
    private AirbnbListing bnb;

    public SinglePropertyView(AirbnbListing bnb){
        this.bnb = bnb;
    }

    public void showProperty(BnbTable table){
        JFrame frame = new JFrame(bnb.getName());
        JFXPanel mapPanel = new JFXPanel();
        JPanel contentPanel = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new BorderLayout());

        frame.add(mapPanel,BorderLayout.PAGE_START);
        System.out.println(bnb.getLatitude());
        Platform.runLater(()->{
            WebView webView = new WebView();
            mapPanel.setScene(new Scene(webView));
            System.out.println("Loading maps");
            webView.getEngine().load("https://www.google.com/maps/search/?api=1&query="+bnb.getLatitude()+","+bnb.getLongitude());
            System.out.println("Maps loaded");
        });
        //  panel.setSize(frame.getWidth(),frame.getHeight()/3);

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
        contentPanel.setLayout(new GridLayout(4,2,2,5));
        southPanel.add(contentPanel);
        frame.add(southPanel, BorderLayout.CENTER);
        frame.setResizable(false);
        contentPanel.setBackground(new Color(255,255,255));
        JButton back = new JButton("Back");
        back.addActionListener((e)->{
            frame.setVisible(false);
            table.displayBnbList();
        });
        back.setSize(30,10);
        frame.add(back,BorderLayout.AFTER_LAST_LINE);
        frame.pack();
        frame.setSize(1000,1000);
        frame.setVisible(true);
    }

    private JLabel makeLabel(String text){
        JLabel label = new JLabel(text);
        label.setFont(new Font("Helvetica",Font.PLAIN,20));
        return label;
    }
}

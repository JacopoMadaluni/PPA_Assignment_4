package PropertyFinder.Map;

import PropertyFinder.AirbnbListing;
import PropertyFinder.AppPanel;
import com.sun.javaws.exceptions.InvalidArgumentException;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * This class represents the London districts map.
 *
 * @author Jacopo Madaluni 1737569
 * @version 14-03-2018
 */
public class Map extends AppPanel
{
    private Image backgroundImage;
    private int prefW;
    private int prefH;
    private ArrayList<District> districts;
    private List<District> shownDistricts;
    private List<AirbnbListing> shownBnbs;
    private JButton iconModeButton;
    private JLabel mode;


    /**
     * Creates a new map.
     * @param title The title of the map.
     * @param bnbs The complete List of AirbnbListings in London.
     * @param lowPrice The minimum price chosen by the user.
     * @param maxPrice The maximum price chosen by the user.
     */
    public Map(String title, List<AirbnbListing> bnbs, int lowPrice, int maxPrice) {
        super(title, bnbs, lowPrice, maxPrice);
        districts = new ArrayList<>();
        shownDistricts = new ArrayList<>();
        shownBnbs = new ArrayList<>();

        District.reset();
        createMap();
        createButton();
        fetchBnbs(bnbs); // don't include the bnbs outside the price range.
        createDistricts(); // create the districts of london
        initializeDistricts(); // initilize the districts properties

        setPreferredSize(new Dimension(prefW, prefH));
    }

    /**
     * Internal method that creates the actual map.
     */
    private void createMap(){
        try {
            setLayout(null);
            File img = new File("resources/maps/london1000px.png");
            BufferedImage londonImage = ImageIO.read(img);

            prefW = londonImage.getWidth();
            prefH = londonImage.getHeight();
            backgroundImage = new ImageIcon("resources/maps/london1000px.png").getImage();
        }catch(IOException ex){
            System.out.println(ex);
        }

        mode = new JLabel("Currently scaling the images");
        mode.setFont(new Font(mode.getName(), Font.PLAIN, 18));
        mode.setBounds(170,15, 1000,20);

        add(mode);
    }

    /**
     * Internal method that creates the change mode button.
     */
    private void createButton(){
        iconModeButton = new JButton();
        iconModeButton.setBounds(0,0,150,30);
        iconModeButton.setLocation(10,10);
        iconModeButton.setText("Change mode");
        iconModeButton.addActionListener(e -> changeMode());
        add(iconModeButton);
    }

    /**
     * Changes the mode from scale to resources or the other way around.
     */
    private void changeMode(){
        if (District.scalingIcons()){
            District.setResMode();
            mode.setText("Currently choosing icons from resources");
        }else{
            District.setScaleMode();
            mode.setText("Currently scaling the images");
        }
        setDistrictsIcons();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, this);
        }
    }

    @Override
    public int getWidth(){
        return prefW;
    }

    @Override
    public int getHeight(){
        return prefH;
    }


    /**
     * Internal method that creates and adds all the districts with the relative X and Y
     * positions on the map.
     */
    private void createDistricts(){
        districts.add(new District("Kingston upon Thames", 280,520));
        districts.add(new District("Croydon",  480,540));
        districts.add(new District("Bromley",  640,588));
        districts.add(new District("Hounslow", 140 ,420));
        districts.add(new District("Ealing", 200 ,280));
        districts.add(new District("Havering", 800 ,180));
        districts.add(new District("Hillingdon",  100, 200));
        districts.add(new District("Harrow",  200,180));
        districts.add(new District("Brent", 270 ,220));
        districts.add(new District("Barnet", 350 ,160));
        districts.add(new District("Enfield", 500 ,110));
        districts.add(new District("Waltham Forest", 576 ,150));
        districts.add(new District("Redbridge", 670 ,210));
        districts.add(new District("Sutton", 400 ,585));
        districts.add(new District("Lambeth", 460 ,420));
        districts.add(new District("Southwark", 510 ,370));
        districts.add(new District("Lewisham", 570 ,440));
        districts.add(new District("Greenwich", 650 ,405));
        districts.add(new District("Bexley", 750 ,350));
        districts.add(new District("Richmond upon Thames", 220 ,455));
        districts.add(new District("Merton", 346 ,475));
        districts.add(new District("Wandsworth", 350 ,415));
        districts.add(new District("Hammersmith and Fulham", 340 ,310));
        districts.add(new District("Kensington and Chelsea", 400 ,350));
        districts.add(new District("City of London", 497 ,311));
        districts.add(new District("Westminster", 410 ,300));
        districts.add(new District("Camden", 410 ,240));
        districts.add(new District("Tower Hamlets", 555 ,290));
        districts.add(new District("Islington", 470 ,230));
        districts.add(new District("Hackney", 530 ,230));
        districts.add(new District("Haringey", 440 ,180));
        districts.add(new District("Newham", 645 ,300));
        districts.add(new District("Barking and Dagenham", 730 ,280));
    }

    /**
     * Initializes the districts.
     * 1) The method assigns every property to the correct district.
     * 2) Every district is put in the sorted Array based on the number of properties.
     * 3) Calls the setDistrictsIcons() method.
     */
    private void initializeDistricts(){
        // assign the bnbs to the correct district
        for (AirbnbListing bnb : shownBnbs){
            String district = bnb.getNeighbourhood();
            for (District d : districts){
                if (d.getName().equals(district)){
                    d.addBnb(bnb);
                    break;
                }
            }
        }
        for (District d : districts){
            d.putInList();
            if (d.getNumberOfBnbs() > 0){
                shownDistricts.add(d);
                add(d);
            }
        }
        setDistrictsIcons();
    }

    /**
     * Set the correct icon for each district.
     */
    private void setDistrictsIcons(){
        for (District district : shownDistricts){
            try {
                district.setCorrectIcon();
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }

    /**
     * Fetches all the Air bnbs and removes all the objects out of the price
     * range chosen by the user.
     * @param bnbs
     * @return
     */
    private void fetchBnbs(List<AirbnbListing> bnbs){
        for (AirbnbListing bnb : bnbs){
            if (bnb.getPrice() >= lowPrice && bnb.getPrice() <= highPrice){
                shownBnbs.add(bnb);
            }
        }
    }

    /**
     * @return The District shown in the map.
     */
    public List<District> getShownDistricts(){
        return shownDistricts;
    }

    /**
     * @return The properties shown in the map.
     */
    public List<AirbnbListing> getShownBnbs(){
        return shownBnbs;
    }

}

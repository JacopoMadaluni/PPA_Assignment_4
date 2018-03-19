import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * This class represents the London districts map.
 * !!! Resources not yet uploaded. This class is still working on local resources !!!
 *
 * @author Jacopo Madaluni
 * @version 14-03-2018
 */
public class Map extends AppPanel
{
    private Image backgroundImage;
    private BufferedImage icon;
    private static final String path = "C:\\Users\\Jaco\\Desktop\\Assignment 4 temp\\london-borough-map.png";
    //private static final String iconPath = "C:\\Users\\Jaco\\Desktop\\Assignment 4 temp\\Rent_Home-512_Icon1.png";
    private int prefW;
    private int prefH;
    private ArrayList<District> districts;
    private List<District> shownDistricts;

    private List<AirbnbListing> allBnbs;
    private List<AirbnbListing> shownBnbs;

    public Map(String title, List<AirbnbListing> bnbs, int lowPrice, int maxPrice) throws IOException {
        super(title, bnbs, lowPrice, maxPrice);
        districts = new ArrayList<>();
        shownDistricts = new ArrayList<>();
        shownBnbs = new ArrayList<>();

        District.reset();
        createMap();
        fetchBnbs(bnbs);
        createDistricts();
        initializeDistricts();

    }
    private void createMap() throws IOException{
        //URL imgUrl = new URL(path);
        setLayout(null);
        File img = new File("resources/maps/london1000px.png");
        //File iconFile = new File(iconPath);
        BufferedImage londonImage = ImageIO.read(img);
        //icon = ImageIO.read(iconFile);

        prefW = londonImage.getWidth();
        prefH = londonImage.getHeight();
        //backgroundImage = new BufferedImage(prefW, prefH,
        //        BufferedImage.TYPE_INT_ARGB);

        backgroundImage = new ImageIcon("resources/maps/london1000px.png").getImage();


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


    private void createDistricts(){
        districts.add(new District("Kingston upon Thames", "medium", 280,520));
        districts.add(new District("Croydon", "big", 480,540));
        districts.add(new District("Bromley", "big", 640,588));
        districts.add(new District("Hounslow", "medium",140 ,420));
        districts.add(new District("Ealing", "big",200 ,280));
        districts.add(new District("Havering", "big",800 ,180));
        districts.add(new District("Hillingdon", "big", 100, 200));
        districts.add(new District("Harrow", "big", 200,180));
        districts.add(new District("Brent", "medium",270 ,220));
        districts.add(new District("Barnet", "big",350 ,160));
        districts.add(new District("Enfield", "big",500 ,110));
        districts.add(new District("Waltham Forest", "medium",576 ,150));
        districts.add(new District("Redbridge", "medium",670 ,210));
        districts.add(new District("Sutton", "medium",400 ,585));
        districts.add(new District("Lambeth", "medium",460 ,420));
        districts.add(new District("Southwark", "small",510 ,370));
        districts.add(new District("Lewisham", "medium",570 ,440));
        districts.add(new District("Greenwich", "medium",650 ,405));
        districts.add(new District("Bexley", "medium",750 ,350));
        districts.add(new District("Richmond upon Thames", "medium",220 ,455));
        districts.add(new District("Merton", "medium",346 ,475));
        districts.add(new District("Wandsworth", "small",350 ,415));
        districts.add(new District("Hammersmith and Fulham", "small",345 ,310));
        districts.add(new District("Kensington and Chelsea", "small",410 ,350));
        districts.add(new District("City of London", "small",497 ,311));
        districts.add(new District("Westminster", "small",410 ,290));
        districts.add(new District("Camden", "small",410 ,230));
        districts.add(new District("Tower Hamlets", "small",560 ,285));
        districts.add(new District("Islington", "small",470 ,230));
        districts.add(new District("Hackney", "small",530 ,230));
        districts.add(new District("Haringey", "small",440 ,180));
        districts.add(new District("Newham", "small",645 ,300));
        districts.add(new District("Barking and Dagenham", "medium",730 ,280));
    }

    /**
     * Initializes the districts to contain only
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

    private void setDistrictsIcons(){
        for (District district : shownDistricts){
            try {
                district.setCorrectIcon();
            }catch(IOException e){
                System.out.println(e);
            }
        }
    }


}

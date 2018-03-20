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
 * !!! Resources not yet uploaded. This class is still working on local resources !!!
 *
 * @author Jacopo Madaluni
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

    public Map(String title, List<AirbnbListing> bnbs, int lowPrice, int maxPrice) throws IOException {
        super(title, bnbs, lowPrice, maxPrice);
        districts = new ArrayList<>();
        shownDistricts = new ArrayList<>();
        shownBnbs = new ArrayList<>();

        District.reset();
        createMap();
        fetchBnbs(bnbs); // don't include the bnbs outside the price range.
        createDistricts(); // create the districts of london
        initializeDistricts(); // initilize the districts properties

        setPreferredSize(new Dimension(prefW, prefH));

    }
    private void createMap() throws IOException{
        setLayout(null);
        File img = new File("resources/maps/london1000px.png");
        BufferedImage londonImage = ImageIO.read(img);

        prefW = londonImage.getWidth();
        prefH = londonImage.getHeight();

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
        districts.add(new District("Hammersmith and Fulham", 345 ,310));
        districts.add(new District("Kensington and Chelsea", 400 ,350));
        districts.add(new District("City of London", 497 ,311));
        districts.add(new District("Westminster", 410 ,300));
        districts.add(new District("Camden", 410 ,230));
        districts.add(new District("Tower Hamlets", 555 ,290));
        districts.add(new District("Islington", 470 ,230));
        districts.add(new District("Hackney", 530 ,230));
        districts.add(new District("Haringey", 440 ,180));
        districts.add(new District("Newham", 645 ,300));
        districts.add(new District("Barking and Dagenham", 730 ,280));
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

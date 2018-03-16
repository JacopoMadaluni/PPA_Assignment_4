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
        createMap();
        initializeIcons();
        // createDistricts();
        // initializeDistricts();

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


    public void initializeIcons() throws IOException{
        createDistricts();
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
        addAll();
    }

    private void addAll(){
        for (District d : districts){
            add(d);
        }
    }

}

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import javax.imageio.ImageIO;
import javax.swing.*;
/**
 * This class represents the London districts map.
 * !!! Resources not yet uploaded. This class is still working on local resources !!!
 *
 * @author Jacopo Madaluni
 * @version 14-03-2018
 */
public class Map extends JPanel
{
    private BufferedImage backgroundImage;
    private BufferedImage icon;
    private static final String path = "C:\\Users\\Jaco\\Desktop\\Assignment 4 temp\\london-borough-map.png";
    private static final String iconPath = "C:\\Users\\Jaco\\Desktop\\Assignment 4 temp\\Rent_Home-512_Icon1.png";
    private int prefW;
    private int prefH;
    private ArrayList<District> districts;

    public Map() throws IOException {
        districts = new ArrayList<>();
        createMap();
        // initializeDistricts();

    }
    private void createMap() throws IOException{
        //URL imgUrl = new URL(path);
        File img = new File(path);
        File iconFile = new File(iconPath);
        BufferedImage londonImage = ImageIO.read(img);
        icon = ImageIO.read(iconFile);

        prefW = londonImage.getWidth();
        prefH = londonImage.getHeight();
        backgroundImage = new BufferedImage(prefW, prefH,
                BufferedImage.TYPE_INT_ARGB);

        Graphics g = backgroundImage.getGraphics();
        g.drawImage(londonImage,0,0,this);
        g.dispose();

        //MouseAdapter mouseAdapter = new MouseAdapter();
        // ...
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
     * Test method to draw a graphic.
     * This will probably changed to be the method that draws
     * all the icons on the map. (working on it)
     * @param g
     */
    public void drawIcon(Graphics g){
        //Graphics toDraw = icon.getGraphics();
        g.drawImage(icon, 100,200,this);
    }
}

package PropertyFinder.Map;

import PropertyFinder.AirbnbListing;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * The district class represent a district area on the
 * London map.
 * @author Jacopo Madaluni 1737569
 */
public class District extends JLabel {

    private static LinkedList<District> orderedDistricts = new LinkedList<>();
    private static boolean scaleIcon = true;

    private int x;
    private int y;
    private int numberOfBnbs;
    private String name;
    private List<AirbnbListing> bnbs;
    private ImageIcon baseIcon;

    /**
     * @return True if the disctricts are in scale mode.
     */
    public static boolean getMode(){
        return scaleIcon;
    }

    /**
     * Set the Districts to scale the images to their right dimensions
     */
    public static void setLogMode(){
        scaleIcon = true;
    }

    /**
     * Set the Districts to choose the right icon from the source.
     */
    public static void setScaleMode(){
        scaleIcon = false;
    }
    public static void reset(){
        orderedDistricts = new LinkedList<>();
    }

    /**
     * Creates a new PropertyFinder.Map.District
     * @param name The name of the district.
     * @param x The absolute x on the map.
     * @param y The absolute y on the map.
     */
    public District(String name, int x, int y){
        super();
        //try {
        //    baseIcon = new ImageIcon(ImageIO.read(new File(getIconAddress())));
        //}catch (IOException ex){
        //    System.out.println(ex);
        //}
        baseIcon = null;
        setIcon(baseIcon);
        this.name = name;
        this.x = x;
        this.y = y;
        setForeground(Color.WHITE);
        setBounds(x,y,x,y);
        numberOfBnbs = 0;
        bnbs = new ArrayList<>();
        initialize();
        setSize(baseIcon.getIconWidth(), baseIcon.getIconHeight());
    }



    public void addBnb(AirbnbListing bnb){
        bnbs.add(bnb);
        numberOfBnbs++;
    }


    public int getNumberOfBnbs() {
        return numberOfBnbs;
    }
    public List<AirbnbListing> getBnbs(){
        return bnbs;
    }
    public String getName(){
        return name;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }



    private void initialize() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                //super.mouseEntered(e);
                mouseEnter();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //super.mouseExited(e);
                mouseExit();
            }
            @Override
            public void mouseClicked(MouseEvent e){
                mouseEnter();
                openTableWindow();
            }
            @Override
            public void mousePressed(MouseEvent e){
                mousePress();
            }
        });
        try {
            fix();
        }catch(IOException ex){
            System.out.println(ex);
        }
    }

    /**
     * This method opens a the window with all the properties in the district when
     * the icon is clicked.
     */
    private void openTableWindow(){
        new BnbTable(this);
    }

    /**
     * This method return the correct icon address from resources when the district is
     * not is scale mode.
     */
    private String getIconAddress(){
        for (int i = 0; i< orderedDistricts.size() ; i++){
            if (orderedDistricts.get(i).equals(this)){
                return "resources/district_icons/sized_icons/icon_" + i +".png";
            }
        }
        if (orderedDistricts.size() == 33) {
            // Didnt find the district that should be there
            System.err.println("Error: PropertyFinder.Map.District not found in the list");
        }
        return "resources/district_icons/sized_icons/icon_0.png";
    }


    private String getZoomedIconAddress(){
        return "resources/district_icons/zoomed_icon_medium.png";
    }

    private void fix() throws IOException{
        mouseEnter();
        setIcon(baseIcon);
        setText("");
    }

    /**
     * This method should be called by the map on every districts once all
     * the districts have been created and initialized.
     *
     * This method puts the district in the static list of distrcts in ascendent order,
     * based on the number of bnbs that are in this one.
     */
    public void putInList(){
        if (orderedDistricts.isEmpty()) {
            orderedDistricts.add(this);
            return;
        }

        for (int index = 0; index < orderedDistricts.size(); index++){
            District nextDistrict = orderedDistricts.get(index);
            if (numberOfBnbs <= nextDistrict.getNumberOfBnbs()){
                orderedDistricts.add(index, this);
                return;
            }
        }
        orderedDistricts.addLast(this);
    }


    public void setCorrectIcon() throws IOException{
        if (scaleIcon){
            resizeIcon();
        }else {
            selectIconFromSource();
        }
    }


    private void mousePress(){
        try{
            Image newImage = ImageIO.read(new File("resources/district_icons/icon_mouse_pressed.png"));
            baseIcon = new ImageIcon(newImage);
            setIcon(baseIcon);
        }catch(IOException ex){
            System.out.println(ex);
        }
    }


    private void mouseExit(){
        try{
            setCorrectIcon();
            setText("");
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    private void mouseEnter(){
        try {
            Image newImage = ImageIO.read(new File(getZoomedIconAddress()));
            baseIcon = new ImageIcon(newImage);
            setIcon (baseIcon);
            setText("" + numberOfBnbs);
            setHorizontalTextPosition(JLabel.CENTER);
            setFont(new Font("Serif", Font.PLAIN, 20));
        }catch(IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * First way of selecting the right size of the house icon.
     * getIconAddress chooses the right address based on the index
     * in the ordered list of districts.
     */
    private void selectIconFromSource() throws IOException{
        Image newImage = ImageIO.read(new File(getIconAddress()));
        baseIcon = new ImageIcon(newImage);
        setIcon(baseIcon);
    }

    /**
     * Second way of selecting the right size of the house icon.
     * Scale the base icon depending on the number of bnbs in the district.
     * @throws IOException
     */
    private void resizeIcon() throws  IOException{
        double p = scaleFunction(numberOfBnbs);
        if (p == -1){
            setIcon(null);
            return;
        }
        int scale = (int) (p * 6);
        if (scale < 20){
            scale = 20;
        }else if (scale > 55){
            scale = 55;
        }
        Image base = ImageIO.read(new File("resources/district_icons/zoomed_icon.png"));
        Image scaledBase = base.getScaledInstance(scale, scale,  java.awt.Image.SCALE_SMOOTH);
        baseIcon = new ImageIcon(scaledBase);
        setIcon(baseIcon);
    }

    private double scaleFunction(int x){
        if (x < 1){
            return -1;
        }else{
            return Math.log(x);
        }
    }
}

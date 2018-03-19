
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
 */
public class District extends JLabel {

    private static LinkedList<District> orderedDistricts = new LinkedList<>();

    private int x;
    private int y;
    private String size;
    private int numberOfBnbs;
    private String name;
    private List<AirbnbListing> bnbs;
    private String iconAddress;
    private ImageIcon baseIcon;


    /**
     * Creates a new District
     * @param name The name of the district.
     * @param size (Removed soon)
     * @param x The absolute x on the map.
     * @param y The absolute y on the map.
     */
    public District(String name, String size, int x, int y){
        super();
        iconAddress = getIconAddress();
        try {
            baseIcon = new ImageIcon(ImageIO.read(new File(iconAddress)));
        }catch (IOException ex){
            System.out.println(ex);
        }
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
                try {
                    resizeImage();
                }catch(IOException ex) {
                    System.out.println(ex);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //super.mouseExited(e);
                try{
                    //ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(iconAddress)));
                    baseIcon = new ImageIcon(ImageIO.read(new File(getIconAddress())));
                    setIcon(baseIcon);
                    setText("");
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.println("works");
            }
        });
        try {
            fix();
        }catch(IOException ex){
            System.out.println(ex);
        }
    }

    private String getIconAddress(){
        for (int i = 0; i< orderedDistricts.size() ; i++){
            if (orderedDistricts.get(i).equals(this)){
                return "resources/district_icons/sized_icons/icon_" + i +".png";
            }
        }
        System.out.println("Error: District not found in the list");
        return "resources/district_icons/sized_icons/icon_0.png";
    }


    private String getZoomedIconAddress(){
        return "resources/district_icons/zoomed_icon_medium.png";
    }

    private void fix() throws IOException{
        resizeImage();
        //ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(iconAddress)));
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
        Image newImage = ImageIO.read(new File(getIconAddress()));
        baseIcon = new ImageIcon(newImage);
        setIcon(baseIcon);
    }



    private void resizeImage() throws IOException{
        //Image image = baseIcon.getImage();
        Image newImage = ImageIO.read(new File(getZoomedIconAddress()));
        //Image newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        baseIcon = new ImageIcon(newImage);
        //setSize(baseIcon.getIconWidth(), baseIcon.getIconHeight());
        setIcon(baseIcon);
        setText("" + numberOfBnbs);
        setHorizontalTextPosition(JLabel.CENTER);
        setFont(new Font("Serif", Font.PLAIN, 20));

    }
}

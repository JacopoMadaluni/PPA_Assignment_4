
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The district class represent a district area on the
 * London map
 */
public class District extends JLabel {

    private int x;
    private int y;
    private String size;
    private int numberOfBnbs;
    private String name;
    private List<AirbnbListing> bnbs;
    private String iconAddress;
    private ImageIcon baseIcon;


    public District(String name, String size, int x, int y){
        super();
        iconAddress = getIconAddress(size);
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

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public void resizeImage() throws IOException{
        Image image = baseIcon.getImage();
        Image newImage = ImageIO.read(new File(getZoomedIconAddress()));
        //Image newImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        baseIcon = new ImageIcon(newImage);
        setSize(baseIcon.getIconWidth(), baseIcon.getIconHeight());
        setIcon(baseIcon);

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
                try {
                    ImageIcon imageIcon = new ImageIcon(ImageIO.read(new File(iconAddress)));
                    setIcon(imageIcon);
                }catch(Exception ex){
                    System.out.println(ex);
                }
            }
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.println("works");
            }
        });

    }

    private String getIconAddress(String size){
        String iconAddress = "resources/district_icons/base_icon";
        switch (size){
            case "big":
                return iconAddress + ".png";
            case "medium":
                return iconAddress + "_medium.png";
            default:
                return iconAddress + ".png";
        }
    }

    private String getZoomedIconAddress(){
        return "resources/district_icons/zoomed_icon.png";
    }

}

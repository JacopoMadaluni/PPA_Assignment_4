
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.lang.management.BufferPoolMXBean;
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
    private int numberOfBnbs;
    private String name;
    private List<AirbnbListing> bnbs;
    private ImageIcon baseIcon;


    /**
     * Creates a new District
     * @param name The name of the district.
     * @param x The absolute x on the map.
     * @param y The absolute y on the map.
     */
    public District(String name, int x, int y){
        super();
        try {
            baseIcon = new ImageIcon(ImageIO.read(new File(getIconAddress())));
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

    public static void reset(){
        orderedDistricts = new LinkedList<>();
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
                mouseEnter();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //super.mouseExited(e);
                mouseExit();
            }
            @Override
            public void mouseClicked(MouseEvent e){
                    displayBnbList();
            }
            @Override
            public void mousePressed(MouseEvent e){
                mousePress();
            }
            @Override
            public void mouseReleased(MouseEvent e){
                mouseEnter();
            }
        });



        try {
            fix();
        }catch(IOException ex){
            System.out.println(ex);
        }
    }

    private void displayBnbList(){
        JFrame frame = new JFrame("Airbnb's in "+name);
      //  Container content = frame.getContentPane();

        JList airbnbList = new JList(bnbs.toArray());
        airbnbList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        airbnbList.setLayoutOrientation(JList.VERTICAL);

       // JTable propertiesTable = makeTable();
      /*  JLabel label = new JLabel(name);
        content.add(label);*/
      JScrollPane scrollPane = new JScrollPane(makeTable());
     // scrollPane.setPreferredSize(new Dimension(300,300));
      frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);

    }
    private AirbnbListing getPropertyByName(String name){
        for (AirbnbListing bnb: bnbs){
            if (bnb.getName().equals(name))
                return bnb;
        }
        return null;
    }

    private JTable makeTable(){
        String [] columns = {"Name","Price","Room type","Reviews"};
        Object[][] data = gatherData(columns);
        JTable table = new JTable(data,columns);
        table.setName("Properties in "+name);
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //If a row is clicked, it will expand to show more information specific to that Airbnb
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                AirbnbListing bnb =getPropertyByName((String)table.getValueAt(table.rowAtPoint(e.getPoint()),0));
                showProperty(bnb);

            }
        });
        //Sets the table as not editable and sets the correct sorting parameters
        DefaultTableModel tableModel = new DefaultTableModel(data,columns){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
            public Class getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return String.class;
                    case 1:
                        return Integer.class;
                    case 2:
                        return String.class;
                    default:
                        return Integer.class;
                }
            }
        };
        table.setModel(tableModel);
        return table;
    }
    public Object [][] gatherData(String[] columns){
        Object [][] data = new Object[bnbs.size()][columns.length];
        for (int property = 0; property<bnbs.size();property++){
            AirbnbListing bnb = bnbs.get(property);
            data[property] = new Object[]{bnb.getName(),bnb.getPrice(),bnb.getRoom_type(),bnb.getNumberOfReviews()};
        }
       return data;
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
    private void showProperty(AirbnbListing bnb){
        JFrame frame = new JFrame(bnb.getName());
        JFXPanel mapPanel = new JFXPanel();
        JPanel contentPanel = new JPanel(new BorderLayout());
        frame.add(mapPanel,BorderLayout.PAGE_START);

        Platform.runLater(()->{
            WebView webView = new WebView();
            mapPanel.setScene(new Scene(webView));
            webView.getEngine().load("https://www.google.com/maps/search/?api=1&query="+bnb.getLatitude()+","+bnb.getLongitude());
        });
      //  panel.setSize(frame.getWidth(),frame.getHeight()/3);
        JLabel title = new JLabel(bnb.getName());
        contentPanel.add(title);
        JLabel price = new JLabel("£"+bnb.getPrice());
        contentPanel.add(price);
        contentPanel.add(new JLabel(""+bnb.getAvailability365()));
        contentPanel.add(new JLabel("Reviews "+bnb.getNumberOfReviews()));
        contentPanel.add(new JLabel(""+bnb.getMinimumNights()));
        contentPanel.add(new JLabel(bnb.getNeighbourhood()));
        contentPanel.add(new JLabel(bnb.getRoom_type()));
        contentPanel.add(new JLabel(""+bnb.getReviewsPerMonth()));
        contentPanel.add(new JLabel(bnb.getHost_name()));
        contentPanel.add(new JLabel(bnb.getHost_id()));
        contentPanel.add(new JLabel(bnb.getId()));
        frame.add(contentPanel, BorderLayout.SOUTH);
          frame.pack();
        frame.setSize(1000,1000);

        frame.setVisible(true);

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
        Image newImage = ImageIO.read(new File(getIconAddress()));
        baseIcon = new ImageIcon(newImage);
        setIcon(baseIcon);
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
            baseIcon = new ImageIcon(ImageIO.read(new File(getIconAddress())));
            setIcon(baseIcon);
            setText("");
        }catch(Exception ex){
            System.out.println(ex);
        }
    }

    private void mouseEnter(){
        try {
            Image newImage = ImageIO.read(new File(getZoomedIconAddress()));
            baseIcon = new ImageIcon(newImage);
            setIcon(baseIcon);
            setText("" + numberOfBnbs);
            setHorizontalTextPosition(JLabel.CENTER);
            setFont(new Font("Serif", Font.PLAIN, 20));
        }catch(IOException ex) {
            System.out.println(ex);
        }
    }
}

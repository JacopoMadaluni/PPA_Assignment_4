package PropertyFinder.Map;

import PropertyFinder.AirbnbListing;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * BnbTable Class. This class creates a table with basic
 * information about a list of Airbnb's given.
 */
public class BnbTable {
    protected List<AirbnbListing> bnbs;
    protected JFrame frame;
    protected String name;

    /**
     * Creates a BnbTable object specific to a district
     * @param district District of the properties
     */
    public BnbTable(District district){
        this.bnbs = district.getBnbs();
        this.name = "Airbnb's in "+district.getName();
        this.displayBnbList();
    }

    /**
     * Creates a BnbTable object specific to a list of
     * properties
     * @param bnbs List of Airbnb's
     */
    public BnbTable(List<AirbnbListing> bnbs){
        this.bnbs = bnbs;
        this.name = "My List";
        this.displayBnbList();
    }

    /**
     * Displays the table of properties.
     */
    public void displayBnbList(){
        frame = new JFrame(name);
        JScrollPane scrollPane = new JScrollPane(makeTable());
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Creates the table with all the information
     * @return Table
     */
    private JTable makeTable(){
        String [] columns = {"Name","Price","Room type","Reviews"};
        Object[][] data = gatherData(columns);
        JTable table = new JTable(data,columns);
        table.setName(name);

        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //If a row is clicked, it will expand to show more information specific to that Airbnb
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AirbnbListing bnb = getPropertyByName((String)table.getValueAt(table.rowAtPoint(e.getPoint()),0));
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
        table.setRowSorter(new TableRowSorter<>(tableModel));
        table.getRowSorter().toggleSortOrder(0);
        return table;
    }

    /**
     * Closes the window and displays a
     * SinglePropertyView specific to an Airbnb
     * @param bnb AirBnb
     */
    protected void showProperty(AirbnbListing bnb){
        frame.dispose();
        new SinglePropertyView(bnb,this).showProperty();
    }

    /**
     * Gathers all the information from the AirBnb's
     * @param columns Columns of the table
     * @return Data to populate the Table with
     */
    protected Object [][] gatherData(String[] columns){
        Object [][] data = new Object[bnbs.size()][columns.length];
        for (int property = 0; property<bnbs.size();property++){
            AirbnbListing bnb = bnbs.get(property);
            data[property] = new Object[]{bnb.getName(),bnb.getPrice(),bnb.getRoom_type(),bnb.getNumberOfReviews()};
        }
        return data;
    }

    /**
     * Gets an AirBnb with the given name
     * @param name Name of the property
     * @return Property with the given name
     */
    protected AirbnbListing getPropertyByName(String name){
        for (AirbnbListing bnb: bnbs){
            if (bnb.getName().equals(name))
                return bnb;
        }
        return null;
    }
}

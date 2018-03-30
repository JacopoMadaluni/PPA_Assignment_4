package PropertyFinder.Map;

import PropertyFinder.AirbnbListing;
import PropertyFinder.CriminalInfoLoader;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * This class creates a table with extended information given a list of
 * properties.
 * @author Alvaro Rausell
 * @version 28/03/2018
 */
public class ComparatorTable extends BnbTable {
    public ComparatorTable(List<AirbnbListing> bnbs){
        super(bnbs);
        frame.setVisible(false);
    }
    /**
     * Creates the table with all the information
     * @return Table
     */
    public JTable makeTable(){
        String [] columns = {"Name","Price","Room type","Reviews","Neighborhood","Crime rate per 1000 citizens","Availability per year"};
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
                    case 3:
                        return Integer.class;
                    case 4:
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
     * Gathers all the information from the AirBnb's
     * @param columns Columns of the table
     * @return Data to populate the Table with
     */
    protected Object [][] gatherData(String[] columns) {
        Object [][] data = new Object[bnbs.size()][columns.length];
        for (int property = 0; property<bnbs.size();property++) {
            AirbnbListing bnb = bnbs.get(property);
            try {
                data[property] = new Object[]{bnb.getName(), bnb.getPrice(), bnb.getRoom_type(), bnb.getNumberOfReviews(), bnb.getNeighbourhood(), CriminalInfoLoader.getBoroughCrimeRate(bnb.getNeighbourhood()), bnb.getAvailability365()};
            } catch (IOException | URISyntaxException e){
                e.printStackTrace();
            }
        }
        return data;
    }
}

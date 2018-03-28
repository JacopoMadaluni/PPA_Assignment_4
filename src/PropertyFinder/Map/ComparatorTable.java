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
import java.util.ArrayList;

public class ComparatorTable extends BnbTable {
    public ComparatorTable(ArrayList<AirbnbListing> bnbs){
        super(bnbs);
        frame.setVisible(false);
    }
    public JTable makeTable() throws IOException, URISyntaxException {
        String [] columns = {"Name","Price","Room type","Reviews","Neighborhood","Crime rate per 1000","Availability per year"};
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
    protected Object [][] gatherData(String[] columns) throws IOException, URISyntaxException {
        Object [][] data = new Object[bnbs.size()][columns.length];
        for (int property = 0; property<bnbs.size();property++){
            AirbnbListing bnb = bnbs.get(property);
            data[property] = new Object[]{bnb.getName(),bnb.getPrice(),bnb.getRoom_type(),bnb.getNumberOfReviews(),bnb.getNeighbourhood(), CriminalInfoLoader.getBoroughCrimeRate(bnb.getNeighbourhood()),bnb.getAvailability365()};
        }
        return data;
    }


}

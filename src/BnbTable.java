import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.web.WebView;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class BnbTable {
    private District district;
    private ArrayList<AirbnbListing> bnbs;

    public BnbTable(District district,ArrayList<AirbnbListing> bnbs){
        this.district = district;
        this.bnbs = bnbs;
    }
    private Object [][] gatherData(String[] columns){
        Object [][] data = new Object[bnbs.size()][columns.length];
        for (int property = 0; property<bnbs.size();property++){
            AirbnbListing bnb = bnbs.get(property);
            data[property] = new Object[]{bnb.getName(),bnb.getPrice(),bnb.getRoom_type(),bnb.getNumberOfReviews()};
        }
        return data;
    }
    private JTable makeTable(){
        String [] columns = {"Name","Price","Room type","Reviews"};
        Object[][] data = gatherData(columns);
        JTable table = new JTable(data,columns);
        table.setName("Properties in "+district.getName());
        table.setAutoCreateRowSorter(true);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        //If a row is clicked, it will expand to show more information specific to that Airbnb
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

                AirbnbListing bnb =getPropertyByName((String)table.getValueAt(table.rowAtPoint(e.getPoint()),0));
                new SinglePropertyView(bnb).showProperty();

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
    private AirbnbListing getPropertyByName(String name){
        for (AirbnbListing bnb: bnbs){
            if (bnb.getName().equals(name))
                return bnb;
        }
        return null;
    }


    public void displayBnbList(){
        JFrame frame = new JFrame("Airbnb's in "+district.getName());
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

}

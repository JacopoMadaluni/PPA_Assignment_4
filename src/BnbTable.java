import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class BnbTable {
    private District district;
    private List<AirbnbListing> bnbs;
    private JFrame frame;
    public BnbTable(District district){
        this.district = district;
        this.bnbs = district.getBnbs();
        this.displayBnbList();
    }

    public void displayBnbList(){
        frame = new JFrame("Airbnb's in "+district.getName());
        JScrollPane scrollPane = new JScrollPane(makeTable());
        frame.setContentPane(scrollPane);
        frame.pack();
        frame.setVisible(true);
    }

    private JTable makeTable(){
        String [] columns = {"Name","Price","Room type","Reviews"};
        Object[][] data = gatherData(columns);
        JTable table = new JTable(data,columns);
        table.setName("Properties in "+district.getName());

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
    private void showProperty(AirbnbListing bnb){
        frame.dispose();
        new SinglePropertyView(bnb,this).showProperty();
    }
    private Object [][] gatherData(String[] columns){
        Object [][] data = new Object[bnbs.size()][columns.length];
        for (int property = 0; property<bnbs.size();property++){
            AirbnbListing bnb = bnbs.get(property);
            data[property] = new Object[]{bnb.getName(),bnb.getPrice(),bnb.getRoom_type(),bnb.getNumberOfReviews()};
        }
        return data;
    }

    private AirbnbListing getPropertyByName(String name){
        for (AirbnbListing bnb: bnbs){
            if (bnb.getName().equals(name))
                return bnb;
        }
        return null;
    }
}

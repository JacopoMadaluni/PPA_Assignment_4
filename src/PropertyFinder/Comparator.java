package PropertyFinder;
import PropertyFinder.Map.ComparatorTable;
import javax.swing.*;
import java.awt.*;

/**
 * Comparator class for the property finder.
 * This will create a panel that shows a
 * comparative table of the properties saved by the user.
 *
 * @author Alvaro Rausell
 * @version 27/03/2018
 */
public class Comparator extends AppPanel {
    private MainWindow mainWindow;
    /**
     *Creates the comparator panel.
     * Requires a MainWindow to access MyList and refresh the page.
     * @param mainWindow main window
     */
    public Comparator(MainWindow mainWindow){
        //Creates the panel
        super("Comparator",MainWindow.getMyList(),0,0);
        this.mainWindow = mainWindow;
        this.listings = mainWindow.getMyList();
        //Sets the elements of the page
        this.setPage();
    }
    private void setPage(){
        JTextArea status = new JTextArea(listings.isEmpty()? "No properties in My List, please add them in the" +
                " single property view.\nIf you have done so already, press the 'refresh button'":"You have "+listings.size()+(listings.size() > 1?" properties":" property"));
        status.setFont(new Font("Arial",Font.PLAIN,18));
        JButton refresh = new JButton("Refresh");
        //Refreshes the page
        refresh.addActionListener((e)->{
            System.out.println("Done");
            mainWindow.highPriceClicked();
        });
        JButton compare = new JButton("Compare properties");
        //Invisible while there are no properties in MyList
        compare.setVisible(!listings.isEmpty());
        //Creates the comparison table
        compare.addActionListener((e)->{
            try {
                JScrollPane comparison = new JScrollPane(new ComparatorTable(listings).makeTable());
                add(comparison);
                compare.setVisible(false);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        compare.setSize(100,25);
        JPanel buttons = new JPanel();
        buttons.setBackground(Color.WHITE);
        buttons.add(compare);
        buttons.add(refresh);
        add(buttons,BorderLayout.AFTER_LAST_LINE);
        add(status,BorderLayout.PAGE_START);
    }



}

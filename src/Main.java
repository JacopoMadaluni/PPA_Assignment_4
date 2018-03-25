import java.util.ArrayList;

public class Main {
    private static ArrayList<AirbnbListing> myList;
    public static void main(String[] args) {
        myList = new ArrayList<>();
        MainWindow wnd = new MainWindow();
    }
    public static ArrayList<AirbnbListing> getMyList(){
        return myList;
    }
}
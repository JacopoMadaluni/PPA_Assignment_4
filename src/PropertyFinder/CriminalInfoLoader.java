package PropertyFinder;

import com.opencsv.CSVReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Tool for loading the information from the crime-rates.csv file.
 */
public class CriminalInfoLoader {

    /**
     * Gets the crime rate for a given borough
     * @param borough
     * @return
     * @throws IOException
     * @throws URISyntaxException
     */
    public static double getBoroughCrimeRate(String borough) throws IOException, URISyntaxException {
        double rate = 0;
        URL url = new File("resources/datasets/crime-rates.csv").toURI().toURL();
        CSVReader reader = new CSVReader(new FileReader(new File(url.toURI()).getAbsolutePath()));
        String [] line;
        //skip the first row (column headers)
       reader.readNext();

        int years = 0;
        while ((line = reader.readNext())!=null && line[3].equals("All recorded offences")){
            if (line[1].equals(borough)){
                rate += Double.parseDouble(line[4]);
                years++;
            }
        }
        return years != 0? toDecPlaces(rate/years,2): Double.NaN;
    }

    /**
     * Simple method to round a double to a given
     * number of decimal places
     * @param number number to round
     * @param places number of decimal places
     * @return
     */
    private static Double toDecPlaces(Double number,int places) {
        int rounded = (int) (number *  Math.pow(10,places));
        number = (rounded * 1.0) / Math.pow(10,places);
        return number;
    }
}

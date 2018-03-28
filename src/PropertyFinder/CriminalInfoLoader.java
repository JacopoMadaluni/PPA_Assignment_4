package PropertyFinder;

import com.opencsv.CSVReader;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class CriminalInfoLoader {

    public static double getBoroughCrimeRate(String borough) throws IOException, URISyntaxException {
        double rate = 0;
        URL url = new File("resources/datasets/airbnb-london.csv").toURI().toURL();
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
        return years != 0? rate/years: Double.NaN;
    }
}

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Generates a csv file containing all the informations about the classes and methods of the java file.
 *
 * @author : Maxime Lechasseur
 * @author : Han Zhang
 */
public class CSVGenerator {

    /**
     * This method generates a csv file containing all the informations about the classes and methods of the java file.
     *
     * @param type choice between classes and methods
     * @param list list of the data
     */
    public void generateCSV(String type, ArrayList<ArrayList<String>> list) {

        //creating the csv file
        File csvFile = new File(type + ".csv");

        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile))) {

            //headers of the csv file depending on the type chosen
            if (type.equals("classes")) {
                csvWriter.append("chemin, class, classe_LOC, classe_CLOC, classe_DC, WMC, classe_BC");
            } else if (type.equals("methods")) {
                csvWriter.append("chemin, class, methode, methode_LOC,  methode_CLOC, methode_DC, CC, methode_BC");
            }
            csvWriter.append('\n');

            //adding all the data from the list to the csv
            for (ArrayList<String> line : list) {
                for (String data : line) {
                    csvWriter.append(data);
                    csvWriter.append(',');
                }
                csvWriter.append('\n');
            }

            csvWriter.close();
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

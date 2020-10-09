import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVGenerator {

    /**
     * This method generates a csv file containing all the informations about the classes and methods of the java file.
     * @param type
     * @param list
     * @return void
     */
    public void generateCSV(String type, ArrayList<ArrayList<String>> list) {
        File csvFile = new File(type + ".csv");
        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile))) {
            if (type.equals("classes")) {
                csvWriter.append("chemin, class, classe_LOC, classe_CLOC, classe_DC, WMC, classe_BC");
            } else if (type.equals("methods")) {
                csvWriter.append("chemin, class, method, method_LOC,  method_CLOC, method_DC, CC, methode_BC");
            }
            csvWriter.append('\n');
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

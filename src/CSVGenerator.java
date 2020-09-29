import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CSVGenerator {

    public void generateClassCSV(ArrayList<ArrayList<String>> list) {
        File csvFile = new File("classes.csv");
        try (PrintWriter csvWriter = new PrintWriter(new FileWriter(csvFile))) {
            csvWriter.append("chemin, class, classe_LOC, classe_CLOC, classe_DC");
            csvWriter.append('\n');
            for (ArrayList<String> line : list) {
                for (String data : line) {
                    csvWriter.append(data);
                    csvWriter.append(',');
                }
                csvWriter.append('\n');
            }
            csvWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

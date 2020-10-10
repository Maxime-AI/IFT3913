import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This program analyses java code and generates csv files containing different metrics such as LOC, CLOC and
 * cyclomatic complexity, with the classes and methods of the files in the source folder.
 *
 * @author Maxime Lechasseur
 * @author Han Zhang
 */
public class Main {
    public static void main(String[] args) throws Exception {
        FilesManager filesManager = new FilesManager();
        Parser parser = new Parser();
        CSVGenerator csvGenerator = new CSVGenerator();

        String folderName = "sourceFolder";

        //to prevent errors if the source folder doesn't exist
        if (!Files.exists(Path.of(folderName))) {
            Files.createDirectories(Paths.get(folderName));
            System.out.println("Created source folder.");
            return;
        }

        List<File> filesList = filesManager.getFiles(folderName);

        //generating the methods data and classes data
        ArrayList<ArrayList<String>> methodsData = parser.getMethodsData(filesList);
        ArrayList<ArrayList<String>> classesData = parser.getClassData(filesList);

        //putting the data in csv files
        csvGenerator.generateCSV("methods", methodsData);
        csvGenerator.generateCSV("classes", classesData);
    }
}





































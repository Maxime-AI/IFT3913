import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
*@author : Maxime Lechasseur
*@author : Han Zhang
*
 */
public class Main {
    public static void main(String[] args) throws Exception {
//        long start = System.currentTimeMillis();
        FilesManager filesManager = new FilesManager();
        Parser parser = new Parser();
        CSVGenerator csvGenerator = new CSVGenerator();
        List<File> filesList = filesManager.getFiles("sourceFolder");
        ArrayList<ArrayList<String>> methodsData = parser.getMethodsData(filesList);
        ArrayList<ArrayList<String>> classesData = parser.getClassData(filesList);
        csvGenerator.generateCSV("methods", methodsData);
        csvGenerator.generateCSV("classes", classesData);
//        long time = System.currentTimeMillis() - start;
//        System.out.println(time);
    }
}





































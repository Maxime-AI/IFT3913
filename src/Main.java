import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
//        long start = System.currentTimeMillis();

        FilesManager filesManager = new FilesManager();
        Parser parser = new Parser();
        CSVGenerator csvGenerator = new CSVGenerator();
        List<File> filesList = filesManager.getFiles("sourceFolder");
        ArrayList<ArrayList<String>> classesData = parser.getClassData(filesList);
        ArrayList<ArrayList<String>> methodsData = parser.getMethodsData(filesList);
       System.out.println(classesData);
        csvGenerator.generateMethodCSV(methodsData);
        System.out.println(methodsData);
       csvGenerator.generateClassCSV(classesData);
//       System.out.println(data.toArray().length);
//        long time = System.currentTimeMillis() - start;
//        System.out.println(time);
//        generateCSV(data);
        String x = "public void allo(String args, int x, String x) {".replace(" ", "");


    }
}





































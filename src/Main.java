import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
//        long start = System.currentTimeMillis();

        FilesManager filesManager = new FilesManager();
        Parser parser = new Parser();
        List<File> filesList = filesManager.getFiles("sourceFolder");
        ArrayList<ArrayList<String>> data = parser.getClassData(filesList);
        System.out.println(data);

//        System.out.println(data.toArray().length);
//        long time = System.currentTimeMillis() - start;
//        System.out.println(time);
//        generateCSV(data);

    }
}





































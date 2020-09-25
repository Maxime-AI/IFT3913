import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        String fichier = "";
        List<File> filesInFolder = Files.walk(Paths.get("codeSource"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        System.out.println(filesInFolder);

        /*File folder = new File("codeSource");
        String[] listeFichiers = folder.list();*/
        for(File path:filesInFolder) {
            File file = new File(String.valueOf(path));
            BufferedReader br = new BufferedReader(new FileReader(file));

            int x = 0;
            String st;
            while ((st = br.readLine()) != null){
                x++;
            }
            System.out.println(x);
            System.out.println(path);
        }

//        File file = new File("codeSource\\test.java");
//        BufferedReader br = new BufferedReader(new FileReader(file));
//
//        int x = 0;
//        String st;
//        while ((st = br.readLine()) != null){
//            x++;
//        }
//        System.out.println(x);
    }
}





































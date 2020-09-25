import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        List<File> filesInFolder = Files.walk(Paths.get("codeSource"))
                .filter(Files::isRegularFile)
                .map(Path::toFile)
                .collect(Collectors.toList());

        System.out.println(filesInFolder);

        for (File path:filesInFolder) {
            File file = new File(String.valueOf(path));
            BufferedReader br = new BufferedReader(new FileReader(file));

            int x = 0;
            String st;
            while ((st = br.readLine()) != null) {
                if ("".equals(st) ) {continue;}
                else if (st.contains("//")) {continue;}
                else if (st.contains("/*")) {continue;}
                else if (st.endsWith("*/")) {continue;}
                System.out.println(x);
                x++;
            }
            System.out.println(x);
            System.out.println(path);
        }
    }
}





































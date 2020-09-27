import java.io.*;
import java.nio.file.*;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws Exception {

        int loc = 0;
        int cloc = 0;
        int dc = 0;

        List<File> filesInFolder = Files.walk(Paths.get("codeSource"))
                .filter(p -> p.getFileName().toString().endsWith(".java"))
                .map(Path::toFile)
                .collect(Collectors.toList());

        for (File path : filesInFolder) {
            File file = new File(String.valueOf(path));
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                st = st.replaceAll(" ", "");
                if ("".equals(st) || st.startsWith("import")) {
                    continue;
                } else if (st.startsWith("//") || st.startsWith("*") || st.startsWith("/*") || st.startsWith("*/")
                        || st.endsWith("*/") || st.startsWith("/**")) {
                    cloc++;
                    continue;
                } else if (st.contains("//") || (st.contains("/*") && st.contains("*/"))) {
                    loc++;
                    cloc++;
                    continue;
                }
                loc++;
            }
            dc = (int) ((double) cloc / loc * 100);
        }
        System.out.println(dc);
        System.out.println(loc);
        System.out.println(cloc);
    }
    
}





































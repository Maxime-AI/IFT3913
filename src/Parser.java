import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private double classDC;
    private int classLOC = 0;
    private int classCLOC = 0;
    private double methodDC;
    private int methodLOC = 0;
    private int methodCLOC = 0;
    private ArrayList<ArrayList<String>> methodsData = new ArrayList<>();
    private ArrayList<ArrayList<String>> classesData = new ArrayList<>();

    public void Parser() {
    }

    public ArrayList<ArrayList<String>> getClassData(List<File> filesList) throws Exception {
        //ArrayList<ArrayList<String>> classesData = new ArrayList<>();
        //chemin, class, classe_LOC, classe_CLOC, classe_DC

        for (File path : filesList) {
            ArrayList<String> classData = new ArrayList<>();
            File file = new File(String.valueOf(path));
            BufferedReader br = new BufferedReader(new FileReader(file));

            classData.add(path.getPath());
            classData.add(path.getName().replace(".java", ""));

            String st;
            while ((st = br.readLine()) != null) {
                st = st.replaceAll(" ", "");
                if ("".equals(st)) {
                    continue;
                } else if (st.startsWith("//") || st.startsWith("*") || st.startsWith("/*") || st.startsWith("*/")
                        || st.endsWith("*/") || st.startsWith("/**")) {
                    //classLOC++; // LOC inclus les commentaires?
                    classCLOC++;
                    continue;
                } else if (st.contains("//") || (st.contains("/*") && st.contains("*/"))) {
                    //classLOC++;
                    classCLOC++;
                    continue;
                }
                classLOC++;
            }
            classDC = ((double) classCLOC / classLOC);
            classData.add(classLOC + "");
            classData.add(classCLOC + "");
            classData.add(classDC + "");
            classesData.add(classData);

            br.close();
        }
        return classesData;
    }

    public ArrayList<ArrayList<String>> getMethodsData(List<File> filesList) throws Exception {
    //chemin, class, method, method_LOC, method_CLOC, method_DC
    for (File path : filesList) {
        ArrayList<String> methodData = new ArrayList<>();
        File file = new File(String.valueOf(path));
        BufferedReader br = new BufferedReader(new FileReader(file));




        String string = br.readLine();
        while ((string = br.readLine()) != null) {
            string = string.replaceAll(" ", "");
            if (string.endsWith("{") && (string.startsWith("public") || string.startsWith("private") || string.startsWith("protected"))
                    && (string.contains("int") || string.contains("boolean") || string.contains("void") || string.contains("String"))) {
                System.out.println(methodLOC+"allo");
                String method = "";
                method+=string;
                methodData.add(path.getPath());
                methodData.add(path.getName().replace(".java", ""));
                methodData.add(method);
                methodData.add(methodLOC + "");
                methodData.add(methodCLOC + "");
                methodDC = ((double) methodCLOC / methodLOC);
                methodData.add(methodDC + "");
                methodLOC = 0 ;
                System.out.println(methodLOC);
                methodCLOC = 0;
                methodsData.add(methodData);
                continue;

            }
            else if ("".equals(string) && "import".equals(string)) {
                continue;
            } else if (string.startsWith("//") || string.startsWith("*") || string.startsWith("/*")
                    || string.startsWith("*/") || string.endsWith("*/") || string.startsWith("/**")) {
                methodLOC++;
                methodCLOC++;

                continue;
            } else if (string.contains("//") || (string.contains("/*") && string.contains("*/"))) {
                methodLOC++;
                methodCLOC++;

                continue;

            }
            methodLOC++;
            System.out.println(methodCLOC+"C");

            System.out.println(methodLOC+"L");



        }


        // methodsData.add(methodData);


        br.close();
    }
    return methodsData;
}
}

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Parser {

      private int methodLOC = 0;
      private int methodCLOC = 0;
      private ArrayList<ArrayList<String>> methodsData = new ArrayList<>();
      private ArrayList<ArrayList<String>> classesData = new ArrayList<>();

      public void Parser(){}

    public ArrayList<ArrayList<String>> getClassData(List<File> filesList) throws Exception {
        //ArrayList<ArrayList<String>> classesData = new ArrayList<>();
        //chemin, class, classe_LOC, classe_CLOC, classe_DC

        for (File path : filesList) {
            int classLOC = 0;
            int classCLOC = 0;
            double classDC;
            ArrayList<String> classData = new ArrayList<>();
            File file = new File(String.valueOf(path));
            BufferedReader br = new BufferedReader(new FileReader(file));

            classData.add(path.getPath());
            classData.add(path.getName().replace(".java", ""));

            String st;
            while ((st = br.readLine()) != null) {
                st = st.replaceAll(" ", "");
                if ("".equals(st) || st.startsWith("import")) {
                    continue;
                } else if (st.startsWith("//") || st.startsWith("*") || st.startsWith("/*") || st.startsWith("*/")
                        || st.endsWith("*/") || st.startsWith("/**")) {
                    classLOC++; // LOC inclus les commentaires?
                    classCLOC++;
                    continue;
                } else if (st.contains("//") || (st.contains("/*") && st.contains("*/"))) {
                    classLOC++;
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

        for (File path : filesList) {
            int classLOC = 0;
            int classCLOC = 0;
            double classDC;
            ArrayList<String> methodData = new ArrayList<>();
            File file = new File(String.valueOf(path));
            BufferedReader br = new BufferedReader(new FileReader(file));
        }
        return methodsData;
      }

}


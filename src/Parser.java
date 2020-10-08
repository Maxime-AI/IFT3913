import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {

    private int classLOC = 0;
    private int classCLOC = 0;

    private ArrayList<ArrayList<String>> methodsData = new ArrayList<>();
    private ArrayList<ArrayList<String>> classesData = new ArrayList<>();

    public Parser() {
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
                    classLOC++;
                    classCLOC++;
                    continue;
                } else if (st.contains("//") || (st.contains("/*") && st.contains("*/"))) {
                    classLOC++;
                    classCLOC++;
                    continue;
                }
                classLOC++;
            }
            double classDC = ((double) classCLOC / classLOC);
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
            File file = new File(String.valueOf(path));
            List<String> lines = Files.readAllLines(Paths.get(String.valueOf(path)));
            ArrayList<String> methods = findMethods(lines);
            ArrayList<ArrayList<String>> methodsCode = getMethodCode(lines);

            for (String method : methods) {
                ArrayList<String> methodData = new ArrayList<>();
                methodData.add(path.getPath());
                methodData.add(path.getName().replace(".java", ""));
                methodData.add(method);
                ArrayList<String> temp = findMethodContent(lines, method);
                int methodLOC = 0;
                int methodCLOC = 0;
                for (int i = 0; i < temp.size(); i++) {
                    String string = temp.get(i).replaceAll(" ", "");;
                    if ("".equals(string)) {
                        continue;
                    } else if (string.startsWith("//") || string.startsWith("*") || string.startsWith("/*") || string.startsWith("*/")
                            || string.endsWith("*/") || string.startsWith("/**")) {
                        methodLOC++;
                        methodCLOC++;
                        continue;
                    } else if (string.contains("//") || (string.contains("/*") && string.contains("*/"))) {
                        methodLOC++;
                        methodCLOC++;
                        continue;
                    }
                    methodLOC++;
                }
                methodData.add(methodLOC + "");
                methodData.add(methodCLOC + "");
                double methodDC = ((double) methodCLOC / methodLOC);
                methodData.add(methodDC + "");
                methodsData.add(methodData);
            }
        }
        return methodsData;
    }

    public ArrayList<String> findMethods(List<String> lines) {
        ArrayList<String> methods = new ArrayList<>();
        int counter = 0;
        boolean x = false;
        for (String st : lines) {
            st = st.replaceAll(" ", "");
            if (isMethodBool(st)) {
                methods.add(st);
            }
        }
        return methods;
    }

    public ArrayList<String> findMethodContent(List<String> lines, String method) {
        int bracketCount = 0;
        boolean boolMethod = false;

        ArrayList<String> tempArray = new ArrayList<>();
        for (String line : lines) {
            line = line.replaceAll(" ", "");
            if (line.equals(method)) {
                tempArray.add(line);
                boolMethod = true;
                if (line.contains("{")) {
                    bracketCount++;
                }
            } else if (boolMethod) {

                tempArray.add(line);
                if (!line.contains("}") && line.contains("{")) {
                    bracketCount++;
                } else if (line.contains("}") && !line.contains("{")) {
                    bracketCount--;
                }
                if (bracketCount == 0) {
                    boolMethod = false;
                    break;
                }
            }
        }
        return tempArray;
    }

//    public String removeSpaces(String line) {
//        String[] temp = line.split(" ");
//        ArrayList<String> tempArray = new ArrayList<>();
//        for (int i = 0; i < temp.length; i++) {
//            if (!temp[i].equals(" ")) {
//                tempArray.add(temp[i]);
//            }
//        }
//        return String.join("", tempArray);
//    }

    public ArrayList<ArrayList<String>> getMethodCode(List<String> lines) {
        ArrayList<String> methods = findMethods(lines);
        ArrayList<ArrayList<String>> methodsCode = new ArrayList<>();
        for (int i = 0; i < methods.size(); i++) {
            ArrayList<String> temp = new ArrayList<>();
            if (methods.size() == 1) {
                temp.addAll(lines);
            } else if (i == methods.size() - 1) {
                temp.addAll(lines);
            }
            methodsCode.add(temp);
        }
        return methodsCode;
    }

    public boolean isMethodBool(String st) {
        st = st.replaceAll(" ", "");
        return st.endsWith("{") && (st.startsWith("public") || st.startsWith("private") || st.startsWith("protected"))
                && (st.contains("int") || st.contains("boolean") || st.contains("void") || st.contains("String")) &&
                !st.startsWith("public class");
    }

}

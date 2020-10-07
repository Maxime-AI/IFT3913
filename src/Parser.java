import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Parser {
    private int wmc = 0;
    private int cc = 1;
    private int classLOC = 0;
    private int classCLOC = 0;
    private int methodLOC;
    private int methodCLOC;
    private boolean boolMethod;
    private int bracketCount;

    private ArrayList<String> methods;
    private ArrayList<String> methodData = new ArrayList<>();
    private ArrayList<String> classData = new ArrayList<>();
    private ArrayList<ArrayList<String>> methodsData = new ArrayList<>();
    private ArrayList<ArrayList<String>> classesData = new ArrayList<>();
    ArrayList<ArrayList<String>> methodsCode = new ArrayList<>();

    public Parser() {
    }

    public ArrayList<ArrayList<String>> getClassData(List<File> filesList) throws Exception {
        //ArrayList<ArrayList<String>> classesData = new ArrayList<>();
        //chemin, class, classe_LOC, classe_CLOC, classe_DC

        for (File path : filesList) {
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
                } else if (st.contains("for")||st.contains("if")||st.contains("else if")||st.contains("while")|| st.contains("switch")) {
                    //wmc++;

                } else if (st.contains("&&")||st.contains("||")) {
                   // wmc++;

                }

                classLOC++;
            }
            double  class_BC = (classDC(classCLOC,classLOC))/wmc;
            classData.add(wmc(methodsData) +"");
            classData.add(class_BC+"");
            classData.add(classLOC + "");
            classData.add(classCLOC + "");
            classData.add(classDC(classCLOC, classLOC) + "");
            classesData.add(classData);

            br.close();
        }
        return classesData;
    }
    public  double classDC (int classCLOC, int classLOC) {
        this.classLOC = classLOC;
        this.classCLOC = classCLOC;
        double classDC;
        return classDC = ((double) classCLOC / classLOC);
    }
    public double wmc(ArrayList<ArrayList<String>>methodsData) {
        int wmc = 0;
        for (int i = 0; i < methodData.size(); i++) {
            wmc += Integer.parseInt(methodData.get(3));
        }
        return wmc;
    }

    public ArrayList<ArrayList<String>> getMethodsData(List<File> filesList) throws Exception {
        //chemin, class, method, method_LOC, method_CLOC, method_DC
        for (File path : filesList) {
            File file = new File(String.valueOf(path));
            List<String> lines = Files.readAllLines(Paths.get(String.valueOf(path)));
             methods = findMethods(lines);
            ArrayList<ArrayList<String>> methodsCode = getMethodCode(lines);
            for (String method : methods) {
                methodData.add(path.getPath());
                methodData.add(path.getName().replace(".java", ""));
                methodData.add(method);
                ArrayList<String> temp = findMethodContent(lines, method);
                 methodLOC = 0;
                 methodCLOC = 0;
                for (int i = 0; i < temp.size(); i++) {
                    String string = temp.get(i).replaceAll(" ", "");
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
                    }else if (string.contains("for")||string.contains("if")||string.contains("else if")||string.contains("while")|| string.contains("switch")) {
                        cc++;
                        continue;

                    } else if (string.contains("&&")||string.contains("||")) {
                        cc++;
                        continue;

                    }
                    methodLOC++;
                }
                double  method_BC = (methodDC(methodCLOC,methodLOC))/cc;
                methodData.add(cc + "");
                methodData.add(method_BC + "");
                methodData.add(methodLOC + "");
                methodData.add(methodCLOC + "");
                methodData.add(methodDC(methodCLOC,methodLOC) + "");
                methodsData.add(methodData);

            }
        }
        return methodsData;
    }
    public double methodDC(int methodCLOC, int methodLOC) {
        this.methodCLOC = methodCLOC;
        this.methodLOC = methodLOC;
        double methodDC;
        return methodDC = ((double) methodCLOC / methodLOC);

    }

    public ArrayList<String> findMethods(List<String> lines) {
        methods = new ArrayList<>();
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
        bracketCount = 0;
         boolMethod = false;

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
 //  }


    public ArrayList<ArrayList<String>> getMethodCode(List<String> lines) {
         methods = findMethods(lines);
         methodsCode = new ArrayList<>();
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
    public ArrayList<String> getMethods() {
        return methods;
    }
}

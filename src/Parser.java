import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

    private int classLOC = 0;
    private int classCLOC = 0;
    private  int methodLOC;
    private int methodCLOC;
    private double methodDC;
    private ArrayList<ArrayList<String>> methodsData = new ArrayList<>();
    private ArrayList<ArrayList<String>> classesData = new ArrayList<>();
    boolean methodBool ;

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
            ArrayList<String> methodData = new ArrayList<>();
            File file = new File(String.valueOf(path));
            BufferedReader br = new BufferedReader(new FileReader(file));

            String st;
            while ((st = br.readLine()) != null) {
                st = st.replaceAll(" ", "");


                if (st.endsWith("{") && (st.startsWith("public") || st.startsWith("private") || st.startsWith("protected"))
                        && (st.contains("int") || st.contains("boolean") || st.contains("void") || st.contains("String"))) {

                        this.methodBool = true;
                        String method = "";
                        method += st;
                        methodData.add(path.getPath());
                        methodData.add(path.getName().replace(".java", ""));
                        methodData.add(method);
//                    String[] string = "               public static void main(String[] args) throws Exception {".split(" ");
//                    String[] objects = Arrays.stream(string).filter(x -> !x.isEmpty()).toArray(String[]::new);
//        https://stackoverflow.com/questions/41935581/removing-empty-element-from-arrayjava/41935895#41935895
//                    System.out.println(Arrays.toString(objects));

                       /* methodData.add(methodLOC + "");
                        methodData.add(methodCLOC + "");
                        methodDC = ((double) methodCLOC / methodLOC);
                        methodData.add(methodDC + "");*/

                        while (methodBool) {
                            if ("".equals(st)) {
                                continue;
                            } else if (st.startsWith("//") || st.startsWith("*") || st.startsWith("/*") || st.startsWith("*/")
                                    || st.endsWith("*/") || st.startsWith("/**")) {
                                methodLOC++;
                                System.out.println(methodLOC+"s");
                                methodCLOC++;
                                continue;
                            } else if (st.contains("//") || (st.contains("/*") && st.contains("*/"))) {
                                methodLOC++;
                                System.out.println(methodLOC+"L");
                                methodCLOC++;
                                continue;
                            }if (methodBool) {
                               methodLOC++;
                               methodCLOC++;
                               this.methodBool = false;
                                methodData.add(methodLOC + "");
                                methodData.add(methodCLOC + "");
                                methodDC = ((double) methodCLOC / methodLOC);
                                methodData.add(methodDC + "");
                                methodsData.add(methodData);
                                methodData = new ArrayList<>();
                            } else {
                                this.methodBool = true;
                            }
                            methodLOC++;
                            System.out.println(methodLOC + "M");
                        }

                }

            }
            br.close();
        }
        return methodsData;
    }
}

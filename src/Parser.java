import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Parser {

    private ArrayList<Integer> WMCList = new ArrayList<>();

    /**
     *
     * @param filesList
     * @return ArrayList<ArrayList<String>> data of every class in the folder
     * @throws Exception
     */
    public ArrayList<ArrayList<String>> getClassData(List<File> filesList) throws Exception {
        ArrayList<ArrayList<String>> classesData = new ArrayList<>();
        int index = 0;

        for (File path : filesList) {
            BufferedReader br = new BufferedReader(new FileReader(new File(String.valueOf(path))));
            String className = path.getName().replace(".java", "");
            int classLOC = 0;
            int classCLOC = 0;
            String line;

            while ((line = br.readLine()) != null) {
                line = line.replaceAll(" ", "");

                if ("".equals(line)) {
                    continue;
                } else if (isComment(line)) {
                    classLOC++;
                    classCLOC++;
                    continue;
                }

                classLOC++;
            }

            double classDC = ((double) classCLOC / classLOC);
            double classBC = classDC / WMCList.get(index);
            List<String> data = Arrays.asList(path.getPath(), className, classLOC + "", classCLOC + "", classDC + "",
                    WMCList.get(index) + "", classBC + "");
            classesData.add(new ArrayList<>(data));
            br.close();
            index++;

        }
        return classesData;
    }

    /**
     *
     * @param filesList
     * @return ArrayList<ArrayList<String>> data of every method in the folder
     * @throws Exception
     */
    public ArrayList<ArrayList<String>> getMethodsData(List<File> filesList) throws Exception {
        ArrayList<ArrayList<String>> methodsData = new ArrayList<>();

        for (File path : filesList) {
            List<String> lines = Files.readAllLines(Paths.get(String.valueOf(path)));
            ArrayList<String> methods = findMethods(lines);
            int WMC = 0;

            for (String method : methods) {
                String className = path.getName().replace(".java", "");
                ArrayList<String> temp = findMethodContent(lines, method);
                int methodLOC = 0;
                int methodCLOC = 0;
                int methodCC = 1;

                for (String line : temp) {
                    line = line.replaceAll(" ", "");

                    if ("".equals(line)) {
                        continue;
                    } else if (isComment(line)) {
                        methodLOC++;
                        methodCLOC++;
                        continue;
                    } else if (checkCC(line)) {
                        methodCC++;
                        continue;
                    }
                    methodLOC++;
                }
                WMC += methodCC;
                double methodDC = ((double) methodCLOC / methodLOC);
                double methodBC = (methodDC / methodCC);
                List<String> data = Arrays.asList(path.getPath(), className, method, methodLOC + "", methodCLOC + "",
                        methodDC + "", methodCC + "", methodBC + "");
                methodsData.add(new ArrayList<>(data));
            }
            WMCList.add(WMC);
        }
        return methodsData;
    }

    /**
     *
     * @param line
     * @return
     */
    public boolean checkCC(String line) {
        return line.contains("for") || line.contains("if") || line.contains("else if") || line.contains("while") ||
                line.contains("switch") || line.contains("&&") || line.contains("||");
    }

    /**
     *
     * @param line
     * @return
     */
    public boolean isComment(String line) {
        return line.startsWith("//") || line.startsWith("*") || line.startsWith("/*") || line.startsWith("*/")
                || line.endsWith("*/") || line.startsWith("/**") || line.contains("//") || (line.contains("/*") &&
                line.contains("*/"));
    }

    /**
     *
     * @param line
     * @return
     */
    public boolean isMethod(String line) {
        return line.endsWith("{") && (line.startsWith("public") || line.startsWith("private") ||
                line.startsWith("protected")) && (line.contains("int") || line.contains("boolean") ||
                line.contains("void") || line.contains("String")) && !line.startsWith("public class");
    }

    /**
     *
     * @param lines
     * @return
     */
    public ArrayList<String> findMethods(List<String> lines) {
        ArrayList<String> methods = new ArrayList<>();

        for (String st : lines) {
            st = st.replaceAll(" ", "");
            if (isMethod(st)) {
                methods.add(st);
            }
        }
        return methods;
    }

    /**
     *
     * @param lines
     * @param method
     * @return
     */
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

}

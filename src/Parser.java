import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {

    private ArrayList<Integer> WMCList = new ArrayList<>();

    /**
     * @param filesList
     * @return ArrayList<ArrayList < String>> data of every class in the folder
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
                line = line.trim();

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
     * @param filesList
     * @return ArrayList<ArrayList < String>> data of every method in the folder
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
                methodsData.add(addMethodData(path.getPath(), className, method.replaceAll(",", ""),
                        methodLOC + "", methodCLOC + "", methodDC + "",
                        methodCC + "", methodBC + ""));
            }
            WMCList.add(WMC);
        }
        return methodsData;
    }

    public ArrayList<String> addMethodData(String path, String className, String method, String methodLOC,
                                           String methodCLOC, String methodDC, String methodCC, String methodBC) {
        ArrayList<String> data = new ArrayList<>();
        data.add(path);
        data.add(className);
        data.add(method);
        data.add(methodLOC);
        data.add(methodCLOC);
        data.add(methodDC);
        data.add(methodCC);
        data.add(methodBC);
        return data;
    }


    /**
     * @param line
     * @return
     */
    public boolean checkCC(String line) {
        return line.contains("for") || line.contains("if") || line.contains("else if") || line.contains("while") ||
                line.contains("switch") || line.contains("&&") || line.contains("||");
    }

    /**
     * @param line
     * @return
     */
    public boolean isComment(String line) {
        return line.startsWith("//") || line.startsWith("*") || line.startsWith("/*") || line.startsWith("*/")
                || line.endsWith("*/") || line.startsWith("/**") || line.contains("//") || (line.contains("/*") &&
                line.contains("*/"));
    }

    /**
     * @param line
     * @return
     */
    public boolean isMethod(String line) {
        // regex source : https://stackoverflow.com/questions/68633/regex-that-will-match-a-java-method-declaration
        String regex = "(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\]]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
        return line.matches(regex);
    }

    /**
     * @param lines
     * @return
     */
    public ArrayList<String> findMethods(List<String> lines) {
        ArrayList<String> methods = new ArrayList<>();
        for (String st : lines) {
            st = st.trim();
            if (isMethod(st)) {
                methods.add(st);
            }
        }
        return methods;
    }

    /**
     * @param lines
     * @param method
     * @return
     */
    public ArrayList<String> findMethodContent(List<String> lines, String method) {
        int bracketCount = 0;
        boolean boolMethod = false;

        ArrayList<String> tempArray = new ArrayList<>();
        for (String line : lines) {
            line = line.trim();
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

}

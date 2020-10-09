import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Parser {

    private ArrayList<Integer> WMCList = new ArrayList<>();

    /**
     * This method gets a class and calculate its number of lines of code, lines of comments and other indexes.
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
     * This method gets methods  and calculate its number of lines of code, lines of comments and other indexes
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
                methodsData.add(addMethodData(path.getPath(), className, cleanMethodName(method),
                        methodLOC + "", methodCLOC + "", methodDC + "",
                        methodCC + "", methodBC + ""));
            }
            WMCList.add(WMC);
        }
        return methodsData;
    }

    /**
     * This method adds all the indexes that goes into the data structure of getMethodsData
     * @param path
     * @param className
     * @param method
     * @param methodLOC
     * @param methodCLOC
     * @param methodDC
     * @param methodCC
     * @param methodBC
     * @return ArrayList<String>
     */
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
     * This method replace all the parameters of the methods by only their types and their names with
     * underscores as spaces.
     * ex: methodName_type1_type2
     * @param line
     * @return String
     */
    public String cleanMethodName(String line) {
        String temp = line.replace("(", " ").replace(")", " ").replace("{", "").replace(",", "");

        List<String> list = Stream.of(temp.split(" "))
                .map(String::trim)
                .collect(Collectors.toList());

        list.removeAll(Collections.singleton(""));
        if (list.contains("static")) {
            list.remove(0);
        }
        list.remove(0);
        list.remove(0);

        if (list.contains("throws")) {
            list.remove(list.size() - 1);
            list.remove(list.size() - 1);
        }

        StringBuilder method = new StringBuilder();
        method.append(list.get(0));
        for (int i = 1; i < list.size(); i += 2) {
            method.append("_").append(list.get(i));
        }

        return method.toString();
    }

    /**
     * This method analyzes if a line contains some predicate from the complexity of McCabe or not and gives it a state.
     * @param line
     * @return boolean
     */
    public boolean checkCC(String line) {
        return line.contains("for") || line.contains("if") || line.contains("else if") || line.contains("while") ||
                line.contains("switch") || line.contains("&&") || line.contains("||");
    }

    /**
     * This method analyzes if a line is a comment or not and gives it a state (true or false).
     * @param line
     * @return boolean
     */
    public boolean isComment(String line) {
        return line.startsWith("//") || line.startsWith("*") || line.startsWith("/*") || line.startsWith("*/")
                || line.endsWith("*/") || line.startsWith("/**") || line.contains("//") || (line.contains("/*") &&
                line.contains("*/"));
    }

    /**
     * This method analyzes if a line is a method or not and gives it a state (true or false).
     * @param line
     * @return boolean
     */
    public boolean isMethod(String line) {
        // regex source : https://stackoverflow.com/questions/68633/regex-that-will-match-a-java-method-declaration
        String regex = "(public|protected|private|static|\\s) +[\\w\\<\\>\\[\\],\\s]+\\s+(\\w+) *\\([^\\)]*\\) *(\\{?|[^;])";
        return line.matches(regex);
    }

    /**
     * This method finds if a line in the file is a method and stores it in a Arraylist.
     * @param lines
     * @return ArrayList<String>
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
     * This method finds  the content inside of a method.
     * @param lines
     * @param method
     * @return  ArrayList<String>
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

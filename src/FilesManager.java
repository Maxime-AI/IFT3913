import java.io.File;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FilesManager {

    /**
     * @source : https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
     * This method takes a file and read through it.
     * @param folder
     * @return List<File> list of all java files in the folder
     * @throws Exception
     */
    public List<File> getFiles(String folder) throws Exception {
        return Files.walk(Paths.get(folder))
                .filter(p -> p.getFileName().toString().endsWith(".java"))
                .map(Path::toFile)
                .collect(Collectors.toList());
    }

}

import java.io.File;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Manages the source folder
 *
 * @author : Maxime Lechasseur
 * @author : Han Zhang
 */
public class FilesManager {

    /**
     * This method takes a root folder and gets all the .java files in it.
     *
     * @param folder root folder
     * @return List<File> list of all java files in the folder
     * @throws Exception exception
     */
    public List<File> getFiles(String folder) throws Exception {
        //inspired from https://stackoverflow.com/questions/1844688/how-to-read-all-files-in-a-folder-from-java
        return Files.walk(Paths.get(folder))
                .filter(p -> p.getFileName().toString().endsWith(".java"))
                .map(Path::toFile)
                .collect(Collectors.toList());
    }
}

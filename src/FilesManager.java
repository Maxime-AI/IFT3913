import java.io.File;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

public class FilesManager {

    /**
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

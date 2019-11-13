package singlepagespringboot.utilities.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Contains mockable implementations of static methods.
 *
 * @author Max Günther
 */
public class MockableFileUtils {

    /**
     * Probes the content type of a file. See {@link Files#probeContentType(Path)}.
     *
     * @param path the path of the file to probe
     * @return the content type or <code>null</code> if the content type could not be determined
     * @throws IOException if an I/O related error occured while probing the file
     * @see Files#probeContentType(Path)
     */
    public String probeContentType(Path path) throws IOException {
        return Files.probeContentType(path);
    }
}

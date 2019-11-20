package singlepagespringboot.utilities.io;

import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Contains mockable implementations of static methods.
 *
 * @author Max Günther
 */
@Component
public class MockableFileUtils {
    @Autowired private Tika tika;

    /**
     * Probes the content type of a file.
     *
     * @param file the file to probe
     * @return the content type or <code>null</code> if the content type could not be determined
     * @throws IOException if an I/O related error occured while probing the file
     */
    public String probeContentType(File file) throws IOException {
        return tika.detect(file);
    }
}

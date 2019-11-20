package singlepagespringboot.utilities.io;

import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Mockable implementations for static methods around I/O streams.
 *
 * @author Max Günther
 */
@Component
public class MockableIOStreamUtils {

    /**
     * Creates an {@link InputStreamResource} from a file.
     *
     * @param file the file to read
     * @return the stream resource
     * @throws FileNotFoundException if the file could not be found
     */
    public InputStreamResource inputStreamResourceFromFile(File file) throws FileNotFoundException {
        return new InputStreamResource(new FileInputStream(file));
    }
}

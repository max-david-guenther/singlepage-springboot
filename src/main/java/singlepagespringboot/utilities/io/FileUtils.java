package singlepagespringboot.utilities.io;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Contains convenience methods for using files.
 *
 * @author Max Günther
 */
@Component
public class FileUtils {
    @Autowired private MockableFileUtils mockableFileUtils;

    /**
     * Probes the media type of the file at the given path. See {@link Files#probeContentType(Path)}.
     *
     * @param path the path to the file
     * @return the media type or <code>null</code> if the media type could not be determined.
     * @throws IOException if the file could not be read
     */
    public MediaType probeMediaType(Path path) throws IOException {
        Optional<String> mediaTypeStr = Optional.ofNullable(mockableFileUtils.probeContentType(path));
        return mediaTypeStr.map(MediaType::valueOf).orElse(null);
    }
}

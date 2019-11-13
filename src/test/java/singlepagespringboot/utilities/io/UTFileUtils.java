package singlepagespringboot.utilities.io;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UTFileUtils {
    /* class under test */
    @InjectMocks private FileUtils fileUtils;
    /* dependencies */
    @Mock private MockableFileUtils mockableFileUtils;
    /* parameters */
    private Path path;
    private String contentType;
    private MediaType expectedMediaType;

    @Before
    public void setUp() throws IOException {
        path = Paths.get("/path/to/file.json");
        contentType = "application/json";
        expectedMediaType = MediaType.APPLICATION_JSON;
        when(mockableFileUtils.probeContentType(path)).thenReturn(contentType);
    }

    @Test
    public void probeMediaType() throws IOException {
        // act
        MediaType mediaType = fileUtils.probeMediaType(path);

        // assert
        assertThat(mediaType).isEqualTo(expectedMediaType);
    }

    @Test
    public void probeMediaType_null() throws IOException {
        // arrange
        when(mockableFileUtils.probeContentType(path)).thenReturn(null);

        // act
        MediaType mediaType = fileUtils.probeMediaType(path);

        // assert
        assertThat(mediaType).isNull();
    }
}

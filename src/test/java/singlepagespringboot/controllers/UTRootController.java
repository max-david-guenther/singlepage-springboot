package singlepagespringboot.controllers;

import org.assertj.core.api.SoftAssertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import singlepagespringboot.utilities.io.FileUtils;
import singlepagespringboot.utilities.io.MockableIOStreamUtils;
import singlepagespringboot.request.RequestResourceMapper;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UTRootController {
    /* class under test */
    @InjectMocks private RootController rootController;
    /* mocked dependencies */
    @Mock private RequestResourceMapper requestResourceMapper;
    @Mock private FileUtils fileUtils;
    @Mock private MockableIOStreamUtils mockableIOStreamUtils;
    /* parameters */
    @Mock private HttpServletRequest request;
    @Mock private InputStreamResource mappedStream;
    @Mock private File mappedRequestFile;
    private Path mappedRequestFilePath;
    private long mappedFileLength;
    private Locale requestLocale;
    private String requestUri;
    private MediaType detectedMediaType;

    @Before
    public void setUp() throws IOException {
        requestLocale = Locale.forLanguageTag("en-US");
        detectedMediaType = MediaType.APPLICATION_JSON;
        requestUri = "/index.html";
        mappedRequestFilePath = Paths.get("/en-US/index.html");
        mappedFileLength = 1024;
        when(mappedRequestFile.toPath()).thenReturn(mappedRequestFilePath);
        when(mappedRequestFile.length()).thenReturn(mappedFileLength);
        when(request.getRequestURI()).thenReturn(requestUri);
        when(requestResourceMapper.map(requestUri, requestLocale)).thenReturn(mappedRequestFile);
        when(fileUtils.probeMediaType(mappedRequestFile.toPath())).thenReturn(detectedMediaType);
        when(mockableIOStreamUtils.inputStreamResourceFromFile(mappedRequestFile)).thenReturn(mappedStream);
    }

    @Test
    public void get() throws IOException {
        // act
        var response = rootController.get(request, requestLocale);

        // assert
        assertThat(response).isNotNull();
        var softly = new SoftAssertions();
        softly.assertThat(response.getBody()).isEqualTo(mappedStream);
        softly.assertThat(response.getHeaders().getContentType()).isEqualTo(detectedMediaType);
        softly.assertThat(response.getHeaders().getContentLength()).isEqualTo(mappedFileLength);
        softly.assertAll();
    }
}

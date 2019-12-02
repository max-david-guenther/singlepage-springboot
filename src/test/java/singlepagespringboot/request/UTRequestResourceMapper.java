package singlepagespringboot.request;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import singlepagespringboot.properties.UIProperties;

import java.io.File;
import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UTRequestResourceMapper {
    /* class under test */
    @InjectMocks
    private RequestResourceMapper requestResourceMapper;
    /* mocked dependencies */
    @Mock private UIProperties uiProperties;
    /* parameters */
    @Mock private StaticFileRootProvider fileRootProvider;
    @Mock(name="/en-US/style.css") private File expectedMappedFile;
    @Mock(name="/en-US/index.html") private File expectedMappedIndexFile;
    private String configuredIndexFileName;
    private String uri;
    private Locale locale;
    private String localizedPath;
    private String localizedIndexPath;

    @Before
    public void setUp() {
        configuredIndexFileName = "index.html";
        uri = "/style.css";
        locale = Locale.forLanguageTag("en-US");
        localizedPath = "en-US/style.css";
        localizedIndexPath = "en-US/index.html";

        when(fileRootProvider.getFile(localizedPath)).thenReturn(expectedMappedFile);
        when(fileRootProvider.getFile(localizedIndexPath)).thenReturn(expectedMappedIndexFile);
        when(expectedMappedFile.exists()).thenReturn(true);
        when(expectedMappedFile.isDirectory()).thenReturn(false);
        when(uiProperties.getIndexFileName()).thenReturn(configuredIndexFileName);
    }

    @Test
    public void map() {
        // act
        File mappedFile = requestResourceMapper.map(fileRootProvider, uri, locale);

        // assert
        assertThat(mappedFile).isEqualTo(expectedMappedFile);
    }

    @Test
    public void map_notFound() {
        // arrange
        when(expectedMappedFile.exists()).thenReturn(false);

        // act
        File mappedFile = requestResourceMapper.map(fileRootProvider, uri, locale);

        // assert
        assertThat(mappedFile).isEqualTo(expectedMappedIndexFile);
    }
}

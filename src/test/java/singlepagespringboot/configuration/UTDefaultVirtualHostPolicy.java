package singlepagespringboot.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import singlepagespringboot.properties.UIProperties;
import singlepagespringboot.properties.VirtualHostProperties;
import singlepagespringboot.request.StaticFileRootProvider;

import javax.servlet.http.HttpServletRequest;

import java.io.File;

import static org.apache.tomcat.websocket.Constants.HOST_HEADER_NAME;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UTDefaultVirtualHostPolicy {
    /* class under test */
    @InjectMocks
    private DefaultVirtualHostPolicy defaultVirtualHostPolicy;
    /* mocked dependencies */
    @Mock private VirtualHostProperties virtualHostProperties;
    @Mock private UIProperties uiProperties;
    /* parameters */
    @Mock private HttpServletRequest request;
    private File fileRoot;
    private String indexHtmlName;
    private String virtualHostName;

    @Before
    public void setUp() {
        fileRoot = new File("/home/app/static");
        indexHtmlName = "index.html";
        virtualHostName = "virtual-host";

        when(virtualHostProperties.isEnabled()).thenReturn(false);
        when(uiProperties.getStaticFileRoot()).thenReturn(fileRoot);
    }

    @Test
    public void isEnabled() {
        // act
        boolean enabled = defaultVirtualHostPolicy.isEnabled();

        // assert
        assertThat(enabled).isFalse();
    }

    @Test
    public void getFileRootProvider_virtualHostDisabled() {
        // act
        StaticFileRootProvider fileRootProvider = defaultVirtualHostPolicy.getFileRootProvider(request);
        File file = fileRootProvider.getFile(indexHtmlName);

        // assert
        assertThat(file).isEqualTo(new File(fileRoot, indexHtmlName));
    }

    @Test
    public void getFileRootProvider_virtualHostEnabled() {
        // arrange
        when(virtualHostProperties.isEnabled()).thenReturn(true);
        when(request.getHeader(HOST_HEADER_NAME)).thenReturn(virtualHostName);

        // act
        StaticFileRootProvider fileRootProvider = defaultVirtualHostPolicy.getFileRootProvider(request);
        File file = fileRootProvider.getFile(indexHtmlName);

        // assert
        File virtualHostRoot = new File(fileRoot, virtualHostName);
        File expectedFile = new File(virtualHostRoot, indexHtmlName);
        assertThat(file).isEqualTo(expectedFile);
    }

    @Test
    public void getFileRootProvider_virtualHostEnabledButNotRequested() {
        // arrange
        when(virtualHostProperties.isEnabled()).thenReturn(true);

        // act
        StaticFileRootProvider fileRootProvider = defaultVirtualHostPolicy.getFileRootProvider(request);
        File file = fileRootProvider.getFile(indexHtmlName);

        // assert
        assertThat(file).isEqualTo(new File(fileRoot, indexHtmlName));
    }
}

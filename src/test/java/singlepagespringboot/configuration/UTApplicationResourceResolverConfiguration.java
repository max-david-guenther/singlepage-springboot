package singlepagespringboot.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import singlepagespringboot.properties.UIProperties;
import singlepagespringboot.request.StaticFileRootProvider;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UTApplicationResourceResolverConfiguration {
    /* class under test */
    @InjectMocks private ApplicationResourceResolverConfiguration applicationResourceResolverConfiguration;
    /* mocked dependencies */
    @Mock private UIProperties uiProperties;
    /* parameters */
    private File root = new File("/test/path");

    @Before
    public void setUp() {
        when(uiProperties.getStaticFileRoot()).thenReturn(root);
    }

    @Test
    public void staticFileRootProvider() {
        // act
        StaticFileRootProvider provider = applicationResourceResolverConfiguration.staticFileRootProvider();

        // assert
        String path = "test/path";
        assertThat(provider.getFile(path)).isEqualTo(new File(root, path));
    }
}

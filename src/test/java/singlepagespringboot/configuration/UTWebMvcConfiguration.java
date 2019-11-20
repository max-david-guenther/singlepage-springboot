package singlepagespringboot.configuration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;

import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UTWebMvcConfiguration {
    /* class under test */
    @InjectMocks private WebMvcConfiguration webMvcConfiguration;
    /* mocked dependencies */
    /* parameters */
    @Mock private ContentNegotiationConfigurer contentNegotiationConfigurer;

    @Test
    public void configureContentNegotiation() {
        // act
        webMvcConfiguration.configureContentNegotiation(contentNegotiationConfigurer);

        // assert
        verify(contentNegotiationConfigurer).defaultContentType(MediaType.APPLICATION_OCTET_STREAM);
    }
}

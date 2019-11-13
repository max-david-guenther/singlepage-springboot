package singlepagespringboot.configuration;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import singlepagespringboot.configuration.LocaleConfiguration;
import singlepagespringboot.locale.AcceptHeaderAndCookieLocaleResolver;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class UTLocaleConfiguration {
    /* class under test */
    @InjectMocks private LocaleConfiguration localeConfiguration;

    @Before
    public void setUp() {

    }

    @Test
    public void localeResolver() {
        // act
        LocaleResolver resolver = localeConfiguration.localeResolver();

        // assert
        assertThat(resolver).isInstanceOf(AcceptHeaderAndCookieLocaleResolver.class);
    }
}

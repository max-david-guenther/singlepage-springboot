package singlepagespringboot.locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import singlepagespringboot.properties.LocaleProperties;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UTLocalizationCookie {
    /* class under test */
    @InjectMocks
    private LocalizationCookie localizationCookie;
    /* mocked dependencies */
    @Mock private LocaleProperties localeProperties;
    /* parameters */
    @Mock private HttpServletRequest request;
    private String configuredCookieName;
    private Locale firstLocaleCookie;
    private Locale secondLocaleCookie;

    @Before
    public void setUp() {
        configuredCookieName = "Localization";
        firstLocaleCookie = Locale.forLanguageTag("en-US");
        secondLocaleCookie = Locale.forLanguageTag("de-DE");

        when(localeProperties.getCookie()).thenReturn(configuredCookieName);
        when(request.getCookies()).thenReturn(new Cookie[] {
            new Cookie("ignore-me", "bla"),
            new Cookie(configuredCookieName, firstLocaleCookie.toLanguageTag()),
            new Cookie("ignore-me-too", "bla"),
            new Cookie(configuredCookieName, secondLocaleCookie.toLanguageTag()),
        });
    }

    @Test
    public void readLocalizationCookie() {
        // act
        var locale = localizationCookie.readLocalizationCookie(request);

        // assert
        assertThat(locale).isNotEmpty();
        assertThat(locale.get()).isEqualTo(firstLocaleCookie);
    }

    @Test
    public void readLocalizationCookie_noCookies() {
        // arrange
        when(request.getCookies()).thenReturn(null);

        // act
        var locale = localizationCookie.readLocalizationCookie(request);

        // assert
        assertThat(locale).isEmpty();
    }

    @Test
    public void readLocalizationCookie_noLocalizationCookie() {
        // arrange
        when(request.getCookies()).thenReturn(new Cookie[] {});

        // act
        var locale = localizationCookie.readLocalizationCookie(request);

        // assert
        assertThat(locale).isEmpty();
    }
}

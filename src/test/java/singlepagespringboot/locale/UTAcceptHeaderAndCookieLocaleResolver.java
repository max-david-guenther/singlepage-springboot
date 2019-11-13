package singlepagespringboot.locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import singlepagespringboot.properties.LocaleProperties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UTAcceptHeaderAndCookieLocaleResolver {
    /* class under test */
    @InjectMocks
    private AcceptHeaderAndCookieLocaleResolver acceptHeaderAndCookieLocaleResolver;
    /* mocked dependencies */
    @Mock private LocalizationCookie localizationCookie;
    @Mock private LocaleProperties localeProperties;
    /* parameters */
    @Mock private HttpServletRequest request;
    @Mock private HttpServletResponse response;
    private Locale locale;
    private Locale cookieLocale;
    private List<Locale> requestedLocales;
    private Set<Locale> availableLocales;
    private Locale defaultLocale;

    @Before
    public void setUp() {
        locale = Locale.forLanguageTag("en-US");
        cookieLocale = Locale.forLanguageTag("en-US");
        requestedLocales = Arrays.asList(
            Locale.forLanguageTag("de-DE"),
            Locale.forLanguageTag("en")
        );
        availableLocales = new HashSet<>(Arrays.asList(
            Locale.forLanguageTag("en-US"),
            Locale.forLanguageTag("en")
        ));
        defaultLocale = Locale.forLanguageTag("en-US");
        when(localizationCookie.readLocalizationCookie(request)).thenReturn(Optional.of(cookieLocale));
        when(request.getLocales()).thenReturn(Collections.enumeration(requestedLocales));
        when(localeProperties.getAvailable()).thenReturn(availableLocales);
        when(localeProperties.getDefault()).thenReturn(defaultLocale);
    }

    @Test
    public void setLocale() {
        // act & assert
        assertThatThrownBy(() -> acceptHeaderAndCookieLocaleResolver.setLocale(request, response, locale))
            .isInstanceOf(UnsupportedOperationException.class);
    }

    @Test
    public void resolveLocale_cookieIsFirstPriority() {
        // act
        Locale resolvedLocale = acceptHeaderAndCookieLocaleResolver.resolveLocale(request);

        // assert
        assertThat(resolvedLocale).isEqualTo(cookieLocale);
    }

    @Test
    public void resolveLocale_noMatchForCookie() {
        // arrange
        when(localizationCookie.readLocalizationCookie(request)).thenReturn(Optional.empty());

        // act
        Locale resolvedLocale = acceptHeaderAndCookieLocaleResolver.resolveLocale(request);

        // assert
        assertThat(resolvedLocale).isEqualTo(requestedLocales.get(1));
    }

    @Test
    public void resolveLocale_fallbackToDefault() {
        // arrange
        when(localizationCookie.readLocalizationCookie(request)).thenReturn(Optional.empty());
        when(request.getLocales()).thenReturn(Collections.enumeration(Arrays.asList(
            Locale.forLanguageTag("zh-CN")
        )));

        // act
        Locale resolvedLocale = acceptHeaderAndCookieLocaleResolver.resolveLocale(request);

        // assert
        assertThat(resolvedLocale).isEqualTo(defaultLocale);
    }
}

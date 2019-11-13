package singlepagespringboot.locale;

import singlepagespringboot.properties.LocaleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Read the localization cookie from a {@link HttpServletRequest}.
 *
 * @author Max Günther
 */
@Component
public class LocalizationCookie {
    @Autowired private LocaleProperties localeProperties;

    /**
     * Read the <code>Localization</code> from the given HTTP request and return it. Returns an empty optional if the
     * localization cookie is not present.
     *
     * @param request the HTTP request
     * @return the requested localization or an empty localization if none is explicitly requested
     */
    public Optional<Locale> readLocalizationCookie(HttpServletRequest request) {
        return Stream.of(Optional.ofNullable(request.getCookies()).orElse(new Cookie[] {}))
                .filter(x -> x.getName().equals(localeProperties.getCookie()))
                .findFirst()
                .map(x -> Locale.forLanguageTag(x.getValue()));
    }
}

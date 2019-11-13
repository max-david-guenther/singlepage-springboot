package singlepagespringboot.locale;

import singlepagespringboot.properties.LocaleProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Resolves the locale by consulting a cookie, and if not set the <code>Accept</code> header from a set of available
 * locales.
 *
 * @author Max Günther
 */
@Component
public class AcceptHeaderAndCookieLocaleResolver implements LocaleResolver {
    @Autowired private LocalizationCookie localizationCookie;
    @Autowired private LocaleProperties localeProperties;

    /**
     * Set the locale. Unsupported operation.
     *
     * @param request the request
     * @param response the response
     * @param locale the locale
     */
    @Override
    public void setLocale(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable Locale locale) {
        throw new UnsupportedOperationException("Cannot change HTTP accept header");
    }

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        List<Locale.LanguageRange> priorityList = Stream.concat(
            localizationCookie.readLocalizationCookie(request).map(Stream::of).orElseGet(Stream::empty),
            Collections.list(request.getLocales()).stream()
        ).map(x -> new Locale.LanguageRange(x.toLanguageTag())).collect(Collectors.toList());

        List<Locale> matchingLocales = Locale.filter(
            priorityList,
            localeProperties.getAvailable(),
            Locale.FilteringMode.EXTENDED_FILTERING
        );
        return matchingLocales.isEmpty() ? localeProperties.getDefault() : matchingLocales.get(0);
    }
}

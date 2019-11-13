package singlepagespringboot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Locale;
import java.util.Set;

/**
 * Properties concerning the locale.
 *
 * @author Max Günther
 */
@Component
@ConfigurationProperties(prefix="singlepage-springboot.locale")
public class LocaleProperties {
    private Locale defaultLocale;
    private Set<Locale> available;
    private String cookie;

    /**
     * The default locale served by the application. Must be one of <code>singlepage-springboot.locale.available</code>.
     *
     * @return the default locale
     */
    public Locale getDefault() {
        return defaultLocale;
    }

    /**
     * The default locale served by the application. Must be one of <code>singlepage-springboot.locale.available</code>.
     *
     * @param value the default locale
     */
    public void setDefault(Locale value) {
        this.defaultLocale = value;
    }

    /**
     * The locales served by the application.
     *
     * @return the locales
     */
    public Set<Locale> getAvailable() {
        return available;
    }

    /**
     * The locales served by the application.
     *
     * @param available the locales
     */
    public void setAvailable(Set<Locale> available) {
        this.available = available;
    }

    /**
     * The name of the cookie used to override the accepted locales in the <code>Accept</code> header.
     *
     * @return the cookie name
     */
    public String getCookie() {
        return cookie;
    }

    /**
     * The name of the cookie used to override the accepted locales in the <code>Accept</code> header.
     *
     * @param cookie the cookie name
     */
    public void setCookie(String cookie) {
        this.cookie = cookie;
    }
}

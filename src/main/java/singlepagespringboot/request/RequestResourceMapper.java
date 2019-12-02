package singlepagespringboot.request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import singlepagespringboot.properties.UIProperties;

import javax.validation.constraints.NotNull;
import java.io.File;
import java.util.Locale;

/**
 * Map a requested path to an application resource.
 *
 * @author Max Günther
 */
@Component
public class RequestResourceMapper {
    @Autowired private UIProperties uiProperties;

    /**
     * Maps a requested path to an application resource in the given locale, relative to the given file root. If the
     * path doesn't exist for the given locale then the index file (typically <code>index.html</code> unless configured
     * otherwise) for that locale is returned.
     *
     * @param fileRootProvider provides the file root
     * @param uri the path
     * @param locale the locale
     * @return the path
     */
    public File map(StaticFileRootProvider fileRootProvider, String uri, @NotNull Locale locale) {
        String localizedPath = String.format("%s%s", locale.toLanguageTag(), uri == null ? "/" : uri);
        File file = fileRootProvider.getFile(localizedPath);
        if (file.exists() && !file.isDirectory()) {
            return file;
        }
        return indexHtml(fileRootProvider, locale);
    }

    private File indexHtml(StaticFileRootProvider fileRootProvider, Locale locale) {
        String indexFileName = uiProperties.getIndexFileName();
        String localizedIndexPath = String.format("%s/%s", locale.toLanguageTag(), indexFileName);
        return fileRootProvider.getFile(localizedIndexPath);
    }
}

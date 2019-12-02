package singlepagespringboot.controllers;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import singlepagespringboot.request.StaticFileRootProvider;
import singlepagespringboot.request.VirtualHostPolicy;
import singlepagespringboot.utilities.io.FileUtils;
import singlepagespringboot.utilities.io.MockableIOStreamUtils;
import singlepagespringboot.request.RequestResourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Maps paths in fashion that is useful in single page JavaScript application.
 *
 * <p>
 * Maps a <code>path</code> to a localized path <code>&lt;localization&gt;/&lt;path&gt;</code>. If the file at the
 * mapped path doesn't exists then <code>&lt;localization&gt;/index.html</code> is used.
 *
 * @author Max Günther
 */
@RestController
@RequestMapping(path="/")
public class RootController {
    @Autowired private RequestResourceMapper requestResourceMapper;
    @Autowired private FileUtils fileUtils;
    @Autowired private MockableIOStreamUtils mockableIOStreamUtils;
    @Autowired private VirtualHostPolicy virtualHostPolicy;

    /**
     * Serves all application requests.
     *
     * @param request the request
     * @param locale the locale of the request
     * @return the response
     * @throws IOException if an IO related exception occurs on the way
     */
    @GetMapping(path="**")
    public ResponseEntity<InputStreamResource> get(
            HttpServletRequest request,
            Locale locale
    ) throws IOException {
        String uri = request.getRequestURI();
        StaticFileRootProvider fileRootProvider = virtualHostPolicy.getFileRootProvider(request);
        File file = requestResourceMapper.map(fileRootProvider, uri, locale);
        MediaType mediaType = fileUtils.probeMediaType(file);
        InputStreamResource resourceStream = mockableIOStreamUtils.inputStreamResourceFromFile(file);

        return ResponseEntity.ok()
                .contentLength(file.length())
                .contentType(mediaType)
                .body(resourceStream);
    }
}

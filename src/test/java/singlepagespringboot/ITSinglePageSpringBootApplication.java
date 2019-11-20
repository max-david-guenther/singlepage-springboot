package singlepagespringboot;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import singlepagespringboot.request.StaticFileRootProvider;

import javax.servlet.http.Cookie;
import java.io.File;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(properties={"singlepage-springboot.locale.available=en_US,de_DE"})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ITSinglePageSpringBootApplication {
    @Autowired private MockMvc mockMvc;
    @MockBean private StaticFileRootProvider staticFileRootProvider;

    @Before
    public void setUp() {
        ClassLoader classLoader = getClass().getClassLoader();
        File resourcesDir = new File(classLoader.getResource(".").getFile());
        when(staticFileRootProvider.getFile(any()))
            .thenAnswer(mock -> new File(resourcesDir, mock.getArgument(0)));
    }

    @Test
    public void get_root() throws Exception {
        mockMvc.perform(get("/"))
            .andExpect(content().contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    public void get_indexHtml() throws Exception {
        mockMvc.perform(get("/index.html"))
            .andExpect(content().contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    public void get_existingResources() throws Exception {
        mockMvc.perform(get("/style.css"))
            .andExpect(content().contentType("text/css"))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("background-color: black")));
    }

    @Test
    public void get_missingResources() throws Exception {
        mockMvc.perform(get("/some/path"))
            .andExpect(content().contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hello World")));
    }

    @Test
    public void get_localization() throws Exception {
        mockMvc.perform(get("/some/path").header("Accept-Language", "de-DE"))
            .andExpect(content().contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hallo Welt")));
    }

    @Test
    public void get_localizationCookie() throws Exception {
        mockMvc.perform(get("/some/path").cookie(new Cookie("Localization", "de-DE")))
            .andExpect(content().contentType(MediaType.TEXT_HTML))
            .andExpect(status().isOk())
            .andExpect(content().string(containsString("Hallo Welt")));
    }

    @Test
    public void get_unrecognizableContentType() throws Exception {
        mockMvc.perform(get("/unrecognizable.fileextension"))
            .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM))
            .andExpect(status().isOk());
    }
}

package singlepagespringboot.controllers.exceptions;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.web.context.request.WebRequest;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UTGlobalExceptionHandler {
    /* class under test */
    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;
    /* mocked dependencies */
    /* parameters */
    @Mock private WebRequest request;
    private FileNotFoundException fileNotFoundException;

    @Before
    public void setUp() {
        fileNotFoundException = new FileNotFoundException();
    }

    @Test
    public void handleNotFound() {
        // act
        var response = globalExceptionHandler.handleNotFound(fileNotFoundException, request);

        // assert
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }
}

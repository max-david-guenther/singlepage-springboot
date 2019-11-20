package singlepagespringboot.configuration;

import org.apache.tika.Tika;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UTTikaConfiguration {
    /* class under test */
    @InjectMocks private TikaConfiguration tikaConfiguration;

    @Test
    public void tika() {
        // act
        Tika tika = tikaConfiguration.tika();

        // assert
        assertThat(tika).isNotNull();
    }
}

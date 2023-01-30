import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import java.util.Base64;
import org.springframework.boot.test.mock.mockito.MockBean;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.request;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.springframework.web.client.RestTemplate;
import org.mockito.Mock;
import org.mockito.InjectMocks;

@DisplayName("Security unit tests")
@WebMvcTest
@Tag("security")
public class securityTest {
    private byte[] JPEG = hexStringToByteArray("ffd8ffe000104a464946000101000001");
    private byte[] GIF = new byte[]{0x47, 0x49, 0x46, 0x38, 0x39, 0x61, 0x00, 0x00, 0x01, 0x00, 0x00, (byte)0xff, 0x00, 0x00, 0x00, 0x00, 0x00, 0x01, 0x00, 0x01, 0x00, 0x00, 0x02, 0x00};

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    @Autowired
    private MockMvc mockMvc;

    //@MockBean
    //CapitalZeroController CapitalZeroController;

    @InjectMocks
    CapitalZeroController CapitalZeroController;

    // @Mock
    // private RestTemplate restTemplate;

    @Test
    public void should_Unauthorize1() throws Exception {
        byte[] bytes = JPEG;
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        ResponseEntity<byte[]> response=new ResponseEntity<byte[]>(bytes, headers, HttpStatus.OK);
        System.out.println(response);
        when(CapitalZeroController.perform_fetch("http://127.0.0.1/image.jpg")).thenReturn(response);
        mockMvc.perform(post("/upload").param("thumbnail","http://127.0.0.1/image.jpg"))
                //.andExpect(status().isUnauthorized());
                .andExpect(content().bytes(JPEG));
    }

}
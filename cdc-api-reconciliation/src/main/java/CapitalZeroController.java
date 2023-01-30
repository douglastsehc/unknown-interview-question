import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.time.Duration;
import java.util.Base64;

@RestController
public class CapitalZeroController {

    /**
     * Gets a thumbnail URL from GET request params
     * @return base64 encoded image tag
     */
    @RequestMapping("/")
    public ResponseEntity index() {
        return new ResponseEntity<>("<h1>Welcome to Capital Zero!</h1><p>Enter a photo URL to update your profile</p><br><form action='/upload'>Photo URL (jpg only): <input type='input' name='thumbnail' placeholder='http://secdim.com/image.jpg'><br><input type='submit' value='Submit'></form>",
                HttpStatus.OK);
    }

    @RequestMapping("/upload")
    public ResponseEntity render(@RequestParam String thumbnail) {

        try {
            String image = fetch(thumbnail);
            return new ResponseEntity<>("<img src='data:image/jpeg;charset=utf-8;base64,"+image+"'/>",HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.toString(), HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Download content of the provided URL
     * @return Content of the image in base64 format
     */
    public String fetch(String url) throws IllegalArgumentException {
        // Allow only JPEG images
        if (url.indexOf(".jpg") == -1) {
            throw new IllegalArgumentException("You must supply a URL with a JPEG image");
        }
        // Disallow localhost connections
        if (url.indexOf("localhost") != -1) {
            throw new IllegalArgumentException("You cannot connect to localhost");
        }

        System.out.println("[i] Downloading image from "+url);
        ResponseEntity<byte[]> response = perform_fetch(url);
        System.out.println(Base64.getEncoder().encodeToString(response.getBody()));
        return Base64.getEncoder().encodeToString(response.getBody());
    }

    public ResponseEntity<byte[]> perform_fetch(String url) {
        RestTemplate restTemplate = restTemplate(new RestTemplateBuilder());
        ResponseEntity<byte[]> response = restTemplate.getForEntity(url, byte[].class);
        return response;
    }

    // The following is a timeout for tests that may take too long to complete.
    // Where possible do not edit.
    private RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofDays(5000))
                .setReadTimeout(Duration.ofDays(5000))
                .build();
    }
}
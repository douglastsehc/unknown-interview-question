package service;

import io.netty.handler.codec.http.HttpResponseStatus;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Base64;

public class TestService {

    public String fetch()throws IllegalArgumentException{
        ResponseEntity<byte[]> response = perform_fetch(HttpStatus.BAD_GATEWAY);
        return Base64.getEncoder().encodeToString(response.getBody());
    }

    public ResponseEntity<byte[]> perform_fetch(HttpStatus httpStatus){
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}

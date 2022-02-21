package ee.mihkel.webshop.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.Date;

@Data
public class ErrorResponse {
    private String message;
    private Date timeStamp;
    private HttpStatus httpStatus;
}

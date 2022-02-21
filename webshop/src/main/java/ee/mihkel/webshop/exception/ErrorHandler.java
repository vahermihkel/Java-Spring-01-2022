package ee.mihkel.webshop.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.util.Date;
import java.util.NoSuchElementException;

// annab sellele klassile võimekuse exceptioneid kinni püüda
// läheb kõikide kontrollite külge
@ControllerAdvice
public class ErrorHandler {

        // ReponseEntity koosneb kogu HTTP tagastusest
        // a) body b) headers c) status d).....
    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleException(NoSuchElementException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Otsitud kirjet ei leitud");
        response.setTimeStamp(new Date());
        response.setHttpStatus(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentTypeMismatchException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Lisati päringusse parameetrina mittesobiv tüüp");
        response.setTimeStamp(new Date());
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleException(DataIntegrityViolationException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Mitteunikaalne ribakood");
        response.setTimeStamp(new Date());
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler()
    public ResponseEntity<ErrorResponse> handleException(ConstraintViolationException e) {
        ErrorResponse response = new ErrorResponse();
        response.setMessage("Mitteunikaalne ribakood");
        response.setTimeStamp(new Date());
        response.setHttpStatus(HttpStatus.BAD_REQUEST);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

//    @ExceptionHandler()
//    public ResponseEntity<ErrorResponse> handleException(Exception e) {
//        ErrorResponse response = new ErrorResponse();
//        response.setMessage("Juhtus tundmatu error");
//        response.setTimeStamp(new Date());
//        response.setHttpStatus(HttpStatus.BAD_REQUEST);
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    // Kasutaja entity
    // Siduma temaga tellimus
    // Selle kasutaja tellimused---
    // Order WHERE isik, JOIN isik, JOIN product
    // 15 minutilise Cache

    // Cache -- Google Guava -- järgmine kord

    // Mockito
    // Unit Testid

    // Spring Security
    // JSON Web Token

    // Smart ID
    // Mobiil ID

}

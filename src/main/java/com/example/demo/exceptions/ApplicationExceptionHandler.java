package com.example.demo.exceptions;
import com.example.demo.apiResponse.Constants;
import com.example.demo.apiResponse.OperationResponse;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(NoLoanFoundException.class)
    public ResponseEntity<OperationResponse> handleNoLoanFoundException(NoLoanFoundException ex){
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidAccountNumberException.class)
    public ResponseEntity<OperationResponse> handleInvalidAccountNumberException(InvalidAccountNumberException ex){
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, ex.getLocalizedMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NonUniqueResultException.class)
    public ResponseEntity<OperationResponse> handleNonUniqueResultException(NonUniqueResultException ex){
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<OperationResponse> handleIllegalStateException(IllegalStateException ex){
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(GenericServiceException.class)
    public ResponseEntity<OperationResponse> handleGenericServiceException(GenericServiceException ex) {
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

    }
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<OperationResponse> handleMissingServletRequestParameterException(MissingServletRequestParameterException ex) {
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<OperationResponse> handleHttpClientErrorExceptionException(HttpClientErrorException ex) {
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, ex.getLocalizedMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<OperationResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException ex) {
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, ex.getLocalizedMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<OperationResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, "Required request body is missing"), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(ExpiredJwtException.class)
    public ResponseEntity<OperationResponse> handleExpiredJwtException(ExpiredJwtException ex) {
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, ex.getLocalizedMessage()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<OperationResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
        return new ResponseEntity<>(new OperationResponse(Constants.OPERATION_FAILURE_CODE, Constants.OPERATION_FAILED_DESCRIPTION, "Invalid account number"), HttpStatus.FORBIDDEN);
    }
}


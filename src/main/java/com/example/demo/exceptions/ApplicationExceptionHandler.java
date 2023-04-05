package com.example.demo.exceptions;
import com.example.demo.apiResponse.Constants;
import com.example.demo.apiResponse.OperationResponse;
import jakarta.persistence.NonUniqueResultException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

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



}


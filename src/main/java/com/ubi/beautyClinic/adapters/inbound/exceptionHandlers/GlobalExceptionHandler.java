package com.ubi.beautyClinic.adapters.inbound.exceptionHandlers;

import com.ubi.beautyClinic.application.core.exceptions.BusinessLogicException;
import com.ubi.beautyClinic.application.core.exceptions.ObjectNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource msgSrc;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        return handleExceptionInternal(ex, buildErrorBody(ex, status), headers, status, request);
    }

    private Error buildErrorBody(MethodArgumentNotValidException exception, HttpStatusCode status){

        var error = new Error();
        error.setStatus(status.value());
        error.setDateTime(OffsetDateTime.now());
        error.setTitle("Error: one or more fields are incorrect!");
        error.setFields(returnErrorList(exception));

        return error;
    }

    private List<Field> returnErrorList(MethodArgumentNotValidException exception){

        var errors = new ArrayList<Field>();
        for(ObjectError error : exception.getBindingResult().getAllErrors()){
            String fieldName = ((FieldError) error).getField();
            String message = msgSrc.getMessage(error, LocaleContextHolder.getLocale());
            errors.add(new Field(fieldName, message));
        }
        return errors;
    }

    private Error buildErrorBody(String message, HttpStatus status){

        var error = new Error();
        error.setStatus(status.value());
        error.setDateTime(OffsetDateTime.now());
        error.setTitle(message);
        return error;
    }

    @ExceptionHandler(BusinessLogicException.class)
    public ResponseEntity<Object> handleBusinessLogicException(BusinessLogicException exception, WebRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return handleExceptionInternal(exception, buildErrorBody(exception.getMessage(), status), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Object> handleObjectNotFoundException(ObjectNotFoundException exception, WebRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        return handleExceptionInternal(exception, buildErrorBody(exception.getMessage(), status), new HttpHeaders(), status, request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException ex) {
        List<Field> errorFields = new ArrayList<>();

        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            String fieldName = violation.getPropertyPath().toString();
            String errorMessage = violation.getMessage();
            Field errorField = new Field(fieldName, errorMessage);
            errorFields.add(errorField);
        }

        Error error = new Error();
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setDateTime(OffsetDateTime.now());
        error.setTitle("Validation error!");
        error.setFields(errorFields);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}

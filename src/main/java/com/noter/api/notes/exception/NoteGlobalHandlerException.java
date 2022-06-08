package com.noter.api.notes.exception;

import com.noter.api.notes.response.NoteResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class NoteGlobalHandlerException extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
        final MethodArgumentNotValidException e,
        final HttpHeaders headers,
        final HttpStatus status,
        final WebRequest request
    ) {
        final StringBuilder messageBuilder = new StringBuilder();

        for (FieldError fieldError : e.getFieldErrors()) {
            messageBuilder
                    .append("'")
                    .append(fieldError.getField())
                    .append("' ")
                    .append(fieldError.getDefaultMessage())
                    .append("; ");
        }
        messageBuilder.deleteCharAt(messageBuilder.length() - 1);

        return new ResponseEntity<>(
                new NoteResponse(
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        messageBuilder.toString()),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Object> handleNoteNotFoundException(final NoteNotFoundException e) {
        final NoteResponse response = new NoteResponse(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Object> handleConstraintViolationException(final ConstraintViolationException e) {
        final NoteResponse response = new NoteResponse(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<Object> handleAllUncaughtException(final Exception e) {
        final NoteResponse response = new NoteResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

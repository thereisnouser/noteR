package com.noter.api.notes.exception;

import com.noter.api.notes.dto.NoteResponseDto;
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

@RestControllerAdvice
public class GlobalHandlerException extends ResponseEntityExceptionHandler {

    @Override
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    protected ResponseEntity handleMethodArgumentNotValid(
        final MethodArgumentNotValidException e,
        final HttpHeaders headers,
        final HttpStatus status,
        final WebRequest request
    ) {
        final StringBuilder messageBuilder = new StringBuilder();

        for (FieldError fieldError : e.getFieldErrors()) {
            messageBuilder.append("'" + fieldError.getField() + "' field is invalid; ");
        }
        messageBuilder.deleteCharAt(messageBuilder.length() - 1);

        return new ResponseEntity(
            new NoteResponseDto(
                HttpStatus.UNPROCESSABLE_ENTITY,
                messageBuilder.toString()),
            HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(NoteNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleNoteNotFoundException(final NoteNotFoundException e) {
        final NoteResponseDto response = new NoteResponseDto(HttpStatus.NOT_FOUND, e.getMessage());
        return new ResponseEntity(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity handleBadRequestException(final BadRequestException e) {
        final NoteResponseDto response = new NoteResponseDto(HttpStatus.BAD_REQUEST, e.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}

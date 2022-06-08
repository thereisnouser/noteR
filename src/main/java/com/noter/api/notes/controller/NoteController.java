package com.noter.api.notes.controller;

import com.noter.api.notes.dto.NoteCreateDto;
import com.noter.api.notes.dto.NoteResponseDto;
import com.noter.api.notes.dto.NoteUpdateDto;
import com.noter.api.notes.response.NoteResponse;
import com.noter.api.notes.service.NoteService;
import com.noter.api.shared.EndpointPath;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(EndpointPath.NOTES)
@Validated
public class NoteController {

    private final NoteService service;

    @Autowired
    public NoteController(final NoteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<Object> getAllNotes() {
        final List<NoteResponseDto> notes = this.service.getAllNotes();
        return createSuccessResponseWithData(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNote(@PathVariable("id") @Min(1) final Long id) {
        final List<NoteResponseDto> note = this.service.getNoteById(id);
        return createSuccessResponseWithData(note);
    }

    @PostMapping
    public ResponseEntity<Object> createNote(@Valid @RequestBody final NoteCreateDto dto) {
        final List<NoteResponseDto> createdNote = this.service.createNote(dto);
        return createSuccessResponseWithData(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateNote(@Valid @RequestBody final NoteUpdateDto dto) {
        final List<NoteResponseDto> updatedNote = this.service.updateNote(dto);
        return createSuccessResponseWithData(updatedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNote(@PathVariable("id") @Min(1) final Long id) {
        final List<NoteResponseDto> removedNote = this.service.deleteNoteById(id);
        return createSuccessResponseWithData(removedNote);
    }

    private ResponseEntity<Object> createSuccessResponseWithData(final List<NoteResponseDto> data) {
        final NoteResponse response = new NoteResponse(HttpStatus.OK, "Success", data);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

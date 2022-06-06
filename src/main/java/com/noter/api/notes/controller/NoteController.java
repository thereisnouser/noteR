package com.noter.api.notes.controller;

import com.noter.api.notes.dto.NoteRequestDto;
import com.noter.api.notes.dto.NoteResponseDto;
import com.noter.api.notes.entity.Note;
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

    private final NoteService noteService;

    @Autowired
    public NoteController(final NoteService noteService) {
        this.noteService = noteService;
    }

    @GetMapping
    public ResponseEntity getAllNotes() {
        final List<Note> notes = this.noteService.getAllNotes();
        return createSuccessResponseWithData(notes);
    }

    @GetMapping("/{id}")
    public ResponseEntity getNote(@PathVariable("id") @Min(1) final Long id) {
        final List<Note> note = this.noteService.getNoteById(id);
        return createSuccessResponseWithData(note);
    }

    @PostMapping
    public ResponseEntity createNote(@Valid @RequestBody final NoteRequestDto requestDto) {
        final List<Note> createdNote = this.noteService.createNote(requestDto);
        return createSuccessResponseWithData(createdNote);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateNote(@Valid @RequestBody final NoteRequestDto requestDto) {
        final List<Note> updatedNote = this.noteService.updateNote(requestDto);
        return createSuccessResponseWithData(updatedNote);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteNote(@PathVariable("id") @Min(1) final Long id) {
        final List<Note> removedNote = this.noteService.deleteNoteById(id);
        return createSuccessResponseWithData(removedNote);
    }

    private ResponseEntity createSuccessResponseWithData(final List<Note> data) {
        final NoteResponseDto response = new NoteResponseDto(HttpStatus.OK, "Success", data);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}

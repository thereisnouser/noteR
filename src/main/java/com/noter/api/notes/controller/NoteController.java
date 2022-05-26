package com.noter.api.notes.controller;

import com.noter.api.notes.dto.NoteDto;
import com.noter.api.notes.exception.BadRequestException;
import com.noter.api.notes.exception.NoteNotFoundException;
import com.noter.api.notes.service.NoteService;
import com.noter.api.shared.EndpointPath;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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
public class NoteController {
    
    private final NoteService noteService;

    @Autowired
	public NoteController(final NoteService noteService) {
		this.noteService = noteService;
	}
    
    @GetMapping
    public ResponseEntity getAllNotes() {
		final List<NoteDto> notes = this.noteService.getAllNotes();
		return ResponseEntity.ok().body(notes);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity getNote(@PathVariable("id") final Long id) {
		try {
			final NoteDto note = this.noteService.getNoteById(id);
			return ResponseEntity.ok().body(note);
		} catch (BadRequestException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NoteNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
    }
    
    @PostMapping
    public ResponseEntity createNote(@RequestBody final NoteDto noteDto) {
		final NoteDto createdNote = this.noteService.createNote(noteDto);
		return ResponseEntity.ok().body(createdNote);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateNote(@RequestBody final NoteDto noteDto) {
		try {
			final NoteDto updatedNote = this.noteService.updateNote(noteDto);
			return ResponseEntity.ok().body(updatedNote);
		} catch (BadRequestException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NoteNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteNote(@PathVariable("id") final Long id) {
		try {
			final NoteDto removedNote = this.noteService.deleteNoteById(id);
			return ResponseEntity.ok().body(removedNote);
		} catch (BadRequestException e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		} catch (NoteNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
    }
}

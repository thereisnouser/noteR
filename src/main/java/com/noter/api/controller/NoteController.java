package com.noter.api.controller;

import com.noter.api.model.Note;
import com.noter.api.service.NoteService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/notes")
public class NoteController {
    
    @Autowired
    private NoteService noteService;
    
    @GetMapping
    public List<Note> getAllNotes() {
        return noteService.getAllNotes();
    }
    
    @GetMapping("/{id}")
    public Note getNote(@PathVariable("id") final int id) {
        return noteService.getNote(id);
    }
    
    @PostMapping
    public ResponseEntity createNote(@RequestBody final Note note) {
        return noteService.createNote(note);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity updateNote(@RequestBody final Note note) {
        return noteService.updateNote(note);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity deleteNote(@PathVariable("id") final int id) {
        return noteService.deleteNote(id);
    }
}

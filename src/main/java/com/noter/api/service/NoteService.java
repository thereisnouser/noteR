package com.noter.api.service;

import com.noter.api.model.Note;
import java.util.List;
import org.springframework.http.ResponseEntity;

public interface NoteService {
        
    public List<Note> getAllNotes();
    
    public Note getNote(final int id);
    
    public ResponseEntity createNote(final Note note);
    
    public ResponseEntity updateNote(final Note note);
    
    public ResponseEntity deleteNote(final int id);
}

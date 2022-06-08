package com.noter.api.notes.dao;

import com.noter.api.notes.entity.Note;
import java.util.List;

public interface NoteDao {
    
    List<Note> getAllNotes();
    
    Note getNoteById(Long id);
    
    void createNote(Note note);
    
    void updateNote(Note note);
    
    void deleteNote(Note note);
}

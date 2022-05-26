package com.noter.api.notes.dao;

import com.noter.api.notes.entity.Note;
import java.util.List;

public interface NoteDao {
    
    List<Note> getAllNotes();
    
    Note getNoteById(final Long id);
    
    void createNote(final Note note);
    
    void updateNote(final Note note);
    
    void deleteNote(final Note note);
}

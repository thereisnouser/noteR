package com.noter.api.repository;

import com.noter.api.model.Note;
import java.util.List;

public interface NoteRepository {
    
    public List<Note> getAllNotes();
    
    public Note getNote(final int id);
    
    public void createNote(final Note note);
    
    public void updateNote(final Note note);
    
    public void deleteNote(final int id);
}

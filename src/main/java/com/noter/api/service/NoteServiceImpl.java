package com.noter.api.service;

import com.noter.api.model.Note;
import com.noter.api.repository.NoteRepository;
import java.util.Date;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
    
    @Autowired
    private NoteRepository noteRepository;
    
    @Override
    public List<Note> getAllNotes() {
        return noteRepository.getAllNotes();
    }

    @Override
    public Note getNote(final int id) {
        return noteRepository.getNote(id);
    }

    @Override
    public ResponseEntity createNote(final Note note) {
        final Date currentDate = new Date();
        
        if (note.getCreatedDate() == null) {
            note.setCreatedDate(currentDate);
        }
        if (note.getUpdatedDate() == null) {
            note.setUpdatedDate(currentDate);
        }
        
        noteRepository.createNote(note);
        
        return ResponseEntity.ok("Note is created successfully!");
    }

    @Override
    public ResponseEntity updateNote(final Note note) {
        final boolean noteToUpdateIsExist = this.getNote(note.getId()) != null;
        
        if (!noteToUpdateIsExist) {
            return ResponseEntity.badRequest().body("Set correct value for the 'id'");
        } else if (note.getCreatedDate() != null) {
            return ResponseEntity.badRequest().body("You can not modify the 'createdDate'");
        }
        
        noteRepository.updateNote(note);
        
        return ResponseEntity.ok("Note is updated successfully!");
    }

    @Override
    public ResponseEntity deleteNote(final int id) {
        if (this.getNote(id) == null) {
            return ResponseEntity.badRequest().body("Set correct value for the 'id'");
        }
        
        noteRepository.deleteNote(id);
        
        return ResponseEntity.ok("Note is removed successfully!");
    }
}

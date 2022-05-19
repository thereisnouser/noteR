package com.noter.api.repository;

import com.noter.api.model.Note;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class NoteRepositoryImpl implements NoteRepository {
    
    @Autowired
    private EntityManager entityManager;
    
    @Override
    public List<Note> getAllNotes() {
        return entityManager.createQuery("FROM Note").getResultList();
    }
    
    @Override
    public Note getNote(final int id) {
        return entityManager.find(Note.class, id);
    }
    
    @Override
    public void createNote(final Note note) {
        entityManager.persist(note);
    }
    
    @Override
    public void updateNote(final Note note) {
        entityManager.merge(note);
    }
    
    @Override
    public void deleteNote(final int id) {
        Note note = this.getNote(id);
        entityManager.remove(note);
    }
}

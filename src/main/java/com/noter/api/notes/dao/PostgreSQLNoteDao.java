package com.noter.api.notes.dao;

import com.noter.api.notes.entity.Note;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PostgreSQLNoteDao implements NoteDao {
    
    private final EntityManager entityManager;

    @Autowired
	public PostgreSQLNoteDao(final EntityManager entityManager) {
		this.entityManager = entityManager;
	}
    
    @Override
    public List<Note> getAllNotes() {
        return entityManager.createQuery("FROM Note").getResultList();
    }
    
    @Override
    public Note getNoteById(final Long id) {
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
    public void deleteNote(final Note note) {
        entityManager.remove(note);
    }
}

package com.noter.api.notes.dao;

import com.noter.api.notes.entity.Note;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@DataJpaTest
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class PostgreSQLNoteDaoTest {

    @Autowired
    private EntityManager entityManager;
    private NoteDao dao;

    @BeforeEach
    void setUp() {
        dao = new PostgreSQLNoteDao(entityManager);
    }

    @Test
    void getAllNotes_notesAreNotCreated_returnsEmptyList() {

        // When
        final List<Note> result = dao.getAllNotes();

        // Then
        assertThat(result).isEmpty();
    }

    @Test
    void getAllNotes_createdTwoNotes_returnsCreatedTwoNotes() {

        // Given
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note note1 = new Note("text1", currentUTCDateTime, currentUTCDateTime);
        final Note note2 = new Note("text2", currentUTCDateTime, currentUTCDateTime);
        dao.createNote(note1);
        dao.createNote(note2);

        // When
        final List<Note> result = dao.getAllNotes();

        // Then
        assertThat(result).containsAll(Arrays.asList(note1, note2));
    }

    @Test
    void getNoteById_idIsNotExist_returnsNull() {

        // Given
        final Long id = 1L;

        // When
        final Note result = dao.getNoteById(id);

        // Then
        assertThat(result).isNull();
    }

    @Test
    void getNoteById_idIsExist_returnsNoteWithTheId() {

        // Given
        final Long id = 1L;
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note note = new Note("text", currentUTCDateTime, currentUTCDateTime);
        dao.createNote(note);

        // When
        final Note result = dao.getNoteById(id);

        // Then
        assertThat(result).isEqualTo(note);
    }

    @Test
    void updateNote_createdNoteWithTextEqualsToAbcAndNoteWithTextEqualsToCbaAsParameter_createdNoteIsUpdatedToTextEqualsToCba() {

        // Given
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note note = new Note("abc", currentUTCDateTime, currentUTCDateTime);
        dao.createNote(note);

        // When
        final Long id = 1L;
        final Note updatedNote = new Note(id, "cba", currentUTCDateTime, currentUTCDateTime);
        dao.updateNote(updatedNote);

        // Then
        final List<Note> result = dao.getAllNotes();
        assertThat(result).containsOnly(updatedNote);
    }

    @Test
    void deleteNote_createdNoteAndNoteEqualsToCreatedNoteAsParameter_createdNoteIsRemoved() {

        // Given
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note note = new Note("text", currentUTCDateTime, currentUTCDateTime);
        dao.createNote(note);

        // When
        dao.deleteNote(note);

        // Then
        final List<Note> result = dao.getAllNotes();
        assertThat(result).isEmpty();
    }
}

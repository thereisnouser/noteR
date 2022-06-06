package com.noter.api.notes.service;

import com.noter.api.notes.dao.NoteDao;
import com.noter.api.notes.dto.NoteRequestDto;
import com.noter.api.notes.entity.Note;
import com.noter.api.notes.exception.BadRequestException;
import com.noter.api.notes.exception.NoteNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.BDDMockito.given;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NoteServiceTest {

    @Mock
    private NoteDao dao;
    private NoteService service;

    @BeforeEach
    void setUp() {
        service = new NoteService(dao);
    }

    @Test
    void getAllNotes_createdTwoNotes_returnsListOfTwoNotes() {

        // Given
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note note1 = new Note("text1", currentUTCDateTime, currentUTCDateTime);
        final Note note2 = new Note("text2", currentUTCDateTime, currentUTCDateTime);

        given(dao.getAllNotes()).willReturn(Arrays.asList(note1, note2));

        // When
        final List<Note> result = service.getAllNotes();

        // Then
        assertThat(result).hasOnlyElementsOfType(Note.class);
    }

    @Test
    void getNoteById_noteWithIdParameterDoesNotExist_throwsNoteNotFoundException() {

        // Given
        final Long id = 1L;

        // When
        // Then
        assertThatThrownBy(() -> service.getNoteById(id))
            .isInstanceOf(NoteNotFoundException.class)
            .hasMessageContaining("Note with id = " + id + " is not found");
    }

    @Test
    void getNoteById_noteWithIdParameterIsExist_returnsListWithRequiredIdNote() {

        // Given
        final Long id = 1L;
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note note = new Note("text", currentUTCDateTime, currentUTCDateTime);

        given(dao.getNoteById(id)).willReturn(note);

        // When
        final List<Note> result = service.getNoteById(id);

        // Then
        assertThat(result).isEqualTo(Arrays.asList(note));
    }

    @Test
    void createNote_dtoAsParameterIsNotNull_createdAtAndUpdatedAtFieldsAreFilledWithNotNullValue() {

        // Given
        final NoteRequestDto dto = new NoteRequestDto("text");

        // When
        service.createNote(dto);

        // Then
        final ArgumentCaptor<Note> noteArgumentCaptor = ArgumentCaptor.forClass(Note.class);
        verify(dao).createNote(noteArgumentCaptor.capture());

        final Note capturedNote = noteArgumentCaptor.getValue();
        assertThat(capturedNote.getCreatedAt()).isNotNull();
        assertThat(capturedNote.getUpdatedAt()).isNotNull();
        assertThat(capturedNote.getUpdatedAt()).isEqualTo(capturedNote.getUpdatedAt());
    }

    @Test
    void updateNote_createdNoteWithId1_updatedAtFieldOfCreatedNoteIsUpdated() {

        // Given
        final Long id = 1L;
        final LocalDateTime createdNoteDateTime = LocalDateTime.of(2001, 1, 1, 0, 0, 0);
        final Note createdNote = new Note(id, "abc", createdNoteDateTime, createdNoteDateTime);
        final NoteRequestDto dto = new NoteRequestDto(id, "cba");

        given(dao.getNoteById(id)).willReturn(createdNote);

        // When
        service.updateNote(dto);

        // Then
        final ArgumentCaptor<Note> noteArgumentCaptor = ArgumentCaptor.forClass(Note.class);
        verify(dao).updateNote(noteArgumentCaptor.capture());

        final Note capturedNote = noteArgumentCaptor.getValue();
        assertThat(capturedNote.getText()).isEqualTo(dto.getText());
        assertThat(capturedNote.getCreatedAt()).isEqualTo(createdNote.getCreatedAt());
        assertThat(capturedNote.getUpdatedAt()).isNotEqualTo(createdNote.getUpdatedAt());
    }

    @Test
    void updateNote_dtoIdIsNull_throwsBadRequestException() {

        // Given
        final NoteRequestDto dto = new NoteRequestDto("text");

        // When
        // Then
        assertThatThrownBy(() -> service.updateNote(dto))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining("Field 'id' can not be empty");
    }

    @Test
    void deleteNote_noteWithIdAsParameterIsNotExist_throwsNoteNotFoundException() {

        // Given
        final Long id = 1L;

        // When
        // Then
        assertThatThrownBy(() -> service.deleteNoteById(id))
            .isInstanceOf(NoteNotFoundException.class)
            .hasMessageContaining("Note with id = " + id + " is not found");
    }
}

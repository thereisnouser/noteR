package com.noter.api.notes.service;

import com.noter.api.notes.dao.NoteDao;
import com.noter.api.notes.dto.NoteDto;
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
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class NoteServiceImplTest {

    @Mock private NoteDao dao;
    private NoteService service;

    @BeforeEach
    void setUp() {
        service = new NoteServiceImpl(dao);
    }

    @Test
    void getAllNotes_createdTwoNotes_returnsCreatedTwoNotesMappedToNoteDto() {

        // Given
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note note1 = new Note("text1", currentUTCDateTime, currentUTCDateTime);
        final Note note2 = new Note("text2", currentUTCDateTime, currentUTCDateTime);

        given(dao.getAllNotes()).willReturn(Arrays.asList(note1, note2));

        // When
        final List<NoteDto> result = service.getAllNotes();

        // Then
        assertThat(result).hasOnlyElementsOfType(NoteDto.class);
    }

    @Test
    void getNoteById_idAsParameterIsNull_throwsBadRequstException() {

        // Given
        final Long id = null;

        // When
        // Then
        assertThatThrownBy(() -> service.getNoteById(id))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining("Set correct value for the 'id'");
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
    void getNoteById_noteWithIdParameterIsExist_returnsNoteWithRequiredIdMappedToNoteDto() {

        // Given
        final Long id = 1L;
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note note = new Note("text", currentUTCDateTime, currentUTCDateTime);

        given(dao.getNoteById(id)).willReturn(note);

        // When
        final NoteDto result = service.getNoteById(id);

        // Then
        assertThat(result).isEqualTo(new NoteDto(note));
    }

    @Test
    void createNote_dtoAsParameterIsNull_throwsBadRequestException() {

        // Given
        final NoteDto dto = null;

        // When
        // Then
        assertThatThrownBy(() -> service.createNote(dto))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining("You can't create a note with empty 'text'");
    }

    @Test
    void createNote_textInDtoAsParameterIsEmpty_throwsBadRequestException() {

        // Given
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final NoteDto dto = new NoteDto(1L, "", currentUTCDateTime, currentUTCDateTime);

        // When
        // Then
        assertThatThrownBy(() -> service.createNote(dto))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining("You can't create a note with empty 'text'");
    }

    @Test
    void createNote_dtoAsParameterIsNotNull_daoCreateNoteMethodIsInvokedWithMappedNoteDtoToNote() {

        // Given
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final NoteDto dto = new NoteDto(1L, "text", currentUTCDateTime, currentUTCDateTime);

        // When
        service.createNote(dto);

        // Then
        final ArgumentCaptor<Note> noteArgumentCaptor = ArgumentCaptor.forClass(Note.class);
        verify(dao).createNote(noteArgumentCaptor.capture());

        final Note capturedNote = noteArgumentCaptor.getValue();
        assertThat(capturedNote).isInstanceOf(Note.class);
    }

    @Test
    void createNote_dtoAsParameterIsNotNull_dtoCreatedAtAndUpdatedAtFieldsAreReplacedWithNewLocalDateTime() {

        // Given
        final LocalDateTime customDateTime = LocalDateTime.of(2001, 1, 1, 0, 0, 0);
        final NoteDto dto = new NoteDto(1L, "text", customDateTime, customDateTime);

        // When
        service.createNote(dto);

        // Then
        final ArgumentCaptor<Note> noteArgumentCaptor = ArgumentCaptor.forClass(Note.class);
        verify(dao).createNote(noteArgumentCaptor.capture());

        final Note capturedNote = noteArgumentCaptor.getValue();
        assertThat(capturedNote.getCreatedAt()).isNotEqualTo(dto.getCreatedAt());
        assertThat(capturedNote.getUpdatedAt()).isNotEqualTo(dto.getUpdatedAt());
        assertThat(capturedNote.getUpdatedAt()).isEqualTo(capturedNote.getUpdatedAt());
    }

    @Test
    void createNote_dtoAsParameterIsNotNull_returnsNewNoteDtoMappedFromCreatedNote() {

        // Given
        final LocalDateTime customDateTime = LocalDateTime.of(2001, 1, 1, 0, 0, 0);
        final NoteDto dto = new NoteDto(1L, "text", customDateTime, customDateTime);

        // When
        final NoteDto result = service.createNote(dto);

        // Then
        assertThat(result).isNotEqualTo(dto);
    }

    @Test
    void updateNote_dtoAsParameterIsNull_throwsBadRequestException() {

        // Given
        final NoteDto dto = null;

        // When
        // Then
        assertThatThrownBy(() -> service.updateNote(dto))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining("You can't set empty 'text'");
    }

    @Test
    void updateNote_textInDtoAsParameterIsEmpty_throwsBadRequestException() {

        // Given
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final NoteDto dto = new NoteDto(1L, "", currentUTCDateTime, currentUTCDateTime);

        // When
        // Then
        assertThatThrownBy(() -> service.updateNote(dto))
            .isInstanceOf(BadRequestException.class)
            .hasMessageContaining("You can't set empty 'text'");
    }

    @Test
    void updateNote_dtoAsParameterIsNotNull_serviceGetNoteByIdIsInvoked() {

        // Given
        final Long id = 1L;
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final NoteDto dto = new NoteDto(id, "text", currentUTCDateTime, currentUTCDateTime);

        final NoteService spyService = spy(service);
        doReturn(dto).when(spyService).getNoteById(id);

        // When
        spyService.updateNote(dto);

        // Then
        verify(spyService).getNoteById(id);
    }

    @Test
    void updateNote_allDtoFieldsAreFilledNoteWithRequiredIdExist_daoUpdateNoteIsInvokedWithCreatedNoteThatHasOnlyTextFieldFromNoteDto() {

        // Given
        final Long id = 1L;
        final LocalDateTime customDateTime = LocalDateTime.of(2001, 1, 1, 0, 0, 0);
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final NoteDto createdNote = new NoteDto(id, "abc", customDateTime, customDateTime);
        final NoteDto dto = new NoteDto(id, "cba", currentUTCDateTime, currentUTCDateTime);

        final NoteService spyService = spy(service);
        doReturn(createdNote).when(spyService).getNoteById(id);

        // When
        spyService.updateNote(dto);

        // Then
        final ArgumentCaptor<Note> noteArgumentCaptor = ArgumentCaptor.forClass(Note.class);
        verify(dao).updateNote(noteArgumentCaptor.capture());

        final Note capturedNote = noteArgumentCaptor.getValue();
        assertThat(capturedNote.getText()).isEqualTo(dto.getText());
        assertThat(capturedNote.getCreatedAt()).isEqualTo(createdNote.getCreatedAt());
        assertThat(capturedNote.getUpdatedAt()).isNotEqualTo(dto.getUpdatedAt());
    }

    @Test
    void updateNote_dtoAsParameterIsNotNullNoteWithRequiredIdExist_returnsNewNoteDtoMappedFromCreatedNote() {

        // Given
        final Long id = 1L;
        final LocalDateTime customDateTime = LocalDateTime.of(2001, 1, 1, 0, 0, 0);
        final NoteDto dto = new NoteDto(id, "text", customDateTime, customDateTime);

        final NoteService spyService = spy(service);
        doReturn(dto).when(spyService).getNoteById(id);

        // When
        final NoteDto result = spyService.updateNote(dto);

        // Then
        assertThat(result).isNotEqualTo(dto);
    }

    @Test
    void deleteNote_idIsNotNull_serviceGetNoteByIdIsInvoked() {

        // Given
        final Long id = 1L;
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final NoteDto createdNoteDto = new NoteDto(id, "text", currentUTCDateTime, currentUTCDateTime);

        final NoteService spyService = spy(service);
        doReturn(createdNoteDto).when(spyService).getNoteById(id);

        // When
        spyService.deleteNoteById(id);

        // Then
        verify(spyService).getNoteById(id);
    }

    @Test
    void deleteNote_noteWithIdAsParameterExist_daoDeleteNoteIsInvokedWithNoteMappedFromNoteDtoWithRequiredId() {

        // Given
        final Long id = 1L;
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final NoteDto createdNoteDto = new NoteDto(id, "text", currentUTCDateTime, currentUTCDateTime);
        final Note noteToRemove = new Note(createdNoteDto);

        final NoteService spyService = spy(service);
        doReturn(createdNoteDto).when(spyService).getNoteById(id);

        // When
        spyService.deleteNoteById(id);

        // Then
        final ArgumentCaptor<Note> noteArgumentCaptor = ArgumentCaptor.forClass(Note.class);
        verify(dao).deleteNote(noteArgumentCaptor.capture());

        final Note capturedNote = noteArgumentCaptor.getValue();
        assertThat(capturedNote).isEqualTo(noteToRemove);
    }

    @Test
    void deleteNote_noteWithIdAsParameterExist_returnsNoteDtoResultFromServiceGetNoteById() {

        // Given
        final Long id = 1L;
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final NoteDto createdNoteDto = new NoteDto(id, "text", currentUTCDateTime, currentUTCDateTime);

        final NoteService spyService = spy(service);
        doReturn(createdNoteDto).when(spyService).getNoteById(id);

        // When
        final NoteDto result = spyService.deleteNoteById(id);

        // Then
        assertThat(result).isEqualTo(createdNoteDto);
    }
}

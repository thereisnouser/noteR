package com.noter.api.notes.service;

import com.noter.api.notes.dao.NoteDao;
import com.noter.api.notes.dto.NoteCreateDto;
import com.noter.api.notes.dto.NoteResponseDto;
import com.noter.api.notes.dto.NoteUpdateDto;
import com.noter.api.notes.entity.Note;
import com.noter.api.notes.exception.NoteNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NoteService {

    private final NoteDao dao;

    @Autowired
    public NoteService(final NoteDao dao) {
        this.dao = dao;
    }

    public List<NoteResponseDto> getAllNotes() {
        return dao
                .getAllNotes()
                .stream()
                .map(NoteResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<NoteResponseDto> getNoteById(final Long id) {
        final Note note = findNoteByIdOrThrowException(id);
        return createListWithEntityMappedToDto(note);
    }

    public List<NoteResponseDto> createNote(final NoteCreateDto dto) {
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note createdNote = new Note(dto.getText(), currentUTCDateTime, currentUTCDateTime);

        dao.createNote(createdNote);

        return createListWithEntityMappedToDto(createdNote);
    }

    public List<NoteResponseDto> updateNote(final NoteUpdateDto dto) {
        final Note noteToUpdate = findNoteByIdOrThrowException(dto.getId());
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note updatedNote = new Note(dto.getId(),
                                          dto.getText(),
                                          noteToUpdate.getCreatedAt(),
                                          currentUTCDateTime);

        dao.updateNote(updatedNote);

        return createListWithEntityMappedToDto(updatedNote);
    }

    public List<NoteResponseDto> deleteNoteById(final Long id) {
        final Note noteToRemove = findNoteByIdOrThrowException(id);
        dao.deleteNote(noteToRemove);

        return createListWithEntityMappedToDto(noteToRemove);
    }

    private Note findNoteByIdOrThrowException(final Long id) {
        final Note note = dao.getNoteById(id);

        if (note == null) {
            throw new NoteNotFoundException("Note with 'id' = " + id + " is not found");
        }

        return note;
    }

    private List<NoteResponseDto> createListWithEntityMappedToDto(final Note entity) {
        return List.of(new NoteResponseDto(entity));
    }
}

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
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Transactional
@Validated
public class NoteService {

    private final NoteDao noteDao;

    @Autowired
    public NoteService(final NoteDao noteDao) {
        this.noteDao = noteDao;
    }

    public List<Note> getAllNotes() {
        return noteDao.getAllNotes();
    }

    public List<Note> getNoteById(@Min(1) final Long id) {
        return Arrays.asList(findNoteByIdOrThrowException(id));
    }

    public List<Note> createNote(@Valid final NoteRequestDto noteDto) {
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
        final Note note = new Note(noteDto.getText(), currentUTCDateTime, currentUTCDateTime);

        noteDao.createNote(note);

        return Arrays.asList(note);
    }

    public List<Note> updateNote(@Valid final NoteRequestDto noteDto) {
        final Long dtoId = noteDto.getId();
        if (dtoId == null) {
            throw new BadRequestException("Field 'id' can not be empty");
        }

        final Note noteToUpdateDto = findNoteByIdOrThrowException(dtoId);
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);

        final Note note = new Note(dtoId,
                                   noteDto.getText(),
                                   noteToUpdateDto.getCreatedAt(),
                                   currentUTCDateTime);
        noteDao.updateNote(note);

        return Arrays.asList(note);
    }

    public List<Note> deleteNoteById(@Min(1) final Long id) {
        final Note noteToRemove = findNoteByIdOrThrowException(id);

        noteDao.deleteNote(noteToRemove);

        return Arrays.asList(noteToRemove);
    }

    private Note findNoteByIdOrThrowException(final Long id) {
        final Note note = noteDao.getNoteById(id);

        if (note == null) {
            throw new NoteNotFoundException("Note with id = " + id + " is not found");
        }

        return note;
    }
}

package com.noter.api.notes.service;

import com.noter.api.notes.dto.NoteDto;
import java.util.List;

public interface NoteService {

    List<NoteDto> getAllNotes();

    NoteDto getNoteById(final Long id);

    NoteDto createNote(final NoteDto noteDto);

    NoteDto updateNote(final NoteDto noteDto);

    NoteDto deleteNoteById(final Long id);
}

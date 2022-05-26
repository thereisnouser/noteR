package com.noter.api.notes.service;

import com.noter.api.notes.entity.Note;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.noter.api.notes.dao.NoteDao;
import com.noter.api.notes.dto.NoteDto;
import com.noter.api.notes.exception.BadRequestException;
import com.noter.api.notes.exception.NoteNotFoundException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.stream.Collectors;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
    
    private final NoteDao noteDao;

    @Autowired
	public NoteServiceImpl(final NoteDao noteDao) {
		this.noteDao = noteDao;
	}
    
    @Override
    public List<NoteDto> getAllNotes() {
        final List<Note> notesList = noteDao.getAllNotes();

		return notesList.stream()
			.map(NoteDto::new)
			.collect(Collectors.toList());
    }

    @Override
    public NoteDto getNoteById(final Long id) {
		if (id == null) {
			throw new BadRequestException("Set correct value for the 'id'");
		}

		final Note note = noteDao.getNoteById(id);

		if (note == null) {
			throw new NoteNotFoundException("Note with id = " + id + " is not found");
		}

        return new NoteDto(note);
    }

    @Override
    public NoteDto createNote(final NoteDto noteDto) {
        final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
		final Note note = new Note(noteDto.getText(), currentUTCDateTime, currentUTCDateTime);

        noteDao.createNote(note);

		return noteDto;
    }

    @Override
    public NoteDto updateNote(final NoteDto noteDto) {
		final Long id = noteDto.getId();
        final NoteDto noteToUpdateDto = this.getNoteById(id);
		final LocalDateTime currentUTCDateTime = LocalDateTime.now(ZoneOffset.UTC);
		final Note note = new Note(noteDto.getId(),
								   noteDto.getText(),
								   noteToUpdateDto.getCreatedAt(),
								   currentUTCDateTime);
        
        noteDao.updateNote(note);

		return new NoteDto(note);
    }

    @Override
    public NoteDto deleteNoteById(final Long id) {
        final NoteDto noteToRemoveDto = this.getNoteById(id);
		final Note note = new Note(noteToRemoveDto.getId(),
								   noteToRemoveDto.getText(),
								   noteToRemoveDto.getCreatedAt(),
								   noteToRemoveDto.getUpdatedAt());

        noteDao.deleteNote(note);
        
        return noteToRemoveDto;
    }
}

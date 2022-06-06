package com.noter.api.notes.dto;

import com.noter.api.notes.entity.Note;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.http.HttpStatus;

public class NoteResponseDto {

    private int status;
    private String message;
    private List<Note> data;

    public NoteResponseDto() {
    }

    public NoteResponseDto(final HttpStatus status,
                           final String message,
                           final List<Note> data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public NoteResponseDto(final HttpStatus status,
                           final String message) {
        this.status = status.value();
        this.message = message;
        this.data = new ArrayList<>();
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status.value();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Note> getData() {
        return data;
    }

    public void setData(List<Note> data) {
        this.data = data;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 29 * hash + this.status;
        hash = 29 * hash + Objects.hashCode(this.message);
        hash = 29 * hash + Objects.hashCode(this.data);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }

        final NoteResponseDto other = (NoteResponseDto) obj;

        if (this.status != other.status) {
            return false;
        } else if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        return Objects.equals(this.data, other.data);
    }

    @Override
    public String toString() {
        return "NoteResponseDto{" + "status=" + status + ", message=" + message + ", data=" + data + "}";
    }
}

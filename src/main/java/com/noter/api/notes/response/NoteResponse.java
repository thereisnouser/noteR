package com.noter.api.notes.response;

import com.noter.api.notes.dto.NoteResponseDto;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class NoteResponse {

    private int status;
    private String message;
    private List<NoteResponseDto> data;

    public NoteResponse() {
    }

    public NoteResponse(final HttpStatus status,
                        final String message,
                        final List<NoteResponseDto> data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }

    public NoteResponse(final HttpStatus status,
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

    public List<NoteResponseDto> getData() {
        return data;
    }

    public void setData(List<NoteResponseDto> data) {
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

        final NoteResponse other = (NoteResponse) obj;

        if (this.status != other.status) {
            return false;
        } else if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        return Objects.equals(this.data, other.data);
    }

    @Override
    public String toString() {
        return "NoteResponse{" + "status=" + status + ", message=" + message + ", data=" + data + "}";
    }
}

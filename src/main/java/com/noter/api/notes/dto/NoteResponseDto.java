package com.noter.api.notes.dto;

import com.noter.api.notes.entity.Note;

import java.time.LocalDateTime;
import java.util.Objects;

public class NoteResponseDto {

    private Long id;
    private String text;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public NoteResponseDto() {
    }

    public NoteResponseDto(final Long id,
                           final String text,
                           final LocalDateTime createdAt,
                           final LocalDateTime updatedAt) {
        this.id = id;
        this.text = text;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public NoteResponseDto(final Note entity) {
        this.id = entity.getId();
        this.text = entity.getText();
        this.createdAt = entity.getCreatedAt();
        this.updatedAt = entity.getUpdatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(final LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(final LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.text);
        hash = 59 * hash + Objects.hashCode(this.createdAt);
        hash = 59 * hash + Objects.hashCode(this.updatedAt);
        return hash;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (getClass() != obj.getClass()) {
            return false;
        }

        final NoteResponseDto other = (NoteResponseDto) obj;

        if (!Objects.equals(this.text, other.text)) {
            return false;
        } else if (!Objects.equals(this.id, other.id)) {
            return false;
        } else if (!Objects.equals(this.createdAt, other.createdAt)) {
            return false;
        }
        return Objects.equals(this.updatedAt, other.updatedAt);
    }

    @Override
    public String toString() {
        return "NoteResponseDto{"
                + "id=" + id
                + ", text=" + text
                + ", createdAt=" + createdAt
                + ", updatedAt=" + updatedAt + "}";
    }
}

package com.noter.api.notes.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;

public class NoteUpdateDto {

    @NotNull
    @Min(1)
    private Long id;

    @NotBlank
    private String text;

    public NoteUpdateDto() {
    }

    public NoteUpdateDto(final Long id, final String text) {
        this.id = id;
        this.text = text;
    }

    public NoteUpdateDto(final String text) {
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.text);
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

        final NoteUpdateDto other = (NoteUpdateDto) obj;

        if (!Objects.equals(this.text, other.text)) {
            return false;
        }
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "NoteUpdateDto{" + "id=" + id + ", text=" + text + "}";
    }
}

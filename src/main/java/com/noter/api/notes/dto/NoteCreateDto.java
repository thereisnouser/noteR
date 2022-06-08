package com.noter.api.notes.dto;

import java.util.Objects;
import javax.validation.constraints.NotBlank;

public class NoteCreateDto {

    @NotBlank
    private String text;

    public NoteCreateDto() {
    }

    public NoteCreateDto(final String text) {
        this.text = text;
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

        final NoteCreateDto other = (NoteCreateDto) obj;

        return Objects.equals(this.text, other.text);
    }

    @Override
    public String toString() {
        return "NoteCreateDto{" + "text=" + text + "}";
    }
}

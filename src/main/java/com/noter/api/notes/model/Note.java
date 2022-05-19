package com.noter.api.notes.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "notes")
public class Note {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "text")
    private String text;
    
    @Column(name = "createdDate")
    private Date createdDate;
    
    @Column(name = "updatedDate")
    private Date updatedDate;
    
    public Note() {
    }
    
    public Note(String text, Date createdDate, Date updatedDate) {
        this.text = text;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(Date updatedDate) {
        this.updatedDate = updatedDate;
    }

    @Override
    public String toString() {
        return "NoteEntity{"
                + "id=" + id
                + ", text=" + text
                + ", createdDate="+ createdDate
                + ", updatedDate=" + updatedDate + '}';
    }
}

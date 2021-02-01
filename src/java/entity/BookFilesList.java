/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Melnikov
 */
@Entity
public class BookFilesList implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Book book;
    private BookFile bookFile;

    public BookFilesList() {
    }

    public BookFilesList(Book book, BookFile bookFile) {
        this.book = book;
        this.bookFile = bookFile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public BookFile getBookFile() {
        return bookFile;
    }

    public void setBookFile(BookFile bookFile) {
        this.bookFile = bookFile;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(this.id);
        hash = 61 * hash + Objects.hashCode(this.book);
        hash = 61 * hash + Objects.hashCode(this.bookFile);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BookFilesList other = (BookFilesList) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.book, other.book)) {
            return false;
        }
        if (!Objects.equals(this.bookFile, other.bookFile)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BookFilesList{" 
                + "id=" + id 
                + ", book=" + book.getName()
                + ", bookFile=" + bookFile.getDescription()
                + '}';
    }
   
}

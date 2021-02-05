/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.BookPictures;
import entity.Picture;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jvm
 */
@Stateless
public class BookPicturesFacade extends AbstractFacade<BookPictures> {

    @PersistenceContext(unitName = "JKTVR19WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookPicturesFacade() {
        super(BookPictures.class);
    }

    public Picture findPictureByBook(Book book) {
        try { 
            return (Picture) em.createQuery("SELECT bp.picture FROM BookPictures bp WHERE bp.book = :book")
                    .setParameter("book", book)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public void replacePicture(Picture picture, Book book) {
        em.createQuery("UPDATE BookPictures bp SET bp.picture = :picture WHERE bp.book.id = :bookId")
                .setParameter("bookId", book.getId())
                .setParameter("picture", picture)
                .executeUpdate();
    }
    
}

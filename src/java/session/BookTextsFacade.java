/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Book;
import entity.BookTexts;
import entity.Picture;
import entity.Text;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author jvm
 */
@Stateless
public class BookTextsFacade extends AbstractFacade<BookTexts> {

    @PersistenceContext(unitName = "JKTVR19WebLibraryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BookTextsFacade() {
        super(BookTexts.class);
    }

    public Text findTextByBook(Book book) {
        try { 
            return  (Text) em.createQuery("SELECT bt.text FROM BookTexts bt WHERE bt.book = :book")
                    .setParameter("book", book)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }    
    }

    public void replaceText(Text text, Book book) {
        em.createQuery("UPDATE BookTexts bt SET bt.text = :text WHERE bt.book.id = :bookId")
                .setParameter("bookId", book.getId())
                .setParameter("text", text)
                .executeUpdate();    
    }
    
}

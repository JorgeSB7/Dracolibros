package com.dracolibros;

import com.dracolibros.model.BookEntity;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class BookTest {
    private BookEntity book;

    @Before
    public void setUp(){
        this.book = new BookEntity();
    }

    @Test
    public void bookId(){
        this.book.setId("71");
        assertEquals("71", this.book.getId());
        this.book.setId("16");
        assertEquals("16", this.book.getId());
    }

    @Test
    public void bookName(){
        assertEquals(false, this.book.setName("el señor de los anillos"));
        assertEquals("", this.book.getName());
        assertEquals(false, this.book.setName("El"));
        assertEquals("", this.book.getName());
        assertEquals(true, this.book.setName("El señor de los anillos"));
        assertEquals("El señor de los anillos", this.book.getName());
    }


    @Test
    public void bookAuthor(){
        assertEquals(false, this.book.setAuthor("jorge"));
        assertEquals("", this.book.getAuthor());
        assertEquals(false, this.book.setAuthor("Jo"));
        assertEquals("", this.book.getAuthor());
        assertEquals(true, this.book.setAuthor("Jorge"));
        assertEquals("Jorge", this.book.getAuthor());
    }

    @Test
    public void bookCode(){
        assertEquals(false, this.book.setCode("000AAA"));
        assertEquals("", this.book.getCode());
        assertEquals(false, this.book.setCode("0000aaa"));
        assertEquals("", this.book.getCode());
        assertEquals(true, this.book.setCode("0000AAA"));
        assertEquals("0000AAA", this.book.getCode());
    }

    @Test
    public void bookIsbn(){
        assertEquals(false, this.book.setIsbn("989-22-12345-11"));
        assertEquals("", this.book.getIsbn());
        assertEquals(true, this.book.setIsbn("989-22-12345-11-2"));
        assertEquals("989-22-12345-11-2", this.book.getIsbn());
    }

    @Test
    public void bookDate(){
        assertEquals(false, this.book.setDate("32/1/1995"));
        assertEquals("", this.book.getDate());
        assertEquals(true, this.book.setDate("31/1/1995"));
        assertEquals("31/1/1995", this.book.getDate());
    }

    @Test
    public void bookImage(){
        this.book.setImage("1234556789");
        assertEquals("1234556789", this.book.getImage());
        this.book.setImage("987654321");
        assertEquals("987654321", this.book.getImage());
    }

    @Test
    public void bookGenre(){
        this.book.setGenre("Manga");
        assertEquals("Manga", this.book.getGenre());
        this.book.setGenre("Ocultista");
        assertEquals("Ocultista", this.book.getGenre());
    }

    @Test
    public void bookAvailable(){
        this.book.setAvailable(false);
        assertEquals(false, this.book.isAvailable());
        this.book.setAvailable(true);
        assertEquals(true, this.book.isAvailable());
    }

}

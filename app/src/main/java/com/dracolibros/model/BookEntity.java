package com.dracolibros.model;

import com.dracolibros.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookEntity {

    private String id;
    private String name;
    private String author;
    private String code;
    private String isbn;
    private String date;
    private String image;

    public BookEntity() {}

    public String getName() {
        return name;
    }

    public boolean setName(String name) {
        if (name.length()>2 && name.startsWith(String.valueOf(name.toUpperCase().charAt(0)))){
            this.name = name;
            return true;
        } else {
            return false;
        }
    }

    public String getAuthor() { return author; }

    public boolean setAuthor(String author) {
        if (author.length()>2 && author.startsWith(String.valueOf(author.toUpperCase().charAt(0)))){
            this.author = author;
            return true;
        } else {
            return false;
        }
    }

    public String getCode() { return code; }

    // Formato 0000AAA
    // "^[0-9]{4}[A-Z]{3}$"
    public boolean setCode(String code) {
        Pattern pat = Pattern.compile("^[0-9]{4}[A-Z]{3}$");
        Matcher mat = pat.matcher(code);
        if (mat.find()) {
            this.code = code;
            return true;
        } else {
            return false;
        }
    }

    public String getIsbn() {
        return isbn;
    }

    // Formato ISBN de libros
    // "[0-9]*[-| ][0-9]*[-| ][0-9]*[-| ][0-9]*[-| ][0-9]*"
    public boolean setIsbn(String isbn) {
        Pattern pat = Pattern.compile("[0-9]*[-| ][0-9]*[-| ][0-9]*[-| ][0-9]*[-| ][0-9]*");
        Matcher mat = pat.matcher(isbn);
        if (mat.find()) {
            this.isbn = isbn;
            return true;
        } else {
            return false;
        }
    }

    public String getDate() { return date; }

    public boolean setDate(String date) {
        Pattern pat = Pattern.compile("^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[13-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$");
        Matcher mat = pat.matcher(date);
        if (mat.find()) {
            this.date = date;
            return true;
        } else {
            return false;
        }
    }

    //_____________________________________________IMG

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

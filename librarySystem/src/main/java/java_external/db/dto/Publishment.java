package java_external.db.dto;

import java.util.Collection;

/**
 * Created by olga on 12.05.18.
 */
public class Publishment {

    private int id;
    private String name;
    private Collection<Book> books;

    public Publishment(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }
}

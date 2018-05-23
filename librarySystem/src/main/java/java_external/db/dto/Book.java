package java_external.db.dto;

import java.sql.Date;
import java.util.List;

public class Book {

    private String title;
    private int id;
    private Publishment publishment;
    private List<Author> authors;
    private List<Keyword> keywords;

    private Genre genre;

    private boolean avilable;
    private Shelf location;
    private User reader;
    private Date dateOfReturn;

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Publishment getPublishment() {
        return publishment;
    }

    public void setPublishment(Publishment publishment) {
        this.publishment = publishment;
    }

    public boolean isAvilable() {
        return avilable;
    }

    public void setAvilable(boolean avilable) {
        this.avilable = avilable;
    }

    public Shelf getLocation() {
        return location;
    }

    public void setLocation(Shelf location) {
        this.location = location;
    }

    public User getReader() {
        return reader;
    }

    public void setReader(User reader) {
        this.reader = reader;
    }

    public Date getDateOfReturn() {
        return dateOfReturn;
    }

    public void setDateOfReturn(Date dateOfReturn) {
        this.dateOfReturn = dateOfReturn;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Book)) return false;

        Book book = (Book) o;

        if (isAvilable() != book.isAvilable()) return false;
        if (getTitle() != null ? !getTitle().equals(book.getTitle()) : book.getTitle() != null) return false;
        if (getPublishment() != null ? !getPublishment().equals(book.getPublishment()) : book.getPublishment() != null)
            return false;
        if (getAuthors() != null ? !getAuthors().equals(book.getAuthors()) : book.getAuthors() != null) return false;
        if (getGenre() != null ? !getGenre().equals(book.getGenre()) : book.getGenre() != null) return false;
        if (getLocation() != null ? !getLocation().equals(book.getLocation()) : book.getLocation() != null)
            return false;
        if (getReader() != null ? !getReader().equals(book.getReader()) : book.getReader() != null) return false;
        return getDateOfReturn() != null ? getDateOfReturn().equals(book.getDateOfReturn()) : book.getDateOfReturn() == null;
    }

    @Override
    public int hashCode() {
        int result = getTitle() != null ? getTitle().hashCode() : 0;
        result = 31 * result + (getPublishment() != null ? getPublishment().hashCode() : 0);
        result = 31 * result + (getAuthors() != null ? getAuthors().hashCode() : 0);
        result = 31 * result + (getGenre() != null ? getGenre().hashCode() : 0);
        result = 31 * result + (isAvilable() ? 1 : 0);
        result = 31 * result + (getLocation() != null ? getLocation().hashCode() : 0);
        result = 31 * result + (getReader() != null ? getReader().hashCode() : 0);
        result = 31 * result + (getDateOfReturn() != null ? getDateOfReturn().hashCode() : 0);
        return result;
    }
}

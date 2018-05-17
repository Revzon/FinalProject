package java_external.db.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by olga on 12.05.18.
 */
public class Book {

    private String title;
    private int id;
//    private Publishment publishment;
    private List<Author> authors;
    private List<KeyWord> keyWords;

//    private Genre genre;

    //    private Date yearOfPublishing;
    private boolean aviable;
    private Shelf location;
    private User reader;
    private Date dateOfReturn;
//    private int deposit;
//    private boolean readingRoomOnly;

    public List<KeyWord> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<KeyWord> keyWords) {
        this.keyWords = keyWords;
    }


//    public Genre getGenre() {
//        return genre;
//    }
//
//    public void setGenre(Genre genre) {
//        this.genre = genre;
//    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    public Publishment getPublishment() {
//        return publishment;
//    }
//
//    public void setPublishment(Publishment publishment) {
//        this.publishment = publishment;
//    }

    public boolean isAviable() {
        return aviable;
    }

    public void setAviable(boolean aviable) {
        this.aviable = aviable;
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
}

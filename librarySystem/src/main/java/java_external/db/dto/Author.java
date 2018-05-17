package java_external.db.dto;

import java.util.Date;
import java.util.List;

/**
 * Created by olga on 12.05.18.
 */

public class Author {

    private int id;
    private String firstName;
    private String secondName;
    private String patronimycName;
    private Date birthDate;
    private List<Book> books;



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getPatronimycName() {
        return patronimycName;
    }

    public void setPatronimycName(String patronimycName) {
        this.patronimycName = patronimycName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Author{" +
                "firstName='" + firstName + '\'' +
                ", secondName='" + secondName + '\'' +
                ", patronimycName='" + patronimycName + '\'' +
                ", birthDate=" + birthDate +
                '}';
    }
}

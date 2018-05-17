package java_external.db.dto;

/**
 * Created by olga on 13.05.18.
 */
public class Shelf {

    private int id;
    private int name;
    private Section section;

    public Shelf(int id, int name, Section section) {
        this.id = id;
        this.name = name;
        this.section = section;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }
}

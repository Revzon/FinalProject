package java_external.db.dto;




public class Shelf {

    private int id;
    private String name;
    private Section section;

    public Shelf(String name, Section section) {
        this.name = name;
        this.section = section;
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

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Shelf shelf = (Shelf) o;

        if (getName() != null ? !getName().equals(shelf.getName()) : shelf.getName() != null) return false;
        return getSection() != null ? getSection().equals(shelf.getSection()) : shelf.getSection() == null;
    }

    @Override
    public int hashCode() {
        int result = getName() != null ? getName().hashCode() : 0;
        result = 31 * result + (getSection() != null ? getSection().hashCode() : 0);
        return result;
    }
}

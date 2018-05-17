package java_external.db.dto;

/**
 * Created by olga on 12.05.18.
 */
public class KeyWord {

    private Integer id;
    private String name;

    public KeyWord(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

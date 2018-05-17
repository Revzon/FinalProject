package java_external.enums;

/**
 * Created by olga on 15.05.18.
 */
public enum Role {
    READER(1), ADMIN(2), GUEST(0);
    private int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}

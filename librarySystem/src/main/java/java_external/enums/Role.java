package java_external.enums;

public enum Role {

    GUEST(2), READER(1), ADMIN(0);
    private int id;

    Role(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static Role getRoleByd(int id) {
        for (Role role: Role.values()) {
            if (role.id == id) {
                return role;
            }
        }
        return null;
    }
}

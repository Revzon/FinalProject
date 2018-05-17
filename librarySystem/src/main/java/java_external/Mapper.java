package java_external;

import java_external.enums.Role;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Created by olga on 16.05.18.
 */
public class Mapper {

    private HashMap<String, List<Role>> jspRoleAvialability;
    private HashMap<String, String> endPointJspEquivalent;

    private static Mapper mapper;

    public static Mapper getInstance(){
        if (Objects.isNull(mapper)) {
            mapper = new Mapper();
        }
        return mapper;
    }

    public HashMap<String, List<Role>> getJspRoleAvialability() {
        return jspRoleAvialability;
    }

    public void setJspRoleAvialability(HashMap<String, List<Role>> jspRoleAvialability) {
        this.jspRoleAvialability = jspRoleAvialability;
    }

    public HashMap<String, String> getEndPointJspEquivalent() {
        return endPointJspEquivalent;
    }

    public void setEndPointJspEquivalent(HashMap<String, String> endPointJspEquivalent) {
        this.endPointJspEquivalent = endPointJspEquivalent;
    }
}

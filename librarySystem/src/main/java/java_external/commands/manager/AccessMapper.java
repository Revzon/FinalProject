package java_external.commands.manager;

import java_external.enums.Role;
import javax.servlet.ServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;


public class AccessMapper {

    private static HashMap<String, List<Role>> rights;
    private static AccessMapper instance;
    private static final String PARAM_NAME_ACTION = "action";

    private AccessMapper() {
        rights = setRights();

    }

    private HashMap<String, List<Role>> setRights() {

        rights = new HashMap<>();

        // Authentification & user management operations//
        rights.put("login", Arrays.asList(Role.GUEST));
        rights.put("login-submit", Arrays.asList(Role.GUEST));
        rights.put("logout", Arrays.asList(Role.READER, Role.ADMIN));
        rights.put("register", Arrays.asList(Role.GUEST));
        rights.put("register-submit", Arrays.asList(Role.GUEST));
        rights.put("restore-password-start", Arrays.asList(Role.GUEST));
        rights.put("find-user-to-restore-password", Arrays.asList(Role.GUEST));
        rights.put("change-password", Arrays.asList(Role.GUEST, Role.READER, Role.ADMIN));
        rights.put("change-password-submit", Arrays.asList(Role.GUEST, Role.READER, Role.ADMIN));
        rights.put("edit-user", Arrays.asList(Role.READER, Role.ADMIN));
        rights.put("edit-user-submit", Arrays.asList(Role.READER, Role.ADMIN));
        rights.put("change-role", Arrays.asList(Role.ADMIN));


        // Navigation
        rights.put("homepage", Arrays.asList(Role.GUEST, Role.READER, Role.ADMIN));
        rights.put("search-page", Arrays.asList(Role.GUEST, Role.READER, Role.ADMIN));
        rights.put("findBook", Arrays.asList(Role.GUEST, Role.READER, Role.ADMIN));
        rights.put("user-page", Arrays.asList(Role.READER, Role.ADMIN));
        rights.put("personal-cabinet", Arrays.asList(Role.READER, Role.ADMIN));
        rights.put("overdued-books-list", Arrays.asList(Role.READER, Role.ADMIN));
        rights.put("ref", Arrays.asList(Role.GUEST, Role.READER, Role.ADMIN));

        // Actions//
        rights.put("borrow-book", Arrays.asList(Role.READER, Role.ADMIN));
        rights.put("return-book", Arrays.asList(Role.READER, Role.ADMIN));

        // Show lists operations//
        rights.put("show-shelfs", Arrays.asList(Role.ADMIN));
        rights.put("show-genres", Arrays.asList(Role.ADMIN));
        rights.put("show-sections", Arrays.asList(Role.ADMIN));
        rights.put("show-keywords", Arrays.asList(Role.ADMIN));
        rights.put("show-authors", Arrays.asList(Role.ADMIN));
        rights.put("show-publishments", Arrays.asList(Role.ADMIN));
        rights.put("show-users", Arrays.asList(Role.ADMIN));
        rights.put("show-books", Arrays.asList(Role.GUEST, Role.READER, Role.ADMIN));

        // Delete operations//
        rights.put("delete-book", Arrays.asList(Role.ADMIN));
        rights.put("delete-author", Arrays.asList(Role.ADMIN));
        rights.put("delete-user", Arrays.asList(Role.ADMIN));
        rights.put("delete-keyword", Arrays.asList(Role.ADMIN));
        rights.put("delete-genre", Arrays.asList(Role.ADMIN));
        rights.put("delete-shelf", Arrays.asList(Role.ADMIN));
        rights.put("delete-section", Arrays.asList(Role.ADMIN));
        rights.put("delete-publishment", Arrays.asList(Role.ADMIN));

        // Add operations//
        rights.put("add-book", Arrays.asList(Role.ADMIN));
        rights.put("add-book-submit", Arrays.asList(Role.ADMIN));
        rights.put("add-author", Arrays.asList(Role.ADMIN));
        rights.put("add-author-submit", Arrays.asList(Role.ADMIN));
        rights.put("add-user", Arrays.asList(Role.ADMIN));
        rights.put("add-keyword", Arrays.asList(Role.ADMIN));
        rights.put("add-genre", Arrays.asList(Role.ADMIN));
        rights.put("add-shelf", Arrays.asList(Role.ADMIN));
        rights.put("add-section", Arrays.asList(Role.ADMIN));
        rights.put("add-publishment", Arrays.asList(Role.ADMIN));

        // Edit operations//
        rights.put("edit-book", Arrays.asList(Role.ADMIN));
        rights.put("edit-book-submit", Arrays.asList(Role.ADMIN));
        rights.put("edit-author", Arrays.asList(Role.ADMIN));
        rights.put("edit-author-submit", Arrays.asList(Role.ADMIN));

        rights.put("show-author-page", Arrays.asList(Role.READER, Role.ADMIN));
        rights.put("edit-genre", Arrays.asList(Role.ADMIN));
        rights.put("edit-genre-submit", Arrays.asList(Role.ADMIN));
        rights.put("edit-publishment", Arrays.asList(Role.ADMIN));
        rights.put("edit-publishment-submit", Arrays.asList(Role.ADMIN));
        rights.put("edit-section", Arrays.asList(Role.ADMIN));
        rights.put("edit-section-submit", Arrays.asList(Role.ADMIN));
        rights.put("edit-shelf", Arrays.asList(Role.ADMIN));
        rights.put("edit-shelf-submit", Arrays.asList(Role.ADMIN));
        rights.put("edit-keyword", Arrays.asList(Role.ADMIN));
        rights.put("edit-keyword-submit", Arrays.asList(Role.ADMIN));

        return rights;
    }

    public static AccessMapper getInstance() {
        if (Objects.isNull(instance)) {
            instance = new AccessMapper();
        }
        return instance;
    }


    public boolean checkRights(ServletRequest request, Role role) {
        String action = request.getParameter(PARAM_NAME_ACTION);
        if (Objects.isNull(action) || !rights.containsKey(action)) {
            return false;
        }
        return rights.get(action).contains(role);
    }


}

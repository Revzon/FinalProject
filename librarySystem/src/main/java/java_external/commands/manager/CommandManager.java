package java_external.commands.manager;

import java_external.commands.*;
import java_external.commands.add.*;
import java_external.commands.delete.*;
import java_external.commands.book_operations.*;
import java_external.commands.edit.*;
import java_external.commands.list.*;
import java_external.commands.navigation.*;
import java_external.commands.session_commands.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Objects;

public class CommandManager {

    private static HashMap<String, Command> commands;
    private static CommandManager instance;
    private static final String PARAM_NAME_ACTION = "action";

    private CommandManager(){

        commands = new HashMap<>();

        // Authentification operations
        commands.put("login", new LoginCommand());
        commands.put("login-submit", new LoginSubmitCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("register", new RegisterCommand());
        commands.put("register-submit", new AddUserSubmitCommand());
        commands.put("ref", new RefCommand());

        // Navigation commands
        commands.put("homepage", new HomepageCommand());
        commands.put("search-page", new SearchpageCommand());
        commands.put("user-page", new UserpageCommand());
        commands.put("personal-cabinet", new PersonalPageCommand());
        commands.put("overdued-books-list", new OverduedBooksCommand());

        // Book Operations
        commands.put("borrow-book", new BorrowBookCommand());
        commands.put("return-book", new ReturnBookCommand());
        commands.put("findBook", new FindBookCommand());

        // Password restore operations
        commands.put("restore-password-start", new RestorePasswordControlCommand());
        commands.put("find-user-to-restore-password", new FindUserToRestorePasswordCommand());
        commands.put("change-password", new ChangePasswordCommand());
        commands.put("change-password-submit", new ChangePasswordSubmitCommand());
        commands.put("change-role", new ChangeUserRoleCommand());

        // Show-list operations
        commands.put("show-shelfs", new ShelfListCommand());
        commands.put("show-sections", new SectionListCommand());
        commands.put("show-keywords", new KeywordListCommand());
        commands.put("show-authors", new AuthorListCommand());
        commands.put("show-publishments", new PublishmentListCommand());
        commands.put("show-genres", new GenreListCommand());
        commands.put("show-books", new SearchpageCommand());
        commands.put("show-users", new UserListCommand());
        commands.put("show-author-page", new AuthorPageCommand());

        // Delete operations
        commands.put("delete-book", new DeleteBookCommand());
        commands.put("delete-author", new DeleteAuthorCommand());
        commands.put("delete-user", new DeleteUserCommand());
        commands.put("delete-keyword", new DeleteKeywordCommand());
        commands.put("delete-genre", new DeleteGenreCommand());
        commands.put("delete-shelf", new DeleteShelfCommand());
        commands.put("delete-section", new DeleteSectionCommand());
        commands.put("delete-publishment", new DeletePublishmentCommand());

        // Add operations
        commands.put("add-author", new AddAuthorCommand());
        commands.put("add-author-submit", new AddAuthorSubmitCommand());
        commands.put("add-user", new AddUserSubmitCommand());
        commands.put("add-keyword", new AddKeywordCommand());
        commands.put("add-genre", new AddGenreCommand());
        commands.put("add-shelf", new AddShelfCommand());
        commands.put("add-section", new AddSectionCommand());
        commands.put("add-publishment", new AddPublishmentCommand());
        commands.put("add-book", new AddBookCommand());
        commands.put("add-book-submit", new AddBookSubmitCommand());

        // Edit operations
        commands.put("edit-author", new EditAuthorCommand());
        commands.put("edit-author-submit", new EditAuthorSubmitCommand());
        commands.put("edit-book", new EditBookCommand());
        commands.put("edit-book-submit", new EditBookSubmitCommand());
        commands.put("edit-user", new EditUserCommand());
        commands.put("edit-user-submit", new EditUserSubmitCommand());
        commands.put("edit-genre", new EditGenreCommand());
        commands.put("edit-genre-submit", new EditGenreSubmitCommand());
        commands.put("edit-publishment", new EditPublishmentCommand());
        commands.put("edit-publishment-submit", new EditPublishmentSubmitCommand());
        commands.put("edit-section", new EditSectionCommand());
        commands.put("edit-section-submit", new EditSectionSubmitCommand());
        commands.put("edit-shelf", new EditShelfCommand());
        commands.put("edit-shelf-submit", new EditShelfSubmitCommand());
        commands.put("edit-keyword", new EditKeywordCommand());
        commands.put("edit-keyword-submit", new EditKeywordSubmitCommand());

    }

    public static CommandManager getInstance(){
        if (Objects.isNull(instance)) {
            instance = new CommandManager();
        }
        return instance;
    }


    public Command getCommand(HttpServletRequest request){
        Command command = null;
        command = searchCommand(request);
        if (Objects.isNull(command)) {
            command = new DefaultCommand();
        }
        return command;
    }

    private Command searchCommand(HttpServletRequest request) {

        String action = request.getParameter(PARAM_NAME_ACTION);
        if (Objects.isNull(action)) {
            return null;
        }
        Command command = commands.get(action);
        return command;
    }

}

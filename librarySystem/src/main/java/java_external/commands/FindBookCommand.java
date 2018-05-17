package java_external.commands;

import java_external.services.manager.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by olga on 16.05.18.
 */
public class FindBookCommand implements Command {


    @Override
    public String execute(HttpServletRequest request) {

//        String page = null;
//        List<Book> books;
//
//        SearchStrategy strategy =
//
//        if (name.length() != 0) {
//            if (author.length() != 0) {
//                books.setBooks(bookdao.findByNameAndAuthor(name, author));
//            } else {
//                books.setBooks(bookdao.findByName(name, true));
//            }
//        } else if (author.length() != 0) {
//            books.setBooks(bookdao.findByAuthor(author, true));
//        } else {
//            books = null;
//        }
//        request.getSession().setAttribute(ATTR_NAME_BOOKS, books);
//        page = ConfigurationManager.getInstance().getProperty(
//                ConfigurationManager.BOOK_PAGE_PATH);
//
//        return page;

        String page = ConfigurationManager.getInstance().getProperty(
                ConfigurationManager.INDEX_PAGE_PATH);
        return page;
    }
}

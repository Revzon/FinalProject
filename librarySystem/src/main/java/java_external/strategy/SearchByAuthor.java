package java_external.strategy;

import java_external.db.dto.Book;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by olga on 16.05.18.
 */
public class SearchByAuthor implements SearchStrategy {
    @Override
    public List<Book> findAllObjects(String searchString) {
        return null;
    }
}


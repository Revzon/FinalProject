package java_external.services;

import java_external.db.dto.Book;
import java_external.enums.SearchType;
import java_external.strategy.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by olga on 16.05.18.
 */
public class SearchService {

    private static SearchService instance = null;
    private static final String PARAM_NAME_SEARCH_TYPE = "search-type";
    private static final String PARAM_NAME_SEARCH_OBJECT = "object";


    public List<Book> findBooks(HttpServletRequest request) {

        List<Book> books;
        return null;
//        request.
//
//        SearchStrategy searchStrategy = getStrategy(searchType)
    }

    public static SearchService getInstance() {
        if (instance == null) {
            instance = new SearchService();
        }
        return instance;
    }


    public SearchStrategy getStrategy(SearchType searchType) {

        switch (searchType) {
            case AUTHOR:
                return new SearchByAuthor();
            case TITLE:
                return new SearchByTitle();

            case GENRE:
                return new SearchByGenre();

            case KEYWORD:
                return new SearchByKeyword();

            case PUBLISHMENT:
                return null;
//                return new SearchBy();

            case AVIABLE:
                return null;
//                return new SearchByAv();

            default:
                //LOG ERROR
                return null;


        }


    }

//    private Command searchCommand(HttpServletRequest request) {
//        Enumeration<?> params = request.getParameterNames();
//        while (params.hasMoreElements()) {
//            String param = (String) params.nextElement();
//            if (param.startsWith(PARAM_NAME_COMMAND)) {
//                String action = request.getParameter(param);
//                Command command = commands.get(action);
//                String sub = getSubParam(param);
//                if (sub != null)
//                    request.setAttribute(ATTR_NAME_ID, Integer.parseInt(sub));
//                return command;
//            }
//        }
//        return null;
//    }
//
//    private String getSubParam(String param) {
//        String id = param.substring(PARAM_NAME_COMMAND.length());
//        return (id.length() > 0) ? id : null;
//    }
}

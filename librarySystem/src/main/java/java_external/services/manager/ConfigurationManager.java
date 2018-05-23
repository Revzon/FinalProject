package java_external.services.manager;

import java.util.Objects;
import java.util.ResourceBundle;

public class ConfigurationManager {
	private static ConfigurationManager instance;
	private static ResourceBundle resourceBundle;

	private static final String BUNDLE_NAME = "properties/config";

	public static final String DATABASE_DRIVER_NAME = "DATABASE_DRIVER_NAME";
	public static final String DATABASE_URL = "DATABASE_URL";
	public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
	public static final String INDEX_PAGE_PATH = "INDEX_PAGE_PATH";
	public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
	public static final String REGISTRATION_PAGE_PATH = "REGISTRATION_PAGE_PATH";
	public static final String CHANGE_PASSWORD_PAGE_PATH = "CHANGE_PASSWORD_PAGE_PATH";
	public static final String RESTORE_PASSWORD_CONTROL_PAGE_PATH = "RESTORE_PASSWORD_CONTROL_PAGE_PATH";
	public static final String SEARCH_PAGE_PATH = "SEARCH_PAGE_PATH";

	public static final String TAKEN_BOOKS_PAGE_PATH = "TAKEN_BOOKS_PAGE_PATH";
	public static final String AUTHOR_PAGE_PATH = "AUTHOR_PAGE_PATH";

	public static final String ADD_BOOK_PAGE_PATH = "ADD_BOOK_PAGE_PATH";
	public static final String ADD_AUTHOR_PAGE_PATH = "ADD_AUTHOR_PAGE_PATH";

	public static final String EDIT_AUTHOR_PAGE_PATH = "EDIT_AUTHOR_PAGE_PATH";
	public static final String EDIT_BOOK_PAGE_PATH = "EDIT_BOOK_PAGE_PATH";
	public static final String EDIT_USER_DATA_PAGE_PATH = "EDIT_USER_DATA_PAGE_PATH";

	public static final String GENRE_LIST_PAGE_PATH = "GENRE_LIST_PAGE_PATH";
	public static final String PUBLISHMENT_LIST_PAGE_PATH = "PUBLISHMENT_LIST_PAGE_PATH";
	public static final String KEYWORD_LIST_PAGE_PATH = "KEYWORD_LIST_PAGE_PATH";
	public static final String SECTION_LIST_PAGE_PATH = "SECTION_LIST_PAGE_PATH";
	public static final String SHELF_LIST_PAGE_PATH = "SHELF_LIST_PAGE_PATH";
	public static final String AUTHOR_LIST_PAGE_PATH = "AUTHOR_LIST_PAGE_PATH";
//	public static final String BOOK_LIST_PAGE_PATH = "BOOK_LIST_PAGE_PATH";
	public static final String USER_LIST_PAGE_PATH = "USER_LIST_PAGE_PATH";

	public static final String USER_PAGE_PATH = "USER_PAGE_PATH";


	public static ConfigurationManager getInstance() {
		if (Objects.isNull(instance)) {
			instance = new ConfigurationManager();
		}
		return instance;
	}

	private ConfigurationManager() {
		resourceBundle = ResourceBundle.getBundle(BUNDLE_NAME);
	}

	public String getProperty(String key) {
		return (String) resourceBundle.getObject(key);
	}
	
}

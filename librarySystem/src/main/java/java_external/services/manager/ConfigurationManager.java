package java_external.services.manager;

import java.util.ResourceBundle;

public class ConfigurationManager {
	private static ConfigurationManager instance;
	private static ResourceBundle resourceBundle;

	private static final String BUNDLE_NAME = "properties/config";

	public static final String DATABASE_DRIVER_NAME = "DATABASE_DRIVER_NAME";
	public static final String DATABASE_URL = "DATABASE_URL";
	public static final String ERROR_PAGE_PATH = "ERROR_PAGE_PATH";
	public static final String INDEX_PAGE_PATH = "INDEX_PAGE_PATH";
	public static final String BORROW_BOOK_PAGE_PATH = "BORROW_BOOK_PAGE_PATH";
	public static final String LOGIN_PAGE_PATH = "LOGIN_PAGE_PATH";
	public static final String REGISTRATION_PAGE_PATH = "REGISTRATION_PAGE_PATH";
	public static final String SEARCH_PAGE_PATH = "SEARCH_PAGE_PATH";
	public static final String TAKEN_BOOKS_PAGE_PATH = "TAKEN_BOOKS_PAGE_PATH";;

	public static ConfigurationManager getInstance() {
		if (instance == null) {
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

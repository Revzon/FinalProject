package java_external.commands;

import javax.servlet.http.HttpServletRequest;

public class RefCommand implements Command{

        private static final String PAGE_REFERRER = "ref";

        @Override
        public String execute(HttpServletRequest request) {
            return PAGE_REFERRER;
        }

}



package java_external.commands.navigation;

import java_external.commands.Command;

import javax.servlet.http.HttpServletRequest;

public class RefCommand implements Command {

        private static final String PAGE_REFERRER = "ref";
        private static final String LANG_PARAM = "lang";

        @Override
        public String execute(HttpServletRequest request) {

            return PAGE_REFERRER;
        }

}



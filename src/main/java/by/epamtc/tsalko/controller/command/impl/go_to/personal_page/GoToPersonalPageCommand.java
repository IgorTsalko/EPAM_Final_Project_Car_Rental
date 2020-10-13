package by.epamtc.tsalko.controller.command.impl.go_to.personal_page;

import by.epamtc.tsalko.bean.User;
import by.epamtc.tsalko.controller.command.Command;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToPersonalPageCommand implements Command {

    private static final Logger logger = LogManager.getLogger(GoToPersonalPageCommand.class);

    private static final String COMMAND_GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";

    private static final String PERSONAL_PAGE = "/WEB-INF/jsp/personal_page/personalPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            logger.warn("Attempt to obtain private data");
            resp.sendRedirect(COMMAND_GO_TO_MAIN_PAGE);
        } else {
            req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
        }
    }
}
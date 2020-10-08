package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.controller.command.Command;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GoToRulesCommand implements Command {

    private static final String RULES_PAGE = "/WEB-INF/jsp/rules.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(RULES_PAGE).forward(req, resp);
    }
}

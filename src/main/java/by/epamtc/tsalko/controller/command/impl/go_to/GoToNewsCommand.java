package by.epamtc.tsalko.controller.command.impl.go_to;

import by.epamtc.tsalko.bean.content.News;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ContentService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToNewsCommand implements Command {

    private static final String ALL_NEWS = "all_news";

    private static final String NEWS_PAGE = "/WEB-INF/jsp/news.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        ContentService contentService = serviceProvider.getContentService();

        try {
            List<News> allNews = contentService.getAllNews();
            req.setAttribute(ALL_NEWS, allNews);

            req.getRequestDispatcher(NEWS_PAGE).forward(req, resp);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}

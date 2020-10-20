package by.epamtc.tsalko.controller.command.impl.user.go_to;

import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.bean.user.UserDetails;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.ContentService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToPersonalPageDetailsCommand implements Command {

    private static final String ATTRIBUTE_USER = "user";

    private static final String USER_DETAILS = "user_details";
    private static final String ALL_ROLES = "all_roles";
    private static final String ALL_RATINGS = "all_ratings";

    private static final String MESSAGE_DETAILS = "message_details";
    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";

    private static final String GO_TO_MAIN_PAGE = "mainController?command=go_to_main_page";
    private static final String PERSONAL_PAGE = "/WEB-INF/jsp/personal_page/personalPage.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getSession().getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            resp.sendRedirect(GO_TO_MAIN_PAGE);
        } else {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();
            ContentService contentService = serviceProvider.getContentService();

            UserDetails userDetails;
            List<Role> allRoles;
            List<Rating> allRatings;
            int userID = user.getId();

            try {
                userDetails = userService.getUserDetails(userID);
                allRoles = contentService.getAllRoles();
                allRatings = contentService.getAllRatings();

                req.setAttribute(USER_DETAILS, userDetails);
                req.setAttribute(ALL_ROLES, allRoles);
                req.setAttribute(ALL_RATINGS, allRatings);
            } catch (ServiceException e) {
                req.setAttribute(MESSAGE_DETAILS, ERROR_DATA_RETRIEVE);
            }

            req.getRequestDispatcher(PERSONAL_PAGE).forward(req, resp);
        }
    }
}
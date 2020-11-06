package by.epamtc.tsalko.controller.command.impl.user.go_to;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.bean.content.Rating;
import by.epamtc.tsalko.bean.content.Role;
import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.bean.user.UserDetails;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.BankcardService;
import by.epamtc.tsalko.service.ContentService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class GoToAllUserDataCommand implements Command {

    private static final String PARAMETER_USER_ID = "user_id";

    private static final String USER_DETAILS = "user_details";
    private static final String ALL_ROLES = "all_roles";
    private static final String ALL_RATINGS = "all_ratings";
    private static final String USER_PASSPORT = "user_passport";
    private static final String BANKCARD_NUMBERS = "bankcard_numbers";

    private static final String MESSAGE_DETAILS = "message_details";
    private static final String MESSAGE_PASSPORT = "message_passport";
    private static final String MESSAGE_BANKCARDS = "message_bankcards";
    private static final String ERROR_DATA_RETRIEVE = "data_retrieve_error";

    private static final String USER_DATA_PAGE = "/WEB-INF/jsp/personal_page/userData.jsp";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        UserService userService = serviceProvider.getUserService();
        BankcardService bankcardService = serviceProvider.getBankcardService();
        ContentService contentService = serviceProvider.getContentService();

        UserDetails userDetails;
        Passport userPassport;
        List<Role> allRoles;
        List<Rating> allRatings;
        List<Bankcard> bankcards;

        int userID = 0;
        try {
            userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));
        } catch (NumberFormatException ignore) {/* NOPE */}

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

        try {
            userPassport = userService.getUserPassport(userID);
            req.setAttribute(USER_PASSPORT, userPassport);
        } catch (ServiceException e) {
            req.setAttribute(MESSAGE_PASSPORT, ERROR_DATA_RETRIEVE);
        }

        try {
            bankcards = bankcardService.getUserBankcards(userID);
            req.setAttribute(BANKCARD_NUMBERS, bankcards);
        } catch (ServiceException e) {
            req.setAttribute(MESSAGE_BANKCARDS, ERROR_DATA_RETRIEVE);
        }

        req.getRequestDispatcher(USER_DATA_PAGE).forward(req, resp);
    }
}
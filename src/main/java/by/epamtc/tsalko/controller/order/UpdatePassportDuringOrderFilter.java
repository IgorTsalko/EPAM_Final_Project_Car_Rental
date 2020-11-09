package by.epamtc.tsalko.controller.order;

import by.epamtc.tsalko.bean.user.Passport;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class UpdatePassportDuringOrderFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(UpdatePassportDuringOrderFilter.class);

    private static final String ATTRIBUTE_USER = "user";

    private static final String PARAMETER_UPDATE_PASSPORT = "update_passport";
    private static final String PARAMETER_USER_PASSPORT_SERIES = "user_passport_series";
    private static final String PARAMETER_USER_PASSPORT_NUMBER = "user_passport_number";
    private static final String PARAMETER_USER_PASSPORT_DATE_OF_ISSUE = "user_passport_date_of_issue";
    private static final String PARAMETER_USER_PASSPORT_DATE_OF_BIRTH = "user_passport_date_of_birth";
    private static final String PARAMETER_USER_PASSPORT_ADDRESS = "user_passport_address";
    private static final String PARAMETER_USER_PASSPORT_ISSUED_BY = "user_passport_issued_by";
    private static final String PARAMETER_USER_PASSPORT_SURNAME = "user_passport_surname";
    private static final String PARAMETER_USER_PASSPORT_NAME = "user_passport_name";
    private static final String PARAMETER_USER_PASSPORT_THIRDNAME = "user_passport_thirdname";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        boolean updatePassport = Boolean.parseBoolean(req.getParameter(PARAMETER_UPDATE_PASSPORT));

        if (updatePassport) {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();

            try {
                Passport passport = new Passport();

                passport.setUserID(user.getId());
                passport.setPassportDateOfIssue(
                        LocalDate.parse(req.getParameter(PARAMETER_USER_PASSPORT_DATE_OF_ISSUE)));
                passport.setPassportUserDateOfBirth(
                        LocalDate.parse(req.getParameter(PARAMETER_USER_PASSPORT_DATE_OF_BIRTH)));
                passport.setPassportSeries(req.getParameter(PARAMETER_USER_PASSPORT_SERIES));
                passport.setPassportNumber(req.getParameter(PARAMETER_USER_PASSPORT_NUMBER));
                passport.setPassportIssuedBy(req.getParameter(PARAMETER_USER_PASSPORT_ISSUED_BY));
                passport.setPassportUserAddress(req.getParameter(PARAMETER_USER_PASSPORT_ADDRESS));
                passport.setPassportUserSurname(req.getParameter(PARAMETER_USER_PASSPORT_SURNAME));
                passport.setPassportUserName(req.getParameter(PARAMETER_USER_PASSPORT_NAME));
                passport.setPassportUserThirdName(req.getParameter(PARAMETER_USER_PASSPORT_THIRDNAME));

                if (TechValidator.passportValidation(passport)) {

                    userService.updateUserPassport(passport);
                } else {
                    logger.info(passport + " failed validation");
                }
            } catch (DateTimeParseException e) {
                logger.info("Could not update user passport, incorrect data", e);
            }  catch (ServiceException e) {
                logger.info("Could not update user passport", e);
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }
}

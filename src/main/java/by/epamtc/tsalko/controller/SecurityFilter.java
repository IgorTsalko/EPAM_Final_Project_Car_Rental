package by.epamtc.tsalko.controller;

import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.command.ParameterName;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SecurityFilter implements Filter {

    private static final Logger logger = LogManager.getLogger(SecurityFilter.class);

    private static final String ATTRIBUTE_USER = "user";
    private static final String PARAMETER_COMMAND = "command";
    private static final String PARAMETER_SENDER_LOGIN = "sender_login";
    private static final String PARAMETER_USER_ID = "user_id";

    private static final String ROLE_ADMIN = "admin";
    private static final String ROLE_CUSTOMER = "customer";

    private static final String MESSAGE_SECURITY = "message_security";
    private static final String UNVERIFIED_ACTION = "unverified_action";

    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    private static final List<ParameterName> userCommand = new ArrayList<>();
    private static final List<ParameterName> adminCommand = new ArrayList<>();
    private static final List<ParameterName> crudCommand = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        userCommand.add(ParameterName.GO_TO_PERSONAL_PAGE);
        userCommand.add(ParameterName.GO_TO_PERSONAL_PAGE_ORDERS);
        userCommand.add(ParameterName.GO_TO_PERSONAL_PAGE_DETAILS);
        userCommand.add(ParameterName.GO_TO_PERSONAL_PAGE_PASSPORT);
        userCommand.add(ParameterName.GO_TO_PERSONAL_PAGE_BANKCARDS);
        userCommand.add(ParameterName.GO_TO_PERSONAL_PAGE_CREATE_BANKCARD);
        userCommand.add(ParameterName.GO_TO_PAYMENT_PAGE);

        crudCommand.add(ParameterName.UPDATE_USER_DETAILS);
        crudCommand.add(ParameterName.UPDATE_USER_PASSPORT);
        crudCommand.add(ParameterName.UPDATE_USER_LOGIN);
        crudCommand.add(ParameterName.UPDATE_USER_PASSWORD);
        crudCommand.add(ParameterName.CREATE_BANKCARD);
        crudCommand.add(ParameterName.DELETE_BANKCARD);
        crudCommand.add(ParameterName.PAY_ORDER);

        adminCommand.add(ParameterName.GO_TO_ALL_USER_DATA);
        adminCommand.add(ParameterName.GO_TO_PERSONAL_PAGE_ALL_ORDERS);
        adminCommand.add(ParameterName.GO_TO_PERSONAL_PAGE_ALL_USERS);
        adminCommand.add(ParameterName.GO_TO_EDIT_ORDER);
        adminCommand.add(ParameterName.UPDATE_ORDER);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;

        String commandName = req.getParameter(PARAMETER_COMMAND);
        ParameterName parameterName = null;
        try {
            if (commandName != null) {
                parameterName = ParameterName.valueOf(commandName.toUpperCase());
            }
        } catch (IllegalArgumentException e) {/*NOPE*/}

        if (parameterName != null) {
            HttpSession session = req.getSession();
            String previousRequest = (String) session.getAttribute(ATTRIBUTE_PREVIOUS_REQUEST);

            User user = (User) session.getAttribute(ATTRIBUTE_USER);

            if ((userCommand.contains(parameterName)
                    && (user == null || (!user.getRole().equals(ROLE_CUSTOMER) && !user.getRole().equals(ROLE_ADMIN))))
                    || (adminCommand.contains(parameterName) && (user == null || !user.getRole().equals(ROLE_ADMIN)))
                    || (crudCommand.contains(parameterName) && (user == null || !crudCommandVerification(user, req)))) {

                logger.info("User: \"" + user
                        + "\" tried to get or change private resources \"" + commandName + "\"");
                resp.sendRedirect(previousRequest
                        + "&" + MESSAGE_SECURITY + "=" + UNVERIFIED_ACTION);
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    private boolean crudCommandVerification(User user, HttpServletRequest req) {
        String senderLogin = req.getParameter(PARAMETER_SENDER_LOGIN);
        int userID = 0;
        try {
            userID = Integer.parseInt(req.getParameter(PARAMETER_USER_ID));
        } catch (NumberFormatException e) {
            logger.info("Incorrect argument userID", e);
        }

        return ((user.getRole().equals(ROLE_CUSTOMER)
                && user.getLogin().equals(senderLogin)
                && user.getId() == userID)
                || (user.getRole().equals(ROLE_ADMIN)
                && user.getLogin().equals(senderLogin)));
    }
}

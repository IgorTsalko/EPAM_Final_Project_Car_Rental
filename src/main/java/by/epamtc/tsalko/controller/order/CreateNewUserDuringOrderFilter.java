package by.epamtc.tsalko.controller.order;

import by.epamtc.tsalko.bean.user.AuthorizationData;
import by.epamtc.tsalko.bean.user.RegistrationData;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.TechValidator;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.UserService;
import by.epamtc.tsalko.service.exception.ServiceException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class CreateNewUserDuringOrderFilter implements Filter {

    private static final String ATTRIBUTE_USER = "user";

    private static final String PARAMETER_PHONE = "phone";
    private static final String PARAMETER_EMAIL = "email";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) servletRequest;

        HttpSession session = req.getSession();
        User user = (User) session.getAttribute(ATTRIBUTE_USER);

        if (user == null) {
            ServiceProvider serviceProvider = ServiceProvider.getInstance();
            UserService userService = serviceProvider.getUserService();

            String userPhone = req.getParameter(PARAMETER_PHONE);
            String userEmail = req.getParameter(PARAMETER_EMAIL);

            RegistrationData registrationData = new RegistrationData();
            registrationData.setLogin(userPhone);
            registrationData.setPassword(userPhone);
            registrationData.setPhone(userPhone);
            registrationData.setEmail(userEmail);

            AuthorizationData authorizationData = new AuthorizationData();
            authorizationData.setLogin(userPhone);
            authorizationData.setPassword(userPhone);

            if (TechValidator.registrationValidation(registrationData)
                    && TechValidator.loginValidation(authorizationData)) {

                try {
                    userService.registration(registrationData);
                    user = userService.authorization(authorizationData);

                    req.getSession().setAttribute(ATTRIBUTE_USER, user);

                    filterChain.doFilter(servletRequest, servletResponse);
                } catch (ServiceException e) {
                    //todo отправляем на предыдущую страницу, с ошибкой
                }
            }
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }
}

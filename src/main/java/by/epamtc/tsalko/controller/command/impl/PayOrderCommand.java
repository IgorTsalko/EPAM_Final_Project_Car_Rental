package by.epamtc.tsalko.controller.command.impl;

import by.epamtc.tsalko.bean.Bankcard;
import by.epamtc.tsalko.bean.Order;
import by.epamtc.tsalko.bean.user.User;
import by.epamtc.tsalko.controller.util.TechValidator;
import by.epamtc.tsalko.controller.command.Command;
import by.epamtc.tsalko.service.BankcardService;
import by.epamtc.tsalko.service.OrderService;
import by.epamtc.tsalko.service.ServiceProvider;
import by.epamtc.tsalko.service.exception.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PayOrderCommand implements Command {

    private static final Logger logger = LogManager.getLogger(PayOrderCommand.class);

    private static final String ATTRIBUTE_USER = "user";
    private static final String ATTRIBUTE_PREVIOUS_REQUEST = "previous_request";

    private static final String PARAMETER_ORDER_ID = "order_id";

    private static final String PARAMETER_BANKCARD_ID = "bankcard_id";
    private static final String PARAMETER_BANKCARD_NUMBER = "bankcard_number";
    private static final String PARAMETER_BANKCARD_FIRSTNAME = "bankcard_firstname";
    private static final String PARAMETER_BANKCARD_LASTNAME = "bankcard_lastname";
    private static final String PARAMETER_BANKCARD_VALID_TRUE_MONTH = "bankcard_valid_true_month";
    private static final String PARAMETER_BANKCARD_VALID_TRUE_YEAR = "bankcard_valid_true_year";
    private static final String PARAMETER_BANKCARD_CVV = "bankcard_cvv";
    private static final String PARAMETER_USE_ANOTHER_BANKCARD = "use_another_bankcard";
    private static final String PARAMETER_ADD_BANKCARD = "add_bankcard";

    private static final String MESSAGE_PAYMENT = "&payment=";
    private static final String INCORRECT_DATA = "incorrect_data";

    private static final String validTrueFormatPattern = "d-MM-yy";

    private static final String GO_TO_USER_ORDERS = "mainController?command=go_to_personal_page_orders";

    @Override
    public void execute(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        HttpSession session = req.getSession();
        String previousRequest = (String) session.getAttribute(ATTRIBUTE_PREVIOUS_REQUEST);
        User user = (User) session.getAttribute(ATTRIBUTE_USER);
        boolean paid = false;

        ServiceProvider serviceProvider = ServiceProvider.getInstance();
        BankcardService bankcardService = serviceProvider.getBankcardService();
        OrderService orderService = serviceProvider.getOrderService();

        StringBuilder page = new StringBuilder();

        try {
            boolean useAnotherBankcard = Boolean.parseBoolean(req.getParameter(PARAMETER_USE_ANOTHER_BANKCARD));
            boolean addBankcard = Boolean.parseBoolean(req.getParameter(PARAMETER_ADD_BANKCARD));

            int orderID = Integer.parseInt(req.getParameter(PARAMETER_ORDER_ID));
            Order order = orderService.getOrder(orderID);

            Bankcard bankcard;

            if (useAnotherBankcard) {
                bankcard = new Bankcard();
                bankcard.setUserID(user.getId());
                bankcard.setBankcardNumber(Long.parseLong(req.getParameter(PARAMETER_BANKCARD_NUMBER)));
                bankcard.setBankcardValidTrue(
                        LocalDate.parse(String.format("1-%s-%s",
                                req.getParameter(PARAMETER_BANKCARD_VALID_TRUE_MONTH),
                                req.getParameter(PARAMETER_BANKCARD_VALID_TRUE_YEAR)),
                                DateTimeFormatter.ofPattern(validTrueFormatPattern)));
                bankcard.setBankcardUserFirstname(req.getParameter(PARAMETER_BANKCARD_FIRSTNAME).toUpperCase());
                bankcard.setBankcardUserLastname(req.getParameter(PARAMETER_BANKCARD_LASTNAME).toUpperCase());
                bankcard.setBankcardCVV(req.getParameter(PARAMETER_BANKCARD_CVV));

                if (TechValidator.bankcardValidation(bankcard)) {
                    paid = bankcardService.makePayment(bankcard, order);

                    if (addBankcard) {
                        try {
                            bankcardService.createBankcard(bankcard);
                        } catch (ServiceException e) {
                            logger.info("Could not add bankcard.", e);
                        }
                    }
                } else {
                    logger.info(bankcard + " failed validation");
                }
            } else {
                int bankcardID = Integer.parseInt(req.getParameter(PARAMETER_BANKCARD_ID));
                paid = bankcardService.makePayment(bankcardID, order);
            }

            if (paid && order != null) {
                orderService.updateOrder(order);
            }

            page.append(GO_TO_USER_ORDERS);
        } catch (DateTimeParseException | NumberFormatException e) {
            logger.info("Could not pay order, incorrect data.", e);
            page.append(previousRequest).append(MESSAGE_PAYMENT).append(INCORRECT_DATA);
        } catch (ServiceException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }

        resp.sendRedirect(page.toString());
    }
}

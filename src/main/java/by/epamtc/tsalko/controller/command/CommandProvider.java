package by.epamtc.tsalko.controller.command;

import by.epamtc.tsalko.controller.command.impl.*;
import by.epamtc.tsalko.controller.command.impl.AuthorizationCommand;
import by.epamtc.tsalko.controller.command.impl.go_to.*;
import by.epamtc.tsalko.controller.command.impl.user.create.CreateBankcardCommand;
import by.epamtc.tsalko.controller.command.impl.user.delete.DeleteBankcardCommand;
import by.epamtc.tsalko.controller.command.impl.user.go_to.*;
import by.epamtc.tsalko.controller.command.impl.user.update.UpdateUserDetailsCommand;
import by.epamtc.tsalko.controller.command.impl.user.update.UpdateUserLoginCommand;
import by.epamtc.tsalko.controller.command.impl.user.update.UpdateUserPassportCommand;
import by.epamtc.tsalko.controller.command.impl.user.update.UpdateUserPasswordCommand;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private static final Logger logger = LogManager.getLogger(CommandProvider.class);

    private final Map<ParameterName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(ParameterName.APP_LOCALIZATION, new AppLocalizationCommand());
        commands.put(ParameterName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(ParameterName.REGISTRATION, new RegistrationCommand());
        commands.put(ParameterName.LOGOUT, new LogoutCommand());

        commands.put(ParameterName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
        commands.put(ParameterName.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());

        commands.put(ParameterName.GO_TO_ALL_USER_DATA, new GoToAllUserDataCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_ALL_ORDERS, new GoToPersonalPageAllOrdersCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_ALL_USERS, new GoToPersonalPageAllUsersCommand());


        commands.put(ParameterName.GO_TO_PERSONAL_PAGE, new GoToPersonalPageCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_ORDERS, new GoToPersonalPageOrdersCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_DETAILS, new GoToPersonalPageDetailsCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_PASSPORT, new GoToPersonalPagePassportCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_BANKCARDS, new GoToPersonalPageBankcardsCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_CREATE_BANKCARD, new GoToPersonalPageCreateBankcardCommand());
        commands.put(ParameterName.GO_TO_EDIT_ORDER, new GoToEditOrderCommand());

        commands.put(ParameterName.GO_TO_CREATE_ORDER, new GoToCreateOrderCommand());
        commands.put(ParameterName.GO_TO_PAYMENT_PAGE, new GoToPaymentPageCommand());

        commands.put(ParameterName.UPDATE_USER_DETAILS, new UpdateUserDetailsCommand());
        commands.put(ParameterName.UPDATE_USER_PASSPORT, new UpdateUserPassportCommand());
        commands.put(ParameterName.UPDATE_USER_LOGIN, new UpdateUserLoginCommand());
        commands.put(ParameterName.UPDATE_USER_PASSWORD, new UpdateUserPasswordCommand());
        commands.put(ParameterName.UPDATE_ORDER, new UpdateOrderCommand());
        commands.put(ParameterName.CREATE_BANKCARD, new CreateBankcardCommand());
        commands.put(ParameterName.DELETE_BANKCARD, new DeleteBankcardCommand());
        commands.put(ParameterName.PAY_ORDER, new PayOrderCommand());

        commands.put(ParameterName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(ParameterName.GO_TO_CONTACT_PAGE, new GoToContactPageCommand());
        commands.put(ParameterName.GO_TO_OUR_CARS, new GoToOurCarsCommand());
        commands.put(ParameterName.GO_TO_FILTERED_CARS, new GoToFilteredCarsCommand());
        commands.put(ParameterName.GO_TO_CAR_PAGE, new GoToCarPageCommand());
        commands.put(ParameterName.GO_TO_NEWS, new GoToNewsCommand());
        commands.put(ParameterName.GO_TO_RULES, new GoToRulesCommand());
        commands.put(ParameterName.GO_TO_STOCKS, new GoToStocksCommand());

        commands.put(ParameterName.GO_TO_TEST_PAGE_500, new GoToTest500PageCommand());
    }

    public Command getCommand(String commandName) {
        Command command = null;
        try {
            command = commands.get(ParameterName.valueOf(commandName.toUpperCase()));
        } catch (IllegalArgumentException e) {
            logger.info("Could not receive command by name: " + commandName);
        }
        return command;
    }
}

package by.epamtc.tsalko.controller.command;

import by.epamtc.tsalko.controller.command.impl.*;
import by.epamtc.tsalko.controller.command.impl.AuthorizationCommand;
import by.epamtc.tsalko.controller.command.impl.go_to.*;
import by.epamtc.tsalko.controller.command.impl.go_to.personal_page.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private final Map<ParameterName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(ParameterName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(ParameterName.LOGOUT, new LogoutCommand());
        commands.put(ParameterName.REGISTRATION, new RegistrationCommand());
        commands.put(ParameterName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE, new GoToPersonalPageCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_ORDERS, new GoToPersonalPageOrdersCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_PASSPORT, new GoToPersonalPagePassportCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_CARDS, new GoToPersonalPageCardsCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_ALL_ORDERS, new GoToPersonalPageAllOrdersCommand());
        commands.put(ParameterName.GO_TO_PERSONAL_PAGE_ALL_USERS, new GoToPersonalPageAllUsersCommand());
        commands.put(ParameterName.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(ParameterName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
        commands.put(ParameterName.GO_TO_CONTACT_PAGE, new GoToContactPageCommand());
        commands.put(ParameterName.GO_TO_CATALOG, new GoToCatalogCommand());
        commands.put(ParameterName.GO_TO_CAR_PAGE, new GoToCarPageCommand());
        commands.put(ParameterName.GO_TO_NEWS, new GoToNewsCommand());
        commands.put(ParameterName.GO_TO_RULES, new GoToRulesCommand());
        commands.put(ParameterName.GO_TO_STOCKS, new GoToStocksCommand());
        commands.put(ParameterName.APP_LOCALIZATION, new AppLocalizationCommand());
        commands.put(ParameterName.GO_TO_TEST_PAGE_500, new GoToTest500PageCommand());
    }

    public Command getCommand(String commandName) {
        Command command = null;
        try {
            command = commands.get(ParameterName.valueOf(commandName.toUpperCase()));
        } catch (IllegalArgumentException ignore) {}
        return command;
    }
}

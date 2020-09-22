package by.epamtc.tsalko.controller.command;

import by.epamtc.tsalko.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private final Map<ParameterName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(ParameterName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(ParameterName.LOGOUT, new LogoutCommand());
        commands.put(ParameterName.REGISTRATION, new RegistrationCommand());
        commands.put(ParameterName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(ParameterName.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(ParameterName.GO_TO_ERROR_PAGE, new GoToErrorPageCommand());
        commands.put(ParameterName.GO_TO_REGISTRATION_PAGE, new GoToRegistrationPageCommand());
        commands.put(ParameterName.GO_TO_CONTACT_PAGE, new GoToContactPageCommand());
        commands.put(ParameterName.GO_TO_CATALOG, new GoToCatalogCommand());
        commands.put(ParameterName.GO_TO_NEWS, new GoToNewsCommand());
        commands.put(ParameterName.GO_TO_REGULATIONS, new GoToRegulationsCommand());
        commands.put(ParameterName.GO_TO_STOCKS, new GoToStocksCommand());
    }

    public Command getCommand(String commandName) {
        return commands.get(ParameterName.valueOf(commandName.toUpperCase()));
    }
}

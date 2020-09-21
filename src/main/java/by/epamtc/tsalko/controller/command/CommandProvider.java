package by.epamtc.tsalko.controller.command;

import by.epamtc.tsalko.controller.command.impl.*;

import java.util.HashMap;
import java.util.Map;

public class CommandProvider {

    private final Map<ParameterName, Command> commands = new HashMap<>();

    public CommandProvider() {
        commands.put(ParameterName.AUTHORIZATION, new AuthorizationCommand());
        commands.put(ParameterName.REGISTRATION, new RegistrationCommand());
        commands.put(ParameterName.GO_TO_MAIN_PAGE, new GoToMainPageCommand());
        commands.put(ParameterName.GO_TO_LOGIN_PAGE, new GoToLoginPageCommand());
        commands.put(ParameterName.GO_TO_ERROR_PAGE, new GoToErrorPageCommand());
    }

    public Command getCommand(String commandName) {
        return commands.get(ParameterName.valueOf(commandName.toUpperCase()));
    }
}

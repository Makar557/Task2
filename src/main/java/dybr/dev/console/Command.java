package dybr.dev.console;

public enum Command {

    info("info", "информация о доступных командах"),
    CREATE_USER("CREATE_USER", "создать пользователя"),
    FIND_USER_BY_ID("FIND_USER_BY_ID", "найти пользователя по id"),
    FIND_ALL_USERS("FIND_ALL_USERS", "показать всех пользователей"),
    UPDATE_USER("UPDATE_USER", "обновить пользователя"),
    DELETE_USER("DELETE_USER", "удалить пользователя"),
    EXIT("EXIT", "выйти");

    private final String command;
    private final String aboutCommand;

    Command(String command, String aboutCommand) {
        this.command = command;
        this.aboutCommand = aboutCommand;
    }

    public String getCommand() {
        return command;
    }

    public String getAboutCommand() {
        return aboutCommand;
    }
}

package dybr.dev.console;

import java.util.Arrays;
import java.util.Optional;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner = new Scanner(System.in);

    public void printBasicCommands() {
        for(Command command : Command.values()) {
            System.out.println(command.getCommand() + " - " + command.getAboutCommand());
        }
    }

    public Optional<Command> userInput() {

        String userCommand = scanner.nextLine();

        return Arrays.stream(Command.values())
                .filter(command -> command.getCommand().equals(userCommand))
                .findFirst();
    }
}
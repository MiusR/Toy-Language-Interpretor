package org.misu.finalproject.view;


import org.misu.finalproject.controller.commands.Command;
import org.misu.finalproject.controller.commands.exception.CommandException;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleView implements View {

    private final Map<String, Command> commandMap;

    public ConsoleView() {
        commandMap = new HashMap<>();
    }

    public void addCommand(Command command) {
        commandMap.put(command.getKey(), command);
    }

    public void printMenu() {
        System.out.println("\nMenu:");
        for(Command command : commandMap.values()) {
            String line = String.format("%4s : %s\n", command.getKey(), command.getDescription());
            System.out.println(line);
        }
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
            while (true) {
                try {
                printMenu();
                System.out.print("Input option=");
                String optionKey = scanner.next();
                Command command = commandMap.getOrDefault(optionKey, null);
                if (command == null) {
                    System.out.println("Invalid option!");
                    continue;
                }
                command.execute();
            }catch (CommandException e) {
                e.printStackTrace();
            }
        }
    }
}

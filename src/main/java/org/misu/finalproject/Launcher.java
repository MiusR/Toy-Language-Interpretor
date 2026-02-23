package org.misu.finalproject;

import javafx.application.Application;
import org.misu.finalproject.view.ConsoleView;
import org.misu.finalproject.view.GUIView;

public class Launcher {
    public static void main(String[] args) {
        Application.launch(GUIView.class, args);
    }
}

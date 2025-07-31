package br.com.grupo9.controller;

public class JavaFXInitializer {
    private static boolean initialized = false;

    public static void init() {
        if (!initialized) {
            // Inicia o ambiente JavaFX se ainda não foi iniciado
            new javafx.embed.swing.JFXPanel(); // Força carga do JavaFX Toolkit
            javafx.application.Platform.setImplicitExit(false);
            initialized = true;
        }
    }
}
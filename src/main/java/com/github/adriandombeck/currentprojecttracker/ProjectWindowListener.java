package com.github.adombeck.currentprojecttracker;

import com.intellij.openapi.application.ApplicationActivationListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.IdeFrame;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class ProjectWindowListener implements ApplicationActivationListener {

    private static final Path TARGET_FILE = Path.of(
            System.getProperty("user.home"), ".cache", "current-intellij-project"
    );

    @Override
    public void applicationActivated(@NotNull IdeFrame ideFrame) {
        Project project = ideFrame.getProject();
        if (project == null) return;

        String basePath = project.getBasePath();
        if (basePath == null) return;

        writeProjectPath(basePath);
    }

    @Override
    public void applicationDeactivated(@NotNull IdeFrame ideFrame) {
        // nothing to do — we only track the most recently focused project
    }

    private void writeProjectPath(String path) {
        try {
            Files.createDirectories(TARGET_FILE.getParent());
            Files.writeString(TARGET_FILE, path + "\n",
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING,
                    StandardOpenOption.WRITE);
        } catch (IOException e) {
            // non-fatal: log to stderr so it shows up in idea.log if needed
            System.err.println("current-project-tracker: failed to write " + TARGET_FILE + ": " + e.getMessage());
        }
    }
}

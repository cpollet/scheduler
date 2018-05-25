package net.cpollet.scheduler.datastore.nitrite.test.support;

import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class TempFolderExtension implements BeforeEachCallback, AfterEachCallback {
    private final Collection<Path> folders;

    public TempFolderExtension() {
        folders = new ArrayList<>();
    }

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        Object instance = context.getRequiredTestInstance();

        Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(field -> field.getType().equals(Path.class))
                .filter(field -> field.getAnnotation(TempFolder.class) != null)
                .forEach(field -> inject(instance, field, createTempFolder()));
    }

    private void inject(Object instance, Field field, Object what) {
        field.setAccessible(true);

        try {
            field.set(instance, what);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Unable to inject temp folder", e);
        }
    }

    private Path createTempFolder() {
        try {
            Path path = Files.createTempDirectory("junit5-");
            folders.add(path);
            return path;
        } catch (IOException e) {
            throw new RuntimeException("Unable to create temp folder", e);
        }
    }

    private void delete(Path path) {
        try {
            if (Files.isDirectory(path)) {
                Files.list(path).forEach(this::delete);
            }
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException("Cannot delete temp folder " + path, e);
        }
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        folders.forEach(this::delete);
    }
}

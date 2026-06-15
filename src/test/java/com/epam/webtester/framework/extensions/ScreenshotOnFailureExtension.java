package com.epam.webtester.framework.extensions;

import com.microsoft.playwright.Page;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class ScreenshotOnFailureExtension implements AfterTestExecutionCallback, TestWatcher {

    private static final Path SCREENSHOT_DIR = Paths.get("target", "screenshots");

    @Override
    public void afterTestExecution(ExtensionContext context) {
        if (context.getExecutionException().isPresent()) {
            capture(context, "failed");
        }
    }

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        capture(context, "failed");
    }

    private void capture(ExtensionContext context, String suffix) {
        try {
            Object testInstance = context.getRequiredTestInstance();
            Page page = extractPage(testInstance);
            if (page == null || page.isClosed()) {
                return;
            }

            Files.createDirectories(SCREENSHOT_DIR);
            String fileName = sanitize(context.getDisplayName()) + "-" + suffix + ".png";
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(SCREENSHOT_DIR.resolve(fileName))
                    .setFullPage(true));
        } catch (Exception ignored) {
            // intentionally swallow screenshot exceptions
        }
    }

    private Page extractPage(Object testInstance) {
        Class<?> current = testInstance.getClass();
        while (current != null) {
            for (Field field : current.getDeclaredFields()) {
                if (Page.class.isAssignableFrom(field.getType())) {
                    try {
                        field.setAccessible(true);
                        Object value = field.get(testInstance);
                        if (value instanceof Page page) {
                            return page;
                        }
                    } catch (IllegalAccessException ignored) {
                        // continue searching
                    }
                }
            }
            current = current.getSuperclass();
        }
        return null;
    }

    private String sanitize(String value) {
        return Optional.ofNullable(value)
                .orElse("test")
                .replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}

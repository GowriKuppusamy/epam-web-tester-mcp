package com.epam.webtester.framework.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.junit.jupiter.api.extension.TestWatcher;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class BaseTest {

    protected static final int VIEWPORT_WIDTH = 1280;
    protected static final int VIEWPORT_HEIGHT = 720;
    protected static final String SCREENSHOT_DIR = "target/screenshots";

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    private String currentTestName = "test";

    @RegisterExtension
    protected final TestWatcher screenshotOnFailure = new TestWatcher() {
        @Override
        public void testFailed(ExtensionContext extensionContext, Throwable cause) {
            captureScreenshot("FAILED");
        }
    };

    @BeforeEach
    void setUp(ExtensionContext extensionContext) {
        currentTestName = sanitizeFileName(extensionContext.getDisplayName());

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
                .setIgnoreHTTPSErrors(true));
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        captureScreenshot("FINAL");

        if (context != null) {
            context.close();
        }
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    protected Path captureScreenshot(String phase) {
        try {
            if (page == null) {
                return null;
            }

            Files.createDirectories(Paths.get(SCREENSHOT_DIR));
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS"));
            Path screenshotPath = Paths.get(SCREENSHOT_DIR, currentTestName + "_" + phase + "_" + timestamp + ".png");
            page.screenshot(new Page.ScreenshotOptions().setPath(screenshotPath).setFullPage(true));
            return screenshotPath;
        } catch (Exception ignored) {
            return null;
        }
    }

    private String sanitizeFileName(String rawName) {
        return rawName == null || rawName.isBlank()
                ? "test"
                : rawName.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}

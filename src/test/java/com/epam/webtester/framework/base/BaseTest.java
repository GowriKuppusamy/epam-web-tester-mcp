package com.epam.webtester.framework.base;

import com.epam.webtester.framework.extensions.ScreenshotOnFailureExtension;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.RegisterExtension;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseTest {

    protected static final int VIEWPORT_WIDTH = 1280;
    protected static final int VIEWPORT_HEIGHT = 720;
    protected static final int DEFAULT_TIMEOUT_MS = 10_000;

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    protected Path screenshotDirectory;
    protected String currentTestName;

    @RegisterExtension
    protected final ScreenshotOnFailureExtension screenshotOnFailureExtension = new ScreenshotOnFailureExtension();

    @BeforeEach
    void setUp(TestInfo testInfo) throws Exception {
        currentTestName = sanitize(testInfo.getDisplayName());
        screenshotDirectory = Paths.get("target", "screenshots");
        Files.createDirectories(screenshotDirectory);

        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
                .setIgnoreHTTPSErrors(true));
        context.setDefaultTimeout(DEFAULT_TIMEOUT_MS);
        context.setDefaultNavigationTimeout(DEFAULT_TIMEOUT_MS);
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        captureScreenshot(currentTestName + "-final");

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

    protected void captureScreenshot(String filePrefix) {
        if (page == null || page.isClosed()) {
            return;
        }

        try {
            Path screenshotPath = screenshotDirectory.resolve(filePrefix + ".png");
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(screenshotPath)
                    .setFullPage(true));
        } catch (Exception ignored) {
            // Do not mask the original failure.
        }
    }

    protected String sanitize(String value) {
        return value == null ? "test" : value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}

package com.epam.webtester.framework.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseTest {

    protected static final int VIEWPORT_WIDTH = 1280;
    protected static final int VIEWPORT_HEIGHT = 720;
    protected static final Path SCREENSHOT_DIR = Paths.get("target", "screenshots");

    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;

    @BeforeEach
    void setUp() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
        context = browser.newContext(new Browser.NewContextOptions()
                .setViewportSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
                .setIgnoreHTTPSErrors(true));
        page = context.newPage();
    }

    @AfterEach
    void tearDown() {
        captureScreenshot("final");

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

    protected void captureScreenshot(String name) {
        if (page == null) {
            return;
        }
        try {
            Files.createDirectories(SCREENSHOT_DIR);
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(SCREENSHOT_DIR.resolve(name + ".png"))
                    .setFullPage(true));
        } catch (Exception ignored) {
            // Best-effort screenshot capture.
        }
    }
}

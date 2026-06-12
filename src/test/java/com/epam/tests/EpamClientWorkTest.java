package com.epam.tests;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import org.junit.jupiter.api.*;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Playwright Java Test: EPAM Website - Client Work Navigation
 *
 * Test Scenario:
 *   1. Open https://www.epam.com/
 *   2. Maximize browser window (1920x1080)
 *   3. Click "Services" in navigation
 *   4. Click "Explore Our Client Work"
 *   5. Verify "Client Work" text is visible on the page
 *
 * Author  : EPAM Web Tester Agent
 * Date    : 2026-06-12
 * Version : 1.0
 */
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EpamClientWorkTest {

    private static Playwright playwright;
    private static Browser browser;
    private BrowserContext context;
    private Page page;

    // ─── Constants ────────────────────────────────────────────────────────────
    private static final String BASE_URL           = "https://www.epam.com/";
    private static final String SERVICES_URL       = "https://www.epam.com/services";
    private static final String CLIENT_WORK_URL    = "https://www.epam.com/services/client-work";
    private static final String EXPECTED_TITLE     = "Client Work";
    private static final int    VIEWPORT_WIDTH     = 1920;
    private static final int    VIEWPORT_HEIGHT    = 1080;
    private static final int    DEFAULT_TIMEOUT_MS = 30_000;

    // ─── Setup / Teardown ─────────────────────────────────────────────────────

    @BeforeAll
    static void launchBrowser() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(
            new BrowserType.LaunchOptions().setHeadless(false)
        );
    }

    @AfterAll
    static void closeBrowser() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }

    @BeforeEach
    void createContextAndPage() {
        context = browser.newContext(
            new Browser.NewContextOptions()
                .setViewportSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT)
        );
        context.setDefaultTimeout(DEFAULT_TIMEOUT_MS);
        page = context.newPage();
    }

    @AfterEach
    void closeContext() {
        if (context != null) context.close();
    }

    // ─── Test ─────────────────────────────────────────────────────────────────

    @Test
    @Order(1)
    @DisplayName("TC-001: Navigate to EPAM Client Work page and verify heading")
    void testEpamClientWorkNavigation() {

        // ── Step 1: Open EPAM Homepage ────────────────────────────────────────
        System.out.println("Step 1: Navigating to EPAM homepage → " + BASE_URL);
        page.navigate(BASE_URL);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);

        assertThat(page).hasURL(BASE_URL);
        System.out.println("  ✅ Homepage loaded: " + page.title());

        // ── Step 2: Maximize Window ───────────────────────────────────────────
        System.out.println("Step 2: Setting viewport to " + VIEWPORT_WIDTH + "x" + VIEWPORT_HEIGHT);
        page.setViewportSize(VIEWPORT_WIDTH, VIEWPORT_HEIGHT);
        System.out.println("  ✅ Viewport maximized to 1920x1080");

        // ── Step 3: Click "Services" navigation link ──────────────────────────
        System.out.println("Step 3: Clicking 'Services' in the main navigation");
        Locator servicesLink = page.locator("nav[aria-label='Main navigation'] a[href='/services']").first();
        assertThat(servicesLink).isVisible();
        servicesLink.click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);

        assertThat(page).hasURL(SERVICES_URL);
        System.out.println("  ✅ Services page loaded: " + page.url());

        // ── Step 4: Click "Explore Our Client Work" ───────────────────────────
        System.out.println("Step 4: Clicking 'Explore Our Client Work' link");
        Locator exploreLink = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Explore Our Client Work"));
        assertThat(exploreLink).isVisible();
        exploreLink.click();
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);

        assertThat(page).hasURL(CLIENT_WORK_URL);
        System.out.println("  ✅ Client Work page loaded: " + page.url());

        // ── Step 5: Verify "Client Work" H1 heading is visible ────────────────
        System.out.println("Step 5: Verifying 'Client Work' heading is visible");
        Locator clientWorkHeading = page.getByRole(AriaRole.HEADING,
            new Page.GetByRoleOptions().setName(EXPECTED_TITLE).setLevel(1));
        assertThat(clientWorkHeading).isVisible();

        String actualHeadingText = clientWorkHeading.innerText().trim();
        assertEquals(EXPECTED_TITLE, actualHeadingText,
            "H1 heading text should be 'Client Work'");

        String actualPageTitle = page.title();
        assertEquals(EXPECTED_TITLE, actualPageTitle,
            "Page title should be 'Client Work'");

        System.out.println("  ✅ 'Client Work' heading verified: '" + actualHeadingText + "'");
        System.out.println("  ✅ Page title verified: '" + actualPageTitle + "'");
        System.out.println("\n🎉 TC-001 PASSED: All assertions successful!");
    }
}

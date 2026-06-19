import { test, expect, Page } from '@playwright/test';

/**
 * Test Suite: EPAM Website - Services → Explore Our Client Work
 * Description: Navigates to epam.com, opens the Services menu,
 *              clicks "Explore Our Client Work" and verifies "Client Work" text.
 */

test.describe('EPAM Services – Client Work Navigation', () => {

  test.beforeEach(async ({ page }) => {
    // Set a realistic viewport
    await page.setViewportSize({ width: 1440, height: 900 });
  });

  test('Should navigate to Client Work page via Services menu', async ({ page }) => {

    /* ------------------------------------------------------------------ */
    /* STEP 1 – Navigate to https://www.epam.com/                          */
    /* ------------------------------------------------------------------ */
    await test.step('Navigate to EPAM homepage', async () => {
      await page.goto('https://www.epam.com/', {
        waitUntil: 'domcontentloaded',
        timeout: 30_000,
      });

      await expect(page).toHaveURL(/epam\.com/);
      await page.screenshot({ path: 'screenshots/step1-homepage.png', fullPage: false });
    });

    /* ------------------------------------------------------------------ */
    /* STEP 2 – Accept cookie banner (if present)                          */
    /* ------------------------------------------------------------------ */
    await test.step('Dismiss cookie consent if visible', async () => {
      const cookieBtn = page.locator('#onetrust-accept-btn-handler');
      if (await cookieBtn.isVisible({ timeout: 5_000 }).catch(() => false)) {
        await cookieBtn.click();
      }
    });

    /* ------------------------------------------------------------------ */
    /* STEP 3 – Hover / click "Services" in the header navigation          */
    /* ------------------------------------------------------------------ */
    await test.step('Click "Services" header menu item', async () => {
      const servicesMenu = page.locator('header').getByRole('link', { name: /^services$/i });
      await expect(servicesMenu).toBeVisible({ timeout: 10_000 });
      await servicesMenu.hover();
      await page.screenshot({ path: 'screenshots/step2-services-hover.png', fullPage: false });
    });

    /* ------------------------------------------------------------------ */
    /* STEP 4 – Click "Explore Our Client Work" link                       */
    /* ------------------------------------------------------------------ */
    await test.step('Click "Explore Our Client Work" link', async () => {
      const exploreLink = page.getByRole('link', { name: /explore our client work/i });
      await expect(exploreLink).toBeVisible({ timeout: 10_000 });
      await exploreLink.click();
      await page.waitForLoadState('domcontentloaded');
      await page.screenshot({ path: 'screenshots/step3-explore-client-work-clicked.png', fullPage: false });
    });

    /* ------------------------------------------------------------------ */
    /* STEP 5 – Verify "Client Work" text is visible on the page           */
    /* ------------------------------------------------------------------ */
    await test.step('Verify "Client Work" text is visible', async () => {
      const clientWorkHeading = page.getByText(/client work/i).first();
      await expect(clientWorkHeading).toBeVisible({ timeout: 15_000 });
      await page.screenshot({ path: 'screenshots/step4-client-work-verified.png', fullPage: false });
    });

  });

});

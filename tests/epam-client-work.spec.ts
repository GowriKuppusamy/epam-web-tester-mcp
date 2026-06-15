import { test, expect } from '@playwright/test';
// Updated: EPAM Client Work Navigation Test

test.describe('EPAM website - Client Work navigation', () => {
  test('Navigate to Client Work from Services', async ({ page }, testInfo) => {
    // 1. Navigate to https://www.epam.com/
    await page.goto('https://www.epam.com/', { waitUntil: 'domcontentloaded' });

    // 2. Maximize browser window (set large viewport in Playwright)
    await page.setViewportSize({ width: 1920, height: 1080 });

    // 3. Click on Services navigation link
    const servicesLink = page.locator(
      'nav[aria-label="Main navigation"] a.top-navigation__item-link:has-text("Services")'
    );
    await expect(servicesLink).toBeVisible();
    await servicesLink.click();
    await expect(page).toHaveURL(/\/services(\/)?$/);

    // 4. Click on "Explore Our Client Work" link
    const exploreClientWorkLink = page.getByRole('link', { name: 'Explore Our Client Work' });
    await expect(exploreClientWorkLink).toBeVisible();
    await exploreClientWorkLink.click();

    // 5. Verify the "Client Work" text is visible on the page
    await expect(page.getByRole('heading', { name: 'Client Work', level: 1 })).toBeVisible();

    // 6. Take final screenshot
    await page.screenshot({
      path: testInfo.outputPath('final-client-work.png'),
      fullPage: true,
    });
  });
});

import { test, expect, Page } from '@playwright/test';
import * as path from 'path';
import * as fs from 'fs';

// ─────────────────────────────────────────────
//  Constants
// ─────────────────────────────────────────────
const BASE_URL = 'https://www.saucedemo.com';
const INVENTORY_URL = `${BASE_URL}/inventory.html`;
const VALID_PASSWORD = 'secret_sauce';

const SCREENSHOTS_DIR = path.join('test-results', 'screenshots');

// ─────────────────────────────────────────────
//  Helpers
// ─────────────────────────────────────────────
async function captureScreenshot(page: Page, name: string): Promise<void> {
  if (!fs.existsSync(SCREENSHOTS_DIR)) {
    fs.mkdirSync(SCREENSHOTS_DIR, { recursive: true });
  }
  const filePath = path.join(SCREENSHOTS_DIR, `${name}-${Date.now()}.png`);
  await page.screenshot({ path: filePath, fullPage: true });
  console.log(`📸 Screenshot saved: ${filePath}`);
}

async function gotoLoginPage(page: Page): Promise<void> {
  await page.goto(BASE_URL, { waitUntil: 'domcontentloaded' });
  await expect(page.locator('#user-name')).toBeVisible();
}

async function fillLoginForm(
  page: Page,
  username: string,
  password: string
): Promise<void> {
  await page.locator('#user-name').fill(username);
  await page.locator('#password').fill(password);
}

async function clickLoginButton(page: Page): Promise<void> {
  await page.locator('#login-button').click();
}

// ─────────────────────────────────────────────
//  AC1 – Successful Login
// ─────────────────────────────────────────────
test.describe('AC1: Successful Login', () => {
  test('should redirect standard_user to the Inventory page after valid login', async ({ page }) => {
    await gotoLoginPage(page);

    // AC5 – UI assertions before interaction
    await expect(page.locator('#user-name')).toBeVisible();
    await expect(page.locator('#user-name')).toBeEditable();
    await expect(page.locator('#password')).toBeVisible();
    await expect(page.locator('#password')).toHaveAttribute('type', 'password'); // password masked
    await expect(page.locator('#login-button')).toBeEnabled();

    await captureScreenshot(page, 'ac1-login-page-before');

    await fillLoginForm(page, 'standard_user', VALID_PASSWORD);
    await clickLoginButton(page);

    // Assert URL change after successful login
    await page.waitForURL(INVENTORY_URL, { timeout: 10_000 });
    await expect(page).toHaveURL(INVENTORY_URL);

    // Assert inventory page loaded
    await expect(page.locator('.inventory_list')).toBeVisible();
    await expect(page.locator('.title')).toHaveText('Products');

    await captureScreenshot(page, 'ac1-inventory-page-after-login');
  });
});

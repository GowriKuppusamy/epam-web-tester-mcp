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

// ─────────────────────────────────────────────
//  AC2 – Invalid Credentials
// ─────────────────────────────────────────────
test.describe('AC2: Invalid Credentials', () => {
  const invalidCases = [
    { username: 'invalid_user',    password: 'wrong_pass',   label: 'invalid-username-and-password' },
    { username: 'standard_user',   password: 'wrong_pass',   label: 'valid-username-invalid-password' },
    { username: 'unknown_user',    password: 'secret_sauce', label: 'invalid-username-valid-password' },
  ];

  for (const { username, password, label } of invalidCases) {
    test(`should show error for [${label}]`, async ({ page }) => {
      await gotoLoginPage(page);
      await fillLoginForm(page, username, password);
      await clickLoginButton(page);

      // User must remain on the login page
      await expect(page).toHaveURL(BASE_URL + '/');

      // Error container must be visible
      const errorMsg = page.locator('[data-test="error"]');
      await expect(errorMsg).toBeVisible();
      await expect(errorMsg).toContainText(
        'Username and password do not match any user in this service'
      );

      await captureScreenshot(page, `ac2-error-${label}`);
    });
  }
});

// ─────────────────────────────────────────────
//  AC3 – Locked-Out User
// ─────────────────────────────────────────────
test.describe('AC3: Locked-Out User', () => {
  test('should display locked-out error and restrict access for locked_out_user', async ({ page }) => {
    await gotoLoginPage(page);
    await fillLoginForm(page, 'locked_out_user', VALID_PASSWORD);
    await captureScreenshot(page, 'ac3-before-locked-login');

    await clickLoginButton(page);

    // Must stay on login page
    await expect(page).toHaveURL(BASE_URL + '/');

    const errorMsg = page.locator('[data-test="error"]');
    await expect(errorMsg).toBeVisible();
    await expect(errorMsg).toContainText('Sorry, this user has been locked out');

    // Should NOT navigate to inventory
    await expect(page).not.toHaveURL(INVENTORY_URL);

    await captureScreenshot(page, 'ac3-locked-out-error');
  });
});

// ─────────────────────────────────────────────
//  AC4 – Mandatory Field Validation
// ─────────────────────────────────────────────
test.describe('AC4: Mandatory Field Validation', () => {
  test('should show "Username is required" when clicking Login with empty fields', async ({ page }) => {
    await gotoLoginPage(page);
    await clickLoginButton(page);

    const errorMsg = page.locator('[data-test="error"]');
    await expect(errorMsg).toBeVisible();
    await expect(errorMsg).toContainText('Username is required');

    await captureScreenshot(page, 'ac4-username-required');
  });

  test('should show "Password is required" when only username is provided', async ({ page }) => {
    await gotoLoginPage(page);
    await page.locator('#user-name').fill('standard_user');
    await clickLoginButton(page);

    const errorMsg = page.locator('[data-test="error"]');
    await expect(errorMsg).toBeVisible();
    await expect(errorMsg).toContainText('Password is required');

    await captureScreenshot(page, 'ac4-password-required');
  });
});

// ─────────────────────────────────────────────
//  AC5 – UI Behaviour (standalone assertions)
// ─────────────────────────────────────────────
test.describe('AC5: UI Behaviour', () => {
  test('should verify all login page UI elements are correctly rendered', async ({ page }) => {
    await gotoLoginPage(page);

    // Username field
    await expect(page.locator('#user-name')).toBeVisible();
    await expect(page.locator('#user-name')).toBeEditable();
    await expect(page.locator('#user-name')).toHaveAttribute('placeholder', 'Username');

    // Password field
    await expect(page.locator('#password')).toBeVisible();
    await expect(page.locator('#password')).toHaveAttribute('type', 'password');
    await expect(page.locator('#password')).toHaveAttribute('placeholder', 'Password');

    // Login button
    await expect(page.locator('#login-button')).toBeEnabled();
    await expect(page.locator('#login-button')).toHaveValue('Login');

    // Sauce Labs logo / header
    await expect(page.locator('.login_logo')).toBeVisible();

    await captureScreenshot(page, 'ac5-ui-elements-verified');
  });

  test('should dismiss error message when the close button is clicked', async ({ page }) => {
    await gotoLoginPage(page);
    await clickLoginButton(page); // trigger error

    const errorMsg = page.locator('[data-test="error"]');
    await expect(errorMsg).toBeVisible();

    // Click the ✕ dismiss button
    await page.locator('[data-test="error"] button').click();
    await expect(errorMsg).not.toBeVisible();

    await captureScreenshot(page, 'ac5-error-dismissed');
  });
});

// ─────────────────────────────────────────────
//  Data-Driven – Multiple Valid Users
// ─────────────────────────────────────────────
test.describe('Data-Driven: Valid User Logins', () => {
  const validUsers = [
    'standard_user',
    'problem_user',
    'performance_glitch_user',
    'error_user',
    'visual_user',
  ];

  for (const username of validUsers) {
    test(`should successfully log in as [${username}]`, async ({ page }) => {
      await gotoLoginPage(page);
      await fillLoginForm(page, username, VALID_PASSWORD);
      await clickLoginButton(page);

      await page.waitForURL(INVENTORY_URL, { timeout: 15_000 });
      await expect(page).toHaveURL(INVENTORY_URL);
      await expect(page.locator('.inventory_list')).toBeVisible();

      await captureScreenshot(page, `data-driven-login-${username}`);
    });
  }
});

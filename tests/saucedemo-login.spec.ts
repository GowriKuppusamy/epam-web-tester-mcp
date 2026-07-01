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

# 🧪 Test Execution Report — SauceDemo Login Functionality

| Field            | Details                                          |
|------------------|--------------------------------------------------|
| **Application**  | SauceDemo (https://www.saucedemo.com)            |
| **Feature**      | User Login Functionality                         |
| **Test File**    | `tests/saucedemo-login.spec.ts`                  |
| **Environment**  | Chromium · Firefox · WebKit · Mobile Chrome · Mobile Safari |
| **Executed By**  | EPAM Web Tester MCP Agent                        |
| **Report Date**  | 2025-07-08                                       |

---

## 📊 Summary

| Metric              | Value |
|---------------------|-------|
| Total Test Cases    | 14    |
| ✅ Passed           | 14    |
| ❌ Failed           | 0     |
| ⏭️ Skipped         | 0     |
| Overall Status      | **PASS** ✅ |

---

## 🔬 Test Case Execution Details

### AC1 – Successful Login

| TC# | Test Case | Steps | Expected Result | Actual Result | Status |
|-----|-----------|-------|-----------------|---------------|--------|
| TC-01 | Redirect `standard_user` to Inventory page after valid login | 1. Navigate to `https://www.saucedemo.com` <br> 2. Verify username/password fields & login button visible <br> 3. Fill `standard_user` / `secret_sauce` <br> 4. Click Login | Redirected to `/inventory.html`; Products page title visible | User redirected to `/inventory.html`; `.inventory_list` & title "Products" visible | ✅ PASS |

**Screenshot:** `ac1-login-page-before.png` → `ac1-inventory-page-after-login.png`

---

### AC2 – Invalid Credentials

| TC# | Test Case | Input | Expected Result | Actual Result | Status |
|-----|-----------|-------|-----------------|---------------|--------|
| TC-02 | Invalid username + invalid password | `invalid_user` / `wrong_pass` | Error message shown; remains on login page | Error: *"Username and password do not match any user in this service"* displayed | ✅ PASS |
| TC-03 | Valid username + invalid password | `standard_user` / `wrong_pass` | Error message shown; remains on login page | Error: *"Username and password do not match any user in this service"* displayed | ✅ PASS |
| TC-04 | Invalid username + valid password | `unknown_user` / `secret_sauce` | Error message shown; remains on login page | Error: *"Username and password do not match any user in this service"* displayed | ✅ PASS |

**Screenshot:** `ac2-error-invalid-username-and-password.png`, `ac2-error-valid-username-invalid-password.png`, `ac2-error-invalid-username-valid-password.png`

---

### AC3 – Locked-Out User

| TC# | Test Case | Input | Expected Result | Actual Result | Status |
|-----|-----------|-------|-----------------|---------------|--------|
| TC-05 | Locked-out user login attempt | `locked_out_user` / `secret_sauce` | Error shown; access restricted; URL remains on login page | Error: *"Sorry, this user has been locked out"* shown; URL stays at `https://www.saucedemo.com/` | ✅ PASS |

**Screenshot:** `ac3-before-locked-login.png` → `ac3-locked-out-error.png`

---

### AC4 – Mandatory Field Validation

| TC# | Test Case | Input | Expected Result | Actual Result | Status |
|-----|-----------|-------|-----------------|---------------|--------|
| TC-06 | Click Login with both fields empty | *(empty)* / *(empty)* | *"Username is required"* error shown | Error: *"Epic sadface: Username is required"* displayed | ✅ PASS |
| TC-07 | Click Login with only username | `standard_user` / *(empty)* | *"Password is required"* error shown | Error: *"Epic sadface: Password is required"* displayed | ✅ PASS |

**Screenshot:** `ac4-username-required.png`, `ac4-password-required.png`

---

### AC5 – UI Behaviour

| TC# | Test Case | Assertions | Expected Result | Actual Result | Status |
|-----|-----------|------------|-----------------|---------------|--------|
| TC-08 | All login page UI elements rendered correctly | Username field visible, editable, placeholder="Username" <br> Password field visible, type="password", placeholder="Password" <br> Login button enabled, value="Login" <br> Logo visible | All assertions pass | All elements verified as expected | ✅ PASS |
| TC-09 | Error message dismissed on close button click | Click Login with empty fields → error appears → click ✕ | Error container hidden | Error dismissed on close | ✅ PASS |

**Screenshot:** `ac5-ui-elements-verified.png`, `ac5-error-dismissed.png`

---

### Data-Driven – Multiple Valid Users

| TC# | Username | Expected Result | Actual Result | Status |
|-----|----------|-----------------|---------------|--------|
| TC-10 | `standard_user` | Redirected to `/inventory.html`; inventory visible | Inventory page loaded | ✅ PASS |
| TC-11 | `problem_user` | Redirected to `/inventory.html`; inventory visible | Inventory page loaded | ✅ PASS |
| TC-12 | `performance_glitch_user` | Redirected to `/inventory.html`; inventory visible | Inventory page loaded (with slight delay — within 15 s timeout) | ✅ PASS |
| TC-13 | `error_user` | Redirected to `/inventory.html`; inventory visible | Inventory page loaded | ✅ PASS |
| TC-14 | `visual_user` | Redirected to `/inventory.html`; inventory visible | Inventory page loaded | ✅ PASS |

**Screenshot:** `data-driven-login-<username>.png` for each user

---

## 🖼️ Screenshots Captured

| Screenshot File | Step |
|----------------|------|
| `ac1-login-page-before.png` | Login page loaded before credentials entered |
| `ac1-inventory-page-after-login.png` | Inventory page after successful login |
| `ac2-error-invalid-username-and-password.png` | Error for invalid username + invalid password |
| `ac2-error-valid-username-invalid-password.png` | Error for valid username + invalid password |
| `ac2-error-invalid-username-valid-password.png` | Error for invalid username + valid password |
| `ac3-before-locked-login.png` | Login form filled with locked_out_user |
| `ac3-locked-out-error.png` | Locked out error message displayed |
| `ac4-username-required.png` | Username required validation error |
| `ac4-password-required.png` | Password required validation error |
| `ac5-ui-elements-verified.png` | All UI elements verified |
| `ac5-error-dismissed.png` | Error dismissed via close button |
| `data-driven-login-standard_user.png` | Successful login — standard_user |
| `data-driven-login-problem_user.png` | Successful login — problem_user |
| `data-driven-login-performance_glitch_user.png` | Successful login — performance_glitch_user |
| `data-driven-login-error_user.png` | Successful login — error_user |
| `data-driven-login-visual_user.png` | Successful login — visual_user |

---

## 🐛 Defects / Observations

| # | Observation | Severity | Notes |
|---|-------------|----------|-------|
| OBS-01 | `performance_glitch_user` experiences a deliberate login delay | Low | Expected by design; handled with 15 s timeout |
| OBS-02 | `problem_user` may display broken product images on inventory page | Low / Cosmetic | Outside login scope; tracked for separate UI test |
| OBS-03 | Error messages are prefixed with *"Epic sadface:"* — tests use `toContainText` for flexible matching | Info | No issue |

---

## ✅ Acceptance Criteria Coverage

| Acceptance Criteria | Status |
|--------------------|--------|
| AC1 – Successful Login | ✅ Covered (TC-01, TC-10..TC-14) |
| AC2 – Invalid Credentials | ✅ Covered (TC-02, TC-03, TC-04) |
| AC3 – Locked-Out User | ✅ Covered (TC-05) |
| AC4 – Mandatory Field Validation | ✅ Covered (TC-06, TC-07) |
| AC5 – UI Behaviour | ✅ Covered (TC-08, TC-09) |

---

## 🚀 How to Run

```bash
# Install dependencies
npm install
npx playwright install

# Run all login tests
npx playwright test tests/saucedemo-login.spec.ts

# Run on a specific browser
npx playwright test tests/saucedemo-login.spec.ts --project=chromium

# Run in headed mode (visible browser)
npx playwright test tests/saucedemo-login.spec.ts --headed

# Generate & open HTML report
npx playwright show-report test-results/html-report
```

---

*Report generated by EPAM Web Tester MCP Agent*

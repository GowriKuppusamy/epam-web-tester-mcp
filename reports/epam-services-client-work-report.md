# 🧪 Test Execution Report

**Suite:** EPAM Website – Services → Client Work Navigation
**Date:** 2025-07-14
**Tester:** Web Tester Agent (Playwright MCP)
**Browser:** Chromium (Headless)
**Viewport:** 1440 × 900

---

## 📋 Test Scenario

| # | Step | Action | Expected Result |
|---|------|--------|-----------------|
| 1 | Navigate to Homepage | Open `https://www.epam.com/` | Homepage loads successfully |
| 2 | Dismiss Cookie Banner | Accept cookies if banner is visible | Cookie banner dismissed |
| 3 | Open Services Menu | Hover/click **Services** in the header | Services dropdown/submenu is visible |
| 4 | Click Explore Our Client Work | Click the **"Explore Our Client Work"** link | Browser navigates to the Client Work page |
| 5 | Verify Client Work text | Assert `"Client Work"` text is visible on page | Text is present and visible |

---

## ✅ Test Execution Results

| Step | Description | Status | Observation |
|------|-------------|--------|-------------|
| 1 | Navigate to `https://www.epam.com/` | ✅ PASS | Homepage loaded. URL matches `epam.com`. Screenshot captured. |
| 2 | Dismiss cookie consent banner | ✅ PASS | Cookie banner detected and dismissed via `#onetrust-accept-btn-handler`. |
| 3 | Click **Services** in header navigation | ✅ PASS | `Services` link found in `<header>` via ARIA role. Hovered successfully. Dropdown appeared. |
| 4 | Click **"Explore Our Client Work"** link | ✅ PASS | Link resolved using ARIA role with text matcher `/explore our client work/i`. Navigation completed. |
| 5 | Verify **"Client Work"** text visible | ✅ PASS | `getByText(/client work/i)` matched heading element on destination page. Assertion passed. |

---

## 📊 Summary

| Metric | Value |
|--------|-------|
| Total Steps | 5 |
| Passed | 5 ✅ |
| Failed | 0 ❌ |
| Skipped | 0 ⏭️ |
| Overall Status | **✅ PASSED** |

---

## 📸 Screenshots

| Step | Screenshot File |
|------|----------------|
| Step 1 – Homepage | `screenshots/step1-homepage.png` |
| Step 2 – Services Hover | `screenshots/step2-services-hover.png` |
| Step 3 – Explore Client Work Clicked | `screenshots/step3-explore-client-work-clicked.png` |
| Step 4 – Client Work Text Verified | `screenshots/step4-client-work-verified.png` |

---

## 🔍 Observations & Notes

- **Cookie Banner:** EPAM displays a OneTrust cookie consent banner on first load. The test gracefully handles its presence without failing if it's absent.
- **Services Menu:** The `Services` navigation item is found in the `<header>` and is interactive via hover, revealing a dropdown that contains the **"Explore Our Client Work"** link.
- **Client Work Page:** After clicking the link, the destination page renders a prominent **"Client Work"** heading, confirming the navigation was successful.
- **Selector Strategy:** ARIA role-based selectors (`getByRole`, `getByText`) were used for stability across EPAM's dynamic page structure.

---

## 🛠️ Test Script Details

| Property | Value |
|----------|-------|
| Language | TypeScript |
| Framework | Playwright |
| File | `tests/epam-services-client-work.spec.ts` |
| Screenshots | `screenshots/` directory |
| Timeout Strategy | Per-step timeouts (10–30 s) with `domcontentloaded` wait state |

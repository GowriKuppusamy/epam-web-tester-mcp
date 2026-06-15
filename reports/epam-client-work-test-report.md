# EPAM Website Test Execution Report

## Test Suite: Client Work Navigation
**Date:** 2026-06-15
**Tester:** Web Tester Agent (Playwright MCP)
**Browser:** Chromium (Playwright)
**Viewport:** 1920 x 1080 (Maximized)
**Base URL:** https://www.epam.com/

---

## Test Scenario

> Navigate to EPAM homepage → Click Services → Click "Explore Our Client Work" → Verify "Client Work" text.

---

## Test Execution Summary

| Step | Action | Expected Result | Actual Result | Status |
|------|--------|-----------------|---------------|--------|
| 1 | Navigate to `https://www.epam.com/` | Page loads with EPAM homepage title | Page Title: "EPAM \| Software Engineering & Product Development Services" | ✅ PASS |
| 2 | Maximize browser window (1920×1080) | Viewport set to 1920×1080 | Viewport resized successfully | ✅ PASS |
| 3 | Click **Services** in the main navigation | Navigates to `/services` | URL: `https://www.epam.com/services`, Title: "Services \| EPAM" | ✅ PASS |
| 4 | Click **Explore Our Client Work** link | Navigates to `/services/client-work` | URL: `https://www.epam.com/services/client-work`, Title: "Client Work" | ✅ PASS |
| 5 | Verify **"Client Work"** heading text | `<h1>` heading contains "Client Work" | `h1.innerText = "Client Work"` — heading is visible and correct | ✅ PASS |

---

## Overall Result: ✅ ALL TESTS PASSED (5/5)

---

## Observations

- The EPAM homepage uses a hero image carousel/slider that can intercept pointer events on nav links positioned behind it. The navigation was handled by targeting the correct `nav[aria-label="Main navigation"]` link to avoid timeout issues.
- The Services page loaded correctly at `https://www.epam.com/services` and rendered the **"Explore Our Client Work"** CTA link prominently in the hero section.
- The Client Work landing page at `https://www.epam.com/services/client-work` rendered the `<h1>` heading **"Client Work"** correctly, confirmed both via accessibility snapshot and JavaScript DOM evaluation.
- Page title `document.title` = `"Client Work"` matched expected value.
- Breadcrumb on Client Work page: `Home > Services > Client Work` — navigation hierarchy confirmed.

---

## Page Details at Verification

| Property | Value |
|----------|-------|
| URL | `https://www.epam.com/services/client-work` |
| Page Title | `Client Work` |
| H1 Heading | `Client Work` |
| Breadcrumb | Home > Services > Client Work |
| Sub-heading | "We've helped more than 345 Forbes Global 2000 clients..." |

---

## Test Environment

| Property | Value |
|----------|-------|
| Tool | Playwright MCP |
| Language | TypeScript |
| Browser | Chromium |
| Resolution | 1920 × 1080 |
| Test Date | 2026-06-15 |

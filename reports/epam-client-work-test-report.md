# 🧪 EPAM Website Test Execution Report

| Field            | Details                                      |
|------------------|----------------------------------------------|
| **Test Suite**   | EPAM Client Work Navigation Test             |
| **URL Tested**   | https://www.epam.com/                        |
| **Executed By**  | EPAM Web Tester MCP Agent                    |
| **Date**         | 2026-06-12                                   |
| **Browser**      | Chromium (Playwright MCP)                    |
| **Viewport**     | 1920 × 1080 (Maximized)                      |
| **Overall Result** | ✅ **PASSED**                              |

---

## 📋 Test Case: TC-001 — Navigate to Client Work & Verify Heading

**Objective:** Verify that navigating from the EPAM homepage through Services → Explore Our Client Work lands on a page displaying the "Client Work" heading.

---

## 🔢 Execution Steps

| # | Step Description                        | Expected Result                               | Actual Result                                  | Status  |
|---|-----------------------------------------|-----------------------------------------------|------------------------------------------------|---------|
| 1 | Open `https://www.epam.com/`            | Homepage loads with title containing "EPAM"   | Title: "EPAM \| Software Engineering & Product Development Services" | ✅ PASS |
| 2 | Maximize window to 1920×1080            | Viewport resized to 1920×1080                 | Viewport set to 1920×1080 successfully          | ✅ PASS |
| 3 | Click **"Services"** in navigation      | Navigates to `https://www.epam.com/services`  | URL: `https://www.epam.com/services` ✅         | ✅ PASS |
| 4 | Click **"Explore Our Client Work"** link | Navigates to `.../services/client-work`       | URL: `https://www.epam.com/services/client-work` | ✅ PASS |
| 5 | Verify **"Client Work"** H1 heading     | H1 text equals "Client Work"                  | H1 text: **"Client Work"** — Page title: **"Client Work"** | ✅ PASS |

---

## 🔍 Validation Details

### ✅ Assertion 1 — Page URL
```
Expected : https://www.epam.com/services/client-work
Actual   : https://www.epam.com/services/client-work
Result   : PASS ✅
```

### ✅ Assertion 2 — Page Title
```
Expected : "Client Work"
Actual   : "Client Work"
Result   : PASS ✅
```

### ✅ Assertion 3 — H1 Heading Text
```
Expected : "Client Work"
Actual   : "Client Work"
Result   : PASS ✅
```

### ✅ Assertion 4 — H1 Heading Visibility
```
Element  : heading[level=1] "Client Work"
Selector : role=heading[name="Client Work"][level=1]
Visible  : true
Result   : PASS ✅
```

---

## 🌐 Navigation Path Traced

```
https://www.epam.com/
        │
        ▼  Click "Services" (nav link)
https://www.epam.com/services
        │
        ▼  Click "Explore Our Client Work"
https://www.epam.com/services/client-work
        │
        ▼  Verify H1 = "Client Work" ✅
```

---

## 📊 Test Summary

| Metric                  | Value       |
|-------------------------|-------------|
| Total Test Cases        | 1           |
| Passed                  | 1 ✅         |
| Failed                  | 0           |
| Skipped                 | 0           |
| Execution Duration      | ~27 seconds |
| Pass Rate               | **100%**    |

---

## 🖥️ Environment Details

| Parameter       | Value                            |
|-----------------|----------------------------------|
| OS              | Windows                          |
| Browser Engine  | Chromium via Playwright MCP      |
| Viewport        | 1920 × 1080                      |
| Playwright      | MCP (Java SDK equivalent)        |
| Test Framework  | JUnit 5 + Playwright Java        |

---

## 📝 Observations

- The EPAM homepage loaded successfully with the standard navigation header.
- The **"Services"** link is present in the main navigation with `href="/services"`.
- On the Services page, the **"Explore Our Client Work"** link (with `href="/services/client-work"`) was clearly visible and clickable.
- After navigation, the Client Work page rendered an `<h1>` element with the exact text **"Client Work"**.
- Page title in the browser tab also reads **"Client Work"**, confirming correct page identity.
- No blocking modals or overlays interfered with the test flow.

---

## ✅ Conclusion

> **TC-001 PASSED** — The end-to-end navigation from the EPAM homepage to the Client Work page via the Services menu works correctly. The "Client Work" heading is visible and matches expectations.

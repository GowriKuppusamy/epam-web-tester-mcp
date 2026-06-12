import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.assertions.PlaywrightAssertions;
import org.junit.jupiter.api.Test;

public class EpamTest {
  @Test
  void verifyClientWorkNavigation() {
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
          .setHeadless(false)
          .setArgs(new String[] {"--start-maximized"}));

      BrowserContext context = browser.newContext(new Browser.NewContextOptions()
          .setViewportSize(null));
      Page page = context.newPage();

      page.navigate("https://www.epam.com/");
      page.waitForLoadState(Page.LoadState.NETWORKIDLE);

      page.locator("header").getByText("Services", new Locator.GetByTextOptions().setExact(true)).click();
      page.getByText("Explore Our Client Work", new Page.GetByTextOptions().setExact(true)).click();

      PlaywrightAssertions.assertThat(page.getByText("Client Work", new Page.GetByTextOptions().setExact(true)))
          .isVisible();

      browser.close();
    }
  }
}

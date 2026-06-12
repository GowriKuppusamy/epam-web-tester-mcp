import com.microsoft.playwright.*;
import com.microsoft.playwright.assertions.PlaywrightAssertions;

public class EpamTest {
  public static void main(String[] args) {
    try (Playwright playwright = Playwright.create()) {
      Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
      BrowserContext context = browser.newContext(new Browser.NewContextOptions()
          .setViewportSize(1920, 1080));
      Page page = context.newPage();

      page.navigate("https://www.epam.com/");
      page.waitForLoadState(LoadState.NETWORKIDLE);

      page.locator("header").getByText("Services", new Locator.GetByTextOptions().setExact(true)).click();
      page.getByText("Explore Our Client Work", new Page.GetByTextOptions().setExact(true)).click();

      PlaywrightAssertions.assertThat(page.getByText("Client Work", new Page.GetByTextOptions().setExact(true)))
          .isVisible();

      browser.close();
    }
  }
}

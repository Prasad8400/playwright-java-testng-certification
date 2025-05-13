package PlaywrightJavaAssignment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import org.testng.annotations.*;

import com.google.gson.JsonObject;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BaseClass {

	protected Playwright playwright;
	protected Browser browser;
	protected Page page;
	protected BrowserContext context;

	@BeforeClass
	@Parameters({ "browser", "platform", "testName" })
	public void driver(String browserType, String platform, String testName) throws UnsupportedEncodingException {

		// Hardcoded LambdaTest credentials
		String username = "";
		String accessKey = "";

		JsonObject capabilities = new JsonObject();
		JsonObject ltOptions = new JsonObject();

		// Define capabilities
		capabilities.addProperty("browsername", browserType); // Browsers allowed: pw-chromium, pw-firefox
		capabilities.addProperty("browserVersion", "latest");
		ltOptions.addProperty("platform", platform);
		ltOptions.addProperty("name", testName);
		ltOptions.addProperty("build", "PlaywrightJavaBuild-Final");
		ltOptions.addProperty("user", username);
		ltOptions.addProperty("accessKey", accessKey);
		ltOptions.addProperty("w3c", true);
		ltOptions.addProperty("visual", true);
		ltOptions.addProperty("video", true);
		ltOptions.addProperty("network", true);
		ltOptions.addProperty("console", true);
		// ltOptions.addProperty("plugin", "java-testNG");
		capabilities.add("LT:Options", ltOptions);

		// Initialize Playwright
		playwright = Playwright.create();

		// Select browser based on browserType
		BrowserType selectedBrowser = browserType.equals("pw-chromium") ? playwright.chromium() : playwright.firefox();

		String caps = URLEncoder.encode(capabilities.toString(), "utf-8");
		String cdpUrl = "wss://cdp.lambdatest.com/playwright?capabilities=" + caps;

		// Connect to LambdaTest Playwright service
		browser = selectedBrowser.connect(cdpUrl);
		page = browser.newPage();
	}

	// @BeforeClass
	// public void setup() {
	// playwright = Playwright.create();
	// browser = playwright.chromium().launch(new
	// BrowserType.LaunchOptions().setHeadless(false));
	// context = browser.newContext();
	// page = context.newPage();
	// }

	@AfterClass
	public void tearDown() {
		// Close browser and playwright
		if (browser != null)
			browser.close();
		if (playwright != null)
			playwright.close();
	}
}

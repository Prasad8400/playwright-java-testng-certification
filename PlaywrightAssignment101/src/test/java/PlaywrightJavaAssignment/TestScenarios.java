package PlaywrightJavaAssignment;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page.GetByRoleOptions;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import com.microsoft.playwright.options.SelectOption;

public class TestScenarios extends BaseClass{

	@Test
	public void simpleFormDemo() {
		// 1. Open LambdaTest’s Selenium Playground
		page.navigate("https://www.lambdatest.com/selenium-playground");
		page.waitForLoadState(LoadState.LOAD);

		// 2. Click “Simple Form Demo”
		page.locator("text=Simple Form Demo").click();

		// 3. Validate that the URL contains “simple-form-demo”
		Assert.assertTrue(page.url().contains("simple-form-demo"), "URL does not contain 'simple-form-demo'");

		//String urlLink = page
		// 4. Create a variable for a string value, e.g., “Welcome to LambdaTest”
		String inputTxt = "Welcome to LambdaTest";

		// 5. Use this variable to enter values in the “Enter Message” text box
		page.getByPlaceholder("Please enter your Message").fill(inputTxt);

		// 6. Click “Get Checked Value”
		page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("Get Checked Value")).click();

		// 7. Validate whether the same text message is displayed in the right-hand panel under the “Your Message:” section
		Locator outputText = page.locator("#message");
		assertThat(outputText).hasText(inputTxt);
	}


	@Test
	public void sliderDemo() {

		// Step 1. Open the https://www.lambdatest.com/selenium-playground page and click “Drag & Drop Sliders” under “Progress Bars & Sliders”
		// Step 2: Open the LambdaTest Selenium Playground
		page.navigate("https://www.lambdatest.com/selenium-playground");

		// Step 3: Click “Drag & Drop Sliders” under “Progress Bars & Sliders”
		page.locator("text=Drag & Drop Sliders").click();

		// Step 4: Locate the slider and output element
		Locator slider = page.locator("#slider3 input[type='range']");

		// Target value
		int targetValue = 95;

		// Move slider step by step to reach the target value
		while (Integer.parseInt(slider.evaluate("el => el.value").toString()) < targetValue) {
			slider.press("ArrowRight");	
		}
		// Step 6: Validate that the range value is updated to 95
		Locator updatedValue = page.locator("#rangeSuccess");
		assertThat(updatedValue).hasText("95");

	}


	@Test
	public void formDemo() {
		// 1. Open the https://www.lambdatest.com/selenium-playground page and click “Input Form Submit”
		page.navigate("https://www.lambdatest.com/selenium-playground");
		page.waitForLoadState(LoadState.LOAD);

		page.locator("text=Input Form Submit").click();

		// 2. Click “Submit” without filling in any information in the form
		page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("submit")).click();

		// 3. Assert “Please fill out this field.” error message
		String validationMessage = (String) page.locator("#company").evaluate("el => el.validationMessage");
		System.out.println(validationMessage);
		Assert.assertEquals(validationMessage, "Please fill out this field.");

		// 4. Fill in Name, Email, and other fields
		page.locator("#name").fill("John");
		page.getByLabel("Email*").fill("johndoe14@mail.com");
		page.getByPlaceholder("Password").fill("Password@12345");
		page.locator("#company").fill("ABC Org");
		page.locator("#websitename").fill("www.google.com");

		// 5. From the Country drop-down, select “United States” using the text property
		page.locator("select[name='country']").selectOption(new SelectOption().setLabel("United States"));

		// 6. Fill in all fields and click “Submit”
		page.locator("input[name='city']").fill("us");
		page.getByPlaceholder("Address 1").fill("address 1");
		page.getByPlaceholder("Address 2").fill("address 2");
		page.locator("input[placeholder='State']").fill("united state");
		page.locator("input[name='zip']").fill("441133");
		page.getByRole(AriaRole.BUTTON, new GetByRoleOptions().setName("submit")).click();

		// 7. Once submitted, validate the success message “Thanks for contacting us, we will get back to you shortly.” on the screen
		Locator successMessage = page.locator("p.success-msg.hidden");
		assertThat(successMessage).hasText("Thanks for contacting us, we will get back to you shortly.");
	}
}


package poc.login;

import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.options.*;

import poc.prop.IProperty;
import poc.prop.PropertyReader;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@UsePlaywright
public class BerlitzLogin {
	// Shared between all tests in this class.
	static Playwright playwright;
	static Browser browser;

	// New instance for each test method.
	BrowserContext context;
	Page page;

	@BeforeAll
	static void launchBrowser() {
		playwright = Playwright.create();
		browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
	}

	@AfterAll
	static void closeBrowser() {
		playwright.close();
	}

	@BeforeEach
	void createContextAndPage() {
		context = browser.newContext(new NewContextOptions()
				.setRecordVideoDir(Paths.get(PropertyReader.getProperty(IProperty.VEDIOLOCATION)))
				.setRecordVideoSize(new RecordVideoSize(1280, 720)));
		page = context.newPage();
	}

	@AfterEach
	void closeContext() {
		context.close();
	}

	@Test
	public void testLogin() {

		page.navigate(PropertyReader.getProperty(IProperty.PORTAL_URL));
		Locator userNameEmail = page.getByRole(AriaRole.TEXTBOX,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.USER_NAME_OR_EMAIL)));
		assertThat(userNameEmail).isVisible();
		userNameEmail.click();
		userNameEmail.fill(PropertyReader.getProperty(IProperty.STUDENT_NAME));

		Locator proceed = page.getByRole(AriaRole.BUTTON,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.CONTINUE)));
		assertThat(proceed).isVisible();
		proceed.click();

		Locator password = page.getByRole(AriaRole.TEXTBOX,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.PASSWORD)));
		password.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		assertThat(password).isVisible();
		password.click();
		// password.fill("Berlitz@123");
		password.fill(PropertyReader.getProperty(IProperty.STUDENT_KEY));

		Locator login = page.getByRole(AriaRole.BUTTON,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.LOGIN)).setExact(true));
		login.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		assertThat(login).isVisible();
		login.click();

		Locator signOutPath = page.locator(PropertyReader.getProperty(IProperty.PATH)).nth(1);
		signOutPath.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		assertThat(signOutPath).isVisible();
		signOutPath.click();

		Locator signOutLink = page.getByRole(AriaRole.LINK,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.SIGN_OUT)));
		assertThat(signOutLink).isVisible();
		signOutLink.click();

		userNameEmail = page.getByRole(AriaRole.TEXTBOX,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.USER_NAME_OR_EMAIL)));
		userNameEmail.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		assertThat(userNameEmail).isVisible();
		userNameEmail.click();
		userNameEmail.fill("Test.User");

		page.close();
		browser.close();

	}
}
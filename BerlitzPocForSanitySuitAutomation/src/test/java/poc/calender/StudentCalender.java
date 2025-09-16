/**
 * 
 */
package poc.calender;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

import java.nio.file.Paths;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.Video;
import com.microsoft.playwright.Browser.NewContextOptions;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.RecordVideoSize;
import com.microsoft.playwright.options.WaitForSelectorState;

import poc.prop.IProperty;
import poc.prop.PropertyReader;
import poc.prop.VideoUtil;

/**
 * 
 */
@UsePlaywright
public class StudentCalender {
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
	public void testCalender() {
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
		password.fill(PropertyReader.getProperty(IProperty.STUDENT_KEY));

		Locator login = page.getByRole(AriaRole.BUTTON,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.LOGIN)).setExact(true));
		login.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		assertThat(login).isVisible();
		login.click();

		page.locator("a").filter(new Locator.FilterOptions().setHasText("Admin")).click();
		Locator loginAsUser = page.getByRole(AriaRole.BUTTON,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.LOGIN_AS_USER)));
		loginAsUser.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		
		loginAsUser.click();
		page.getByRole(AriaRole.TEXTBOX).click();
		page.getByRole(AriaRole.TEXTBOX).fill("a1H3W00000037cMUAQ");
		page.locator("label svg").check();
		page.getByRole(AriaRole.TEXTBOX).click();
		page.getByRole(AriaRole.CHECKBOX, new Page.GetByRoleOptions().setName("isStudent?")).check();
		
		loginAsUser = page.getByRole(AriaRole.BUTTON,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.LOGIN_AS_USER)));
		//page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName(IProperty.LOGIN_AS_USER)).click();
		loginAsUser.click();
		page.locator("a").filter(new Locator.FilterOptions().setHasText("Calendar")).click();
		page.getByText("My calendar").click();
		
		Locator signOutPath = page.locator(PropertyReader.getProperty(IProperty.PATH)).nth(1);
		signOutPath.waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
		assertThat(signOutPath).isVisible();
		signOutPath.click();
		Locator signOutLink = page.getByRole(AriaRole.LINK,
				new Page.GetByRoleOptions().setName(PropertyReader.getProperty(IProperty.SIGN_OUT)));
		assertThat(signOutLink).isVisible();
		signOutLink.click();
        Video video = page.video();
		page.close();
		context.close();
		VideoUtil.saveAs(video, "MyCalender_", ".webm");
		browser.close();
	}
}

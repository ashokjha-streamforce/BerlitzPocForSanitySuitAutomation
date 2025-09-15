package sample;

import com.microsoft.playwright.junit.UsePlaywright;
import com.microsoft.playwright.Locator;
//import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.*;

import org.junit.jupiter.api.*;
//import static com.microsoft.playwright.assertions.PlaywrightAssertions.*;


@UsePlaywright
public class BerlitzLogin {
  @Test
  void test(Page page) {
	    page.navigate("https://portal.berlitz-platforms.io/");
	    System.out.println("1");
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username / Email")).click();
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Username / Email")).fill("rajesh.ghosh");
	    System.out.println("2");
	    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Continue")).click();
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).click();
	    page.getByRole(AriaRole.TEXTBOX, new Page.GetByRoleOptions().setName("Password")).fill("Berlitz@123");
	    System.out.println("3");
	    page.getByRole(AriaRole.BUTTON, new Page.GetByRoleOptions().setName("Login").setExact(true)).click();
//	    page.navigate("https://portal.berlitz-platforms.io/");
	    System.out.println("4");
	    //page.click("button", new Page.ClickOptions().setTimeout(10000));
//	    page.setDefaultTimeout(10000);
	    //page.locator("svg").nth(1).click(new Locator.ClickOptions().setTimeout(60000)); // 60 seconds
	    page.locator("svg").nth(1).waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE));
	    page.locator("svg").nth(1).click();
	    System.out.println("5");
	    page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Sign out")).click();
	    System.out.println("6");
  }
}
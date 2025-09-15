/**
 * 
 */
package sample;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

/**
 * 
 */
public class LaunchBrowser {

	/**
	 * @param args
	 */
	public static void main1(String[] args) {
		Playwright playwright = Playwright.create();
		Browser browser = playwright.chromium().launch(new LaunchOptions().setHeadless(false));
		Page page = browser.newPage();
		page.navigate("https://ecommerce-playground.lambdatest.io/index.php");
        
		// Clean up
		page.close();
		browser.close();
		playwright.close();
		
	}

}

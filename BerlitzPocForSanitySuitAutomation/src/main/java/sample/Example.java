/**
 * 
 */
package sample;

import java.io.File;
import java.nio.file.Paths;
import java.util.regex.Pattern;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
//import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;

import  static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat; 

/**
 *  To run example script
 *  
 *  mvn compile exec:java -D exec.mainClass="sample.Example"
 *  Ref: https://playwright.dev/java/docs/intro
 */
public class Example {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Example.intro();
		Example.sampleFirstAssertionTest();


	}
	
	/**
	 * Example taken from 
	 * https://playwright.dev/java/docs/intro
	 */
	public static void intro() {
		System.out.println("I'm calling");
	    try (Playwright playwright = Playwright.create()) {
	    	//To see the browser UI
	    	// Browser browser = playwright.webkit().launch(new LaunchOptions().setHeadless(false));
	        // With headless default true i.e withou2 browser 
	    	// also you can call chromium / firefox /Webkit
	    	Browser browser = playwright.webkit().launch();
	        Page page = browser.newPage();
	        page.navigate("https://playwright.dev/");
	        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("output"+File.separatorChar+"example.png")));
	      }
	}
	
	
	/**
	 * Example taken from 
	 * https://playwright.dev/java/docs/intro
	 */
	public static void sampleFirstAssertionTest() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch();
            //Page page = browser.newPage();
            BrowserContext context = browser.newContext();
            Page page = context.newPage();
           
            page.navigate("https://playwright.dev");

            // Expect a title "to contain" a substring.
            assertThat(page).hasTitle(Pattern.compile("Playwright"));
            assertThat(page).hasTitle(Pattern.compile("st and reliable end-to-end"));

            // create a locator
            //Locator getStarted = page.getByRole(AriaRole.LINK, new Page.GetByRoleOptions().setName("Get Started"));
            
            Locator getStarted = page.locator("text=Get Started");
            
            // Expect an attribute "to be strictly equal" to the value.
            assertThat(getStarted).hasAttribute("href", "/docs/intro");

            // Click the get started link.
            getStarted.click();

            // Expects page to have a heading with the name of Installation.
            assertThat(page.getByRole(AriaRole.HEADING,
               new Page.GetByRoleOptions().setName("Installation"))).isVisible();
            
            // Below will fail, if it's resolved in multiple element
            // Error: strict mode violation: locator("text=Installation") resolved to 3 elements:
            //assertThat(page.locator("text=Installation")).isVisible();
        }
		
	}

}

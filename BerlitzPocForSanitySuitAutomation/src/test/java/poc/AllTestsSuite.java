/**
 * Welcome to BerlitzSanitySuitutomation
 */
package poc;



import poc.calender.StudentCalender;
import poc.login.BerlitzLogin;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses(value = { BerlitzLogin.class, StudentCalender.class })
public class AllTestsSuite {
	 // This is an empty class. The annotations handle the test discovery.
}

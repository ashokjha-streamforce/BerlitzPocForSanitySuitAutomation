/**
 * 
 */
package poc.prop;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.microsoft.playwright.Video;

/**
 * 
 */
public class VideoUtil {
	
	public static void saveAs(Video video, String fileBaseName, String extn) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
		String timestamp = LocalDateTime.now().format(formatter);
		video.saveAs(
				Paths.get(PropertyReader.getProperty(IProperty.VEDIOLOCATION) + fileBaseName + timestamp + extn));
		video.delete();
	}

}

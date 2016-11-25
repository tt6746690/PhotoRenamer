
/**
 * 
 */
package photo_renamer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Reads and stores configuration variables
 * @author Eddie
 *
 */
public class ConfigReader{
	
	public static String IMAGE_FILE_STORAGE_PATH;
	public static String TAG_STORAGE_PATH;
	public static String HISTORY_LOG_PATH;
	public static String CROSS_ICON_PATH;

	public static int IMAGE_WIDTH;
	public static int IMAGE_HEIGHT;
	public static int TAG_PANEL_WIDTH;
	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
	public static int CROSS_DIMENSION;
	

	
	/**
	 * Reads configurations from config.properties
	 * @throws IOException
	 */
	public void getConfig() throws IOException{
					
		// initialize properties
		Properties prop = new Properties();
		String fileName = "config.properties";
		
		// load the data
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
		
		if (inputStream != null){
			prop.load(inputStream);
		} else {
			throw new FileNotFoundException(fileName + " not found");
		}
		
		// read the data
		IMAGE_FILE_STORAGE_PATH = prop.getProperty("IMAGE_FILE_PATH");
		TAG_STORAGE_PATH = prop.getProperty("TAG_STORAGE_PATH");
		HISTORY_LOG_PATH = prop.getProperty("HISTORY_LOG_PATH");
		CROSS_ICON_PATH = prop.getProperty("CROSS_ICON_PATH");

		WINDOW_WIDTH = Integer.parseInt(prop.getProperty("WINDOW_WIDTH"));
		WINDOW_HEIGHT = Integer.parseInt(prop.getProperty("WINDOW_HEIGHT"));
		IMAGE_WIDTH = Integer.parseInt(prop.getProperty("IMAGE_WIDTH"));
		IMAGE_HEIGHT = Integer.parseInt(prop.getProperty("IMAGE_HEIGHT"));
		TAG_PANEL_WIDTH = Integer.parseInt(prop.getProperty("TAG_PANEL_WIDTH"));
		CROSS_DIMENSION = Integer.parseInt(prop.getProperty("CROSS_DIMENSION"));
			
		inputStream.close();
	}
}

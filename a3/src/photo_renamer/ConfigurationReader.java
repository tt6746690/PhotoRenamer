
/**
 * 
 */
package photo_renamer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author Eddie
 *
 */
public class ConfigurationReader{
	public InputStream inputStream;
	public static int WINDOW_HEIGHT;
	public static int WINDOW_WIDTH;
	public static String IMAGE_FILE_STORAGE_PATH = "";
	public static String TAG_STORAGE_PATH = "";
	
	/**
	 * Reads the config file and get data from it
	 * @throws IOException
	 */
	public void getValues() throws IOException{
		try {
			
			// initialize properties
			Properties prop = new Properties();
			String fileName = "config.properties";
			
			// load the data
			inputStream = getClass().getClassLoader().getResourceAsStream(fileName);
			
			if (inputStream != null){
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException(fileName + " not found");
			}
			
			// read the data
			IMAGE_FILE_STORAGE_PATH = prop.getProperty("image_file_path");
			TAG_STORAGE_PATH = prop.getProperty("tag_storage_path");
			String sWidth = prop.getProperty("window_width");
			String sHeight = prop.getProperty("window_height");
			WINDOW_WIDTH = Integer.parseInt(sWidth);
			WINDOW_HEIGHT = Integer.parseInt(sHeight);
			
			
			} catch (Exception e){
				System.out.println("Exception: " + e);
			} finally {
				inputStream.close();
			}
		}
}

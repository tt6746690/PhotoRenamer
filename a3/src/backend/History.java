package backend;

import java.io.File;
import java.io.Serializable;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.util.ArrayList;


/**
 * file name history
 * @author markwang
 *
 */
public class History implements Serializable{

	String timestamp;
	File file;
  ArrayList<Tag> tags;

	/**
	 * new file history
	 * @param f
	 * 	file this history refers to
	 */
	public History(File f, ArrayList<Tag> tags){
		this.timestamp = this.getCurrentTime();
    this.tags = tags;
		this.file = f;
	}

	/**
	 * Gets this history timestamp
	 * @return
	 * 	timestamp for this history
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Sets this history timestamp
	 * @param timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	/**
	 * Gets file stored in this history
	 * @return
	 * 	file in history
	 */
	public File getFile() {
		return file;
	}

	/**
	 * Sets file stored in this history
	 * @param file
	 * 	file to be set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * Gets current time during instantiation
	 * @return
	 * 	current time in a simple format
	 */
	public String getCurrentTime(){
		Date date= Calendar.getInstance().getTime();
		DateFormat format = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		String formattedData = format.format(date);
		return formattedData;
	}
}

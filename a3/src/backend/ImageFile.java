/**
 *
 */
package backend;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.logging.ConsoleHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * image file
 * 
 * @author Eddie Ni & Mark Wang
 *
 */
public class ImageFile extends Observable implements Serializable {

	/** serialization version number */
	private static final long serialVersionUID = 8465453207532132848L;
	/** logger for logging renaming events */
	private static Logger logger = Logger.getLogger(ImageFile.class.getName());
	// /** logger handler for handling log outputs*/
	// private static Handler consoleHandler = new ConsoleHandler();

	/** image file's rename history */
	public ArrayList<History> history;
	/** image file's tag */
	public ArrayList<Tag> tags;
	/** image file with current tags */
	public File file;
	/** original path of file without any tags appended */
	public String originalPath;
	/** extension of image file, empty string if not exist */
	public String extension;
	/** original name of path without any tags appended */
	public String originalName;

	/**
	 * create a new ImageFile given path
	 * 
	 * @param path
	 *            path of the selected image file
	 */
	public ImageFile(String path) {
		this.file = new File(path);
		this.history = new ArrayList<History>();
		this.tags = new ArrayList<Tag>();

		String[] pair = ImageFile.decomposeExtension(this.file.getAbsolutePath());
		this.originalPath = pair[0];
		this.extension = pair[1];
		this.originalName = new File(originalPath).getName();

		// binds handler to logger
		logger.setLevel(Level.ALL);
		// consoleHandler.setLevel(Level.ALL);
		// logger.addHandler(consoleHandler);
	}

	/**
	 * Composes ImageFile's path including tags
	 * 
	 * @return image file's path name with tags appended
	 */
	public String composePath() {
		String str = this.originalPath;

		for (Tag tag : this.tags) {
			str += tag.getTagName();
		}
		if (this.extension != "") {
			str += "." + this.extension;
		}
		return str;
	}

	/**
	 * Utility method for decomposing path to path name without extension and
	 * extension
	 * 
	 * @param fullPath
	 *            full path name
	 * @return String[] containing path without extension and the extension
	 */
	public static String[] decomposeExtension(String fullPath) {

		int pos = fullPath.lastIndexOf(".");
		if (pos > 0) {
			String stripped = fullPath.substring(0, pos);
			String extension = fullPath.substring(pos + 1);
			return new String[] { stripped, extension };
		} else {
			return new String[] { fullPath, "" };
		}
	}

	/**
	 * Adds a Tag to ImageFile
	 * 
	 * @param tag
	 *            a new Tag
	 */
	public void addTag(Tag tag) {
		// add to tags arraylist
		if (!this.tags.contains(tag)) {
			this.tags.add(tag);
			this.updateState();
		}

	}

	/**
	 * Adds arraylist of tags to ImageFile
	 * 
	 * @param tags
	 *            tags to be added to image file
	 */
	public void addTag(ArrayList<Tag> tags) {
		this.tags.addAll(tags);
		this.updateState();
	}

	/**
	 * Removes a Tag from ImageFile
	 * 
	 * @param tag
	 *            a existing Tag to be removed 
	 */
	public void removeTag(Tag tag) {
		// remove tag from tags arraylist
		this.tags.remove(tag);
		this.updateState();
	}

	/**
	 * Renames ImageFile and updates ImageFile's state
	 */
	public void updateState() {
		File newImageFile = new File(this.composePath());
		Boolean success = this.file.renameTo(newImageFile);

		System.out.println(newImageFile);
		if (success) {
			this.logRenameHistory(this.file, newImageFile);
			System.out.println("nowhistory has tags: " + this.tags);
			this.recordFileHistory(newImageFile, this.tags);

			// set current file to this.file
			this.file = newImageFile;			
			

			// propagate state change to UI
			this.setChanged();
			this.notifyObservers();
		}
		this.file = newImageFile;
		System.out.println("updatestate is called!");
	}

	/**
	 * logs renaming event
	 * 
	 * @param prevFile
	 *            file renamed from
	 * @param nextFile
	 *            file renamed to
	 */
	public void logRenameHistory(File prevFile, File nextFile) {
		String message = prevFile.getName() + " ==> " + nextFile.getName();
		logger.log(Level.INFO, message);
	}

	/**
	 * updates the history log called every time there is a name change
	 */
	public void recordFileHistory(File file, ArrayList<Tag> tags) {
		this.history.add(new History(file, tags));
	}

	/**
	 * prints history of ImageFile
	 */
	public void printHistory() {
		String str = "history so far: \n";
		for (History hist : this.history) {
			str += this.history.indexOf(hist) + ": " + hist.getTimestamp() + ": " + hist.getFile().getName() + "\n";
		}
		System.out.println(str);
	}

	/**
	 * revert to a old state using history
	 * 
	 * @param hist
	 *            a previous history
	 */
	public void revertHistory(History hist) {
		if(!hist.tags.equals(this.tags)){
			// clears tags 
			this.tags.clear();
			// adds all tags
			this.addTag(hist.tags);
		}
	}

	/**
	 * Human readable representation of image file's current name
	 */
	@Override
	public String toString() {
		return "ImageFile Class: " + this.file.getName() + "; origName: " + this.originalName + " and tags: "
				+ this.tags;
	}

	public static void main(String[] args) {
		String path = "src/image1.jpg";
		ImageFile image = new ImageFile(path);
		System.out.println(image);

		Tag e = new Tag("elephant");
		Tag c = new Tag("carrot");
		Tag t = new Tag("tulip");

		// one by one
		image.addTag(e);
		image.addTag(c);
		image.addTag(t);
		image.removeTag(c);


//		ArrayList<Tag> taglist = new ArrayList<Tag>();
//		taglist.add(e);
//		taglist.add(c);
//		taglist.add(t);

		// or in an arraylist
//		 image.addTag(taglist);



		 image.printHistory();
		 
		 System.out.println("now revert to a previous history... ");
		 History hi = image.history.get(0);
		 System.out.println(hi);
		 System.out.println(hi.tags);

		 image.revertHistory(hi);
		
		
		 System.out.println("final: ");
		 System.out.println(image);
		 image.printHistory();

		image.file.renameTo(new File(path));
	}
}

package backend;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.IOException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * Manages store/retrieval of Tags
 * 
 * @author markwang
 *
 */

public class TagManager extends Observable{

	/**
	 * private constructor defeats instantiation
	 */
	private TagManager() {
	};

	/**
	 * Gets singleton TagManager instance
	 * 
	 * @return singleton TagManager instance
	 */
	public static TagManager getInstance() {
		return TagManagerHolder.INSTANCE;
	}

	/**
	 * Holder class that instantiates TagManager when getInstance is called
	 * 
	 * @author markwang
	 *
	 */
	private static class TagManagerHolder {
		private static final TagManager INSTANCE = new TagManager();
	}

	/** tags stores tagName to Tag mapping */
	public Map<String, Tag> tags;

	/**
	 * Creates a new TagManager, with data stored in the filepath, if exists
	 *
	 * @param filepath
	 *            path to file storing tags
	 * @return
	 */

	public void initialize(String filepath) throws IOException, ClassNotFoundException {
		this.tags = new HashMap<String, Tag>();

		// retrieves persistent data
		File f = new File(filepath);
		if (f.exists() && f.length() != 0) {
			this.readFromFile(filepath);
		}
		{
			f.createNewFile();
		}

	}

	/**
	 * Reads file at filePath
	 * 
	 * @param filePath
	 *            filePath to the file to be read
	 */
	public void readFromFile(String filePath) throws IOException, FileNotFoundException, ClassNotFoundException {
		InputStream fileIn = new FileInputStream(filePath);
		InputStream bufferIn = new BufferedInputStream(fileIn);
		ObjectInput in = new ObjectInputStream(bufferIn);

		// deserialize input
		this.tags = (Map<String, Tag>) in.readObject();
		in.close();

	}

	/**
	 * Writes Tags to file at filePath
	 * 
	 * @param filePath
	 *            filePath to write Tags to
	 */
	public void saveToFile(String filePath) {

		try {
			OutputStream fileOut = new FileOutputStream(filePath);
			OutputStream bufferOut = new BufferedOutputStream(fileOut);
			ObjectOutput output = new ObjectOutputStream(bufferOut);

			// serialize the Map
			output.writeObject(this.tags);
			output.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds Tag with tagName to tags HashMap
	 * 
	 * @param tagName
	 *            name of the Tag to be added
	 */
	public void addTag(String tagName) {
		Tag t = new Tag(tagName);
		this.tags.put(t.getName(), t);
		this.updateState();
	}

	/**
	 * Delete Tag with tagName from tags HashMap, if exists
	 * 
	 * @param tagName
	 */
	public void deleteTag(String tagName) {
		if (this.tags.containsKey(tagName)) {
			this.tags.remove(tagName);
			this.updateState();
		}
	}
	
	public void updateState(){
		setChanged();
		notifyObservers();
	}

//	/**
//	 * Returns iterator over all tags
//	 */
//	@Override
//	public Iterator<Tag> iterator() {
//		return new TagIterator();
//	}
//	
//	/**
//	 * nested Tag Iterator class
//	 * @author markwang
//	 *
//	 */
//	private class TagIterator implements Iterator<Tag> {
//
//		/** The index of the next Tag to return. */
//		private int current = 0;
//
//		/**
//		 * Returns whether there is another Tag to return.
//		 * 
//		 * @return whether there is another Tag to return.
//		 */
//		@Override
//		public boolean hasNext() {
//			return current < tags.size();
//		}
//
//		/**
//		 * Returns the next Tag.
//		 * 
//		 * @return the next Tag.
//		 */
//		@Override
//		public Tag next() {
//			Tag t;
//
//			try {
//				t = tags.get(current);
//			} catch (IndexOutOfBoundsException e) {
//				throw new NoSuchElementException();
//			}
//			current += 1;
//			return t;
//		}
//
//	}

	@Override
	public String toString() {
		String res = "";
		for (Tag t : this.tags.values()) {
			res += t.toString() + "\n";
		}
		return res;
	}

//	public static void main(String[] args) throws ClassNotFoundException, IOException {
//
//		String path = "src/storage/tags.ser";
//		TagManager tm = TagManager.getInstance();
//		tm.initialize(path);
//
//		System.out.println("TagManager initialized with tags.ser: which contains ");
//		System.out.println(tm.toString());
//
//		System.out.println("Adds tag cat and dog to TagManager...");
//		tm.addTag("cat");
//		tm.addTag("dog");
//		System.out.println(tm.toString());
//		System.out.println("remove cat tag");
//		tm.deleteTag("cat");
//
//		tm.saveToFile(path);
//
//		TagManager tm2 = TagManager.getInstance();
//		tm.initialize(path);
//		System.out.println("TagManager reads previous tags.ser and outputs");
//		System.out.println(tm2.toString());
//
//		System.out.println("Test if the two instance are the same");
//		System.out.println(tm == tm2); // same memory address -> same instance
//		System.out.println(tm.equals(tm2)); // same data
//
//	}

}

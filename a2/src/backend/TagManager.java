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
import java.util.Map;

/**
 * Manages store/retrieval of Tags
 * @author markwang
 *
 */

public class TagManager {

	/** tags stores tagName to Tag mapping*/
	private Map<String, Tag> tags;

	/**
	 * Creates a new TagManager, with data stored
	 * in the filepath, if exists
	 *
	 * @param filepath
	 * 	path to file storing tags
	 */
	public TagManager(String filepath) throws IOException, ClassNotFoundException {
		this.tags = new HashMap<String, Tag>();

		// retrieves persistent data
		File f = new File(filepath);
		if(f.exists()){
			this.readFromFile(filepath);
		}{
			f.createNewFile();
		}

	}


	/**
	 * Reads file at filePath
	 * @param filePath
	 * 	filePath to the file to be read
	 */
	public void readFromFile(String filePath) throws IOException, FileNotFoundException, ClassNotFoundException{
		InputStream fileIn = new FileInputStream(filePath);
		InputStream bufferIn = new BufferedInputStream(fileIn);
		ObjectInput in = new ObjectInputStream(bufferIn);

		// deserialize input
		this.tags = (Map<String, Tag>) in.readObject();
		in.close();

	}


	/**
	 * Writes Tags to file at filePath
	 * @param filePath
	 * 	filePath to write Tags to
	 */
	public void saveToFile(String filePath) throws IOException, FileNotFoundException{

		OutputStream fileOut = new FileOutputStream(filePath);
        OutputStream bufferOut = new BufferedOutputStream(fileOut);
        ObjectOutput output = new ObjectOutputStream(bufferOut);

        // serialize the Map
        output.writeObject(this.tags);
        output.close();
	}


	/**
	 * Adds Tag with tagName to tags HashMap
	 * @param tagName
	 * 	name of the Tag to be added
	 */
	public void addTag(String tagName){
		Tag t = new Tag(tagName);
		this.tags.put(t.getName(), t);
	}

	/**
	 * Delete Tag with tagName from tags HashMap, if exists
	 * @param tagName
	 */
	public void deleteTag(String tagName){
		if(this.tags.containsKey(tagName)){
			this.tags.remove(tagName);
		}
	}


	@Override
	public String toString(){
		String res = "";
		for(Tag t: this.tags.values()){
			res += t.toString() + "\n";
		}
		return res;
	}


	// 
	// public static void main(String[] args) throws ClassNotFoundException, IOException {
	//
	// 	String path = "src/storage/tags.ser";
	// 	TagManager tm = new TagManager(path);
  //
	// 	System.out.println("TagManager initialized with tags.ser: which contains ");
	// 	System.out.println(tm.toString());
	//
	// 	System.out.println("Adds tag cat and dog to TagManager...");
	// 	tm.addTag("cat");
	// 	tm.addTag("dog");
	// 	System.out.println(tm.toString());
	// 	System.out.println("remove cat tag");
	// 	tm.deleteTag("cat");
	//
	// 	tm.saveToFile(path);
	//
	// 	TagManager tm2 = new TagManager(path);
	// 	System.out.println("TagManager reads previous tags.ser and outputs");
	// 	System.out.println(tm2.toString());
	//
	//
	// }

}

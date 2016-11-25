/**
 *
 */
package backend;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 * Manages store/retrieval of ImageFiles
 * @author Eddie Ni
 *
 */
public class ImageFileManager {


    /** A store of imageFile modified, mapping image name to ImageFile*/
		private Map<String, ImageFile> imageFiles;

	/**
	 * private constructor defeats instantiation 
	 */
	private ImageFileManager(){};
	
	/**
	 * Gets singleton instance of ImageFileManager
	 * @return 
	 * 	a singleton instance of ImageFileManager 
	 */
	public static ImageFileManager getInstance(){
		return ImageFileManagerHolder.INSTANCE;
	}
	
	/**
	 * Holder class that instantiates ImageFileManager when getInstance is called
	 * @author markwang
	 *
	 */
	private static class ImageFileManagerHolder{
		public static final ImageFileManager INSTANCE = new ImageFileManager();
	}
	
	
	public void initialize(String storagePath) throws IOException, ClassNotFoundException {
		this.imageFiles = new HashMap<String, ImageFile>();
		
		// retrieves persistent data
		File f = new File(storagePath);
		if(f.exists() && f.length() != 0){
			this.readFromFile(storagePath);
		} else {
			f.createNewFile();
		}
	}
		

	/**
	 * Reads file at filePath
	 * @throws IOException, ClassNotFoundException, FileNotFoundException
	 * @param storagePath
	 * 	filePath to the file to be read
	 */
	public void readFromFile(String storagePath) throws IOException, FileNotFoundException, ClassNotFoundException{
		InputStream fileIn = new FileInputStream(storagePath);
		InputStream bufferIn = new BufferedInputStream(fileIn);
		ObjectInput in = new ObjectInputStream(bufferIn);

		// deserialize input
		this.imageFiles = (Map<String, ImageFile>) in.readObject();
		in.close();
	}

	/**
	 * Writes imageFiles to file at filePath
	 * @throws IOException, FileNotFoundException
	 * @param storagePath
	 * 	file path to write imageFiles to
	 */
	public void saveToFile(String storagePath){
		
		try {
			 OutputStream fileOut = new FileOutputStream(storagePath);
		      OutputStream bufferOut = new BufferedOutputStream(fileOut);
		      ObjectOutput output = new ObjectOutputStream(bufferOut);
			   
		      // serialize the Map
		      output.writeObject(this.imageFiles);
		      output.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * creates new ImageFile and put it in the map
	 * check if it already exist
	 * @param path
	 * 	file path
	 * @return ImageFile
	 */
	public ImageFile fetchImageFile(String path){
		System.out.println("fetchImageFile here!!!!!!!!!");
//		ImageFile image = new ImageFile(path);
		
		String origPath = ImageFileManager.getFilePathWithoutTag(path);
	
		if(this.imageFiles.containsKey(origPath)){
			System.out.println("contains key already.. fetch existing ones..");
			return imageFiles.get(origPath);
		} else {
			ImageFile imageFile = new ImageFile(origPath);
			imageFiles.put(origPath, imageFile);
			return imageFile;
		}
	}
	
	
	public static String getFilePathWithoutTag(String path){
		File f = new File(path);
		String fullPath = f.getAbsolutePath();
		
		String fileName = f.getName();
		String dir = fullPath.substring(0, fullPath.lastIndexOf(File.separator));
		String ext = fullPath.substring(fullPath.lastIndexOf(".") + 1, fullPath.length());
		
		// substring fileName
		int dotIndex = fileName.lastIndexOf('.');
		int atIndex = fileName.indexOf('@');
		int position = dotIndex;
		if(atIndex != -1){
			position = Math.min(dotIndex, atIndex);
		}
		fileName = fileName.substring(0, position);
		

		//concat back... 
		return dir + File.separator + fileName + "." + ext;
	}

	/**
	 * list only .png and .jpg images under directoryPath
	 * @param directoryPath
	 * 	directory path
	 */
	public File[] listImageFiles(String directoryPath){
		File directory = new File(directoryPath);
		File[] listOfFiles = directory.listFiles(new FilenameFilter(){
			@Override
			public boolean accept(File dir, String name){
				for (String ext : new String[]{"jpg", "jpeg", "png"}) {
	                if (name.endsWith("." + ext)) {
	                    return true;
	                }
	            }
	            return false;
			}
		});

	    return listOfFiles;
	}


	/**
	 * Outputs a list of imageFile changed so far
	 */
	@Override
	public String toString(){
		String res = "";
		for(ImageFile image: this.imageFiles.values()){
			res += image.toString() + "\n";
		}
		return res;
	}
   
	 public static void main(String[] args) throws IOException, FileNotFoundException, ClassNotFoundException {
  
	 		String storagePath = "src/storage/imageFiles.ser";
       String image1path = "src/image1.jpg";
       String image2path = "src/image2.jpg";
  
       ImageFileManager im = ImageFileManager.getInstance();
       im.initialize(storagePath);
       
       System.out.println("Just reading from imageFile.ser gives: ");
       System.out.println(im);
       
       
//       System.out.println(ImageFileManager.getFilePathWithoutTag(storagePath));
  
//   	  // now displays a list of image files
//       for(File f: im.listImageFiles("src/")){
//     	  System.out.println(f.getName());
//       }
//  
//       System.out.println("Now do something to image1 and image2..");
//       ImageFile image1 = im.fetchImageFile(image1path);
//       ImageFile image2 = im.fetchImageFile(image2path);
//  
//       Tag t= new Tag("tulip");
//       Tag c= new Tag("carrot");
//  
//       try{
//         image1.addTag(t);
//         image1.addTag(c);
//         image2.addTag(c);
//         image1.revertHistory(image1.history.get(1));
//         image2.printHistory();
//  
//         System.out.println("ImageFileManager now contains: ");
//         System.out.println(im);
//  
//         im.saveToFile(storagePath);
//  
//         im.readFromFile(storagePath);
//         System.out.println("save to and later read from file, ImageFileManager now contains: ");
//         System.out.println(im);
//       } catch(Exception e){
//         e.printStackTrace();
//       } finally {
//
//         image1.file.renameTo(new File(image1path));
//         image2.file.renameTo(new File(image2path));
//       }
//  
  
  
  
	 }

}

package photo_renamer;

import java.util.ArrayList;
import java.util.Arrays;


import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;
 
/**
 * Filter that filters for image files
 * @author markwang
 *
 */
public class ImageFilter extends FileFilter {
	
	public static final ArrayList<String> ALLOWED_IMAGE_EXTENSIONS 
		= new ArrayList<String>(Arrays.asList("tiff", "tif", "gif", "jpeg", "jpg", "jpeg", "png"));
 
    
	@Override 
	/** Accepts only directories and some commonly used image files
	 *
	 * @param f	
	 * 	input File to be filtered
	 * @return
	 * 	whether filters out f
	 */
    public boolean accept(File f) {
        if (f.isDirectory()) {
            return true;
        }
 
        String extension = this.getExtension(f);
        if(extension != null){
        	if(ALLOWED_IMAGE_EXTENSIONS.contains(extension)){
        		return true;
        	}
        } 
        return false;
        
     }
 
    /**
     * Description of ImageFilter
     */
	@Override
    public String getDescription() {
        return "Filters only accepts GIF, JPG, JPEG, TIFF, TIF, PNG files";
    }
	
	/**
	 * returns extension of File
	 * 
	 * @param f
	 * 	File to get the extension of 
	 * @return
	 * 	extension of f
	 */
	public static String getExtension(File f) {
        String ext = null;
	    String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 &&  i < s.length() - 1) {
            ext = s.substring(i+1).toLowerCase();
        }
        return ext;
	    }
}
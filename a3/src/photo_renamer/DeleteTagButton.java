package photo_renamer;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import backend.Tag;

/**
 * Button that deletes a tag from current existing pool of tags
 * @author markwang
 *
 */
public class DeleteTagButton extends JButton implements ActionListener{
	

	/**
	 * Creates a new button that deletes tag
	 * @param tagName
	 * 	tag's name to be deleted
	 */
	public DeleteTagButton(String tagName) {
		this.setName(tagName);
		
		this.setIcon(this.resizeIcon(new ImageIcon(ConfigReader.CROSS_ICON_PATH)));
		this.addActionListener(this);
	}

	/**
	 * prompts tagManager to delete a tag 
	 * @param e 
	 *	Button click event 	
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		PhotoRenamer.appLogger.log(Level.INFO, "delete tag button clicked; deleting " + this.getName());
		PhotoRenamer.tagManager.deleteTag(this.getName());
	}
	
	/**
	 * Resizes cross icon for display
	 * 
	 * @param icon
	 *   cross icon
	 * @return Resized Image Icon
	 */
	public ImageIcon resizeIcon(ImageIcon icon) {
		Image image = icon.getImage();
		Image newImg = image.getScaledInstance(-1, ConfigReader.CROSS_DIMENSION, java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(newImg);
	}
}

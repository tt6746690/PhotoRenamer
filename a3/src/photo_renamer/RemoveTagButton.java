package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JButton;

import backend.Tag;

/**
 * Button that removes a tag from image file
 * @author markwang
 *
 */
public class RemoveTagButton extends JButton implements ActionListener {

	/**
	 * Removes a tag from ImageFile
	 * @param tagName
	 */
	public RemoveTagButton(String tagName) {
		super(tagName);
		this.addActionListener(this);
	}

	/**
	 * Removes clicked tag from ImageFile
	 * @param e 
	 * 	button click event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		PhotoRenamer.appLogger.log(Level.INFO, "remove tag button clicked; removing " + this.getText());

		Tag t = new Tag(this.getText());
		PhotoRenamer.imageFile.removeTag(t);
	}

}

package photo_renamer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JButton;

import backend.Tag;

/**
 * Button that adds Tag to ImageFile
 * @author markwang
 *
 */
public class AddTagButton extends JButton implements ActionListener {

	/**
	 * Creates a new AddTagButton
	 * @param tagName
	 */
	public AddTagButton(String tagName) {
		super(tagName);
		this.addActionListener(this);
	}

	/**
	 * Adds clicked tag to ImageFile
	 * @param e 
	 * 	button click event
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		PhotoRenamer.appLogger.log(Level.INFO, "add tag button clicked; adding " + this.getText());

		Tag newTag = new Tag(this.getText());
		PhotoRenamer.imageFile.addTag(newTag);
	}

}

package photo_renamer;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JButton;

import backend.Tag;

public class AddTagButton extends JButton implements ActionListener {

	public AddTagButton(String label) {
		super(label);
		this.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		PhotoRenamer.appLogger.log(Level.INFO, "add tag button clicked; adding " + this.getText());

		Tag newTag = new Tag(this.getText());
		PhotoRenamer.imageFile.addTag(newTag);
	}

}

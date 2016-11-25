package photo_renamer;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;

import javax.swing.JTextField;


/**
 * Text field that creates new tag to current pool of tags
 * @author markwang
 *
 */
public class CreateTagTextField extends JTextField implements ActionListener{
	
	/**
	 * Creates new text field 
	 */
	public CreateTagTextField(){
		this.addActionListener(this);
		this.setColumns(10);
	}

	/**
	 * prompts tagManager to create a new tag
	 * @param e
	 * 	Text field enter event 
	 */
	@Override
	public void actionPerformed(ActionEvent e){
		PhotoRenamer.appLogger.log(Level.INFO, "TextField entered with text: " + this.getText());
		PhotoRenamer.tagManager.createTag(this.getText());
		
	}

}
